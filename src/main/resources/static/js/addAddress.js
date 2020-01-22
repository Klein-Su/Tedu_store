//當頁面加載完成時
$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
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
		.append('<div id="twzipcode-error" style="color: red; display: none;">請選擇地區</div>');
	
	//重置按鈕
	$("#btn-reset").click(function(){
		$("#form-address")[0].reset();
		//清空並重置鄉鎮市區選項
		$("select[name='district']").empty();
		$("select[name='district']").append("<option value>鄉鎮市區</option>");
		$("#name-error").hide();
		$("#twzipcode-error").hide();
		$("#address-error").hide();
		$("#phone-error").hide();
		$("#tel-error").hide();
	});  
	
	//提交按鈕綁定點擊事件	
	$("#btn-submit").click(function(){
		//檢查收貨地址資料
		checkAddress();
	});
});

//檢查收貨地址資料
function checkAddress() {
	var name = $("#name").val(); //收貨人姓名
	var city = $("select[name='city']").val(); //縣市
	var district = $("select[name='district']").val(); //鄉鎮市區
	var zip = $("select[name='zip']").val(); //郵遞區號
	var address = $("#address").val(); //完整地址
	var phone = $("#phone").val(); //手機
	var tel = $("#tel").val(); //電話(市話)
	var valid = true; //新增收貨地址資料，true：符合、false：不符合
	
	//判斷收貨人姓名
	if(name == "") { 
		$("#name-error").show(); 
		valid = valid && false;
	} else {
		$("#name-error").hide();
	}
	//判斷地區資料
	if(city == "" || district == "" || zip == "") { 
		$("#twzipcode-error").show(); 
		valid = valid && false;
	} else {
		$("#twzipcode-error").hide();
	}
	//判斷完整地址資料
	if(address == "") { 
		$("#address-error").show(); 
		valid = valid && false;
	} else {
		$("#address-error").hide();
	}
	//判斷手機號碼
	if(phone == "") { 
		$("#phone-error").show(); 
		valid = valid && false;
	} else {
		//判斷phone是否符合格式，10位的數字組合
		if(!/^[0][9][0-9]{8}$/.test(phone)) {
			$("#phone-error").html("手機號碼格式不符合");
			$("#phone-error").show();
			valid = valid && false;
		} else {
			$("#phone-error").hide();
		}
	}
	//判斷電話
	if(tel != "") { 
		//判斷phone是否符合格式，10位以內的數字組合
		if(!/^\d{9,10}$/.test(tel)) {
			$("#tel-error").html("電話號碼格式不符合");
			$("#tel-error").show(); 
			valid = valid && false;
		} else {
			$("#tel-error").hide();
		}
	} 
	if(valid){ //判斷修改個人資料是否符合資格
		swal({
			title: "新增收貨地址",
			text: "確定將資料送出並新增？",
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
				//新增收貨地址
				addAddress();
			} else {
			    //swal("取消", "取消新增收貨地址！", "error");
			}
		});
	}
}

//保存收貨地址
function addAddress() {
	//將請求提到到哪裡
	//當前位置：web/addAddress.html
	//目標位置：address/create
	var url = "../address/create";
	//請求參數
	var data = $("#form-address").serialize();
	console.log("收貨地址參數："+data);
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200){
				//alert("保存成功！");
				swal("新增成功", "新增收貨地址成功！", "success");
			} else {
				swal("新增失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown) {
			console.log("xhr.status=" + xhr.status);
			alert("您的登入信息已經過期！請重新登入！");
			location.href = "login.html";
		}
	});
}