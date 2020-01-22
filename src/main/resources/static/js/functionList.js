$(function() {
	//載入 header 頁面
	$("#header-div").load("../backstage/backstageHeader.html");
	//載入 footer 頁面
	$("#footer-div").load("../backstage/backstageFooter.html");
	
	//載入功能清單
	funList();
});

//顯示功能清單
function funList() {
	var url = "../backstage/manager/info.do";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				//console.log(JSON.stringify(json.data));
				var manager = json.data;
				if(manager.mStaff != 0) { //人員管理
					$("#mStaff-div").show();
				}
				if(manager.mProduct != 0) { //商品管理
					$("#mProduct-div").show();
				}
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			
		}
	});
}