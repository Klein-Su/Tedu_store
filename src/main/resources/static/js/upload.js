$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html", function(){
		//隱藏header搜尋欄位
		$("#header-search-div").hide();
	});
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
//	//判斷用戶登入後是否有頭像路徑，有則顯示
//	if ($.cookie("avatar") != null) {
//		$("#img-avatar").attr("src", $.cookie("avatar"));
//	}
	
	//上傳按鈕綁定點擊事件	
	$("#btn-upload").click(function(){
		//上傳頭像
		uploadAvatar();
	});
	
	//檔案上傳圖片的觸發事件
    $("#file").change(function () {
        //圖片預覽程式
        preview(this);
    });
    
    //清除圖片預覽及檔案
    $("#clear_image").click(function () {
        $("#file").filestyle("clear"); //移除圖片檔案
        $(".preview").attr("src", ""); //移除圖片預覽
    });
})

//圖片預覽
function preview(input) {
	var file = input.files[0]; //定義file = 發生改的file
    var size = file.size; //檔案大小
    var type = file.type; //檔案類型
    
  //驗證檔案格式是否為 JPG、GIF、PNG、BMP 檔
    if (type != 'image/png' && type != 'image/jpg' && type != 'image/gif' && type != 'image/jpeg' && type != 'image/bmp') {
        swal("上傳失敗", "檔案格式只接受 JPG、GIF、PNG、JPEG、BMP 檔，請重新上傳！", "error");
    	$('#file').filestyle('clear'); //移除圖片檔案
        $('.preview').attr('src', ''); //移除圖片預覽
    } else if (size > 5000000) { //假如檔案大小超過 2MB (2000000/1024)
    	swal("上傳失敗", "檔案大小超過 5 MB，請重新上傳！", "error");
    	$('#file').filestyle('clear'); //移除圖片檔案
        $('.preview').attr('src', ''); //移除圖片預覽
    } else {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('.preview').attr('src', e.target.result);
                $('#img_preview_url').attr('href', e.target.result)
                // var KB = format_float(e.total / 1024, 2);
                // $('.size').text("檔案大小：" + KB + " KB");
            }
            reader.readAsDataURL(input.files[0]);
            //fancybox
            $('[data-fancybox="preview"]').fancybox({
                //options
            });
        }
    }
}

//上傳頭像
function uploadAvatar() {
	//將請求提到到哪裡
	//當前位置：web/upload.html
	//目標位置：user/upload.do
	var url = "../user/upload.do";
	//請求參數
	var data = new FormData($("#form-upload")[0]);
	console.log("修改頭像參數："+data);
	//發出ajax請求，並處理結果
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"contentType": false,
		"processData": false,
		"success": function(json){
			if (json.state == 200){
				//alert("修改頭像成功！");
				swal("修改成功", "修改頭像成功！", "success");
				$("#img-avatar").attr("src", json.data);
				$("#file").filestyle("clear"); //移除圖片檔案
		        $(".preview").attr("src", ""); //移除圖片預覽
				//重新更新cookie當中的avatar值
				$.cookie("avatar", json.data, {
					expires: 7
			    });
			} else {
				swal("修改失敗", json.message, "error");
			}
		},
		"error": function(){
			swal("修改失敗", "修改頭像失敗", "error");
			//alert("修改頭像出錯！");
		}
	});
}

