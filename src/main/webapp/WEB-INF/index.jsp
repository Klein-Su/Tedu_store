<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>學子商城</title>
		<!--導入核心文件-->
		<script src="../bootstrap3/js/holder.js"></script>
		<link href="../bootstrap3/css/bootstrap.css" rel="stylesheet" type="text/css">
		<script src="../bootstrap3/jquery-1.9.1.min.js"></script>
		<script src="../bootstrap3/js/bootstrap.js"></script>
		<!-- 導入首頁的js文件 -->
		<script src="../js/index.js"></script>
		<!-- 字體圖標 -->
		<link rel="stylesheet" href="../bootstrap3/font-awesome-4.7.0/css/font-awesome.css" />
		<link rel="stylesheet" type="text/css" href="../css/layout.css" />
		<link rel="stylesheet" type="text/css" href="../css/webindex.css" />
		<link rel="stylesheet" type="text/css" href="../css/footer.css" />
		<link rel="stylesheet" type="text/css" href="../css/top.css" />
		<script src="../js/menu.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<!-- 載入 header -->
		<%@ include file="header.jsp" %>
		
		<!-- article -->
		<!--導航-->
		<!--分割導航和頂部-->
		<div class="clearfix"></div>
		<hr />
		<div class="col-md-12 top-nav">
			<div class="col-md-6">
				<ul class="nav nav-pills">
					<li>
						<a href="#"></a>
					</li>
					<li class="active"><a href="index.html"><span class="fa fa-home"></span></a></li>
					<li><a href="#">秒殺</a></li>
					<li><a href="#">優惠券</a></li>
					<li><a href="#">學子VIP</a></li>
					<li><a href="#">外賣</a></li>
					<li><a href="#">超市</a></li>
				</ul>
			</div>
			<div class="col-md-6">
				<form action="search.html" class="form-inline" role="form" style="float: right;">
					<div class="form-group">
						<input type="text" class="form-control" id="search" name="search" placeholder="請輸入商品名稱進行搜索">
					</div>
					<button type="submit" class="btn btn-default btn-sm"><span class="fa fa-search"></span></button>
				</form>
			</div>
		</div>
		<!--導航結束-->
		<div class="clearfix"></div>
		<div class="col-md-2" id="indexMenu">
			<ul class="index-menu">

			</ul>
			<div id="showIndex">
				<ul class="second-menu">
					<li class="second-menu-li">####</li>
				</ul>
			</div>
			<div id="showSecond">
				<ul class="third-menu">

				</ul>
			</div>
		</div>
		<div class="col-md-10">
			<div id="myCarousel" class="carousel slide">
				<!-- 輪播(Carousel)指標 -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
					<li data-target="#myCarousel" data-slide-to="3"></li>
					<li data-target="#myCarousel" data-slide-to="4"></li>
				</ol>
				<!-- 輪播(Carousel)項目 -->
				<div class="carousel-inner" align="center">
					<div class="item active">
						<img src="../images/index/index_banner1.png">
					</div>
					<div class="item">
						<img src="../images/index/index_banner2.png">
					</div>
					<div class="item">
						<img src="../images/index/index_banner3.png">
					</div>
					<div class="item">
						<img src="../images/index/index_banner4.png">
					</div>
					<div class="item">
						<img src="../images/index/index_banner5.png">
					</div>
				</div>
				<!-- 輪播(Carousel)導航 -->
				<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
		<div class="clearfix"></div>
		<hr/>
		<!--推薦攔目-->
		<div class="col-md-offset-1 col-md-5">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">新到好貨</h3>
				</div>
				<div class="panel-body panel-item">
					<div class="col-md-12">
						<div class="col-md-7 text-row-2"><a href="product.html">齐心（COMIX）C5902 A5优品商务笔记本子记事本日记本122张</a></div>
						<div class="col-md-2">¥18</div>
						<div class="col-md-3"><img src="../images/portal/02COMIXC5902A5122blue/collect.png" width="100%" /></div>
					</div>
					<div class="col-md-12">
						<div class="col-md-7  text-row-2"><a href="product.html">得力（deli）1548A商务办公桌面计算器 太阳能双电源</a></div>
						<div class="col-md-2">¥50</div>
						<div class="col-md-3"><img src="../images/portal/002calculator1548A/collect.png" width="100%" /></div>
					</div>
					<div class="col-md-12">
						<div class="col-md-7 text-row-2"><a href="product.html">戴尔(DELL)XPS13-9360-R1609 13.3</a></div>
						<div class="col-md-2">¥6299</div>
						<div class="col-md-3"><img src="../images/portal/12(DELL)XPS13gold/collect.png" width="100%" /></div>
					</div>
					<div class="col-md-12">
						<div class="col-md-7 text-row-2"><a href="product.html">联想（Lenovo）IdeaPad310高配版</a></div>
						<div class="col-md-2">¥5298</div>
						<div class="col-md-3"><img src="../images/portal/13LenovoIdeaPad310_black/collect.png" width="100%" /></div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-5">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">熱銷排行</h3>
				</div>
				<div id="list-hot" class="panel-body panel-item">
					<div class="col-md-12">
						<div class="col-md-7 text-row-2"><a href="product.html">广博(GuangBo)10本装40张A5牛皮纸记事本子日记本办公软抄本GBR0731</a></div>
						<div class="col-md-2">¥23</div>
						<div class="col-md-3"><img src="../images/portal/00GuangBo1040A5GBR0731/collect.png" width="100%" /></div>
					</div>
					<div class="col-md-12">
						<div class="col-md-7 text-row-2"><a href="product.html">齐心（COMIX）C5902 A5优品商务笔记本子记事本日记本122张</a></div>
						<div class="col-md-2">¥18</div>
						<div class="col-md-3"><img src="../images/portal/02COMIXC5902A5122blue/collect.png" width="100%" /></div>
					</div>
					<div class="col-md-12">
						<div class="col-md-7 text-row-2"><a href="product.html">广博(GuangBo)皮面日程本子 计划记事本效率手册米色FB60322</a></div>
						<div class="col-md-2">¥28</div>
						<div class="col-md-3"><img src="../images/portal/001GuangBo)FB60322/collect.png" width="100%" /></div>
					</div>
					<div class="col-md-12">
						<div class="col-md-7 text-row-2"><a href="product.html">戴尔Dell 燃700R1605银色</a></div>
						<div class="col-md-2">¥3799</div>
						<div class="col-md-3"><img src="../images/portal/11DELLran7000R1605Ssilvery/collect.png" width="100%" /></div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		
		<!-- 載入 footer -->
		<%@ include file="footer.jsp" %>
	</body>
</html>