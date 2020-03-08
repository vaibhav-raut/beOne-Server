	<!doctype html>
<html>
<head>
      <meta charset="utf-8">
      <title>Welcome to BeOne Software Company</title>
      <meta name="keywords" content="HTML5 Template">
      <meta name="description" content="Mist — Multi-purpose HTML Template">
      <meta name="author" content="zozothemes.com">
      <!--<meta class="viewport" name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="viewport" content="width=device-width, initial-scale=1">-->
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <!-- Favicon -->
      <link rel="shortcut icon" href="resources/img/favicon.ico">
      <!-- Font -->
      <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Arimo:300,400,700,400italic,700italic'>
      <link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
      <link href='resources/css/font-awesome.min.css' rel='stylesheet' type='text/css'/>
      <!-- Plugins CSS -->
      <link rel="stylesheet" href="resources/css/social-icons.css">
      <link rel="stylesheet" href="resources/css/myicons.css">
      <link rel="stylesheet" href="resources/css/bootstrap.min.css">
      <link rel="stylesheet" href="resources/css/jslider.css">
      <link rel="stylesheet" href="resources/css/settings.css">
      <link rel="stylesheet" href="resources/css/animate_home.css">
      <link rel="stylesheet" href="resources/css/jquery.scrollbar.css">
      <!-- Theme CSS -->
      <link rel="stylesheet" href="resources/css/style_home.css">
      <!-- Responsive CSS -->
      <link rel="stylesheet" href="resources/css/responsive.css">
      <!-- Custom CSS -->
      <link rel="stylesheet" href="resources/css/home-pages-customizer.css">
      <!-- IE Styles-->
      <link rel='stylesheet' href="resources/css/ie.css">
      <!--[if lt IE 9]>
      <script src="resources/https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="resources/https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
      <link rel='stylesheet' href="resources/css/ie/ie8.css">
      <![endif]-->
      <script type="text/javascript" src="http://jqueryjs.googlecode.com/files/jquery-1.3.2.js"></script>
            <script type="text/javascript">
$(document).ready(function(){
	$(".trigger").click(function(){
		$(".panel").toggle("fast");
		$(this).toggleClass("active");
		return false;
	});
});
</script>
<script src="resources/scripts/swfobject_modified.js" type="text/javascript"></script>

<script src="resources/js/login.js"></script>
<style type="text/css">
	#errmsg {
		color: red;
	}
</style>
</head>
   <body class="one-page fixed-header">
   
   <div class="panel" style="z-index:999;background-color: white">
   <form name="myform" method="post">
        <div class="form-group">
            <label for="inputAccNumber">User Account Number</label>
			<div class="input-group" id="inputAccNumber">
				<input type="text" class="form-control" name="district" id="district" placeholder="MH01" style="width: 75px; margin-right: 10px;" value="${district}">
				<input type="text" class="form-control" name="groupid" id="groupid" placeholder="10001" style="width: 90px; margin-right: 10px;" value="${groupid}">
				<input type="text" class="form-control" name="memberid" id="memberid" placeholder="0101" style="width: 70px;" value="${memberid}">
	        </div>
        </div>
        <div class="form-group">
            <label for="inputPassword">Password</label>
            <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password">
        </div>
        <div class="checkbox">
            <label><input type="checkbox"> Remember me</label>
        </div>
        <button type="button" id="submitBtn" class="btn btn-primary">Login</button>
		<button type="reset" class="btn theme-btn pull-right">Reset</button>
		<div class="margin-top-20" id="errmsg">${errMsg}</div>
   </form>
   </div>
   <a class="trigger" href="#" style="z-index:1000;color: white">Login</a>

      <div class="page-box">
         <div class="page-box-content">
            <header class="header header-two">
			  <div class="header-wrapper">
				<nav class="navbar">
					<div class="container">
						<div class="navbar-header logo-box">
						  <div class="logo">
							<a href="/shg-portal">
							  <img src="resources/img/logo_new.png" class="logo-img" alt="">
							</a>
						  </div>
						</div><!-- .logo-box -->
						
						<div class="navbar-right right-box">
						  <div class="right-box-wrapper">
							<div class="header-icons">
								
							   <div class="share-header hidden-600">
								<a href="#">
									<i class="fa fa-share-alt"></i>
								</a>
							  </div><!-- .search-header -->
							  
							  <div class="search-header hidden-600">
								<a href="#">
									<i class="fa fa-search"></i>
								</a>
							  </div><!-- .search-header -->
							  
							 <div class="phone-header hidden-600">
								<a href="#">
									<i class="fa fa-mobile"></i>
								</a>
							  </div><!-- .phone-header -->
							</div><!-- .header-icons -->
								
						
							<div class="primary">
								<div class="navbar navbar-default" role="navigation">
								<button type="button" class="navbar-toggle btn-navbar collapsed" data-toggle="collapse" data-target=".primary .navbar-collapse">
									<span class="text"></span>
									<span class="icon-bar"></span>
									<span class="icon-bar"></span>
									<span class="icon-bar"></span>
								</button>
                                    <ul class="collapse navbar-collapse nav navbar-nav navbar-center">
									<li><a class="scroll active" href="#home">Home</a></li>							
									<li><a class="scroll" href="#about-us">About Us</a></li>
									<li><a class="scroll" href="#aboutshg">About SHG-One.net</a></li>
									<li><a class="scroll" href="#shgone">Why SHG-One.net</a></li>
									<li><a class="scroll" href="#team">Team</a></li>
									<li><a class="scroll" href="#contact-us">Contact Us</a></li>
								</ul>
							</div><!-- .primary -->
						  </div>
						</div>
					
						<div class="phone-active col-sm-12 col-md-12">
							  <a href="#" class="close"><span>close</span>×</a>
							  <span class="title">Call Us</span> <strong>+91-9595654794, +91-9049115522</strong>
							</div>
							<div class="search-active col-sm-12 col-md-12">
							  <a href="#" class="close"><span>close</span>×</a>
							  <form name="search-form" class="search-form">
								<input class="search-string form-control" type="search" placeholder="Enter Your Text & Search Here" name="search-string">
								<button class="search-submit">
								  <i class="fa  fa-search text-color"></i>
								</button>
							  </form>
							</div>
							<div class="share-active col-sm-12 col-md-12">
							  <a href="#" class="close"><span>close</span>×</a>
								 <div class="header-social btn-icon">
									<a class="mistbtn mistbtn-circle mistbtn-icon-white mistbtn-icon-bg-transparent color-hover icon-facebook" href="#"></a>
									<a class="mistbtn mistbtn-circle mistbtn-icon-white mistbtn-icon-bg-transparent color-hover icon-twitter" href="#"></a>
									<a class="mistbtn mistbtn-circle mistbtn-icon-white mistbtn-icon-bg-transparent color-hover icon-google" href="#"></a>
									<a class="mistbtn mistbtn-circle mistbtn-icon-white mistbtn-icon-bg-transparent color-hover icon-pinterest" href="#"></a>
									<a class="mistbtn mistbtn-circle mistbtn-icon-white mistbtn-icon-bg-transparent color-hover icon-instagram" href="#"></a>
								</div>
							</div>
						
						
						</div>
					</div>
				</nav>
			  </div><!-- .header-wrapper -->
			</header><!-- .header -->
			
            <div id="main">
               <section id="home" class="full-width-box">
                  <div class="slider rs-slider">
                     <div class="tp-banner-container">
                        <div class="tp-banner">
                           <ul>
                              <!-- Slide -->
                              <li data-delay="7000" data-transition="fade" data-slotamount="7" data-masterspeed="2000">
                                 <div class="elements">
                                    <div class="tp-caption lfb"
                                       data-x="0"
                                       data-y="50"
                                       data-speed="1000"
                                       data-start="2040"
                                       data-easing="Power4.easeOut"
                                       data-endspeed="500"
                                       data-endeasing="Power1.easeIn"
                                       style="z-index: 4">
                                       <img src="resources/img/rs-slider1-img2.png" width="500" height="700" alt="">
                                    </div>
                                    <h2 class="tp-caption lft skewtotop title bold"
                                       data-x="502"
                                       data-y="100"
                                       data-speed="1000"
                                       data-start="1700"
                                       data-easing="Power4.easeOut"
                                       data-endspeed="500"
                                       data-endeasing="Power1.easeIn">
                                       <strong>Welcome To Beone Tech Pvt Ltd</strong>
                                    </h2>
                                    <h2 class="tp-caption lft skewtotop title "
                                       data-x="502"
                                       data-y="170"
                                       data-speed="1000"
                                       data-start="1700"
                                       data-easing="Power4.easeOut"
                                       data-endspeed="500"
                                       data-endeasing="Power1.easeIn">
                                       <strong>Banking software Solutions</strong>
                                   </h2>
                                    <div class="tp-caption lfr skewtoright description black hidden-xs"
                                       data-x="492"
                                       data-y="250"
                                       data-speed="1000"
                                       data-start="1500"
                                       data-easing="Power4.easeOut"
                                       data-endspeed="500"
                                       data-endeasing="Power1.easeIn"
                                       style="max-width: 600px">
                                       <p>Proin a velit aliquam vitae malesuada rutrum. Aenean ullamcorper placerat porttitor velit aliquam vitae. Aliquam a augue suscipit, bibendum luctus neque laoreet rhoncus ipsum, ullamcorper </p>
                                    </div>
                                    
                                 </div>
                                 <img src="resources/img/rs-slider4-bg.jpg" alt="" data-bgfit="cover" data-bgposition="center top" data-bgrepeat="no-repeat">
                              </li>
                              <!-- Slide Ends -->
                              
                           </ul>
                           <div class="tp-bannertimer"></div>
                        </div>
                     </div>
                  </div>
                  <!-- .rs-slider -->
               </section>
               <!-- #home -->
               <section id="about-us" class="full-width-box">
                  <div class="title-box">
                     <!-- Heading -->
                     <h1 class="title">About Us</h1>
                  </div>
                  <div class="container">
                     <div class="row">
                        <div class="content col-sm-12 col-md-12">
                           <h4 class="text-center">MASTER PIC DESIGNS MADE WITH CREATIVE PROFESSIONALS</h4>
                           <p class="text-center">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Assumenda, quisquam, culpa, soluta hic aperiam porro ipsum nisi optio 
                              necessitatibus commodi dolorum sapiente voluptatem officiis similique maiores! 
                           </p>
                           <br />
                           <div class="row special-feature">
                        <!-- Special Feature Box 1 -->
                        <div class="col-md-4">
                           <div class="s-feature-box text-center" data-appear-animation="fadeInLeft">
                              <div class="mask-top">
                                 <!-- Icon -->
                                 <i class="fa fa-eye"></i>
                                 <!-- Title -->
                                 <h4>Our Vision</h4>
                              </div>
                              <div class="mask-bottom">
                                 <!-- Icon -->
                                 <i class="fa fa-eye"></i>
                                 <!-- Title -->
                                 <h4>Our Vision</h4>
                                 <!-- Text -->
                                 <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum nec odio ipsum. Suspendisse cursus malesuada facilisis.</p>
                              </div>
                           </div>
                        </div>
                        <!-- Special Feature Box 2 -->
                        <div class="col-md-4">
                           <div class="s-feature-box text-center" data-appear-animation="fadeInRight">
                              <div class="mask-top">
                                 <!-- Icon -->
                                 <i class="fa fa-cubes"></i>
                                 <!-- Title -->
                                 <h4>Our Values</h4>
                              </div>
                              <div class="mask-bottom">
                                 <!-- Icon -->
                                 <i class="fa fa-cubes"></i>
                                 <!-- Title -->
                                 <h4>Our Values</h4>
                                 <!-- Text -->
                                 <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum nec odio ipsum. Suspendisse cursus malesuada facilisis.</p>
                              </div>
                           </div>
                        </div>
                        <!-- Special Feature Box 3 -->
                        <div class="col-md-4">
                           <div class="s-feature-box text-center" data-appear-animation="fadeInLeft">
                              <div class="mask-top">
                                 <!-- Icon -->
                                 <i class="fa fa-paper-plane-o"></i>
                                 <!-- Title -->
                                 <h4>Our Goals</h4>
                              </div>
                              <div class="mask-bottom">
                                 <!-- Icon -->
                                 <i class="fa fa-paper-plane-o"></i>
                                 <!-- Title -->
                                 <h4>Our Goals</h4>
                                 <!-- Text -->
                                 <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum nec odio ipsum. Suspendisse cursus malesuada facilisis.</p>
                              </div>
                           </div>
                        </div>
                     </div>
                        </div>
                     </div>
                  </div>
               </section>
               <!-- #about-us -->
               
               <section id="aboutshg" class="full-width-box">
                  <div class="container">
                     <div class="title-box text-center">
                        <!-- Heading -->
                        <h2 class="title">Awesome Features</h2>
                        
                     </div>
                     <p class="text-center">Online Cloud based Service managing all aspects of SHG end to end
                           </p>
                           <br />
                     

                     <div class="row services">
                        <div class="service col-sm-6 col-md-4" data-appear-animation="fadeInLeft">
                        <a href="#">
                              <!-- Icon -->
                              <span class="livicon" data-n="responsive" data-s="42" data-c="#0da3e2" data-hc="0" data-d="1600"></span>
                              <!-- Title -->
                              <h6 class="title">Stage I</h6>
                              <!-- Text -->
                              <ul class="list-style" style="padding-left:0">
                                   <li>Online SHG Accounting and available for all stack holders; SHG Members, Federation, Banks, NGO etc.
</li>
                                   <li>Inherently removing all problems caused due to lack of Transparency, Trust and Authenticity
</li>
                                   <li>Stop any SHG failures with good intentions, principles and objectives
</li>
                                 </ul>
                           </a>  
                        </div>
                        <div class="service col-sm-6 col-md-4" data-appear-animation="fadeInDown">
                          <a href="#">
                              <!-- Icon -->
                              <span class="livicon" data-n="responsive" data-s="42" data-c="#0da3e2" data-hc="0" data-d="1600"></span>
                              <!-- Title -->
                              <h6 class="title">Stage II</h6>
                              <!-- Text -->
                              <ul class="list-style" style="padding-left:0">
                                   <li>Identify opportunities for training and development

</li>
                                   <li>Leverage all resources available for optimum use and delivering trainings best suited

</li>
                                   <li>On going Mentoring, Follow up and Management support so as to remove all small and big hurdles for project and business ventures

</li>
                                 </ul>
                           </a> 
                        </div>
                        <div class="service col-sm-6 col-md-4" data-appear-animation="fadeInRight">
                        
                        <a href="#">
                              <!-- Icon -->
                              <span class="livicon" data-n="responsive" data-s="42" data-c="#0da3e2" data-hc="0" data-d="1600"></span>
                              <!-- Title -->
                              <h6 class="title">Stage III</h6>
                              <!-- Text -->
                              <ul class="list-style" style="padding-left:0">
                        <li><strong>Provide Services for:</strong></li>
                        <li>Market Research</li>
                        <li>Product Design</li>				
                        <li>Product Engineering</li>
                        <li>Vendor Management</li>
                        <li>Marketing & Branding</li>
                        <li>Sales & Distribution </li>
                        <li>E-commerce</li>
                        </ul>
                           </a>
                        
                        </div>
                        
                        
                        
                     </div>
                     <div class="clearfix"></div>
                  </div>
               </section>
               
               <div class="container" id="shgone">
   		<div class="row">
		  <div class="bottom-padding col-sm-8 col-md-8">
            <object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="360">
              <param name="movie" value="resources/flash/Car-speakers-590x90.swf">
              <param name="quality" value="high">
              <param name="wmode" value="opaque">
              <param name="swfversion" value="9.0.45.0">
              <!-- This param tag prompts users with Flash Player 6.0 r65 and higher to download the latest version of Flash Player. Delete it if you don’t want users to see the prompt. -->
              <param name="expressinstall" value="Scripts/expressInstall.swf">
              <!-- Next object tag is for non-IE browsers. So hide it from IE using IECC. -->
              <!--[if !IE]>-->
              <object type="application/x-shockwave-flash" data="resources/flash/Car-speakers-590x90.swf" width="100%" height="360">
                <!--<![endif]-->
                <param name="quality" value="high">
                <param name="wmode" value="opaque">
                <param name="swfversion" value="9.0.45.0">
                <param name="expressinstall" value="Scripts/expressInstall.swf">
                <!-- The browser displays the following alternative content for users with Flash Player 6.0 and older. -->
                <div>
                  <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
                  <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33" /></a></p>
                </div>
                <!--[if !IE]>-->
              </object>
              <!--<![endif]-->
            </object>
		  </div>
		  
		  
		  
		  <div class="bottom-padding col-sm-4 col-md-4">
			<div class="title-box">
			  <h2 class="title">About Self Help Group?</h2>
			</div>
			
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Perferendis aliquam id pariatur accusantium perspiciatis deserunt officiis 
            similique nihil dolor blanditiis dignissimos iure praesentium vero suscipit doloribus </p>
            
			<button class="btn btn-block">Dont Wait - Join Us Now</button>
		  </div>
		</div>
   </div>
   
   <div class="container">
   		<div class="row">
		  <div class="bottom-padding col-sm-4 col-md-4">
			<div class="title-box">
			  <h2 class="title">Stages of SHG Growth?</h2>
			</div>
			
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Perferendis aliquam id pariatur accusantium perspiciatis deserunt officiis 
            similique nihil dolor blanditiis dignissimos iure praesentium vero suscipit doloribus </p>
			<button class="btn btn-block">Dont Wait - Join Us Now</button>
		  </div>
          
          <div class="bottom-padding col-sm-8 col-md-8">
            <object id="FlashID1" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="360">
              <param name="movie" value="resources/flash/Car-speakers-590x90.swf">
              <param name="quality" value="high">
              <param name="wmode" value="opaque">
              <param name="swfversion" value="9.0.45.0">
              <!-- This param tag prompts users with Flash Player 6.0 r65 and higher to download the latest version of Flash Player. Delete it if you don't want users to see the prompt. -->
              <param name="expressinstall" value="Scripts/expressInstall.swf">
              <!-- Next object tag is for non-IE browsers. So hide it from IE using IECC. -->
              <!--[if !IE]>-->
              <object type="application/x-shockwave-flash" data="resources/flash/Car-speakers-590x90.swf" width="100%" height="360">
                <!--<![endif]-->
                <param name="quality" value="high">
                <param name="wmode" value="opaque">
                <param name="swfversion" value="9.0.45.0">
                <param name="expressinstall" value="Scripts/expressInstall.swf">
                <!-- The browser displays the following alternative content for users with Flash Player 6.0 and older. -->
                <div>
                  <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
                  <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33" /></a></p>
                </div>
                <!--[if !IE]>-->
              </object>
              <!--<![endif]-->
            </object>
		  </div>
		  
		  
		  
		  
		</div>
   </div>
   
   <div class="container">
   		<div class="row">
		  <div class="bottom-padding col-sm-8 col-md-8">
            <object id="FlashID2" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="360">
              <param name="movie" value="resources/flash/Car-speakers-590x90.swf">
              <param name="quality" value="high">
              <param name="wmode" value="opaque">
              <param name="swfversion" value="9.0.45.0">
              <!-- This param tag prompts users with Flash Player 6.0 r65 and higher to download the latest version of Flash Player. Delete it if you don't want users to see the prompt. -->
              <param name="expressinstall" value="Scripts/expressInstall.swf">
              <!-- Next object tag is for non-IE browsers. So hide it from IE using IECC. -->
              <!--[if !IE]>-->
              <object type="application/x-shockwave-flash" data="resources/flash/Car-speakers-590x90.swf" width="100%" height="360">
                <!--<![endif]-->
                <param name="quality" value="high">
                <param name="wmode" value="opaque">
                <param name="swfversion" value="9.0.45.0">
                <param name="expressinstall" value="Scripts/expressInstall.swf">
                <!-- The browser displays the following alternative content for users with Flash Player 6.0 and older. -->
                <div>
                  <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
                  <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33" /></a></p>
                </div>
                <!--[if !IE]>-->
              </object>
              <!--<![endif]-->
            </object>
		  </div>
		  
		  
		  
		  <div class="bottom-padding col-sm-4 col-md-4">
			<div class="title-box">
			  <h2 class="title">Why SHG-One.net?</h2>
			</div>
			
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Perferendis aliquam id pariatur accusantium perspiciatis deserunt officiis 
            similique nihil dolor blanditiis dignissimos iure praesentium vero suscipit doloribus </p>
			<button class="btn btn-block">Dont Wait - Join Us Now</button>
		  </div>
		</div>
   </div>
   
   <div class="container">
   		<div class="row">
		  <div class="bottom-padding col-sm-4 col-md-4">
			<div class="title-box">
			  <h2 class="title">What is SHG-One.net?</h2>
			</div>
			
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Perferendis aliquam id pariatur accusantium perspiciatis deserunt officiis 
            similique nihil dolor blanditiis dignissimos iure praesentium vero suscipit doloribus </p>
            <p>aperiam unde hic non sint neque molestiae consectetur voluptatum beatae ratione corporis repellat labore est ipsa voluptates temporibus! Possimus?</p>
			<button class="btn btn-block">Dont Wait - Join Us Now</button>
		  </div>
          
          <div class="bottom-padding col-sm-8 col-md-8">
            <object id="FlashID3" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="360">
              <param name="movie" value="resources/flash/Car-speakers-590x90.swf">
              <param name="quality" value="high">
              <param name="wmode" value="opaque">
              <param name="swfversion" value="9.0.45.0">
              <!-- This param tag prompts users with Flash Player 6.0 r65 and higher to download the latest version of Flash Player. Delete it if you don't want users to see the prompt. -->
              <param name="expressinstall" value="Scripts/expressInstall.swf">
              <!-- Next object tag is for non-IE browsers. So hide it from IE using IECC. -->
              <!--[if !IE]>-->
              <object type="application/x-shockwave-flash" data="resources/flash/Car-speakers-590x90.swf" width="100%" height="360">
                <!--<![endif]-->
                <param name="quality" value="high">
                <param name="wmode" value="opaque">
                <param name="swfversion" value="9.0.45.0">
                <param name="expressinstall" value="Scripts/expressInstall.swf">
                <!-- The browser displays the following alternative content for users with Flash Player 6.0 and older. -->
                <div>
                  <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
                  <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33" /></a></p>
                </div>
                <!--[if !IE]>-->
              </object>
              <!--<![endif]-->
            </object>
		  </div>
		  
		  
		  
		  
		</div>
   </div>
               
         <div class="container our-team" id="team">
    	<div class="carousel-box no-style overflow">
		  <div class="title-box">
			<a class="next" href="#">
            	<i class="fa  fa-angle-right"></i>
			</a>
			<a class="prev" href="#">
            	<i class="fa  fa-angle-left"></i>
			</a>
			<h2 class="title">Meet the Team</h2>
		  </div>
		  
		  <div class="clearfix"></div>
		  
              <div class="row">
                <div class="carousel carousel-links">
                  <div class="col-sm-6 col-md-6 team-member">
                    <div class="default">
                      <div class="image" style="text-align:center">
                        <!-- Image -->
                        <img src="resources/img/team-1.jpg" alt="" title="" width="270" height="270">
                      </div>
                      <div class="description text-center">
                        <div class="vertical">
                          <!-- Name -->
                          <h3 class="name text-color">Vaibhav Raut</h3>
                          <!-- Designation -->
                          <div class="role">Founder & CEO</div>	
                        </div>
                      </div>
                    </div>
                    <p class="text-center">A kid crazily in love and amazed with this beautiful world! Born thinker and curious about everything! Very strong passion and commitment to make some positive contribution. <br>
<br>

12+ years of work experience in software product development in varied domains and technologies. Passionate about build synergic team and providing complete & simple solutions for difficult problem. <br>
<br>

Dream of making technology simple & reachable to everyone. 
</p>
                    <div class="social-icon">
                        <!-- Social Icons -->
                        <div class="item"><a class="icon" href="#"><i class="fa fa-facebook"></i></a></div>
                        <div class="item"><a class="icon" href="#"><i class="fa fa-twitter"></i></a></div>
                        <div class="item"><a class="icon" href="#"><i class="fa fa-google"></i></a></div>
                        <div class="item"><a class="icon" href="#"><i class="fa fa-pinterest"></i></a></div>
                    </div>
                 </div><!-- .employee  -->
                  
                  <div class="col-sm-6 col-md-6 team-member">
                    <div class="default">
                      <div class="image" style="text-align:center">
                        <!-- Image -->
                        <img src="resources/img/team-2.jpg" alt="" title="" width="270" height="270">
                      </div>
                      <div class="description text-center">
                        <div class="vertical">
                          <!-- Name -->
                          <h3 class="name text-color">Pravin Khunkar</h3>
                          <!-- Designation -->
                          <div class="role">Chief Operating Officer
</div>	
                        </div>
                      </div>
                    </div>
                    <p class="text-center">Born talented in marketing and building personal relationship. <br>
<br>
 
8+ years of work experience in marketing in Insurance sector. Amazing in grooming team, resource management and execution.
Dream of giving something different and touching everyone's heart.<br>
<br>
<br>
 
</p>
                    <div class="social-icon">
                        <!-- Social Icons -->
                        <div class="item"><a class="icon" href="#"><i class="fa fa-facebook"></i></a></div>
                        <div class="item"><a class="icon" href="#"><i class="fa fa-twitter"></i></a></div>
                        <div class="item"><a class="icon" href="#"><i class="fa fa-google"></i></a></div>
                        <div class="item"><a class="icon" href="#"><i class="fa fa-pinterest"></i></a></div>
                    </div>
                 </div><!-- .employee  -->
                  
			</div>
		  </div>
		</div>
    </div> 
              
        <section id="contact-us" class="full-width-box">
                  <div class="title-box">
                     <!-- Heading -->
                     <h1 class="title">Contact Us</h1>
                  </div>
                  <div class="container">
                     <div class="row">
                        <div class="col-md-12 contact-info">
                           <div class="row text-center">
                              <address class="col-sm-4 col-md-4">
                                 <div class="big-icon bg">
                                    <i class="fa fa-map-marker fa-2x"></i>
                                 </div>
                                 <div class="title">Address</div>
                                 BeOne Tech Private Limited<br>
3rd Floor, Ambaji Towers, Ravi Nagar 
Amravati (MH) 444605
                              </address>
                              <address class="col-sm-4 col-md-4">
                                 <div class="big-icon bg">
                                    <i class="fa fa-microphone fa-2x"></i>
                                 </div>
                                 <div class="title">Phones</div>
                                 <div>+91-9595654794<br>+91-9049115522
</div>
                              </address>
                              <address class="col-sm-4 col-md-4">
                                 <div class="big-icon bg">
                                    <i class="fa fa-envelope fa-2x"></i>
                                 </div>
                                 <div class="title">Email Addresses</div>
                                 <div><a href="mailto:care.beone@gmail.com">care.beone@gmail.com</a></div>
                              </address>
                           </div>
                           <hr>
                        </div>
                        <div class="col-md-4">
                           <h3>We are here for you!</h3>
                           
		  <!-- Address -->
		  <p><strong>Office:</strong> <strong>BeOne Tech Private Limited</strong><br>

3rd Floor, Ambaji Towers, Ravi Nagar Amravati (MH) 444605</p>
		  <!-- Phone -->
		  <p><strong>Call Us:</strong> +91-9595654794,
+91-9049115522</p>
<p><strong>Email-ID:</strong> care.beone@gmail.com</p>

                        </div>
                        <div class="col-md-4">
                        <h3>Send an Enquiry</h3>
                           <form method="POST" class="register-form contact-form" id="contactform">
                              <div id="success"></div>
                              <input type="text" placeholder="Name *" name="name" class="form-control">
                              <input type="email" placeholder="Email *" name="email" class="form-control">
                              <input type="text" placeholder="Phone *" name="phone" class="form-control">
                              <textarea placeholder="Comments *" name="comment" class="form-control bottom-15"></textarea>
                              <div class="clearfix"></div>
                              <div class="buttons-box clearfix text-center">
                                 <button class="btn full btn-default" id="submit">Send Now</button>
                              </div>
                              <!-- .buttons-box -->
                           </form>
                        </div>
                        <div class="col-md-4">
                        <h3>Google Map</h3>
                           <div class="map-box">
                           <img src="resources/img/gmap.jpg" alt="" width="400" height="300"></div>
                       </div>
                     </div>
                  </div>
               </section>
            </div>
         </div>
         
      </div>
      <div class="clearfix"></div>
      
      
      <script src="resources/login/jquery.min.js"></script>
      <script src="resources/login/bootstrap.min.js"></script>
      
      <script src="resources/login/jquery.touchSwipe.min.js"></script>
      <script src="resources/login/jquery.imagesloaded.min.js"></script>
      <script src="resources/login/jquery.appear.js"></script>
      <script src="resources/login/jquery.easing.1.3.js"></script>
      <script src="resources/login/isotope.pkgd.min.js"></script>
      <script src="resources/login/jquery.tubular.1.0.js"></script>
      
      <script src="resources/login/SmoothScroll.js"></script>
      <script src="resources/login/jquery.zozothemes.plugins.min.js"></script>
      <script src="resources/login/jquery.zozothemes.revolution.min.js"></script>
      <script src="resources/login/main.js"></script>
   <script type="text/javascript">
swfobject.registerObject("FlashID");
      </script>
   </body>
</html>