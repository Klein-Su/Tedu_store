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
	var id = $.getUrlParam("id");
	
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	$("input[type='radio']").click(function(){
		var id = $(this).attr("id");
		if (id=="optionsRadios1") {
			$("#card-div").show();
			$("#atm-div").hide();
			$("#arrived-div").hide();
		} else if (id=="optionsRadios2") {
			$("#card-div").hide();
			$("#atm-div").show();
			$("#arrived-div").hide();
		} else if (id=="optionsRadios3") {
			$("#card-div").hide();
			$("#atm-div").hide();
			$("#arrived-div").show();
		}
	});
	
	//戴入訂單資訊
	getOrder(id);
});

//獲取訂單資訊
function getOrder(id) {
	var url = "../order/getData/"+id;
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				var order = json.data;
				//alert(JSON.stringify(order));
				$("#order_id").html(order.id);
				$("#order_pay").html(FormatNumber(order.pay));
				$("#confirm_pay").html(FormatNumber(order.pay));
			} else {
				swal("獲取失敗", json.message, "error");
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





