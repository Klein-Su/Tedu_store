$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//修改按鈕綁定點擊事件	
	$("#btn-change").click(function(){
		var oldPassword = $("#old_password").val();
		var newPassword = $("#new_password").val();
		var conPassword = $("#con_password").val();
		var valid = true; //修改密碼資格，true：符合、false：不符合
		
		//判斷原密碼是否為空
		if(oldPassword == "") { 
			$("#old-password-error").show(); 
			valid = valid && false;
		} else {
			$("#old-password-error").hide();
		}
		//判斷新密碼是否為空
		if(newPassword == "") { 
			$("#new-password-error").show();
			valid = valid && false;
		} else {
			//判斷newPassword是否符合格式，範圍為：6~20字的英文及數字組合
			if(!/^[a-zA-Z0-9]{6,20}$/.test(newPassword)) {
				$("#new-password-error").html("請輸入6~20字以內的英文及數字組合");
				$("#new-password-error").show();
				valid = valid && false;
			} else {
				$("#new-password-error").hide(); 
			}
		}
		//判斷確認密碼是否為空
		if(conPassword == "") { 
			$("#con-password-error").show(); 
			valid = valid && false;
		} else {
			$("#con-password-error").hide(); 
		}
		//判斷所有欄位都不為空
		if(oldPassword != "" && newPassword != "" && conPassword != "") {
			//判斷新密碼與確認密碼是否一致
			if(newPassword != conPassword){
				$("#con-password-error").html("密碼不正確"); 
				$("#con-password-error").show();
				valid = valid && false;
			} else {
				$("#con-password-error").hide();
			}
		}
		console.log("修改密碼資格："+valid);
		if(valid){ //判斷修改密碼資訊是否符合資格
			swal({
				title: "修改密碼",
				text: "確定將資料送出並修改？",
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
					//執行修改密碼
					changePassword();
				} else {
				    swal("取消", "取消修改密碼！", "error");
				}
			});
		}
	});
})

//修改密碼
function changePassword() {	
	//將請求提到到哪裡
	//當前位置：web/password.html
	//目標位置：user/password.do
	var url = "../user/password.do";
	//請求參數
	var data = $("#form-change-password").serialize();
	console.log("修改密碼參數："+data);
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200){
				//alert("修改成功！");
				swal("修改成功", "您的修改密碼已完成！", "success");
				$("#form-change-password")[0].reset();
			} else {
				swal("修改失敗", json.message, "error");
			}
		}
	});
}
