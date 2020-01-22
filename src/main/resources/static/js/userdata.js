//頁面初始化時加載用戶數據
$(document).ready(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//獲取使用者資料
	getUserData();
	
	//修改按鈕綁定點擊事件	
	$("#btn-change").click(function(){
		//檢查資料是否有誤
		checkUserData();
	});
});

//判斷修改資料是否符合規則
function checkUserData() {
	console.log("檢查資料");
	var phone = $("#phone").val(); //電話號碼
	var email = $("#email").val(); //電子信箱
	console.log("email："+email);
	/*
	 * ^\w+：@ 之前必須以一個以上的文字&數字開頭，例如 abc
	 * ((-\w+)：@ 之前可以出現 1 個以上的文字、數字或「-」的組合，例如 -abc-
	 * (\.\w+))：@ 之前可以出現 1 個以上的文字、數字或「.」的組合，例如 .abc.
	 * ((-\w+)|(\.\w+))*：以上兩個規則以 or 的關係出現，並且出現 0 次以上 (所以不能 –. 同時出現)
	 * @：中間一定要出現一個 @
	 * [A-Za-z0-9]+：@ 之後出現 1 個以上的大小寫英文及數字的組合
	 * (\.|-)：@ 之後只能出現「.」或是「-」，但這兩個字元不能連續時出現
	 * ((\.|-)[A-Za-z0-9]+)*：@ 之後出現 0 個以上的「.」或是「-」配上大小寫英文及數字的組合
 	 * \.[A-Za-z]+$/：@ 之後出現 1 個以上的「.」配上大小寫英文及數字的組合，結尾需為大小寫英文
	*/
	//電子信箱規則：正則表達式
	var emailRule = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
	var gender = 0; //性別被選取的數量
	//判斷性別是否有被選取
	$("input[name='gender']").each(function(){
		if($(this).prop("checked")) {
			gender++;
		}
	});
	var valid = true; //修改個人資料，true：符合、false：不符合
	
	//判斷電話號碼
	if(phone == "") { 
		$("#phone-error").show(); 
		valid = valid && false;
	} else {
		//判斷phone是否符合格式，10位的數字組合
		if(!/^[0][9][0-9]{8}$/.test(phone)) {
			$("#phone-error").html("電話號碼格式不符合");
			$("#phone-error").show();
			valid = valid && false;
		} else {
			$("#phone-error").hide();
		}
	}
	//判斷電子信箱
	if(email == "") { 
		$("#email-error").show(); 
		valid = valid && false;
	} else {
		//判斷電子信箱格式
		if(!emailRule.test(email)) {
			$("#email-error").html("電子信箱格式不符合");
			$("#email-error").show();
			valid = valid && false;
		} else {
			$("#email-error").hide();
		}
	}
	//判斷性別是否被選取
	if(gender <= 0) { 
		$("#gender-error").show(); 
		valid = valid && false;
	} else {
		$("#gender-error").hide(); 
	}
	
	if(valid){ //判斷修改個人資料是否符合資格
		swal({
			title: "修改個人資料",
			text: "確定將資料送出並修改？",
			type: "warning",
			showCancelButton: true,
			confirmButtonClass: "btn-info",
			confirmButtonText: "確定",
			cancelButtonClass: "btn-danger",
			cancelButtonText: "取消",
			closeOnConfirm: false,
			closeOnCancel: true
		},
		function(isConfirm) {
			if (isConfirm) {
				//修改個人資料
				updateUserData();
			} else {
			    //swal("取消", "取消修改個人資料！", "error");
			}
		});
	}
}

//獲取使用者資料
function getUserData() {
	console.log("userData");
	//將請求提到到哪裡
	//當前位置：web/userdata.html
	//目標位置：user/info.do
	var url = "../user/info.do";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			var user = json.data;
			$("#usernameData").val(user.username);
			$("#phone").val(user.phone);
			$("#email").val(user.email);
			if (user.gender == 0) {
				$("#gender-female").prop("checked", true);
			}
			if (user.gender == 1) {
				$("#gender-male").prop("checked", true);
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			//alert("xhr.readyState = "+xhr.readyState);
			//alert("xhr.status = "+xhr.status);
			alert("您的登入信息已經過期！請重新登入！")
			location.href = "login.html";
		}
	});
}

function updateUserData() {
	console.log("updateUserData");
	//將請求提到到哪裡
	//當前位置：web/userdata.html
	//目標位置：user/change_info.do
	var url = "../user/change_info.do";
	//請求參數
	var data = $("#form-change-info").serialize();
	console.log("修改個人資料參數："+data);
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200){
				//alert("修改成功！");
				swal("修改成功", "您的修改個人資料已完成！", "success");
				//重新再獲取一次使用者資料
				getUserData();
			} else {
				swal("修改失敗", json.message, "error");
			}
		}
	});
}