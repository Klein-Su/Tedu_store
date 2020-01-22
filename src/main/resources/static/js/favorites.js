$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html");
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//載入獲取收藏筆數
	getAllFavorites();

	/* 商品列表，滑鼠移入時加隱影、移出時移除隱影 */
	$(".goods-panel").hover(function() {
		$(this).css("box-shadow", "0px 0px 8px #888888");

	}, function() {
		$(this).css("box-shadow", "");
	});
	
	//加入收藏時，♥的實心空心切換
	$(".add-fav").click(function() {
		if ($(this).find("span").attr("class") == "fa fa-heart") {
			$(this).html("<span class='fa fa-heart-o'></span>加入收藏");
		} else {
			$(this).html("<span class='fa fa-heart'></span>取消收藏");
		}
	});
})

//獲取收藏筆數
function getAllFavorites() {
	var url = "../favorites/getAllFavorites";
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				var count = json.data; //總筆數
				if(count > 0){ 
					//獲取url中的id，參考 jquery-getUrlParam.js
					//當前頁面處理的商品的id
					var page = $.getUrlParam("page"); //想獲取page，參數就設置"page"，即當前頁數
					var totalPages = 0;
					if(count % 12 == 0) {
						totalPages = parseInt((count/12)); //總頁數
					} else {
						totalPages = parseInt((count/12)+1); //總頁數
					}
					if(page == null || page == "" || isNaN(page) || page <=0) {
						page = 1;
					} else if(page > totalPages){
						page = totalPages;
					}
					//載入收藏列表
					showFavoritesList(page);

					//載入分頁效果
					var options = {//根據後臺返回的分頁相關資訊，設定外掛引數
		                    bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此標識，並且設定包含分頁內容的DOM元素為UL,如果是bootstrap2版本，則DOM包含元素是DIV
		                    currentPage : page, //當前頁數
		                    totalPages : totalPages, //總頁數
		                    numberOfPages : 5,//展示多少頁
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
		                    	showFavoritesList(page);
		                    }
		                };
					$('#mypage').bootstrapPaginator(options);//設定分頁
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

//顯示收藏列表
function showFavoritesList(page) {
	if(page == null || page == "" || isNaN(page) || page <=0) {
		page = 1;
	}
	var url = "../favorites/list";
	var data = "page=" + page;
	$.ajax({
		"url": url,
		"data": data,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if(json.state == 200) {
				var list = json.data;
				console.log(list.length);
				//先清空收藏列表
				$("#favorites-list").empty();
				//創建項目
				var html;
				for (var i=0; i<list.length; i++) {
					//list-div 的id，取商數
					var listDivId = parseInt(i / 4) + 1;
					//以4個項目為一排
					if (i % 4 == 0) {
						var html = '<div id="list-div-'+ listDivId +'" class="col-md-offset-1 col-md-10"></div>';
						$("#favorites-list").append(html);
					}
					//獲取收藏當中商品的數據
					var goods = list[i].goods[0];
						
					var goodsHtml = '<div class="col-md-3">'
									+	'<div class="goods-panel">'
										+	'<div class="move-img img-search">'
											+	'<img src="..#{image}collect.png" width="100%"/>'
										+	'</div>'
										+	'<h4 id="goodsPrice#{gid}">$ #{price}</h4>'
										+	'<h5 class="text-row-3"><a href="product.html?id=#{gid}"><small>#{title}</small></a></h5>'
										+	'<span>'
											+	'<a onclick="deleteById(#{id});" class="btn btn-danger btn-xs add-fav"><span class="fa fa-heart"></span>取消收藏</a>&nbsp;'
											+	'<a onclick="addToCart(#{gid})" class="btn btn-default btn-xs add-cart"><span class="fa fa-cart-arrow-down"></span>加入購物車</a>'
										+	'</span>'
									+	'</div>'
								+	'</div>'
					
					goodsHtml = goodsHtml.replace(/#{id}/g, list[i].id);
					goodsHtml = goodsHtml.replace(/#{image}/g, goods.image);
					goodsHtml = goodsHtml.replace(/#{gid}/g, goods.id);
					goodsHtml = goodsHtml.replace(/#{price}/g, FormatNumber(goods.price));
					goodsHtml = goodsHtml.replace(/#{title}/g, goods.title);
					
					$("#list-div-"+listDivId).append(goodsHtml);
				}
				
			} else {
				swal("獲取失敗", "收藏列表獲取失敗！", "error");
			}
		},
		"error": function(xhr, text, errorThrown) {
			console.log("xhr.status=" + xhr.status);
			swal("登入異常", "您的登入信息已經過期！請重新登入！", "error");
			location.href = "index.html";
		}
	});
}

//添加到購物車
function addToCart(id) {
	var url = "../cart/add_to_cart";
	// gid=xx&price=xx&count=xx
	var gid = id;
	var price = $("#goodsPrice"+gid).html().replace(/[^0-9]/ig,"");
	var count = 1;
	var data = "gid=" + gid + "&price=" + price + "&count=" + count;
	console.log("[data] = "+ data)
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				swal("添加成功", "商品已添加到購物車！", "success");
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

//取消收藏
function deleteById(id) {
	var page = $.getUrlParam("page"); //想獲取id，參數就設置"id"
	var url = "../favorites/delete/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				swal("取消成功", "已將此商品取消收藏！", "success");
				showFavoritesList(page);
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