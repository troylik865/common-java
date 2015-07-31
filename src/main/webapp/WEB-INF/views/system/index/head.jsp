<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>投资大师</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store" />
    <meta name="description" content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included." />
    <link href="templatemo_style.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="css/orman.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen" />

    <link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />

    <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="js/ddsmoothmenu.js"></script>

    <script type="text/javascript">

        ddsmoothmenu.init({
            mainmenuid: "templatemo_menu", //menu DIV id
            orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
            classname: 'ddsmoothmenu', //class added to menu's outer DIV
            //customtheme: ["#1c5a80", "#18374a"],
            contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
        }) ;


        function clearText(field)
        {
            if (field.defaultValue == field.value) field.value = '';
            else if (field.value == '') field.value = field.defaultValue;
        }

    </script>

    <link rel="stylesheet" href="css/slimbox2.css" type="text/css" media="screen" />
    <script type="text/JavaScript" src="js/slimbox2.js"></script>
    <style type="text/css">
        .validate{
            width: 75px;
            height:26px;
            line-height: 25px;
            position:absolute;
            bottom: 39px;
            left: 160px;
        }

    </style>

</head>

<body>

<div id="templatemo_wrapper_h">
    <div id="templatemo_header_wh">
        <div id="templatemo_header" class="header_home">
            <div id="site_title"><a href="http://sc.chinaz.com" rel="nofollow">Free CSS Templates</a></div>
            <div id="templatemo_menu" class="ddsmoothmenu">
                <ul>
                    <li><a href="index.html" class="selected">首页</a></li>
                    <li><a href="about.html">公司简介</a></li>
                    <li><a href="products.html">大师推荐</a>
                        <ul>
                            <li><a href="http://sc.chinaz.com/?page/1" rel="nofollow">Sub Page One</a></li>
                            <li><a href="http://sc.chinaz.com/?page/2" rel="nofollow">Sub Page Two</a></li>
                            <li><a href="http://sc.chinaz.com/?page/3" rel="nofollow">Sub Page Three</a></li>
                            <li><a href="http://sc.chinaz.com/?page/4" rel="nofollow">Sub Page Four</a></li>
                            <li><a href="http://sc.chinaz.com/?page/5" rel="nofollow">Sub Page Five</a></li>
                        </ul>
                    </li>
                    <li><a href="checkout.html">寻找大师</a></li>
                    <li><a href="contact.html">实用软件下载</a></li>
                    <li><a href="faqs.html">会员专区</a></li>
                    <li><a href="faqs.html">新手入门</a></li>
                    <li><a href="faqs.html">联系我们</a></li>
                </ul>
            </div>

            <div class="slider-wrapper theme-orman">
                <div id="slider" class="nivoSlider">
                    <img src="images/portfolio/01.jpg" alt="slider image 1" />
                    <a href="http://sc.chinaz.com/?page/1" rel="nofollow">
                        <img src="images/portfolio/02.jpg" alt="slider image 2" title="This is an example of a caption" />
                    </a>
                    <img src="images/portfolio/03.jpg" alt="slider image 3" />
                    <img src="images/portfolio/04.jpg" alt="slider image 4" title="#htmlcaption" />
                    <img src="images/portfolio/05.jpg" alt="slider image 5" title="#htmlcaption" />
                </div>
                <div id="htmlcaption" class="nivo-html-caption">
                    <strong>Example</strong> caption with <a href="http://sc.chinaz.com" rel="nofollow">a credit link</a> for <em>this slider</em>.
                </div>
            </div>
            <script type="text/javascript" src="js/jquery.nivo.slider.pack.js"></script>
            <script type="text/javascript">
                $(window).load(function() {
                    $('#slider').nivoSlider({
                        controlNav:false
                    });
                });
            </script>
        </div> <!-- END of header -->

    </div> <!-- END of header wrapper -->
    </div>
    </body>