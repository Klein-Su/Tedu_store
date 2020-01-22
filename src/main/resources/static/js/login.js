$(function() {
	//登入按鈕綁定點擊事件	
	$("#btn-login").click(function(){
		//將請求提到到哪裡
		//當前位置：web/login.html
		//目標位置：user/login.do
		var url = "../user/login.do";
		//請求參數
		var data = $("#form-login").serialize();
		console.log("登入參數："+data);
		//發出ajax請求，並處理結果
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json){
				if (json.state == 200){
					alert("登入成功！");
					//將username存到Cookie
					$.cookie("username", json.data.username, {
						expires: 7 //使用期限 7天
					});
					//將頭像路徑存到Cookie
					$.cookie("avatar", json.data.avatar, {
						expires: 7 //使用期限 7天
					});
					console.log("登入成功，將頭像路徑存到Cookie: " +
							$.cookie("avatar"));
				} else {
					alert(json.message);
				}
			}
		});
	});
})


