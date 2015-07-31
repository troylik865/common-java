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
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/webresources/css/datepicker.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
    <script type="text/javascript" src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/troy.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/bootstrap-datepicker.js"></script>
    <style>
        .personalTitle{
            font-size: 16px;
            color:red;
            margin-bottom:10px;
            margin-top:3px;
        }
        .contentDiv{
            margin-top:20px;
        }
        
        
    </style>
    <script>
        $(function () {
        	$("#content1").html('${publishMessage.content}');
        	$("#publish").click(function(){
				    troyConfirm('确定要提交此信息吗?',function(){
				    	var formData=$("#form1").serialize();
		                $.ajax({
					        type: "POST",
					        url: "/biz/publishMessage/update",
					        data: formData,
					        success: function (msg) {
		            			var obj = jQuery.parseJSON(msg);
		            			if(obj.resultCode == "200"){
		            				troyAlert(obj.resultMsg);
		            				window.location.href = "/myTransRecord";
		            			} else {
		            				troyAlert(obj.resultMsg);
		            			}
					        }
					    });
				    });
	            });
        });

    </script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom1">
                <div  style="margin:0 auto;width: 85%">
            		<form id="form1">
            		<input type="hidden" name="id" value="${publishMessage.id}"/>
                    <div style="margin-left: 100px;margin-top:10px;">
                        <textarea rows="10" cols="70" onfocus="clearText(this)" name="content" id="content1">
                        </textarea>
                    </div>
                    <div style="margin-left: 245px;margin-top:10px;"><input type="button" value="发 送" class="btn btn-lg btn-danger" id="publish"/></div>
           			</form>
                </div>
        </div>
        <!-- END of main -->
    </div>
    <!-- END of main wrapper -->
</body>
</html>