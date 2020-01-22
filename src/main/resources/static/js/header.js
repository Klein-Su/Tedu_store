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
			if ($.cookie("username") != null) {
				console.log("username: " + $.cookie("username"));
				console.log("avatar: " + $.cookie("avatar"));
				//獲取使用者訊息
				getUserInfo();
			}
			//判斷用戶登入後是否有頭像路徑，有則顯示
			if ($.cookie("avatar") != null) {
				$("#img-avatar").attr("src", $.cookie("avatar"));
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

//獲取使用者資訊
function getUserInfo() {
	var url = "../user/info.do";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				//顯示登入後的功能選項li
				$(".favorites-li").show(); //收藏
				$(".orders-li").show(); //訂單
				$(".cart-li").show(); //購物車
				
				//獲取user資料
				var user = json.data;
				//清空登入的li
				$("#login-li").empty();
				//添加登入後的html元件
				var html = 
					'<div class="btn-group">' +
					'<button type="button" class="btn btn-link dropdown-toggle" data-toggle="dropdown">' +
						'<span id="top-dropdown-btn"></span>#{username}，您好！&nbsp;<span class="caret"></span></span>' +
					'</button>' +
					'<ul class="dropdown-menu top-dropdown-ul" role="menu">' +
						'<li><a href="/web/password.html">修改密碼</a></li>' +
						'<li><a href="/web/userdata.html">個人資料</a></li>' +
						'<li><a href="/web/upload.html">上傳頭像</a></li>' +
						'<li><a href="/web/address.html">配送地址</a></li>' +
						'<li><a href="" onclick="logout()" >登出</a></li>' +
					'</ul>' +
					'</div>';
				//利用佔位符，再用正確的數據去取代佔位符
				html = html.replace(/#{username}/g, user.username); //以正則表達式，/g：表全局替換
				//添加到登入攔位
				$("#login-li").append(html);
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//使用者登入
function login() {
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
				//alert("登入成功！");
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
				//當前頁面重整
				location.reload();
			} else {
				swal("登入失敗", json.message, "error");
			}
		}
	});
}

//使用者登出
function logout() {
	var url = "../user/logout.do";
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
				$.cookie("avatar", "", {
					expires: -1 
				});
				console.log("username: " + $.cookie("username"));
				console.log("avatar: " + $.cookie("avatar"));
				//alert("登出成功");
				/*
					//轉發到首頁
					location.href = "/web/index.html";
				*/
				//當前頁面重整
				location.reload();
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//立即註冊(檢查註冊資料)
function register() {
	var regUsername = $("#regUsername").val();
	var regPassword = $("#regPassword").val();
	var conPassword = $("#confirmPassword").val();
	var valid = true; //註冊資格，true：符合、false：不符合
	
	//判斷註冊資訊	
	if(regUsername == "") { 
		$("#reg-username-error").show(); 
		valid = valid && false;
	} else {
		//判斷regUsername是否符合格式，以字母開頭的6~20字的英文及數字組合
		if(!/^[a-zA-Z][a-zA-Z0-9]{5,19}$/.test(regUsername)) {
			$("#reg-username-error").html("請輸入以字母開頭的6~20字以內的英文及數字");
			$("#reg-username-error").show();
			valid = valid && false;
		} else {
			console.log("合法的regUsername");
			$("#reg-username-error").hide();
		}
	}
	if(regPassword == "") { 
		$("#reg-password-error").show(); 
		valid = valid && false;
	} else {
		//判斷regPassword是否符合格式，範圍為：6~20字的英文及數字組合
		if(!/^[a-zA-Z0-9]{6,20}$/.test(regPassword)) {
			$("#reg-password-error").html("請輸入6~20字以內的英文及數字組合");
			$("#reg-password-error").show();
			valid = valid && false;
		} else {
			console.log("合法的regPassword");
			$("#reg-password-error").hide(); 
		}
	}
	if(conPassword == "") { 
		$("#reg-confirm-password-error").show(); 
		valid = valid && false;
	} else {
		$("#reg-confirm-password-error").hide(); 
	}
	if(regUsername != "" && regPassword != "" && conPassword != ""){
		if(regPassword != conPassword) {
			$("#reg-confirm-password-error").html("密碼不正確"); 
			$("#reg-confirm-password-error").show();
			valid = valid && false;
		} else {
			$("#reg-confirm-password-error").hide(); 
		}
	}
	console.log("valid："+valid);
	//return valid; //判斷註冊資訊是否符合資格
	
	if(valid) { //判斷註冊資訊是否符合資格
		swal({
			title: "會員註冊",
			text: "確定將資料送出並註冊？",
			type: "warning",
			showCancelButton: true,
			confirmButtonClass: "btn-info",
			confirmButtonText: "確定",
			cancelButtonClass: "btn-danger",
			cancelButtonText: "取消",
			closeOnConfirm: false,
			closeOnCancel: false
		},
		function(isConfirm) {
			if (isConfirm) {
				//進行註冊
				handleReg();
			} else {
			    swal("取消", "您的註冊已取消！", "error");
			}
		});
	}
}

//進行註冊
function handleReg() {
	//將請求提到到哪裡
	//當前位置：web/register.html
	//目標位置：user/reg.do
	var url = "../user/reg.do";
	//請求參數
	var data = $("#form-register").serialize();
	console.log("註冊參數："+data);
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200){
				//註冊成功並且完成登入動作
				//alert("註冊成功！");
				swal("註冊成功", "您已註冊成功！", "success");
				//將username存到Cookie
				$.cookie("username", json.data.username, {
					expires: 7 //使用期限 7天
				});
				//當前頁面重整
				location.reload();
			} else {
				swal("註冊失敗", json.message, "error");
			}
		}
	});
}

//獲取廠牌
function getBrandByCategoryId() {
	if($("#selected_category").html()=="分類") {
//		$("#brand-menu").empty();
//		$("#brand-menu").append('<li><a href="#">不拘</a></li>');
	} else {
		var url = "../goods/getBrandByCategoryId";
		//請求參數
		var data = "categoryId=" + $("#selected_category").attr("data-categoryId");
		//發出ajax請求，並處理結果
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json){
				if (json.state == 200){
					var goods = json.data;
					//alert(JSON.stringify(json.data));
					for(var i=0; i<goods.length; i++) {
						$("#brand-menu").append('<li><a href="#">'+goods[i].brand+'</a></li>');
					}
					//選擇上排進階分類廠牌按鈕
					$("#brand-menu").find("li a").click(function(){
						//判斷 li 的 選擇值
						if($(this).html() == "不拘") {
							var selectedItem = $(this).parent().parent().parent().find("button:eq(0)").attr("data-type");
							$("#brand").val("");
						} else {
							var selectedItem = $(this).html();
							$("#brand").val($(this).html());
						}
						$(this).parent().parent().parent().find("button:eq(0)").html(selectedItem);
					});
				} else {
					//swal("獲取失敗", json.message, "error");
				}
			}
		});
	}
}