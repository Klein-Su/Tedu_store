$(function() {
	//載入 header 頁面
	$("#header-div").load("../backstage/backstageHeader.html");
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//註冊按鈕綁定點擊事件	
	$("#btn-add").click(function(){
		addManager();
	});
});

//新增後台管理人員
function addManager() {
	//將請求提到到哪裡
	//目標位置：/backstage/manager/add_manager
	var url = "../backstage/manager/add_manager";
	//請求參數
	var data = $("#form-add").serialize();
	console.log("新增參數："+data);
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200){
				swal("新增成功", "新增後台人員( " + $("#username").val() + " )成功！", "success");
			} else {
				alert(json.message);
			}
		}
	});
}
