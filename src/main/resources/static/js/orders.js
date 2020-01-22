$(function(){
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//1. 加載訂單列表
	showOrderList();
	
});

function showOrderList() {
	var url = "../order/list";
	//var data = location.search.substring(1);
	//console.log("data="+data);
	$.ajax({
		"url": url,
		//"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				//清空訂單列表
				$("#order-list").empty();
				//創建訂單物件
				var list = json.data;
				//假設沒有訂單
				if (list.length == 0) {
					$("#order-list").append('<div class="panel panel-primary">'
							+	'<div class="panel-heading">'
							+	'<h4 class="panel-title">'
								+	'訂單資訊'
							+	'</h4>'
						+	'</div>'
						+	'<div class="panel-body">'
							+	'無訂單資料'
						+	'</div>'
					+	'</div>');
				}
				//添加獲取到的訂單數據
				for(var i=0; i<list.length; i++) {
					var orderList = '<div class="panel panel-primary">'
									+	'<div class="panel-heading">'
										+	'<h4 class="panel-title">'
											+	'訂單號碼：#{id} / 下單時間：#{orderTime} / 收貨人：#{recvName}'
										+	'</h4>'
									+	'</div>'
									+	'<div class="panel-body">'
										+	'<table class="orders-table" width="100%">'
											+	'<thead>'
												+	'<tr>'
													+	'<th width="110"></th>'
													+	'<th width="35%">商品</th>'
													+	'<th width="10%">單價</th>'
													+	'<th width="8%">數量</th>'
													+	'<th width="10%">小計</th>'
													+	'<th width="10%">售後</th>'
													+	'<th width="10%">狀態</th>'
													+	'<th width="10%">操作</th>'
												+	'</tr>'
											+	'</thead>'
											+	'<tbody id="orderItem#{id}" class="orders-body">'
											+	'</tbody>'
										+	'</table>'
										+	'<div>'
											+	'<span class="pull-right">訂單總金額：$ #{pay}</span>'
										+	'</div>'
									+	'</div>'
								+	'</div>';
					orderList = orderList.replace(/#{id}/g, list[i].id);
					orderList = orderList.replace(/#{orderTime}/g, list[i].orderTime);
					orderList = orderList.replace(/#{recvName}/g, list[i].recvName);
					orderList = orderList.replace(/#{pay}/g, FormatNumber(list[i].pay));
					
					$("#order-list").append(orderList);
					
					//訂單的商品
					var items = list[i].items;
					for(var j=0; j<items.length; j++) {
						var orderItem = '<tr>'
										+	'<td><img src="..#{goodsImage}collect.png" width="110" /></td>'
										+	'<td>#{goodsTitle}</td>'
										+	'<td>$<span>#{goodsPrice}</span></td>'
										+	'<td>#{goodsCount} 件</td>'
										+	'<td>$<span>#{goodsTotalPrice}</span></td>'
										+	'<td><a href="#">申請售後</a></td>'
										+	'<td>'
											+	'<div>已出貨</div>'
											+	'<div><a href="orderInfo.html">訂單詳情</a></div>'
										+	'</td>'
										+	'<td><a href="#" class="btn btn-info btn-xs">確認收貨</a></td>'
									+	'</tr>';
						
						orderItem = orderItem.replace(/#{goodsImage}/g, items[j].goodsImage);
						orderItem = orderItem.replace(/#{goodsTitle}/g, items[j].goodsTitle);
						orderItem = orderItem.replace(/#{goodsPrice}/g, FormatNumber(items[j].goodsPrice));
						orderItem = orderItem.replace(/#{goodsCount}/g, items[j].goodsCount);
						orderItem = orderItem.replace(/#{goodsTotalPrice}/g, FormatNumber(items[j].goodsPrice * items[j].goodsCount));
						
						$("#orderItem"+items[j].oid).append(orderItem);
					}
				}
			} else {
				//失敗
				swal("獲取失敗", json.message, "error");
			}
		}
	});
}

//將數字以千分位做回傳
function FormatNumber(num) { 
	num += ""; //轉成字符串
	var reg = /(\d{1,3})(?=(\d{3})+$)/g; 
	return num.replace(reg,"$1,"); 
}