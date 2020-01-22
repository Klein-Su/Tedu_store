$(function() {
	//載入 header 頁面
	$("#header-div").load("../web/header.html");
	//載入 footer 頁面
	$("#footer-div").load("../web/footer.html");
	
	//獲取符合搜尋值的商品筆數
	getAllData();

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
	
//	//載入 jquery.cookie.js 檔
//	$.getScript( "../bootstrap3/js/jquery.cookie.js", function(data, textStatus, jqxhr) {
//		console.log( "載入jquery.cookie.js成功！" );
//		if(jqxhr.status == 200){
//			//判斷cookie是否有值
//			if ($.cookie("username") != null) {
//				alert("有登入");
//			}
//		}
//	});
});

//獲取符合搜尋值的商品筆數
function getAllData() {
	var url = "../goods/getAlls";
	/*
	 * escape：將字符串進行編碼，解決中文搜尋的問題
	 * unescape：將escape編碼過的字符串進行解碼的動作
	 */
	var categoryId = $.getUrlParam("categoryId");
	var categoryName = escape($.getUrlParam("categoryName"));
	var brand = escape($.getUrlParam("brand"));
	var brandName = "";
	var beginPrice = $.getUrlParam("beginPrice");
	var endPrice = $.getUrlParam("endPrice");
	var priceName = "";
	var inputValue = escape($.getUrlParam("search"));
	var data = "categoryId="+categoryId+"&brand="+brand+"&beginPrice="+beginPrice+
				"&endPrice="+endPrice+"&inputValue="+inputValue;
	
	if(categoryName!=""){
		categoryName = decodeURI(categoryName) + "<span style='color: #000000;'> 》</span>";
	}
	if(brand!=""){
		brandName = decodeURI(brand) + "<span style='color: #000000;'> 》</span>";
	}
	if(beginPrice =="" && endPrice != ""){
		priceName = endPrice +"元 以下 <span style='color: #000000;'> 》</span>";
	} else if (beginPrice !="" && endPrice != "") {
		priceName = (parseInt(beginPrice)+1) +"元 ~ "+ endPrice +"元 <span style='color: #000000;'> 》</span>";
	} else if (beginPrice !="" && endPrice == "") {
		priceName = (parseInt(beginPrice)+1) +"元 以上 <span style='color: #000000;'> 》</span>";
	}
	$("#search-result").html(categoryName+""+brandName+priceName+decodeURI(inputValue));
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		//"contentType": "application/x-www-form-urlencoded; charset=UTF-8",
		"success": function(json) {
			if (json.state == 200) {
				var count = json.data; //總筆數
				//console.log("count: "+count);
				if(count > 0){ 
					//獲取url中的id，參考 jquery-getUrlParam.js
					var page = $.getUrlParam("page"); //想獲取page，參數就設置"page"，即當前頁數
					var pageSize = 12; //每頁顯示的筆數
					var totalPages = 0;
					if(count % pageSize == 0) {
						totalPages = parseInt((count/pageSize)); //總頁數
					} else {
						totalPages = parseInt((count/pageSize)+1); //總頁數
					}
					if(page == null || page == "" || isNaN(page) || page <=0) {
						page = 1;
					} else if(page > totalPages){
						page = totalPages;
					}
					//顯示當前頁面和筆數
					$("#search-count").html(count);
					$("#search-currentPage").html(page);
					//獲取符合搜尋值的商品數據
					getSearchData(categoryId, brand, beginPrice, endPrice, page, pageSize, inputValue);

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
		                    	//獲取符合搜尋值的商品數據
		    					getSearchData(categoryId, brand, beginPrice, endPrice, page, pageSize, inputValue);
		    					$("#search-currentPage").html(page);
		                    }
		                };
					$('#mypage').bootstrapPaginator(options);//設定分頁
				} else {
					//顯示當前頁面和筆數
					$("#search-count").html("0");
					$("#search-currentPage").html("--");
					//假設搜尋結果數量為0
					$("#search-list").append('<div class="col-md-offset-1 col-md-10">'
											+	'<h2><span style="color: red;">查詢不到該商品的資料</span></h2>'
										+	'</div>');
				}
			} else {
				swal("獲取失敗", json.message, "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			//swal("登入異常", "您的登入資訊已經過期或尚未登入，請重新登入！", "error");
			//location.href = "index.html";
		}
	});
}

//獲取符合搜尋值的商品數據
function getSearchData(categoryId, brand, beginPrice, endPrice, page, pageSize, inputValue) {
	var url = "../goods/getSearchData";
	var data = "categoryId="+categoryId+"&brand="+brand+"&beginPrice="+beginPrice+
			"&endPrice="+endPrice+"&inputValue="+inputValue+"&page="+page+"&pageSize="+pageSize;
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if(json.state == 200) {
				var list = json.data;
				//console.log("符合搜尋的商品數據"+list.length);
				//先清空收藏列表
				$("#search-list").empty();
				//創建項目
				var html;
				for (var i=0; i<list.length; i++) {
					//list-div 的id，取商數
					var listDivId = parseInt(i / 4) + 1;
					//以4個項目為一排
					if (i % 4 == 0) {
						var html = '<div id="list-div-'+ listDivId +'" class="col-md-offset-1 col-md-10"></div>';
						$("#search-list").append(html);
					}
					var goodsHtml = '<div class="col-md-3">'
									+	'<div class="goods-panel">'
										+	'<div class="move-img img-search">'
											+	'<img src="..#{image}collect.png" width="100%"/>'
										+	'</div>'
										+	'<h4 id="goodsPrice#{gid}">$ #{price}</h4>'
										+	'<h5 class="text-row-3"><a href="product.html?id=#{gid}"><small>#{title}</small></a></h5>'
										+	'<span>'
											+	'<input type="hidden" id="favoritesId-#{gid}" value="">'
											+	'<a id="btn-add-to-favorites-#{gid}" onclick="addToFavorites(#{gid});" class="btn btn-default btn-xs add-fav"><span class="fa fa-heart-o"></span>加入收藏</a>'
											+	'<a id="btn-delete-from-favorites-#{gid}" onclick="deleteById(#{gid});" class="btn btn-danger btn-xs add-fav" style="display: none;"><span class="fa fa-heart"></span>取消收藏</a>'
											+	'&nbsp;<a onclick="addToCart(#{gid})" class="btn btn-default btn-xs add-cart"><span class="fa fa-cart-arrow-down"></span>加入購物車</a>'
										+	'</span>'
									+	'</div>'
								+	'</div>'
					
					goodsHtml = goodsHtml.replace(/#{id}/g, list[i].id);
					goodsHtml = goodsHtml.replace(/#{image}/g, list[i].image);
					goodsHtml = goodsHtml.replace(/#{gid}/g, list[i].id);
					goodsHtml = goodsHtml.replace(/#{price}/g, FormatNumber(list[i].price));
					goodsHtml = goodsHtml.replace(/#{title}/g, list[i].title);
					
					$("#list-div-"+listDivId).append(goodsHtml);
					
					//判斷cookie是否有值
					if ($.cookie("username") != null) {
						//確認是否為收藏商品
						checkFavorite(list[i].id);
					}
				}
				
			} else {
				swal("獲取失敗", "搜尋列表獲取失敗！", "error");
			}
		}
	});
}

//確認是否為收藏商品
function checkFavorite(gid) {
	var url = "../favorites/findById";
	var data = "gid=" + gid;
	//console.log("data="+data);
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {	
			if (json.state == 200) {
				if(json.data == null) {
					$("#favoritesId-"+gid).val("");
					$("#btn-add-to-favorites-"+gid).show();
					$("#btn-delete-from-favorites-"+gid).hide();
				} else {
					$("#favoritesId-"+gid).val(json.data.id);
					$("#btn-add-to-favorites-"+gid).hide();
					$("#btn-delete-from-favorites-"+gid).show();
				}
			} else {
				swal("獲取失敗", "獲取確認是否為該登入者所收藏商品失敗！", "error");
			}
		},
		"error": function(xhr, text, errorThrown){
			
		}
	});
}

//添加到購物車
function addToCart(gid) {
	var url = "../cart/add_to_cart";
	var price = $("#goodsPrice"+gid).html().replace(/[^0-9]/ig,"");
	var count = 1;
	var data = "gid=" + gid + "&price=" + price + "&count=" + count;
	//console.log("[data] = "+ data)
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

//加入收藏
function addToFavorites(gid) {
	var url = "../favorites/add_to_favorites";
	var data = "gid=" + gid;
	//console.log("[data] = "+ data)
	$.ajax({
		"url": url,
		"data": data,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				swal("新增成功", "已將此商品新增到收藏列表中！", "success");
				$("#favoritesId-"+gid).val(json.data);
				$("#btn-add-to-favorites-"+gid).hide();
				$("#btn-delete-from-favorites-"+gid).show();
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
function deleteById(gid) {
	var id = $("#favoritesId-"+gid).val();
	var url = "../favorites/delete/" + id;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json) {
			if (json.state == 200) {
				swal("取消成功", "已將此商品取消收藏！", "success");
				$("#favoritesId-"+gid).val("");
				$("#btn-add-to-favorites-"+gid).show();
				$("#btn-delete-from-favorites-"+gid).hide();
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