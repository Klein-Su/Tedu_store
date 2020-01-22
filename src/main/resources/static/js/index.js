$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html");
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//新商品排行清單
	showNewList();
	//載入熱銷排行清單
	showHotList();
})

//新商品排行清單
function showNewList() {
	var url = "../goods/new";
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json){
			var list = json.data;
			//清空熱銷排行
			$("#list-new").empty();
			//console.log("size=" + list.length);
			for (var i=0; i<list.length; i++) {
				//console.log("title=" + list[i].title);
				
				var html = '<div class="col-md-12 col-sm-12 col-xs-12">'
						+ '<div class="col-md-6 col-sm-6 col-xs-6 text-row-2"><a href="/web/product.html?id=#{id}">#{title}</a></div>'
						+ '<div class="col-md-3 col-sm-3 col-xs-3">$#{price}</div>'
						+ '<div class="col-md-3 col-sm-3 col-xs-3"><img src="..#{image}collect.png" width="100%" /></div>'
					+ '</div>';
				
				html = html.replace(/#{id}/g, list[i].id);
				html = html.replace(/#{title}/g, list[i].title);
				html = html.replace(/#{price}/g, FormatNumber(list[i].price));
				html = html.replace(/#{image}/g, list[i].image);
				
				//將取出的新商品排行商品添加進來
				$("#list-new").append(html);
			}
		}
	});
}

//熱銷排行清單
function showHotList() {
	var url = "../goods/hot";
	
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json){
			var list = json.data;
			//清空熱銷排行
			$("#list-hot").empty();
			//console.log("size=" + list.length);
			for (var i=0; i<list.length; i++) {
				//console.log("title=" + list[i].title);
				
				var html = '<div class="col-md-12 col-sm-12 col-xs-12">'
						+ '<div class="col-md-6 col-sm-6 col-xs-6 text-row-2"><a href="/web/product.html?id=#{id}">#{title}</a></div>'
						+ '<div class="col-md-3 col-sm-3 col-xs-3">$#{price}</div>'
						+ '<div class="col-md-3 col-sm-3 col-xs-3"><img src="..#{image}collect.png" width="100%" /></div>'
					+ '</div>';
				
				html = html.replace(/#{id}/g, list[i].id);
				html = html.replace(/#{title}/g, list[i].title);
				html = html.replace(/#{price}/g, FormatNumber(list[i].price));
				html = html.replace(/#{image}/g, list[i].image);
				
				//將取出的熱銷排行商品添加進來
				$("#list-hot").append(html);
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