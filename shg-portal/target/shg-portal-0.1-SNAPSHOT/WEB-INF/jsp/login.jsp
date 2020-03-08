<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>SHG-One.net</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />

<!-- BEGIN SCRIPT -->
<!--  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
<script src="resources/js/jquery.min.1.11.0.js"></script>
<script src="resources/js/login.js"></script>
<!-- END SCRIPT -->

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="resources/login/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="resources/login/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->

<link href="resources/login/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" />
<link rel="stylesheet" href="resources/login/plugins/revolution_slider/css/rs-style.css" media="screen">
<link rel="stylesheet" href="resources/login/plugins/revolution_slider/rs-plugin/css/settings.css" media="screen">
<link href="resources/login/plugins/bxslider/jquery.bxslider.css" rel="stylesheet" />
<!-- END PAGE LEVEL PLUGIN STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="resources/login/css/style-metronic.css" rel="stylesheet" type="text/css" />
<link href="resources/login/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/login/css/themes/blue.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="resources/login/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="resources/login/css/custom.css" rel="stylesheet" type="text/css" />
<link href="resources/login/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="resources/login/img/favicon.jpg" />
<!-- END THEME STYLES -->
<style type="text/css">
#errmsg {
	color: red;
}
</style>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-default navbar-static-top no-scrolling-fixed">
		<div class="container">
			<div class="navbar-header">
				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<button class="navbar-toggle btn navbar-btn" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<!-- END RESPONSIVE MENU TOGGLER -->
				<!-- BEGIN LOGO -->
				<a class="navbar-brand" href="/shg-portal"> <img src="resources/img/shg-one.png" alt="logo" class="img-responsive" width="150" height="50" />
				</a>
				<!-- END LOGO -->
			</div>

			<!-- BEGIN TOP NAVIGATION MENU -->
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false" href="#">Home</a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" data-delay="0" data-close-others="false" data-target="#" href="#"> About SHG-ONE </a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false" href="#"> Our Services </a></li>

					<li><a href="#" target="_blank">Contact Us</a></li>
					<li class="menu-search"><span class="sep"></span> <i class="fa fa-search search-btn"></i>

						<div class="search-box">
							<form action="#">
								<div class="input-group input-large">
									<input class="form-control" type="text" placeholder="Search"> <span class="input-group-btn">
										<button type="submit" class="btn theme-btn">Go</button>
									</span>
								</div>
							</form>
						</div></li>
				</ul>
			</div>
			<!-- BEGIN TOP NAVIGATION MENU -->
		</div>
	</div>
	<!-- END HEADER -->

	<!-- BEGIN PAGE CONTAINER -->
	<div class="page-container">
		<!-- BEGIN REVOLUTION SLIDER -->
		<div class="fullwidthbanner-container slider-main">
			<div class="fullwidthabnner">
				<ul id="revolutionul" style="display: none;">
					<li data-transition="fade" data-slotamount="8" data-masterspeed="700" data-delay="5000"><img src="resources/login/img/banner1.jpg" alt=""></li>
					<li data-transition="fade" data-slotamount="7" data-masterspeed="300" data-delay="5000"><img src="resources/login/img/banner2.jpg" alt=""></li>
				</ul>
				<div class="tp-bannertimer tp-bottom"></div>
			</div>
		</div>



		<!-- END REVOLUTION SLIDER -->

		<!-- BEGIN DASHBOARD STATS -->
		<!--
			<div style="padding:20px 20px 0 20px">
            <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat blue">
            <div class="visual"> <i class="fa"><img src="resources/login/img/dsh-icon1.png" width="72" height="73" alt=""></i> </div>
            <div class="details">
              <div class="number"> 550 </div>
              <div class="desc"> Members</div>
            </div>
             </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat green">
            <div class="visual"> <i class="fa"><img src="resources/login/img/dsh-icon2.png" width="75" height="75" alt=""></i> </div>
            <div class="details">
              <div class="number"> 125549 <i class="fa fa-inr" style="size:50px"></i></div>
              <div class="desc"> Monthly Saving</div>
            </div>
             </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat purple">
            <div class="visual"> <i class="fa"><img src="resources/login/img/dsh-icon3.png" width="75" height="75" alt=""></i> </div>
            <div class="details">
              <div class="number">5402548</div>
              <div class="desc"> Current Balance</div>
            </div>
             </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat yellow">
            <div class="visual"> <i class="fa"><img src="resources/login/img/dsh-icon4.png" width="75" height="75" alt=""></i> </div>
            <div class="details">
              <div class="number">584025</div>
              <div class="desc">Total Saving</div>
            </div>
             </div>
        </div>
      </div>
            </div>
 -->
		<!-- END DASHBOARD STATS -->

		<!-- BEGIN CONTAINER -->
		<div class="container">
			<!-- BEGIN SERVICE BOX -->
			<div class="row service-box">
				<div class="col-md-4 col-sm-4">
					<div class="service-box-heading">
						<em><i class="fa fa-location-arrow blue"></i></em> <span>About SHG-ONE.net</span>
					</div>
					<p>System to manage Self Help Group end to end.</p>
					<p>
						<br>Member Management <br> <br>SHG Accounting <br> <br>Project Management <br> <br>Reporting
					</p>
				</div>
				<div class="col-md-4 col-sm-4">
					<div class="service-box-heading">
						<em><i class="fa fa-check red"></i></em> <span>Our vision &amp; mission</span>
					</div>
					<p>Enable Self Help Group to be Strong, Reliable &amp;w Sustainable to Transform Life.</p>
					<p>20 together is power to achieve what one can't. Now think of millions of SHG together would Transform the World.</p>
				</div>
				<div class="col-md-4 col-sm-4 login-signup-page">
					<form name="myform" method="post">

						<h2>Login to your account</h2>

						<div class="input-group margin-bottom-20">
							<p>User Account Number</p>
							<input type="text" class="form-control" name="district" id="district" placeholder="MH01" style="width: 75px; margin-right: 10px;" value="${district}"> <input type="text" class="form-control" name="groupid" id="groupid" placeholder="10001"
								style="width: 100px; margin-right: 10px;" value="${groupid}"> <input type="text" class="form-control" name="memberid" id="memberid" placeholder="0101" style="width: 80px; margin-right: 10px;" value="${memberid}">
						</div>
						<div class="input-group margin-bottom-20">
							<p>Password</p>
							<input type="password" class="form-control" name="password" placeholder="********" style="width: 275px;">
						</div>

						<div class="row">
							<div class="col-md-7 col-sm-6">
								<button type="button" id="submitBtn" class="btn theme-btn pull-left">Login</button>
								<button type="reset" class="btn theme-btn pull-right">Reset</button>
							</div>
						</div>
						<div class="margin-top-20" id="errmsg">${errMsg}</div>
					</form>
				</div>
			</div>
			<!-- END SERVICE BOX -->
			<div class="clearfix"></div>
		</div>
		<!-- END CONTAINER -->
	</div>
	<!-- END PAGE CONTAINER -->

	<!-- BEGIN FOOTER -->
	<div class="copyright">
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-sm-8">
					<p>
						<span class="margin-right-10">2015 &copy; beOne Tech Pvt. Ltd. - ALL Rights Reserved.</span> <a href="#">Privacy Policy</a> | <a href="#">Terms of Service</a>
					</p>
				</div>
				<div class="col-md-4 col-sm-4">
					<ul class="social-footer">
						<li><a href="#"><i class="fa fa-facebook"></i></a></li>
						<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
						<li><a href="#"><i class="fa fa-dribbble"></i></a></li>
						<li><a href="#"><i class="fa fa-linkedin"></i></a></li>
						<li><a href="#"><i class="fa fa-twitter"></i></a></li>
						<li><a href="#"><i class="fa fa-skype"></i></a></li>
						<li><a href="#"><i class="fa fa-github"></i></a></li>
						<li><a href="#"><i class="fa fa-youtube"></i></a></li>
						<li><a href="#"><i class="fa fa-dropbox"></i></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- END FOOTER -->

	<!-- Load javascripts at bottom, this will reduce page load time -->
	<!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) -->
	<!--[if lt IE 9]>
    <script src="resources/login/plugins/respond.min.js"></script>  
    <![endif]-->
	<script src="resources/login/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="resources/login/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="resources/login/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="resources/plugins/back-to-top.js"></script>
	<!-- END CORE PLUGINS -->

	<!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) -->
	<script type="text/javascript" src="resources/login/plugins/fancybox/source/jquery.fancybox.pack.js"></script>
	<script type="text/javascript" src="resources/login/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
	<script type="text/javascript" src="resources/login/plugins/revolution_slider/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
	<script type="text/javascript" src="resources/login/plugins/bxslider/jquery.bxslider.min.js"></script>
	<script src="resources/login/scripts/app.js"></script>
	<script src="resources/login/scripts/index.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			App.init();
			App.initBxSlider();
			Index.initRevolutionSlider();
		});
	</script>
	<!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>