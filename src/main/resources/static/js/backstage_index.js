$(function() {
	//載入 header 頁面
	$("#header-div").load("../backstage/backstageHeader.html");
	//載入 footer 頁面
	$("#footer-div").load("../backstage/backstageFooter.html");
	
	//登入按鈕綁定點擊事件	
	$("#btn-login").click(function(){
		handleLogin();
	});
})

function handleLogin() {
	var username = $("#username").val();
	var password = $("#password").val();
	//判斷帳號和密碼是否為空值
	if(username == "" || password == "") {
		if(username == "") { 
			$("#username-error").show(); 
		} else {
			$("#username-error").hide();
		}
		if(password == "") { 
			$("#password-error").show(); 
		} else {
			$("#password-error").hide(); 
		}
		return false; //判斷是否繼續執行下列程式，true:執行、false:不執行
	} 
	
	//將請求提到到哪裡
	//目標位置：manager/login.do
	var url = "../backstage/manager/login.do";
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
				console.log("登入成功！");
				//將mid存到Cookie
				$.cookie("mid", json.data.id, {
					expires: 7 //使用期限 7天
				});
				//將username存到Cookie
				$.cookie("username", json.data.username, {
					expires: 7 //使用期限 7天
				});
				//location.reload();
				location.href = "../backstage/functionList.html";
			} else {
				swal("錯誤訊息", json.message, "error");
			}
		}
	});
}
