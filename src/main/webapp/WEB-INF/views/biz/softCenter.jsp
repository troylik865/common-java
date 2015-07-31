<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Home</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store"/>
    <meta name="description"
          content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included."/>
    <link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/troy.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
    <style>
        .contentDiv{
            height:300px;
            border:1px #A7A4A4 solid;
            margin-bottom:10px;
            border-radius: 6px;
        }
        .contentDiv .divTitle{
                margin-top:5px;
                color:red;
                font-weight: bold;
                font-size:15px;
                border-bottom:1px #A7A4A4 solid;
                height:30px;
                line-height: 30px;
                padding-left:20px;
        }
        
        .search {
        	width:50px;
        	height:23px;
        }
        
        .titlePic{
    		width:100px;
    		height:38px;
    		position:absolute;
    		margin-top:-2px;
    	}
    	
    	.pageDiv {
    		font-size: 12px;
    		position:absolute;
    		right:15px;
			margin-right: 20px;
    	}
    </style>
    <script>
    	$(document).ready(function(){
    		loadColumn("a-qhgs","showContent");
    		loadColumn("b-zqgs","showContent1");
    		loadColumn("c-fxzb","showContent2");
    		loadColumn("d-hysj","showContent3");
    		loadColumn("e-tzsj","showContent4");
    		loadColumn("f-jxsp","showContent5");
    		loadColumn("g-yhgw","showContent6");
    		loadColumn("h-other","showContent7");
    	});
    	
    	function loadColumn(itemType,showDivId){
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 20,
		        url: "/biz/columnList/list?itemType="+itemType+"&showPos=COLUMN",
		        data: [
		            {
		                param: "itemName",
		                type:"method",
		                method:"add",
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	function add(obj){
    		var itemType = obj["itemContentType"];
	    	if(typeof obj != "underfined" && (itemType == "HTML_A" || itemType == "HTML_FILE")){
    			return "<a href=\""+obj["itemContent"]+"\" target=\"_Blank\">"+obj["itemName"]+"</a>";
	    	}else{
    			return obj["itemName"];
	    	}
    	}
    	
    </script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main" high="test">
        <div><img src="${contextPath}/webresources/images/software.png" class="titlePic"/><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left " style="height:835px;width:700px;margin-top:10px; border-radius: 6px;">
                 <div class="contentDiv">
                     <div class="divTitle">期货公司
                     </div>
                     <div class="divContent" id="showContent">

                     </div>
                 </div>
                <div class="contentDiv">
                    <div class="divTitle">证券公司
                    </div>
                    <div id="showContent1"></div>
                </div>
                <div class="contentDiv">
                    <div class="divTitle">分析指标
                    </div>
                    <div id="showContent2"></div>
                </div>
                <div class="contentDiv">
                    <div class="divTitle">行业数据
                    </div>
                    <div id="showContent3"></div>
                </div>
                <div class="contentDiv">
                    <div class="divTitle">投资书籍
                    </div>
                    <div id="showContent4"></div>
                </div>
                <div class="contentDiv">
                    <div class="divTitle">教学视频
                    </div>
                    <div id="showContent5"></div>
                </div>
                 <div class="contentDiv">
                    <div class="divTitle">银行官网
                    </div>
                    <div id="showContent6"></div>
                </div>
                <div class="contentDiv">
                    <div class="divTitle">银行官网
                    </div>
                    <div id="showContent7"></div>
                </div>
        </div>
    </div>
    <!-- END of main -->
</div>
<!-- END of main wrapper -->
</body>
</html>