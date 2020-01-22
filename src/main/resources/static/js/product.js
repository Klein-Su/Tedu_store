$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html");
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	/*商品小圖片加滑鼠移入加邊框、移出移除邊框*/
	$(".img-small").hover(function() {
			$(this).css("border", "1px solid #4288c3");
		},
		function() {
			$(this).css("border", "");
		})
	//點擊時變化大圖片
	$(".img-small").click(function() {
			//獲得點擊的小圖片數據
			var n = $(this).attr("data");
			//所有大圖隱藏
			$(".img-big").hide();
			//顯示點擊的小圖對應的大圖
			$(".img-big[data='" + n + "']").show();

		})
		//購物數量+1
	$("#numUp").click(function() {
		var n = parseInt($("#num").val());
		$("#num").val(n + 1);
	})

	//購物數量-1
	$("#numDown").click(function() {
		var n = parseInt($("#num").val());
		if (n == 1) {
			return;
		}
		$("#num").val(n - 1);
	})

	//點購物車跳頁面
//	$(".go-cart").click(function() {
//		location.href = "cart.html";
//	});

	$(".img-big:eq(0)").show();
	
	//**********************************
	
	//獲取url中的id，參考 jquery-getUrlParam.js
	//當前頁面處理的商品的id
	var id = $.getUrlParam("id"); //想獲取id，參數就設置"id"
	console.log("id=" + id);
	
	//顯示商品詳情
	showGoodsDetails(id);
	
	//確認是否為收藏商品
	checkFavorite(id);
	
	//處理"立即購買"
	$("#btn-to-buy").click(function(){
		checkToCart(id, true);
	});
	
	//處理"添加到購物車"
	$("#btn-add-to-cart").click(function(){
		checkToCart(id, false);
	});
	
	//處理"添加到收藏"
	$("#btn-add-to-favorites").click(function(){
		addToFavorites(id);
	});
	
	//處理"取消收藏"
	$("#btn-delete-from-favorites").click(function(){
		deleteById();
	});
})

//顯示商品詳細資訊
function showGoodsDetails(id) {
	var url = "../goods/details/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			var goods = json.data;
			if (goods == null) {
				location.href = "index.html";
			}
			
			//商品資訊
			$("#goods-title").attr("title", goods.title);
			$("#goods-title").html(goods.title);
			$("#goods-sell-point").html(goods.sellPoint);
			$("#goods-price").html(FormatNumber(goods.price));
			
			//圖片資訊(大圖)
			$("#img1-big").attr("src", ".." + goods.image + "1_big.png");
			$("#img2-big").attr("src", ".." + goods.image + "2_big.png");
			$("#img3-big").attr("src", ".." + goods.image + "3_big.png");
			$("#img4-big").attr("src", ".." + goods.image + "4_big.png");
			$("#img5-big").attr("src", ".." + goods.image + "5_big.png");
			//圖片資訊(小圖)
			$("#img1-small").attr("src", ".." + goods.image + "1.jpg");
			$("#img2-small").attr("src", ".." + goods.image + "2.jpg");
			$("#img3-small").attr("src", ".." + goods.image + "3.jpg");
			$("#img4-small").attr("src", ".." + goods.image + "4.jpg");
			$("#img5-small").attr("src", ".." + goods.image + "5.jpg");
		},
		"error": function() {
			location.href = "index.html";
		}
	});
}

//確認是否為收藏商品
function checkFavorite(id) {
	var url = "../favorites/findById";
	var gid = id;
	var data = "gid=" + gid;
	console.log("data="+data);
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {	
			if (json.state == 200) {
				if(json.data == null) {
					$("#favoritesId").val("");
					$("#btn-add-to-favorites").show();
					$("#btn-delete-from-favorites").hide();
				} else {
					$("#favoritesId").val(json.data.id);
					$("#btn-add-to-favorites").hide();
					$("#btn-delete-from-favorites").show();
				}
			} else {
				swal("獲取失敗", "獲取確認是否為該登入者所收藏商品失敗！", "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//確認添加到購物車的資料是否正確
function checkToCart(id, target_html) {
	var count = $("#num").val();
	var valid = true; //判斷數字格式是否符合
	//比對是否為數字
	if(!/[^0-9\.-]/g.test(count)){
		//數量是否符合1~999
		if(count <=0 || count >= 1000){
    		$("#num").val("1");
    		$("#num-error").html("商品數量須為1~999之間");
    		$("#num-error").show();
    		valid = false;
    	} else {
    		$("#num-error").hide();
    	}
	} else {
		$("#num-error").html("請填入非負數的數字");
		$("#num-error").show();
		valid = false;
	}
	//判斷數量是否符合格式
	if(valid){ 
		addToCart(id, target_html);
	}
}

//添加到購物車
function addToCart(id, target_html) {
	var url = "../cart/add_to_cart";
	// gid=xx&price=xx&count=xx
	var gid = id;
	var price = $("#goods-price").html().replace(/[^0-9]/ig,"");
	var count = $("#num").val();
	var data = "gid=" + gid + "&price=" + price + "&count=" + count;
	console.log("[data] = "+ data)
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				if(target_html){
					location.href = "cart.html";
				} else {
					swal("添加成功", "商品已添加到購物車！", "success");
				}
			} else {
				swal("添加失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			swal("添加異常", "您的登入資訊已經過期或尚未登入，請重新登入！", "error");
			//location.href = "index.html";
		}
	});
}

//加入收藏
function addToFavorites(id) {
	var url = "../favorites/add_to_favorites";
	var gid = id;
	var data = "gid=" + gid;
	console.log("[data] = "+ data)
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				swal("新增成功", "已將此商品新增到收藏列表中！", "success");
				checkFavorite(id);
			} else {
				swal("新增失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			swal("收藏異常", "您的登入資訊已經過期或尚未登入，請重新登入！", "error");
			//location.href = "index.html";
		}
	});
}

//取消收藏
function deleteById() {
	var id = $("#favoritesId").val();
	var url = "../favorites/delete/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				swal("取消成功", "已將此商品取消收藏！", "success");
				checkFavorite(id);
			} else {
				swal("取消失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			swal("取消收藏異常", "您的登入資訊已經過期或尚未登入，請重新登入！", "error");
			//location.href = "index.html";
		}
	});
}

//將數字以千分位做回傳
function FormatNumber(num) { 
	num += ""; //轉成字符串
	var reg = /(\d{1,3})(?=(\d{3})+$)/g; 
	return num.replace(reg,"$1,"); 
}