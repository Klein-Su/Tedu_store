//當頁面加載完成時
$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//顯示收貨地址列表
	showList();
	
	//修改商品數量視窗 初始化
	$("#dialog-updateAddress-form").dialog({
		resizable: false, //是否可調整視窗大小
		autoOpen: false, //是否自動開啟
	    height: 480,
	    width: 670,
	    modal: true,
	    position: {
	    	my: "top",
	    	at: "top",
	    	of: window
	    },
	    buttons: [
	    	{
		    	text: "確認",
		        "class": 'updateButtonClass',
		        click: function() {
		        	//檢查要修改地址的資料
		        	updateConfirm($("#updateId").val());
		        }
	    	},
	    	{
	            text: "取消",
	            "class": 'cancelButtonClass',
	            click: function() {
	            	$("#dialog-updateAddress-form").dialog( "close" );
	            }
	        }
	    ],
	    open: function() {
	    	// open code here
        },
	    close: function() {
	        //$("#dialog-updateAddress-form")[0].reset();
	        $(".ui-state-error").removeClass( "ui-state-error" );
	        $(".error-message").hide();
	    }
	});
	
	//初始化地址
	$("#twzipcode").twzipcode({
		"zipcodeIntoDistrict": false, //是否將郵遞區號加入在鄉鎮市區
		"css": [
			"form-control", //縣市
			"form-control", //鄉鎮市區
			"form-control" //郵遞區號
		],
		"readonly": true, //郵遞區號只允許讀取，不允許修改
		"countyName": "city", //修改縣市的select name 名稱
		"districtName": "district", //修改縣市的select name 名稱
		"zipcodeName": "zip" //修改郵遞區號的select name 名稱
	});
	
	//在選擇縣市的物件添加錯誤提示
	$("div[data-role='county']")
		.append('<div id="twzipcode-error" class="error-message" style="color: red; display: none;">請選擇地區</div>');
	
})

//顯示收貨地址列表
function showList() {
	//加載當前用戶的收貨地址列表
	var url = "../address/list";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			//清空收貨地址列表
			$("#list").empty();
				
			var list = json.data;
			for (var i=0; i<list.length; i++){
				console.log(list[i].name + ", " + list[i].district);
				//建立要添加的收貨地址物件
				var addressList = '<tr>'
						+ '<td>#{tag}</td>'
						+ '<td>#{name}</td>'
						+ '<td>#{city}/#{district}/#{address}</td>'
						+ '<td>#{phone}</td>'
						+ '<td><a class="btn btn-xs btn-info" '
							+ 'href="javascript:getAddress(#{id})"><span class="fa fa-edit"></span> 修改</a></td>'
						+ '<td><a class="btn btn-xs add-del btn-info" '
							+ 'href="javascript:deleteConfirm(#{id})"><span class="fa fa-trash-o"></span> 刪除</a></td>'
						+ '<td><a #{is_default} class="btn btn-xs add-def btn-default" '
							+ 'href="javascript:setDefault(#{id})">設為預設</a></td>'
					+ '</tr>';
				//利用佔位符，再用正確的數據去取代佔位符
				addressList = addressList.replace(/#{id}/g, list[i].id); //以正則表達式，/g：表全局替換
				addressList = addressList.replace("#{tag}", list[i].tag);
				addressList = addressList.replace("#{name}", list[i].name);
				addressList = addressList.replace("#{city}", list[i].city);
				addressList = addressList.replace("#{district}", list[i].district);
				addressList = addressList.replace("#{address}", list[i].address);
				addressList = addressList.replace("#{phone}", list[i].phone);
				if (list[i].isDefault == 1) {
					addressList = addressList.replace("#{is_default}", 'style="display: none;"');
				} else {
					addressList = addressList.replace("#{is_default}", '');
				}
				
				//添加到<tbody id="list">裡面
				$("#list").append(addressList);
			}
		}
	});
}

//獲取收貨地址資料
function getAddress(id) {
	var url = "../address/get_by_id/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				var data = json.data;
				//設置獲取到的收貨地址資料
				$("#updateId").val(id);
				$("#name").val(data.name); //收貨人姓名
				//傳入縣市、鄉鎮市區、郵遞區號
				$("#twzipcode").twzipcode("set", {
				    "county": data.city,
				    "district": data.district,
				    "zipcode": data.zip
				});
				$("#address").val(data.address); //詳細地址
				$("#phone").val(data.phone); //手機
				$("#tel").val(data.tel); //電話
				$("#tag").val(data.tag); //地址類型
				
				//啟動修改數量視窗
				$("#dialog-updateAddress-form").dialog("open");
			} else {
				//失敗
				swal("獲取失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入資訊已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}
	
//設置收貨地址為默認
function setDefault(id) {
	var url = "../address/default/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				//成功
				showList();
			} else {
				//失敗
				swal("設置失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入資訊已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}

//修改地址確認
function updateConfirm(id) {
	var name = $("#name").val(); //收貨人姓名
	var city = $("select[name='city']").val(); //縣市
	var district = $("select[name='district']").val(); //鄉鎮市區
	var zip = $("select[name='zip']").val(); //郵遞區號
	var address = $("#address").val(); //完整地址
	var phone = $("#phone").val(); //手機
	var tel = $("#tel").val(); //電話(市話)
	var valid = true; //修改收貨地址資料，true：符合、false：不符合
	
	//判斷收貨人姓名
	if(name == "") {
		$("#name").addClass("ui-state-error");
		$("#name-error").show();
		valid = valid && false;
	} else {
		$("#name").removeClass("ui-state-error");
		$("#name-error").hide();
	}
	//判斷地區資料
	if(city == "" || district == "" || zip == "") {
		$("select[name='city']").addClass("ui-state-error");
		$("#twzipcode-error").show(); 
		valid = valid && false;
	} else {
		$("select[name='city']").removeClass("ui-state-error");
		$("#twzipcode-error").hide();
	}
	//判斷完整地址資料
	if(address == "") { 
		$("#address").addClass("ui-state-error");
		$("#address-error").show(); 
		valid = valid && false;
	} else {
		$("#address").removeClass("ui-state-error");
		$("#address-error").hide();
	}
	//判斷手機號碼
	if(phone == "") { 
		$("#phone").addClass("ui-state-error");
		$("#phone-error").show(); 
		valid = valid && false;
	} else {
		//判斷phone是否符合格式，10位的數字組合
		if(!/^[0][9][0-9]{8}$/.test(phone)) {
			$("#phone").addClass("ui-state-error");
			$("#phone-error").html("手機號碼格式不符合");
			$("#phone-error").show();
			valid = valid && false;
		} else {
			$("#phone").removeClass("ui-state-error");
			$("#phone-error").hide();
		}
	}
	//判斷電話
	if(tel != "") { 
		//判斷phone是否符合格式，10位以內的數字組合
		if(!/^\d{9,10}$/.test(tel)) {
			$("#tel").addClass("ui-state-error");
			$("#tel-error").html("電話號碼格式不符合");
			$("#tel-error").show(); 
			valid = valid && false;
		} else {
			$("#tel").removeClass("ui-state-error");
			$("#tel-error").hide();
		}
	} 
	
	if(valid){ //判斷修改收貨地址是否符合資格
		swal({
			title: "修改收貨地址",
			text: "確定修改收貨地址？",
			type: "warning",
			showCancelButton: true,
			confirmButtonClass: "btn-info",
			confirmButtonText: "確定",
			cancelButtonClass: "btn-danger",
			cancelButtonText: "取消",
			closeOnConfirm: false,
			closeOnCancel: true
		},
		function(isConfirm) {
			if (isConfirm) {
				//修改收貨地址
				updateAddress(id);
			} else {
			    //swal("取消", "取消修改收貨地址！", "error");
			}
		});
	}
}

//修改地址資料
function updateAddress(id) {
	//將請求提到到哪裡
	//當前位置：web/addAddress.html
	//目標位置：address/create
	var url = "../address/update";
	//請求參數
	var data = $("#form-updateAddress").serialize();
	console.log("修改收貨地址參數："+data);
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200){
				swal("修改成功", "修改收貨地址成功！", "success");
				//重新載入收貨地址列表
				showList();
				$("#dialog-updateAddress-form").dialog( "close" );
			} else {
				swal("修改失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown) {
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}

//刪除地址確認
function deleteConfirm(id) {
	swal({
		title: "刪除收貨地址",
		text: "確定將該收貨地址刪除？",
		type: "warning",
		showCancelButton: true,
		confirmButtonClass: "btn-primary",
		confirmButtonText: "確定",
		cancelButtonClass: "btn-danger",
		cancelButtonText: "取消",
		closeOnConfirm: false,
		closeOnCancel: true
	},
	function(isConfirm) {
		if (isConfirm) {
			//刪除收貨地址
			deleteById(id);
		} else {
		    //swal("取消刪除", "刪除收貨地址已取消！", "error");
		}
	});
}

//刪除收貨地址
function deleteById(id) {
	var url = "../address/delete/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				//成功
				showList();
				swal("刪除成功", "該收貨地址已刪除" , "success");
			} else {
				//失敗
				swal("刪除失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}