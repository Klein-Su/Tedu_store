var UPDATE = "none"; //人員修改按鈕顯示
var DELETE = "none"; //人員刪除按鈕顯示
	
$(function() {
	//獲取查詢資格
	getpermission();
	
	//載入 header 頁面
	$("#header-div").load("../backstage/backstageHeader.html");
	//載入 footer 頁面
	$("#footer-div").load("../backstage/backstageFooter.html");
	
	
	//綁定搜尋按鈕事件
	$("#btn-search").click(function(){
		var inputValue = $("#search").val();
		getAllData(inputValue);
	});
});

function getpermission() {
	var url = "../backstage/manager/info.do";
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				var manager = json.data;
				//console.log(JSON.stringify(manager));
				$("#managerId").val(manager.id); //儲存登入的管理員id
				$("#managerRoleId").val(manager.roleId); //儲存登入的管理員role_id
				//判斷該管理人員是否有查詢權限，新增:8、刪除:4、修改:2、查詢:1
				if (manager.mStaff & 1) {
					//判斷該管理人員是否有修改權限
					if (manager.mStaff & 2) {
						UPDATE = "";
					}
					//判斷該管理人員是否有刪除權限
					if (manager.mStaff & 4) {
						DELETE = "";
					}
					//獲取所有人員清單
					getAllData("");
				} else {
					$("#manager-div").empty();
					swal({
                        title: '錯誤提示',
                        text: '權限不足無法訪問！，系統 3 秒後，自動移至功能列表！',
                        type: 'error',
                        confirmButtonText: "關閉",
                        timer: 3000,
                        width: 600,
                        padding: 50
                    },
                    function () {
                    	location.href = "/backstage/functionList.html";
                    });
				}
				//判斷是否該管理人員有新增權限，新增:8、刪除:4、修改:2、查詢:1
				if (manager.mStaff & 8) {
					$("#btn-add").show();
				}
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//獲取符合搜尋值的人員筆數
function getAllData(inputValue) {
	var url = "../backstage/manager/getAlls";
	var data = "inputValue="+inputValue;
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		//"contentType": "application/x-www-form-urlencoded; charset=UTF-8",
		"success": function(json) {
			if (json.state == 200) {
				var count = json.data; //總筆數
				console.log("count: "+count);
				if(count > 0){ 
					//獲取url中的id，參考 jquery-getUrlParam.js
					var page = 1; //即當前頁數
					var pageSize = 10; //每頁顯示的筆數
					var totalPages = 0;
					if (count % pageSize == 0) {
						totalPages = parseInt((count/pageSize)); //總頁數
					} else {
						totalPages = parseInt((count/pageSize)+1); //總頁數
					}
					if (page > totalPages){
						page = totalPages;
					}
					
					//獲取符合搜尋值的人員清單數據
					funList(page, pageSize, inputValue);

					//載入分頁效果
					var options = {//根據後臺返回的分頁相關資訊，設定外掛引數
		                    bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此標識，並且設定包含分頁內容的DOM元素為UL,如果是bootstrap2版本，則DOM包含元素是DIV
		                    currentPage : page, //當前頁數
		                    totalPages : totalPages, //總頁數
		                    numberOfPages : 10,//展示多少頁
		                    itemTexts : function(type, page, current) {//設定分頁按鈕顯示字型樣式
		                        switch (type) {
		                            case "first":
		                                return "首頁";
		                            case "prev":
		                                return "上一頁";
		                            case "next":
		                                return "下一頁";
		                            case "last":
		                                return "末頁";
		                            case "page":
		                                return page;
		                        }
		                    },
		                    onPageClicked : function(event, originalEvent, type, page) {//分頁按鈕點選事件
		                    	//獲取符合搜尋值的人員清單數據
		                    	funList(page, pageSize, inputValue);
		                    }
		                };
					$('#mypage').bootstrapPaginator(options);//設定分頁
				} else {
					//假設搜尋結果數量為0
					$("#mypage").empty();
					$("#manager-list").empty();
					$("#manager-list").append('<tr>'
											+	'<td colspan="7"><h2><span style="color: red;">查詢不到該人員的資料</span></h2></td>'
										+	'</tr>');
				}
			} else {
				swal("獲取失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			swal("登入異常", "您的登入資訊已經過期或尚未登入，請重新登入！", "error");
			//location.href = "index.html";
		}
	});
}

//顯示功能清單
function funList(page, pageSize, inputValue) {
	var url = "../backstage/manager/getList";
	var data = "inputValue="+inputValue+"&page="+page+"&pageSize="+pageSize;
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				//console.log(JSON.stringify(json.data));
				var manager = json.data;
				//先清空人員列表
				$("#manager-list").empty();
				//遍歷獲取到的人員列表數據
				for (var i=0; i<manager.length; i++) {
					//判斷是否為自己的資料或是大於等於同職級的資料
					if ($("#managerId").val() == manager[i].id || $("#managerRoleId").val() >= manager[i].roleId) {
						UPDATE = "none";
						DELETE = "none";
					} else {
						UPDATE = "";
						DELETE = "";
					}
					//創建項目
					var html = '<tr>'
								+	'<td>#{id}</td>'
								+	'<td>#{username}</td>'
								+	'<td>#{name}</td>'
								+	'<td>#{roleName}</td>'
								+	'<td>#{email}</td>'
								+	'<td><a href="#" data-toggle="modal" data-target="#update-modal" onclick="getUpdateData(#{id})" class="btn btn-xs btn-info btn-update" style="display: ' + UPDATE + ';"><span class="fa fa-edit"></span> 修改</a></td>'
								+	'<td><a href="javascript:deleteConfirm(#{id})" class="btn btn-xs btn-danger btn-delete" style="display: ' + DELETE + ';"><span class="fa fa-trash-o"></span> 刪除</a></td>'
							+	'</tr>';
					//替換並賦正確的值
					html = html.replace(/#{id}/g, manager[i].id);
					html = html.replace(/#{username}/g, manager[i].username.substring(0,3)+"***");
					html = html.replace(/#{name}/g, manager[i].name);
					html = html.replace(/#{roleName}/g, manager[i].roleName);
					html = html.replace(/#{email}/g, manager[i].email);
					//添加項目
					$("#manager-list").append(html);
				}
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//刪除人員確認
function deleteConfirm(id) {
	swal({
		title: "刪除人員",
		text: "確定將該人員資料刪除？",
		type: "warning",
		showCancelButton: true,
		confirmButtonClass: "btn-primary",
		confirmButtonText: "確定",
		cancelButtonClass: "btn-danger",
		cancelButtonText: "取消",
		closeOnConfirm: false,
		closeOnCancel: true
	},
	function(isConfirm) {
		if (isConfirm) {
			//刪除人員
			deleteById(id);
		} else {
		    //swal("取消刪除", "刪除人員資料已取消！", "error");
		}
	});
}

//刪除人員
function deleteById(id) {
	var url = "../backstage/manager/delete/" + id;
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				//成功
				getAllData($("#search").val());
				swal("刪除成功", "該人員資料已刪除" , "success");
			} else {
				//失敗
				swal("刪除失敗", json.message, "error");
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			//console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "/backstage/index.html";
		}
	});
}

function getUpdateData(id) {
	var url = "../backstage/manager/getData/" + id;
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json){
			if (json.state == 200) {
				var manager = json.data;
				console.log(JSON.stringify(manager));
				$("#name").val(manager.name);
				$("#username").html(manager.username);
				$("#roleId").find("option[value='"+manager.roleId+"']").attr("selected","selected");
				$("#email").val(manager.email);
				if (manager.mStaff & 1) {
					$("#mStaff1").attr("checked","checked");
				}
				if (manager.mStaff & 2) {
					$("#mStaff2").attr("checked","checked");
				}
				if (manager.mStaff & 4) {
					$("#mStaff3").attr("checked","checked");
				}
				if (manager.mStaff & 8) {
					$("#mStaff4").attr("checked","checked");
				}
			}
		},//xhr: XMLHttpRequest
		"error": function(xhr, text, errorThrown){
			//console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "/backstage/index.html";
		}
	});
}