$(function() {
	//註冊按鈕綁定點擊事件	
	$("#btn-reg").click(function(){
		//將請求提到到哪裡
		//當前位置：web/register.html
		//目標位置：user/reg.do
		var url = "../user/reg.do";
		//請求參數
		var data = $("#form-reg").serialize();
		console.log("註冊參數："+data);
		//發出ajax請求，並處理結果
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json){
				if (json.state == 200){
					alert("註冊成功！");
				} else {
					alert(json.message);
				}
			}
		});
	});
})


