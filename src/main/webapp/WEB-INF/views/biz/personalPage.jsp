<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/views/biz/validate.jsp" %>
<%@include file="/WEB-INF/views/biz/showDiv.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Home</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store"/>
    <meta name="description"
          content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included."/>
       <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
	    <link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
	    <link href="${contextPath}/webresources/css/bootstrap.css" rel="stylesheet">
	    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
	    <script type="text/javascript" src="${contextPath}/webresources/js/money.js"></script>
	    <script type="text/javascript" src="${contextPath}/webresources/js/bootstrap-datepicker.js"></script>
	    <link href="${contextPath}/webresources/css/datepicker.css" rel="stylesheet">
    <style>
        .personalTitle{
            font-size: 14px;
            color:red;
            margin-bottom:10px;
            font-weight:bold;
        }
        .contentDiv{
            margin-top:20px;
        }
        
        .coinPic{
        	width:20px;
        	height:20px;
        }
        
        .titlePic{
    		width:100px;
    		height:38px;
    		position:absolute;
    		margin-top:-2px;
    	}
    	
    	.note {
    		font-size:14px;
    	}
    	
    	.attachButton {
    		display:none;
    	}
    </style>
    
    <script>
        
        //当日入金
        var currIncomeWYuan=0.0;
        //当日出金
        var currOutcomeWYuan=0.0;
        //手续费
        var feeWYuan=0.0;
        //当日盈亏
        var gainsAndLossesWYuan=0.0;
        //最新权益
        var currValueWYuan=0.0;
        //上日权益
        var lastDayValueWYuan=0.0;
        //初期资金
        var origionValueWYuan=0.0;
        //累计盈亏
        var totleGainsAndLossesWYuan=0.0;
        //累计入金
        var totalIncomeBrfore=0.0;
        //累计出金
        var totalOutComeBefore=0.0;
        
        $(document).ready(function (){
        	if("true" == '${suc}'){
        		alert("交易录入成功，管理员审核后在实盘交易展示");
        	} else if("false" == '{suc}'){
        		alert("系统异常，交易录入失败！");
        	}
        	$('#importDate').datepicker({
                format: 'yyyy-mm-dd'
            });
            $('#importDateFirst').datepicker({
                format: 'yyyy-mm-dd'
            });
			//是否已经通过验证        	
        	var isValidate = '${isValidated}';
        	if( isValidate == '1'){
        		$('#validateInfo').show();
        		$('#publish').show();
        	}
            $("#publish").click(function(){
            	var obj = window.parent.$('#mainFrame');
            	obj.addClass("contentFrame2");
            	obj.removeClass("contentFrame");
                window.location.href = "biz/member/publish";
            });
            $("#attention").click(function(){
                window.location.href = "biz/member/attention";
            });
            $("#detail").click(function(){
                window.location.href = "biz/member/detail";
            });
            
            $('#lookRecord').click(function (){
    			window.location.href = "/myTransRecord"
            });
            $('#addButton1').click();
            $('#addButton2').click();
        });

        var attachname = "myfiles";
        var i=1;
        function  addInput(param){
          if(i>0){
              var attach = attachname + param + i ;
              if(createInput(param,attach))
                i=i+1;
          }
          
        } 
        function deleteInput(param){
              if(i>1){
              i=i-1;
              if(!removeInput(param))
                  i=i+1;
            }
        } 
        
        function createInput(param,id){   
          var aElement=document.createElement("input");   
          aElement.name=attachname+param;
          aElement.id=id;
          aElement.type="file";
          aElement.size="50";
          if(document.getElementById("upload"+param).appendChild(aElement) == null)
               return false;
          return true;
        } 
        
        function removeInput(param){
            var aElement = document.getElementById("upload"+param);
            if(aElement.removeChild(aElement.lastChild) == null)
              return false;
            return true;   
        } 

        //选择导入时间后获取上日权益以及累计数据
        function query(obj){
        	var dateStr=obj.value;
        	if(dateStr == '' || dateStr == null) {
        		return;
        	}
        	queryForValue(dateStr);
        }

        //选择导入时间后获取上日权益以及累计数据
        function queryForValue(dateStr){
        	var type=$("#investType").val();
        	if(type==null || type==''){
        		alert("请选择投资品种!");
        		return;
        	}
        	$.ajax({
		        type: "POST",
		        url: "biz/transRecord/findLastTransRecord?type="+type+"&dateStr="+dateStr,
		        data: "",
		        success: function (msg) {
        			var obj = jQuery.parseJSON(msg);
        			if(obj.resultCode == "200"){
        				initAmount(obj.data.isHave,obj.data.lastDayValue,obj.data.totalInCome,obj.data.totalOutCome);
        			} else {
        				alert(obj.resultMsg);
        			}
		        }
		    });
        }

        /**
        */
        function initAmount(isHave,primeValue,totalInCome,totalOutCome){
            if(isHave=='true'){
            	$('#lastDayValue').attr("readOnly","true");
            	$('#lastDayValue').val(Number(primeValue).toFixed(2));
            	//上日权益
                lastDayValueWYuan=Number(primeValue);
            }else{
            	$('#lastDayValue').removeAttr("readOnly");
            	$('#lastDayValue').val(0.0);
            	//上日权益
                lastDayValueWYuan=0.0;
            }
            //累计入金
            totalIncomeBrfore=Number(totalInCome);
            //累计出金
            totalOutComeBefore=Number(totalOutCome);
            compute();
        }

        //计算数据
        function compute(){
            //1.导入日期等所有数据改变，所有数据都将重新计算
        	//当日入金
            currIncomeWYuan=Number($("#currIncomeWYuan").val());
            //当日出金
            currOutcomeWYuan=Number($("#currOutcomeWYuan").val());
            //手续费
            feeWYuan=Number($("#feeWYuan").val());
            //当日盈亏
            gainsAndLossesWYuan=Number($("#gainsAndLossesWYuan").val());
            lastDayValueWYuan=Number($("#lastDayValue").val());
            //当日权益
            currValueWYuan=lastDayValueWYuan+gainsAndLossesWYuan+currIncomeWYuan-currOutcomeWYuan-feeWYuan;
            //累计部分
            var totalIncomeWYuan=currIncomeWYuan+totalIncomeBrfore;
            var totalOutComeWYuan=currOutcomeWYuan+totalOutComeBefore;
            origionValueWYuan=$('#origionValueWYuan').val();
            if(origionValueWYuan==0.0){
               alert("初期资金为0，无法计算累计盈亏!");
               return;
            }
            totleGainsAndLossesWYuan=((currValueWYuan-totalIncomeWYuan+totalOutComeWYuan-origionValueWYuan)/origionValueWYuan*100).toFixed(2);
            $('#currValueWYuan').val(currValueWYuan.toFixed(2));
            $('#totalIncomeWYuan').val(totalIncomeWYuan.toFixed(2));
            $('#totalOutComeWYuan').val(totalOutComeWYuan.toFixed(2));
            $('#totleGainsAndLossesWYuan').val(parseFloat(totleGainsAndLossesWYuan)+"%");
        }


        function checkMoney1(obj){
        	if(!checkMoney(obj)){
            	return;
        	}
        	compute();
        }

        function checkMoney2(obj){
        	if(!checkMoney(obj)){
            	return;
        	}
        	compute();
        }
        
        function checkMoney3(obj){
        	if(!checkMoney(obj)){
            	return;
        	}
        	compute();
        }
        

        
       /**
        * 投资品种选中时间，判断是否第一次导入交易，所有金额应该清空的
        */
        function investTypeEvent(obj){
       	    origionValue=0.0;
       	    $('#origionValueWYuan').val("");
       	    $('#origionValueWYuan').removeAttr("readonly");
            var type=obj.value;
            if(type!=null && type!=''){
           	 $.ajax({
    		        type: "POST",
    		        url: "biz/transRecord/findFristTransRecord?type="+type,
    		        data: "",
    		        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
                			var isEXIST=obj.data.isEXIST;
                			if(isEXIST=='true'){
                    			var initialCapital=obj.data.initialCapital;
                    			origionValueWYuan=initialCapital;
                				$('#origionValueWYuan').val(initialCapital);
                				//设置readOnly
                				$('#origionValueWYuan').attr("readonly","readonly");
                				$('#recordType').val("");
                				showParam();
                			}else{
                				if('' == $('#recordType').val()){
                					$('#investTypeFirst').val($('#investType').val());
                				}
                				$('#recordType').val("FIRST");
                				hiddenParam();
                			}
                			//queryForValue($("#importDate").val());
            			} else {
            				alert(obj.resultMsg);
            			}
    		        }
    		    });
            }
        }
        
        function apply1(){
    		troyConfirm('确定要申请参赛吗?',function(){
	                $.ajax({
				        type: "POST",
				        url: "/biz/siteMessage/create?type=apply",
				        data: $("#form3").serialize(),
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				troyAlert(obj.resultMsg);
	            				$(".ui-icon-closethick").click();
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
            	});
    	}
    	
    	function apply(){
			showDiv("dialog-modal2",380,200);
    	}
    	
    	
    	function showDiv(id,width,height){
    		$( "#"+id ).dialog({
					height: height,
		            width:width,
					modal: true
			});
    	}
    	
    	function hiddenParam(){
    		$('#firstTable').show();
    		$('#fullTable').hide();
    	}
    	
    	function showParam(){
    		$('#firstTable').hide();
    		$('#fullTable').show();
    	}
    	
    	function createLink(){
    			$.ajax({
				        type: "POST",
				        url: "/biz/backstage/news/showLink",
				        data: '',
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				$('#linkShow').html(obj.resultMsg);
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
    	}
    </script>
</head>
<body>
    <div id="templatemo_main_wrapper" >
        <div><img src="${contextPath}/webresources/images/member_room.png" class="titlePic"/>
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom" id="contentDiv">
            <div style="margin-left:50px;margin-top:30px;border-bottom: 1px darkgrey solid;width: 85%">
                <p>会员用户：${name}<span style="display:none;color:red" id="validateInfo"><img src="${contextPath}/webresources/images/icon_5.png" class="coinPic"/></span>    &nbsp;您好 
                	<a href="javascript:void(0)" onclick="apply()" style="color: red" id="applyGame">申请参赛>></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<a href="javascript:void(0)" onclick="createLink()" style="color: red" id="applyGame">获取推广链接>></a><span id="linkShow"></span>
                </p>
                <p>您还剩余金币：<span style="color:red">${finance}</span> &nbsp;&nbsp;&nbsp;<input type="button" value="" class="btn btn-lg1 goldButton" id="detail"/><p></p>
                    <input type="button" value="" class="btn btn-lg1 publishButton" id="publish"  style="display:none;"/>
                    <input type="button" value="" class="btn btn-lg1 checkButton" id="attention"/></p>
                <div class="personalTitle" ><span style="color:#4c4b4b">交易记录导入</span><span class="note">&nbsp;&nbsp;(第一次只能导入初期资金和开始交易时间)</span></div>
            </div>
            <form class="troyFrom" id="form1" action="biz/transRecord/create"  method="POST"  enctype="multipart/form-data">
           <input type="hidden" id="recordType" name="recordType" value=""/>
            <div  style="margin-left:50px;margin-top:30px;border-bottom: 1px darkgrey solid;width: 85%">
                    <table class="registTableL" id="fullTable">
                        <tr>
                            <td style="width:25%">
                                <div>投资品种</div>
                            </td>
                            <td ><select id="investType" class="troySelect" name="investType" datatype="*" nullmsg="请投资品种！" errormsg="请投资品种！" onchange="investTypeEvent(this)">
                                <option value="">请选择</option>
                                <option value="qh" >&nbsp;&nbsp;期货&nbsp;&nbsp;</option>
                                <option value="gp">&nbsp;&nbsp;股票&nbsp;&nbsp;</option>
                                <option value="wh">&nbsp;&nbsp;外汇&nbsp;&nbsp;</option>
                                <option value="hj">&nbsp;&nbsp;贵金属&nbsp;&nbsp;</option>
                                <option value="by">&nbsp;&nbsp;模拟区&nbsp;&nbsp;</option>
                            </select></td>
                            <td style="width:20%"><div>导入时间</div></td>
                            <td style="width:20%"><input type="text" id="importDate" name="importDate" readonly="readonly" class="form-control full" datatype="*" nullmsg="请输入您的所在地址！" errormsg="所在地址至少1个字符,最多200个字符！" onblur="query(this)"/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>初期资金(元)</div>
                            </td>
                        <td width="30%">
                            <input type="text" id="origionValueWYuan" name="origionValue" class="form-control largeLi" datatype="*" />
                        </td>
                        <td >
                            <div style="width: 90px;">上日权益(元)</div>
                        </td>
                        <td><input id="lastDayValue" type="text" name="lastDayValue" class="form-control full" datatype="*"  onchange="checkMoney1(this)"/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>手续费(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="feeWYuan" name="fee" class="form-control largeLi" datatype="*" onchange="checkMoney1(this)"/>
                            </td>
                            <td >
                                <div style="width: 90px;">当日盈亏(元)</div>
                            </td>
                            <td><input type="text" id="gainsAndLossesWYuan" name="gainsAndLosses" class="form-control full" datatype="*" onchange="checkMoney3(this)"/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>最新权益(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="currValueWYuan"  name="currValue" class="form-control largeLi" datatype="*" readonly="readonly"/>
                            </td>
                            <td >
                                <div style="width: 90px;">累计盈亏(%)</div>
                            </td>
                            <td><input type="text" id="totleGainsAndLossesWYuan" name="totleGainsAndLosses" class="form-control full" datatype="*" readonly="readonly" /></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>当日入金(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="currIncomeWYuan" name="currIncome" class="form-control largeLi"  datatype="*" onchange="checkMoney1(this)"/>
                            </td>
                            <td >
                                <div style="width: 90px;">累计入金(元)</div>
                            </td>
                            <td><input type="text" id="totalIncomeWYuan" name="totalIncome" class="form-control full"  datatype="*" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>当日出金(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="currOutcomeWYuan"  name="currOutcome" class="form-control largeLi"  datatype="*" onchange="checkMoney1(this)"/>
                            </td>
                            <td >
                                <div style="width: 90px;">累计出金(元)</div>
                            </td>
                            <td><input type="text" id="totalOutComeWYuan" name="totalOutCome" class="form-control full"  datatype="*" readonly="readonly"/></td>
                        </tr>
                    </table>
                    <table class="registTableL" id="firstTable" style="display:none">
                        <tr>
                            <td style="width:25%">
                                <div>投资品种</div>
                            </td>
                            <td ><select id="investTypeFirst" class="troySelect" name="investTypeFirst" datatype="*" nullmsg="请投资品种！" errormsg="请投资品种！" onchange="investTypeEvent(this)">
                                <option value="">请选择</option>
                                <option value="qh" >&nbsp;&nbsp;期货&nbsp;&nbsp;</option>
                                <option value="gp">&nbsp;&nbsp;股票&nbsp;&nbsp;</option>
                                <option value="wh">&nbsp;&nbsp;外汇&nbsp;&nbsp;</option>
                                <option value="hj">&nbsp;&nbsp;贵金属&nbsp;&nbsp;</option>
                                <option value="by">&nbsp;&nbsp;模拟区&nbsp;&nbsp;</option>
                            </select></td>
                            <td><div>导入时间</div></td>
                            <td><input type="text" id="importDateFirst" name="importDateFirst" class="form-control full" datatype="*" nullmsg="请输入您的所在地址！" errormsg="所在地址至少1个字符,最多200个字符！" onchange="query(this)"/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>初期资金(元)</div>
                            </td>
                        <td width="30%">
                            <input type="text" id="origionValueWYuanFirst" name="origionValueFirst" class="form-control largeLi" datatype="*" />
                        </td>
                        <td >
                            <div style="width: 90px;"></div>
                        </td>
                        <td></td>
                        </tr>
                    </table>
                <div class="personalTitle">资金权益及持仓图片导入</div>
            </div>
            <div style="margin-top:10px;margin-left:50px;border-bottom: 1px darkgrey solid;">
                <input type="button" id="addButton1" name="button" value="添加附件" class="btn btn-lg btn-danger attachButton" onclick="addInput(1)" >
                <input type="button" name="button" value="删除附件" class="btn btn-lg btn-danger attachButton" onclick="deleteInput(1)" ><br></br>
                <span id="upload1"></span>
                <br></br>
                <div class="personalTitle">交易明细图片导入</div>
            </div>
            <div style="margin-top:10px;margin-left:50px;">
                <input type="button" id="addButton2"  name="button" value="添加附件" class="btn btn-lg btn-danger attachButton" onclick="addInput(2)" >
                <input type="button" name="button" value="删除附件" class="btn btn-lg btn-danger attachButton" onclick="deleteInput(2)" ></br>
                <span id="upload2"></span>
                <div class="buttonDiv" style="margin-top:50px;">
                 
               <!-- <input type="button" value="预    览" class="btn btn-lg btn-regist"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
                <input type="submit" value="提    交" class="btn btn-lg btn-danger" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="审核信息查看" class="btn btn-lg btn-danger" id="lookRecord"/></div></div>
        </form>
    </div>
    <!-- END of main -->
<!-- END of main wrapper -->
<div id="dialog-modal2" title="选择参赛品种" style="display:none;" >
        <form id="form3">
		<table style="margin-top:30px;">
            <tr>
            	<td>参赛品种</td>
            	<td  style="width:180px;" style="text-align: left;">
                            <input type="checkbox" name="investDirection" value="qh" id="checkbox1" /><label for="checkbox1">期货</label>
                            <input type="checkbox" name="investDirection" value="gp" id="checkbox2"/><label for="checkbox2">股票</label>
                            <input type="checkbox" name="investDirection" value="wh" id="checkbox3"/><label for="checkbox3">外汇</label>
                            <input type="checkbox" name="investDirection" value="hj" id="checkbox4"/><label for="checkbox4">贵金属</label>
                            <input type="checkbox" name="investDirection" value="by" id="checkbox5"/><label for="checkbox5">模拟区</label>
                        </td>
            </tr>
            <tr>
                <td></td><td><input type="button" value="提交" class="btn btn-lg btn-danger" style="margin-top:10px;" id="add3" onclick="apply1()"/></td>
            </tr>
        </table>
        </form>
	</div>
	
	<div id="dialog-modal3" title="推广链接" style="display:none;" >
		<p id="linkContent"></p>
	</div>
</body>
</html>