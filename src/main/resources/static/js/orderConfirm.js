//$(function() {
//	$(".link-pay").click(function() {
//		location.href = "payment.html";
//	})
//})
//$(function() {
//	$(".link-success").click(function() {
//		location.href = "paySuccess.html";
//	})
//})

$(function(){
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//1. 加載收貨地址列表
	showAddressList();
	
	//2. 加載購物數據列表
	showCartList();
	
	//3. 為按鈕綁定提交事件
	$("#btn-submit").click(function(){
		if($("#address-list").val() == "") {
			swal("提交異常", "請至少新增一個送貨地址！", "error");
		}
		//創建訂單
		createOrder();
	});
});

//顯示收貨地址列表
function showAddressList() {
	var url = "../address/list";
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			//清空收貨地址列表
			$("#address-list").empty();
			//創建收貨地址集合物件
			var list = json.data;
			
			if(list == "") {
				$("#address-list").append('<option value>--- 無收貨地址 ---</option>');
			}
			
			//添加獲取到的收貨地址
			for (var i=0; i<list.length; i++) {
				var op = '<option value="'+ list[i].id +'">'
					+ list[i].name + ' / '
					+ list[i].tag + ' / '
					+ list[i].phone + ' / '
					+ list[i].city + " / "
					+ list[i].district + " / " 
					+ list[i].address
					+ '</option>';
				
				$("#address-list").append(op);
			}
		}
	});
}

//顯示購物數據列表
function showCartList() {
	var url = "../cart/get_by_ids";
	var data = location.search.substring(1);
	console.log("data="+data);
	$.ajax({
		"url": url,
		"data": data,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			//清空購物車列表
			$("#cart-list").empty();
			//創建購物車物件
			var list = json.data;
			var allCount = 0; //總數量
			var allPrice = 0; //總價
			//添加獲取到的購物車數據
			for(var i=0; i<list.length; i++) {
				allCount += list[i].count; //累加數量
				allPrice += list[i].newPrice * list[i].count; //累加總價
				
				var html = '<tr>'
					+ '<td><input type="hidden" name="cart_id" value="#{id}" />'
					+ '<img src="..#{image}collect.png" width="110" /></td>'
					+ '<td>#{title}</td>'
					+ '<td>$<span>#{price}</span></td>'
					+ '<td>#{count}</td>'
					+ '<td>$<span>#{totalPrice}</span></td>'
					+ '</tr>';
				
				html = html.replace(/#{id}/g, list[i].id);
				html = html.replace(/#{image}/g, list[i].image);
				html = html.replace(/#{title}/g, list[i].title);
				html = html.replace(/#{price}/g, FormatNumber(list[i].newPrice));
				html = html.replace(/#{count}/g, list[i].count);
				html = html.replace(/#{totalPrice}/g, FormatNumber(list[i].newPrice * list[i].count));
				
				$("#cart-list").append(html);
			}
			$("#selectCount").html(allCount);
			$("#selectTotal").html(FormatNumber(allPrice));
		}
	});
}

//創建訂單
function createOrder() {
	var url = "../order/create";
	var data = $("#form-create-order").serialize();
	console.log(data);
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				var order = json.data; //獲取新建的訂單資料
				var orderId = order.id; //訂單編號
				swal({
                    title: '創建成功',
                    text: '您的訂單已創建成功！！，系統 3 秒後，自動移至線上付款！',
                    type: 'success',
                    confirmButtonText: "關閉",
                    timer: 3000,
                    width: 600,
                    padding: 50
                },
                function () {
                	location.href = "/web/payment.html?id="+orderId;
                });
				//swal("創建成功", "您的訂單已創建成功！", "success");
			} else {
				swal("創建失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown) {
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
		
	});
}

//將數字以千分位做回傳
function FormatNumber(num) { 
	num += ""; //轉成字符串
	var reg = /(\d{1,3})(?=(\d{3})+$)/g; 
	return num.replace(reg,"$1,"); 
}