///*按加号数量增*/
//function addNum(rid) {
//	var n = parseInt($("#goodsCount"+rid).val());
//
//	$("#goodsCount"+rid).val(n + 1);
//	calcRow(rid);
//}
///*按减号数量减*/
//function reduceNum(rid) {
//	var n = parseInt($("#goodsCount"+rid).val());
//	if (n == 0)
//		return;
//	$("#goodsCount"+rid).val(n - 1);
//	calcRow(rid);
//}
///*全选全不选*/
//function checkall(ckbtn) {
//	$(".ckitem").prop("checked", $(ckbtn).prop("checked"));
//	//calcTotal();
//}

//$(function() {
//	//单选一个也得算价格
//	$(".ckitem").click(function() {
//		//calcTotal();
//	})
//	//开始时计算价格
//	//calcTotal()
//
//})

////计算单行小计价格的方法
//function calcRow(rid) {
//	//取单价
//	var vprice = parseFloat($("#goodsPrice"+rid).html());
//	//取数量
//	var vnum = parseFloat($("#goodsCount"+rid).val());
//	//小计金额
//	var vtotal = vprice * vnum;
//	//赋值
//	$("#goodsCast"+rid).html("¥" + vtotal);
//}

//计算总价格的方法
//function calcTotal() {
//	//选中商品的数量
//	var vselectCount = 0;
//	//选中商品的总价
//	var vselectTotal = 0;
//
//	//循环遍历所有tr
//	for (var i = 0; i < $(".cart-body tr").length; i++) {
//		//计算每个商品的价格小计开始
//		//取出1行
//		var $tr = $($(".cart-body tr")[i]);
//		//取单价
//		var vprice = parseFloat($tr.children(":eq(3)").children("span").html());
//		//取数量
//		var vnum = parseFloat($tr.children(":eq(4)").children(".num-text").val());
//		//小计金额
//		var vtotal = vprice * vnum;
//		//赋值
//		$tr.children(":eq(5)").children("span").html("¥" + vtotal);
//		//计算每个商品的价格小计结束
//
//		//检查是否选中
//		if ($tr.children(":eq(0)").children(".ckitem").prop("checked")) {
//			//计数
//			vselectCount++;
//			//计总价
//			vselectTotal += vtotal;
//		}
//		//将选中的数量和价格赋值
//		$("#selectTotal").html(vselectTotal);
//		$("#selectCount").html(vselectCount);
//	}
//
//}

$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//顯示購物車列表
	showCartList();
	
	//修改商品數量視窗 初始化
	$("#dialog-updateCount-form").dialog({
		autoOpen: false,
	    height: 230,
	    width: 400,
	    modal: true,
	    buttons: [
	    	{
		    	text: "確認",
		        "class": 'updateButtonClass',
		        click: function() {
		        	checkUpdateCount(); //檢查數量，無誤就更新
		        }
	    	},
	    	{
	            text: "取消",
	            "class": 'cancelButtonClass',
	            click: function() {
	            	$("#dialog-updateCount-form").dialog( "close" );
	            }
	        }
	    ],
	    open: function() {
	    	// open code here
        },
	    close: function() {
	        //$("#updateCount-form")[0].reset();
	        $("#updateCount").removeClass( "ui-state-error" );
	    }
	});
	
	//結算按鈕綁定事件
	$("#submitBtn").click(function(){
		console.log("結算");
		//獲取所有cartId物件
		var cartId = $("#cart-list tr").find("input[name='cart_id']");
		//購物車中選取的數量
		var selectNum = 0;
		//偏歷 cartId
		cartId.each(function(i){
			if($(this).prop("checked")){
				selectNum++;
			}
		});
		console.log("selectNum: "+selectNum);
		//判斷選取的數量大於0，才能進行結算動作
		if(!selectNum <= 0 ){
			$("#cart-form").submit();
		} else {
			swal("異常通知", "您尚未選取商品！請選取商品再進行結算", "error");
		}
	});
});

//顯示購物車列表
function showCartList() {
	var url = "../cart/list";
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			var list = json.data;
			console.log("list.length=" + list.length);
			var sum = 0;
			//先清空購物車列表
			$("#cart-list").empty();
			
			for (var i=0; i<list.length; i++) {
				//創建項目
				var html = '<tr>'
						+ '<td>'
							+ '<input type="checkbox" name="cart_id" value="#{id}" class="ckitem" />'
						+ '</td>'
						+ '<td><img src="..#{image}collect.png" width="110" /></td>'
						+ '<td><span id="goodsTitle#{id}">#{title}</span></td>'
						+ '<td>$<span id="goodsPrice#{id}">#{price}</span></td>'
						+ '<td>'
							+ '<input type="button" value="-" class="num-btn" onclick="reduceCount(#{id})" />'
							+ '<input id="goodsCount#{id}" title="點擊修改數量" style="cursor: pointer;" readonly="readonly" type="text" size="3" min="1" max="999" class="num-text" value="#{count}">'
							+ '<input class="num-btn" type="button" value="+" onclick="addCount(#{id})" />'
						+ '</td>'
						+ '<td>$<span id="goodsTotalPrice#{id}">#{totalPrice}</span></td>'
						+ '<td><input type="button" onclick="delCartItem(this, #{id})" class="cart-del btn btn-danger btn-xs" value="刪除"/></td>'
					+ '</tr>';
				
				html = html.replace(/#{id}/g, list[i].id);
				html = html.replace(/#{image}/g, list[i].image);
				html = html.replace(/#{title}/g, list[i].title);
				html = html.replace(/#{price}/g, FormatNumber(Math.round(list[i].newPrice)));
				html = html.replace(/#{count}/g, list[i].count);
				html = html.replace(/#{totalPrice}/g, FormatNumber(Math.round(list[i].newPrice * list[i].count)));
				
				$("#cart-list").append(html);
			}
			
			//綁定ckitem點擊事件
			$(".ckitem").click(function(e){
				console.log("e: "+e);
				//計算(所選取)的總價格方法
				calcTotal();
			});
			
			//綁定 id = goodsCount#{id} 點擊事件
			$("input[id*='goodsCount']").click(function(e){
				console.log("修改數量");
				//獲取id
				var id = $(this).attr("id").substring(10);
				//獲取該筆商品資訊
				var title = $("#goodsTitle" + id).html(); //商品名稱
				var count = parseInt(this.value); //商品數量
				console.log("count" + count);
				
				//啟動修改數量視窗
				$("#dialog-updateCount-form").dialog("open");
				//顯示該筆商品名稱
				$("#updateProductName").html(title);
				//顯示該筆商品數量
				$("#updateCount").val(count);
				//賦值商品id - hidden
				$("#updateId").val(id);
				
//				if (/[^0-9\.-]/g.test(this.value)) {
//		            this.value = this.value.replace(/[^0-9\.-]/g, 0);
//		        }
//				var id = $(this).attr("id").substring(10);
//				console.log("id: " + id);
//				var count = parseInt(this.value);
//				console.log("count: " + count);
//				var price = parseInt($("#goodsPrice" + id).html().replace(/[^0-9]/ig,"")); //只取數字
//				console.log("price: " + price);
//				var totalPrice = count * price;
//				//重設該商品的總金額
//				$("#goodsTotalPrice" + id).html(FormatNumber(totalPrice));
//				//計算(所選取)的總價格方法
//				calcTotal();
			});
		}
	});
}

//該筆購物車商品數量增1
function addCount(id) {
	//判斷該筆商品數量不能高於999
	if (parseInt($("#goodsCount" + id).val()) == 999) {
		console.log("商品數量不能高於999");
		return false;
	}
	var url = "../cart/add_count";
	var data = "id=" + id;
	$.ajax({
		"url": url,
		"data": data,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			//alert("OK");
			//獲取該商品的數量並增1
			var c = parseInt($("#goodsCount" + id).val()) + 1;
			//重設該商品數量
			$("#goodsCount" + id).val(c);
			//獲取該商品的金額
			var p = parseInt($("#goodsPrice" + id).html().replace(/[^0-9]/ig,"")); //只取數字
			//計算該商品的總金額
			var tp = p * c;
			//重設該商品的總金額
			$("#goodsTotalPrice" + id).html(FormatNumber(tp));
			//計算(所選取)的總價格方法
			calcTotal();
		},
		"error": function(xhr, text, errorThrown) {
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}

//該筆購物車商品數量減1
function reduceCount(id) {
	//判斷該筆商品數量不能低於0
	if (parseInt($("#goodsCount" + id).val()) <= 1) {
		console.log("商品數量不能低於1");
		return false;
	}
	var url = "../cart/reduce_count";
	var data = "id=" + id;
	$.ajax({
		"url": url,
		"data": data,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			//alert("OK");
			//獲取該商品的數量並減1
			var c = parseInt($("#goodsCount" + id).val()) - 1;
			//重設該商品數量
			$("#goodsCount" + id).val(c);
			//獲取該商品的金額
			var p = parseInt($("#goodsPrice" + id).html().replace(/[^0-9]/ig,"")); //只取數字
			//計算該商品的總金額
			var tp = p * c;
			//重設該商品的總金額
			$("#goodsTotalPrice" + id).html(FormatNumber(tp));
			//計算(所選取)的總價格方法
			calcTotal();
		},
		"error": function(xhr, text, errorThrown) {
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}

//修改商品數量
function updateCount(id) {
	console.log("修改商品數量dialog");
	var count = $("#updateCount").val(); //獲取目前修改商品的數量
	var url = "../cart/update_count";
	var data = "id="+id+"&count="+count;
	console.log("data: "+data);
	$.ajax({
		"url": url,
		"data": data,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			//重設該商品數量
			var c = parseInt(count);
			$("#goodsCount" + id).val(c);
			//獲取該商品的金額
			var p = parseInt($("#goodsPrice" + id).html().replace(/[^0-9]/ig,"")); //只取數字
			//計算該商品的總金額
			var tp = p * c;
			//重設該商品的總金額
			$("#goodsTotalPrice" + id).html(FormatNumber(tp));
			//計算(所選取)的總價格方法
			calcTotal();
		},
		"error": function(xhr, text, errorThrown) {
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}

function checkUpdateCount() {
	console.log("checkUpdateCount");
	//所選中的商品id
	var id = $("#updateId").val();
	//所選中商品的數量
	var count = $("#updateCount").val();
	//比對是否為數字
	if(!/[^0-9\.-]/g.test(count)){
		//數量是否符合1~999
		if(count <=0 || count >= 1000){
    		console.log("商品數量須為1~999之間");
    		$("#updateCount").addClass("ui-state-error");
    		updateTips("商品數量須為1~999之間");
    		return false;
    	}
		$("#updateCount").removeClass("ui-state-error");
		$(".validateTips").hide();
		//console.log("數字正確");
		//執行更新
		updateCount(id);
		//關閉修改商品數量視窗
		$("#dialog-updateCount-form").dialog("close");
		return true;
	} else {
		console.log("請填入非負數的數字");
		$("#updateCount").addClass("ui-state-error");
		updateTips("請填入非負數的數字");
		return false;
	}
}

//計算(所選取)的總價格方法
function calcTotal() {
	//選中商品的數量
	var vselectCount = 0;
	//選中商品的總價
	var vselectTotal = 0;
	
	//console.log($("#cart-list tr").length);
	
	//循環偏歷所有tr
	for (var i = 0; i < $("#cart-list tr").length; i++) {
		//計算每個商品的價格小計--開始
		//取出1行
		var $tr = $($("#cart-list tr")[i]);
		//取單價
		var vprice = parseFloat($tr.children(":eq(3)").children("span").html().replace(/[^0-9]/ig,"")); //只取數字
		//取數量
		var vnum = parseFloat($tr.children(":eq(4)").children(".num-text").val());
		//小計金額
		var vtotal = vprice * vnum;
		//賦值
		$tr.children(":eq(5)").children("span").html(FormatNumber(vtotal));
		//計算每個商品的價格小計--結束

		//檢查是否選中
		if ($tr.children(":eq(0)").children(".ckitem").prop("checked")) {
			//計數
			vselectCount++;
			//計總價
			vselectTotal += vtotal;
		}
	}
	//將選中的數量和價格賦值
	$("#selectTotal").html(FormatNumber(vselectTotal));
	$("#selectCount").html(vselectCount);
}

//將數字以千分位做回傳
function FormatNumber(num) { 
	num += ""; //轉成字符串
	var reg = /(\d{1,3})(?=(\d{3})+$)/g; 
	return num.replace(reg,"$1,"); 
}

//全選/全不選
function checkall(ckbtn) {
	$(".ckitem").prop("checked", $(ckbtn).prop("checked"));
	calcTotal();
}

//修改商品數量錯誤提示
function updateTips(errorMessage) {
	var tips = $(".validateTips");
	console.log("updateTips");
    tips
    	.text(errorMessage)
    	.addClass( "ui-state-highlight" )
    	.show();
    setTimeout(function() {
    	tips.removeClass( "ui-state-highlight", 1500 );
    }, 500 );
}

//購物車商品 - 删除按钮
function delCartItem(btn, id) {	
	var url = "../cart/delete/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				//將html的該元件刪除
				$(btn).parents("tr").remove();
				//計算總價
				calcTotal();
				console.log("刪除成功");
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

//批量删除按钮
function selDelCart() {
	//遍歷所有按鈕
	console.log("批量刪除")
	//判斷是否有購物車商品
	if ($(".ckitem").length <=0) {
		console.log("購物車目前沒有商品可刪除，請添加！");
		return false;
	}
	//遍歷所有按鈕
	for (var i = $(".ckitem").length - 1; i >= 0; i--) {
		//如果選中
		if ($(".ckitem")[i].checked) {
			//刪除該筆購物車商品
			delCartItem($(".ckitem")[i], $(".ckitem")[i].value)
		}
	}
}