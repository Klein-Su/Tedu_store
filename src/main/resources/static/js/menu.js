////修改這個reqpath變數為實際控制器的地址，如../showGoods.do
//var reqpath = "search.html"
//
//ajax獲得的json物件
//var typelist = [{
//		"id": "1",
//		"parentId": "0",
//		"name": "图书音像"
//	}, {
//		"id": "2",
//		"parentId": "0",
//		"name": "家用电器"
//	}, {
//		"id": "3",
//		"parentId": "0",
//		"name": "电脑办公"
//	}, {
//		"id": "4",
//		"parentId": "0",
//		"name": "个护化妆"
//	}, {
//		"id": "5",
//		"parentId": "0",
//		"name": "钟表"
//	}, {
//		"id": "6",
//		"parentId": "0",
//		"name": "母婴"
//	}, {
//		"id": "7",
//		"parentId": "0",
//		"name": "食品饮料"
//	}, {
//		"id": "8",
//		"parentId": "0",
//		"name": "汽车用品"
//	}, {
//		"id": "9",
//		"parentId": "0",
//		"name": "玩具乐器"
//	}, {
//		"id": "10",
//		"parentId": "0",
//		"name": "手机"
//	}, {
//		"id": "11",
//		"parentId": "0",
//		"name": "数码"
//	}, {
//		"id": "12",
//		"parentId": "0",
//		"name": "家居家装"
//	}, {
//		"id": "13",
//		"parentId": "0",
//		"name": "厨具"
//	}, {
//		"id": "14",
//		"parentId": "0",
//		"name": "服饰内衣"
//	}, {
//		"id": "15",
//		"parentId": "0",
//		"name": "鞋靴"
//	}, {
//		"id": "16",
//		"parentId": "0",
//		"name": "礼品箱包"
//	}, {
//		"id": "17",
//		"parentId": "0",
//		"name": "珠宝"
//	}, {
//		"id": "18",
//		"parentId": "0",
//		"name": "运动健康"
//	}, {
//		"id": "19",
//		"parentId": "0",
//		"name": "充值票务"
//	}, {
//		"id": "20",
//		"parentId": "3",
//		"name": "电脑整机"
//	}, {
//		"id": "21",
//		"parentId": "3",
//		"name": "电脑配件"
//	}, {
//		"id": "22",
//		"parentId": "3",
//		"name": "外设产品"
//	}, {
//		"id": "23",
//		"parentId": "3",
//		"name": "网络产品"
//	}, {
//		"id": "24",
//		"parentId": "3",
//		"name": "办公设备"
//	}, {
//		"id": "25",
//		"parentId": "3",
//		"name": "文具耗材"
//	}, {
//		"id": "26",
//		"parentId": "3",
//		"name": "服务产品"
//	}, {
//		"id": "27",
//		"parentId": "20",
//		"name": "笔记本"
//	}, {
//		"id": "28",
//		"parentId": "20",
//		"name": "超极本"
//	}, {
//		"id": "29",
//		"parentId": "20",
//		"name": "游戏本"
//	}, {
//		"id": "30",
//		"parentId": "20",
//		"name": "平板电脑"
//	}, {
//		"id": "31",
//		"parentId": "20",
//		"name": "平板电脑配件"
//	}, {
//		"id": "32",
//		"parentId": "20",
//		"name": "台式机"
//	}, {
//		"id": "33",
//		"parentId": "20",
//		"name": "服务器工作站"
//	}, {
//		"id": "34",
//		"parentId": "20",
//		"name": "笔记本配件"
//	},
//
//]

$(function() {
	//預要儲存分類商品的變數
	//var typelist = null;
	//獲取商品分類
	//getGoodsCategory(0);
	//初始化menu
	initMenu();
	
//	//根據輪播圖片的高，導航的高
//	//獲得輪播圖片的高
//	var lunh = $("#myCarousel").height();
//	//獲取導航的高
//	var lih = (lunh - 10) / 19;
//
//	//確定導航高度
//	$(".index-menu li").css("height", lih + "px")
//
//	//確定按鈕位置
//	var btnt = Math.floor($("#myCarousel").height() / 2 - 30);
//
//	$(".left").css("margin-top", btnt + "px");
//	$(".right").css("margin-top", btnt + "px");
//	
//	//左側分類一級menu控制二級menu顯示和隱藏
//	$(".index-menu").hover(function() {
//			$("#showIndex").show();
//		}, function() {
//			$("#showIndex").hide();
//		})
//	//左側分類二級menu控制三級menu顯示和隱藏
//	$(".second-menu").hover(function() {
//		$("#showSecond").show();
//	}, function() {
//		$("#showSecond").hide();
//	})
//
//	//二級menu自己控制顯示和隱藏
//	$("#showIndex").hover(function() {
//		$("#showIndex").show();
//	}, function() {
//		$("#showIndex").hide();
//	})
//
//	//三級menu自己控制顯示和隱藏
//	$("#showSecond").hover(function() {
//		$("#showIndex").show();
//		$("#showSecond").show();
//	}, function() {
//		$("#showIndex").hide();
//		$("#showSecond").hide();
//	})
//
//
//	//一級分類項li滑鼠移入移出
//	var offTop = -100;
//	var offLeft = 0;
//	$(".index-menu li").hover(function() {
//		$(".second-menu").empty();
//		//加載json數據
//		for (var i = 0; i < typelist.length; i++) {
//			if ($(this).attr("data") == typelist[i].parentId) {
//				$(".second-menu").append($("<li class='second-menu-li' data='" + typelist[i].id + "' >" + typelist[i].name + "</li>"))
//			}
//		}
//		offLeft = $(this).width() + $(this).offset().left;
//		offTop = $(this).offset().top;
//		$("#showIndex").css("top", offTop - 2 + "px")
//		$("#showIndex").css("left", offLeft - 1 + "px")
//		$(this).css("background-color", "#ffffff");
//		$(this).css("color", "#4288c3");
//	}, function() {
//		$(this).css("background-color", "");
//		$(this).css("color", "");
//	})
//
//	//二級分類項li滑鼠移入移出
//	//$(".second-menu-li").live("mouseover", function() {
//	$(".second-menu-li").mouseover(function() {
//		$(".third-menu").empty();
//		/*加载数据*/
//		for (var i = 0; i < typelist.length; i++) {
//			if ($(this).attr("data") == typelist[i].parentId) {
//				$(".third-menu").append($("<li class='third-menu-li' data='" + typelist[i].id + "' ><a href='" + reqpath + "?typeid=" + typelist[i].id + "'>" + typelist[i].name + "</a></li>"))
//			}
//		}
//		//alert($(document).scrollTop() +":"+$(this).offset().top)
//		var ot = $(document).scrollTop() == $(this).offset().top ? offTop : $(this).offset().top;
//		var ol = $(this).width() + $(this).offset().left;
//		$("#showSecond").css("top", ot - 2 + "px");
//		$("#showSecond").css("left", ol + "px")
//		$(this).css("background-color", "#4288c3");
//		$(this).css("color", "#ffffff");
//	})
//	//$(".second-menu-li").live("mouseout", function() {
//	$(".second-menu-li").mouseout(function() {
//		$(this).css("background-color", "");
//		$(this).css("color", "");
//	})
//	//三級分類項li滑鼠移入移出
//	$(".third-menu-li").mouseover(function() {
//		$(this).css("background-color", "#eeeeee");
//		$(this).css("color", "#000000");
//	})
//	$(".third-menu-li").mouseout(function() {
//		$(this).css("background-color", "");
//		$(this).css("color", "");
//	})
});

//獲商品分類
function getGoodsCategory(parentId){
	var url = "../category/list/"+parentId;
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json){
			if(json.state== 200) {
				//typelist = json.data;
			} else {
				console.log("無法獲取到商品分類的資訊");
			}
		}
	});
}

//加載json數據到一級分類的方法
function initMenu() {
	var url = "../category/list/-1";
	$.ajax({
		"url": url,
		"type": "GET",
		"dataType": "json",
		"success": function(json){
			if(json.state== 200) {
				typelist = json.data;
				for (var i = 0; i < typelist.length; i++) {
					if (typelist[i].parentId == "0") {
						$(".index-menu").append($("<li data='" + typelist[i].id + "'>" + typelist[i].name + "</li>"))
					}
				}
				//根據輪播圖片的高，導航的高
				//獲得輪播圖片的高
				var lunh = $("#myCarousel").height();
				//獲取導航的高
				var lih = (lunh - 10) / 19;

				//確定導航高度
				$(".index-menu li").css("height", lih + "px")

				//確定按鈕位置
				var btnt = Math.floor($("#myCarousel").height() / 2 - 30);

				$(".left").css("margin-top", btnt + "px");
				$(".right").css("margin-top", btnt + "px");
				
				//左側分類一級menu控制二級menu顯示和隱藏
				$(".index-menu").hover(function() {
						$("#showIndex").show();
					}, function() {
						$("#showIndex").hide();
					})
				//左側分類二級menu控制三級menu顯示和隱藏
				$(".second-menu").hover(function() {
					$("#showSecond").show();
				}, function() {
					$("#showSecond").hide();
				})

				//二級menu自己控制顯示和隱藏
				$("#showIndex").hover(function() {
					$("#showIndex").show();
				}, function() {
					$("#showIndex").hide();
				})

				//三級menu自己控制顯示和隱藏
				$("#showSecond").hover(function() {
					$("#showIndex").show();
					$("#showSecond").show();
				}, function() {
					$("#showIndex").hide();
					$("#showSecond").hide();
				})


				//一級分類項li滑鼠移入移出
				var offTop = -100;
				var offLeft = 0;
				$(".index-menu li").hover(function() {
					$(".second-menu").empty();
					//加載json數據
					for (var i = 0; i < typelist.length; i++) {
						if ($(this).attr("data") == typelist[i].parentId) {
							$(".second-menu").append($("<li class='second-menu-li' data='" + typelist[i].id + "' >" + typelist[i].name + "</li>"))
						}
					}
					offLeft = $(this).width() + $(this).offset().left;
					offTop = $(this).offset().top;
					$("#showIndex").css("top", offTop - 2 + "px")
					$("#showIndex").css("left", offLeft - 1 + "px")
					$(this).css("background-color", "#ffffff");
					$(this).css("color", "#4288c3");
				}, function() {
					$(this).css("background-color", "");
					$(this).css("color", "");
				})

				//二級分類項li滑鼠移入移出
				//$(".second-menu-li").live("mouseover", function() {
				$(".second-menu-li").mouseover(function() {
					$(".third-menu").empty();
					/*加载数据*/
					for (var i = 0; i < typelist.length; i++) {
						if ($(this).attr("data") == typelist[i].parentId) {
							$(".third-menu").append($("<li class='third-menu-li' data='" + typelist[i].id + "' ><a href='" + reqpath + "?typeid=" + typelist[i].id + "'>" + typelist[i].name + "</a></li>"))
						}
					}
					//alert($(document).scrollTop() +":"+$(this).offset().top)
					var ot = $(document).scrollTop() == $(this).offset().top ? offTop : $(this).offset().top;
					var ol = $(this).width() + $(this).offset().left;
					$("#showSecond").css("top", ot - 2 + "px");
					$("#showSecond").css("left", ol + "px")
					$(this).css("background-color", "#4288c3");
					$(this).css("color", "#ffffff");
				})
				//$(".second-menu-li").live("mouseout", function() {
				$(".second-menu-li").mouseout(function() {
					$(this).css("background-color", "");
					$(this).css("color", "");
				})
				//三級分類項li滑鼠移入移出
				$(".third-menu-li").mouseover(function() {
					$(this).css("background-color", "#eeeeee");
					$(this).css("color", "#000000");
				})
				$(".third-menu-li").mouseout(function() {
					$(this).css("background-color", "");
					$(this).css("color", "");
				})
			} else {
				console.log("無法獲取到商品分類的資訊");
			}
		}
	});
}