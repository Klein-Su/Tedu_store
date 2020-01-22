//當頁面加載完成時
$(function() {
	//載入 jquery.cookie.js 檔
	$.getScript( "../bootstrap3/js/jquery.cookie.js", function(data, textStatus, jqxhr) {
		//console.log( data ); // js檔案中的代碼
		//console.log( textStatus ); // Success：js載入狀態
		//console.log( jqxhr.status ); // 200：js載入的代碼
		console.log( "載入jquery.cookie.js成功！" );
		if(jqxhr.status == 200){
			//判斷cookie是否有值
			if ($.cookie("mid") != null && $.cookie("username") != null) {
				console.log("mid: " + $.cookie("mid"));
				console.log("username: " + $.cookie("username"));
				//獲取管理者訊息
				getManagerInfo();
			}
		}
	});
	//載入 sweetalert.js 檔
	$.getScript( "../bootstrap3/js/sweetalert.min.js", function(data, textStatus, jqxhr) {
		if(jqxhr.status == 200){
			console.log( "載入sweetalert.min.js成功！" );
		}
	});
	//動態載入 sweetalert.css 檔
	var swalCSS = $('<link href="../bootstrap3/css/sweetalert.css" type="text/css" rel="stylesheet" />'); 
    // 提前修改swalCSS屬性 
	swalCSS.attr("title", "global"); 
    swalCSS.appendTo("head"); 
    
});

//獲取管理者資訊
function getManagerInfo() {
	var url = "../backstage/manager/info.do";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				//console.log(JSON.stringify(json.data));
				//顯示登入後的功能選項li
				$(".product-management-li").show(); //商品管理
				$(".users-management-li").show(); //人員管理
				
				//獲取manager資料
				var manager = json.data;
				//清空登入的li
				$("#login-li").empty();
				//添加登入後的html元件
				var html = 
					'<div class="btn-group">' +
					'<button type="button" class="btn btn-link dropdown-toggle" data-toggle="dropdown">' +
						'<span id="top-dropdown-btn"></span>#{username}，您好！&nbsp;<span class="caret"></span></span>' +
					'</button>' +
					'<ul class="dropdown-menu top-dropdown-ul" role="menu">' +
						'<li><a href="password.html">修改密碼</a></li>' +
						'<li><a href="userdata.html">個人資料</a></li>' +
						'<li><a href="" onclick="logout()" >登出</a></li>' +
					'</ul>' +
					'</div>';
				//利用佔位符，再用正確的數據去取代佔位符
				html = html.replace(/#{username}/g, manager.username); //以正則表達式，/g：表全局替換
				//添加到登入攔位
				$("#login-li").append(html);
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//管理者登出
function logout() {
	var url = "../backstage/manager/logout.do";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				// 删除 cookie
				$.cookie("username", "", {
					expires: -1 
				}); 
				$.cookie("mid", "", {
					expires: -1 
				});
				//當前頁面重整
				//location.reload();
				location.href = "../backstage/index.html";
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//
////立即註冊(檢查註冊資料)
//function register() {
//	var regUsername = $("#regUsername").val();
//	var regPassword = $("#regPassword").val();
//	var conPassword = $("#confirmPassword").val();
//	var valid = true; //註冊資格，true：符合、false：不符合
//	
//	//判斷註冊資訊	
//	if(regUsername == "") { 
//		$("#reg-username-error").show(); 
//		valid = valid && false;
//	} else {
//		//判斷regUsername是否符合格式，以字母開頭的6~20字的英文及數字組合
//		if(!/^[a-zA-Z][a-zA-Z0-9]{5,19}$/.test(regUsername)) {
//			$("#reg-username-error").html("請輸入以字母開頭的6~20字以內的英文及數字");
//			$("#reg-username-error").show();
//			valid = valid && false;
//		} else {
//			console.log("合法的regUsername");
//			$("#reg-username-error").hide();
//		}
//	}
//	if(regPassword == "") { 
//		$("#reg-password-error").show(); 
//		valid = valid && false;
//	} else {
//		//判斷regPassword是否符合格式，範圍為：6~20字的英文及數字組合
//		if(!/^[a-zA-Z0-9]{6,20}$/.test(regPassword)) {
//			$("#reg-password-error").html("請輸入6~20字以內的英文及數字組合");
//			$("#reg-password-error").show();
//			valid = valid && false;
//		} else {
//			console.log("合法的regPassword");
//			$("#reg-password-error").hide(); 
//		}
//	}
//	if(conPassword == "") { 
//		$("#reg-confirm-password-error").show(); 
//		valid = valid && false;
//	} else {
//		$("#reg-confirm-password-error").hide(); 
//	}
//	if(regUsername != "" && regPassword != "" && conPassword != ""){
//		if(regPassword != conPassword) {
//			$("#reg-confirm-password-error").html("密碼不正確"); 
//			$("#reg-confirm-password-error").show();
//			valid = valid && false;
//		} else {
//			$("#reg-confirm-password-error").hide(); 
//		}
//	}
//	console.log("valid："+valid);
//	//return valid; //判斷註冊資訊是否符合資格
//	
//	if(valid) { //判斷註冊資訊是否符合資格
//		swal({
//			title: "會員註冊",
//			text: "確定將資料送出並註冊？",
//			type: "warning",
//			showCancelButton: true,
//			confirmButtonClass: "btn-info",
//			confirmButtonText: "確定",
//			cancelButtonClass: "btn-danger",
//			cancelButtonText: "取消",
//			closeOnConfirm: false,
//			closeOnCancel: false
//		},
//		function(isConfirm) {
//			if (isConfirm) {
//				//進行註冊
//				handleReg();
//			} else {
//			    swal("取消", "您的註冊已取消！", "error");
//			}
//		});
//	}
//}
//
////進行註冊
//function handleReg() {
//	//將請求提到到哪裡
//	//當前位置：web/register.html
//	//目標位置：user/reg.do
//	var url = "../user/reg.do";
//	//請求參數
//	var data = $("#form-register").serialize();
//	console.log("註冊參數："+data);
//	//發出ajax請求，並處理結果
//	$.ajax({
//		"url": url,
//		"data": data,
//		"type": "POST",
//		"dataType": "json",
//		"success": function(json){
//			if (json.state == 200){
//				//註冊成功並且完成登入動作
//				//alert("註冊成功！");
//				swal("註冊成功", "您已註冊成功！", "success");
//				//將username存到Cookie
//				$.cookie("username", json.data.username, {
//					expires: 7 //使用期限 7天
//				});
//				//當前頁面重整
//				location.reload();
//			} else {
//				swal("註冊失敗", json.message, "error");
//			}
//		}
//	});
//}