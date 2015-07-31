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


    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>

    <script type="text/javascript" src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
    <style>
        .personalTitle{
            font-size: 16px;
            color:red;
            margin-bottom:10px;
        }
        .contentDiv{
            margin-top:20px;
        }

    </style>
    <script>
        $(document).ready(function (){
            $("#publish").click(function(){
                window.location.href = "biz/member/publish";
            });
            $("#attention").click(function(){
                window.location.href = "biz/member/attention";
            });
            $("#detail").click(function(){
                window.location.href = "biz/member/detail";
            });
            $("#submitForm").click(function(){
            	var formData=$("#form1").serialize();
            	alert(formData);
                $.ajax({
			        type: "POST",
			        url: "biz/transRecord/create",
			        data: formData,
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.statusCode == "200"){
            				alert(obj.message);
            			} else {
            				alert(obj.message);
            			}
			        }
			        });
            });
        });
    </script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom" id="contentDiv">
            <div style="margin-left:50px;margin-top:30px;border-bottom: 1px darkgrey solid;width: 85%">
                <p>会员用户：${name}    &nbsp;您好 <img src="${contextPath}/webresources/images/remove.png" style="width:30px;height:20px" /><a href="#" style="color: red">申请实名认证>></a></p>
                <p>您还剩余金币：<span style="color:red">${finance}</span> &nbsp;&nbsp;&nbsp;<input type="button" value="使用明细" class="btn btn-block btn-lg btn-regist" id="detail"/>
                    <input type="button" value="实盘信息发布" class="btn btn-block btn-lg1 btn-regist" id="publish"/><span class="fontRed">(此功能需认证会员) </span>
                    <input type="button" value="实盘信息查看" class="btn btn-block btn-lg1 btn-regist" id="attention"/></p>
                <div class="personalTitle">交易记录导入</div>

            </div>

            <form class="troyFrom" id="form1" action="biz/transRecord/create" method="POST" enctype="multipart/form-data">
            <div  style="margin-left:50px;margin-top:30px;border-bottom: 1px darkgrey solid;width: 85%">
                    <table class="registTableL">
                        <tr>
                            <td style="width:30%">
                                <div>投资品种</div>
                            </td>
                            <td ><select class="troySelect" name="sex" datatype="*" nullmsg="请选择性别！" errormsg="请选择性别！">
                                <option value="">请选择</option>
                                <option value="1">&nbsp;&nbsp;男&nbsp;&nbsp;</option>
                                <option value="0">&nbsp;&nbsp;女&nbsp;&nbsp;</option>
                            </select></td>
                            <td><div>导入时间</div></td>
                            <td><input type="text" name="importDate" class="form-control full"/></td>
                        </tr>
                        <tr>
                            <td style="width:30%">
                                <div>初期资金</div>
                            </td>
                        <td width="30%">
                            <input type="text" name="origionValue" class="form-control largeLi"/>
                        </td>
                        <td style="padding-left:10px;">
                            <div style="width: 70px;">上日权益</div>
                        </td>
                        <td><input type="text" name="lastDayValue" class="form-control full"/></td>
                        </tr>
                        <tr>
                            <td style="width:30%">
                                <div>手续费</div>
                            </td>
                            <td width="30%">
                                <input type="text" name="fee" class="form-control largeLi"/>
                            </td>
                            <td style="padding-left:10px;">
                                <div style="width: 70px;">当日盈亏</div>
                            </td>
                            <td><input type="text" name="gainsAndLosses" class="form-control full"/></td>
                        </tr>
                        <tr>
                            <td style="width:30%">
                                <div>最新权益</div>
                            </td>
                            <td width="30%">
                                <input type="text" name="lastestValue" class="form-control largeLi"/>
                            </td>
                            <td style="padding-left:10px;">
                                <div style="width: 70px;">累计盈亏</div>
                            </td>
                            <td><input type="text" name="totleGainsAndLosses" class="form-control full"/></td>
                        </tr>
                        <tr>
                            <td style="width:30%">
                                <div>当日入金</div>
                            </td>
                            <td width="30%">
                                <input type="text" name="currIncome" class="form-control largeLi"/>
                            </td>
                            <td style="padding-left:10px;">
                                <div style="width: 70px;">累计入金</div>
                            </td>
                            <td><input type="text" name="totalIncome" class="form-control full"/></td>
                        </tr>
                        <tr>
                            <td style="width:30%">
                                <div>当日出金</div>
                            </td>
                            <td width="30%">
                                <input type="text" name="currOutcome" class="form-control largeLi"/>
                            </td>
                            <td style="padding-left:10px;">
                                <div style="width: 70px;">累计出金</div>
                            </td>
                            <td><input type="text" name="totalIncome" class="form-control full"/></td>
                        </tr>
                    </table>
                <div class="personalTitle">资金权益及持仓图片导入</div>
            </div>
            <div style="margin-top:10px;margin-left:50px;border-bottom: 1px darkgrey solid;">
                <div class="textCenter contentDiv">
                <input type="file" name="myfiles"/> 
                </div>
                <div class="personalTitle">资金权益及持仓图片导入</div>
            </div>
            <div style="margin-top:10px;margin-left:50px;">
                <div class="textCenter contentDiv">
                <input type="file" name="myfiles"/> 
                </div>
                <div class="buttonDiv" style="margin-top:50px;">
                <input type="button" value="预    览" class="btn btn-block btn-lg btn-regist"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="提    交" class="btn btn-block btn-lg btn-danger" id="submitForm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="个人主页" class="btn btn-block btn-lg btn-danger"/></div></div>
        </form>
    </div>
    <!-- END of main -->
</div>
<!-- END of main wrapper -->
</body>
</html>