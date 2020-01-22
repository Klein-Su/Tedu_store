//當頁面加載完成時
$(function() {
	//加載省的列表
	getProvinceList();
	//為省的列表綁定change事件
	$("#provinces").change(function(){
		//加載市的列表
		getCityList();
		//清空區列表
		$("#areas").empty();
		//添加默認選項
		var op = '<option value="0">--- 請選擇 ---</option>';
		$("#areas").append(op);
	});
	//為市的列表綁定change事件
	$("#cities").change(function(){
		//加載區的列表
		getAreaList();
	});
	
	//提交按鈕綁定點擊事件	
	$("#btn-submit").click(function(){
		//將請求提到到哪裡
		//當前位置：web/addAddress.html
		//目標位置：address/create
		var url = "../address/create";
		//請求參數
		var data = $("#form-address").serialize();
		console.log("收貨地址參數："+data);
		//發出ajax請求，並處理結果
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json){
				if (json.state == 200){
					alert("保存成功！");
				} else {
					alert(json.message);
				}
			},
			"error": function(xhr, text, errorThrown) {
				console.log("xhr.status=" + xhr.status);
				alert("您的登入信息已經過期！請重新登入！");
				location.href = "login.html";
			}
		});
	});
})

//加載省的列表
function getProvinceList(){
	//清空當前列表
	$("#provinces").empty();
	//添加默認選項
	var op = "<option value='0'>--- 請選擇 ---</option>";
	$("#provinces").append(op);
	//將請求提到到哪裡
	//當前位置：web/addAddress.html
	//目標位置：district/list
	var url = "../district/list/86";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			//遍歷加載省的新列表項
			var list = json.data;
			for (var i=0; i<list.length; i++) {
				//輸出在控制台，測試確實獲取到數據
				console.log(list[i].code + " - " + list[i].name);
				//<select><option>...</option></select>，建立<option>物件
				var op = "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
				//添加到$("#provinces")之後
				$("#provinces").append(op);
			}
		}
	});
}

//加載市的列表
function getCityList() {
	//清空當前列表
	$("#cities").empty();
	//添加默認選項
	var op = "<option value='0'>--- 請選擇 ---</option>";
	$("#cities").append(op);
	
	//獲取所選擇的省
	var code = $("#provinces").val();
	//判斷省的代號的值
	if (code == 0) {
		return;
	}
	
	//將請求提到到哪裡
	//當前位置：web/addAddress.html
	//目標位置：district/list
	var url = "../district/list/" + code;
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			//遍歷加載市的新列表項
			var list = json.data;
			for (var i=0; i<list.length; i++) {
				//輸出在控制台，測試確實獲取到數據
				console.log(list[i].code + " - " + list[i].name);
				//<select><option>...</option></select>，建立<option>物件
				var op = "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
				//添加到$("#cities")之後
				$("#cities").append(op);
			}
		}
	});
}

//加載區的列表
function getAreaList() {
	//清空當前列表
	$("#areas").empty();
	//添加默認選項
	var op = "<option value='0'>--- 請選擇 ---</option>";
	$("#areas").append(op);
	
	//獲取所選擇的市
	var code = $("#cities").val();
	//判斷省的代號的值
	if (code == 0) {
		return;
	}
	
	//將請求提到到哪裡
	//當前位置：web/addAddress.html
	//目標位置：district/list
	var url = "../district/list/" + code;
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			//遍歷加載市的新列表項
			var list = json.data;
			for (var i=0; i<list.length; i++) {
				//輸出在控制台，測試確實獲取到數據
				console.log(list[i].code + " - " + list[i].name);
				//<select><option>...</option></select>，建立<option>物件
				var op = "<option value='" + list[i].code + "'>" + list[i].name + "</option>";
				//添加到$("#cities")之後
				$("#areas").append(op);
			}
		}
	});
}