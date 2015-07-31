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
        		alert("交易录入成功！");
        	} else if("false" == '{suc}'){
        		alert("系统异常，交易录入失败！");
        	}
        	
        	queryForValue($("#importDate").val());
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
		        url: "/biz/transRecord/findLastTransRecord1?type="+type+"&dateStr="+dateStr,
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
        	//初始化初期资金
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
        
        
    	
    	function hiddenParam(){
    		$('#firstTable').show();
    		$('#fullTable').hide();
    	}
    	
    	function showParam(){
    		$('#firstTable').hide();
    		$('#fullTable').show();
    	}
    </script>
</head>
<body>
    <div id="templatemo_main_wrapper" >
        <div><img src="${contextPath}/webresources/images/member_room.png" class="titlePic"/>
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom" id="contentDiv">
            <form class="troyFrom" id="form1" action="/biz/transRecord/updateRecord"  method="POST"  enctype="multipart/form-data">
           <input type="hidden" id="investType" name="investType" value="${investType}"/>
           <input type="hidden" id="id" name="id" value="${id}"/>
            <div  style="margin-left:50px;margin-top:30px;width: 85%">
                    <table class="registTableL" id="fullTable">
                        <tr>
                            <td style="width:25%">
                                <div>投资品种</div>
                            </td>
                            <td >${investTypeName}</td>
                            <td style="width:20%"><div>导入时间</div></td>
                            <td style="width:20%"><input type="text" id="importDate" name="importDate" class="form-control full"  value="${importDate}" readOnly/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>初期资金(元)</div>
                            </td>
                        <td width="30%">
                            <input type="text" id="origionValueWYuan" name="origionValue" class="form-control largeLi" readOnly value="${origionValue}"/>
                        </td>
                        <td >
                            <div style="width: 90px;">上日权益(元)</div>
                        </td>
                        <td><input id="lastDayValue" type="text" name="lastDayValue" class="form-control full"  value="${lastDayValue}"/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>手续费(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="feeWYuan" name="fee" class="form-control largeLi" value="${fee}" onchange="checkMoney3(this)"/>
                            </td>
                            <td >
                                <div style="width: 90px;">当日盈亏(元)</div>
                            </td>
                            <td><input type="text" id="gainsAndLossesWYuan" name="gainsAndLosses" class="form-control full" value="${gainsAndLosses}" onchange="checkMoney3(this)"/></td>
                        </tr>
                        <tr>
                            <td style="width:25%">
                                <div>最新权益(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="currValueWYuan"  name="currValue" class="form-control largeLi" value="${currValue}" readonly="readonly"/>
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
                                <input type="text" id="currIncomeWYuan" value="${currIncome}" name="currIncome" class="form-control largeLi"  datatype="*" onchange="checkMoney1(this)"/>
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
                                <input type="text" id="currOutcomeWYuan" value="${currOutcome}"   name="currOutcome" class="form-control largeLi"  datatype="*" onchange="checkMoney1(this)"/>
                            </td>
                            <td >
                                <div style="width: 90px;">累计出金(元)</div>
                            </td>
                            <td><input type="text" id="totalOutComeWYuan" name="totalOutCome" class="form-control full"  datatype="*" readonly="readonly"/></td>
                        </tr>
                        <tr style="color:red;font-weight:bold">
                        	<td  style="text-align:right">
                            	审核意见:
                        </td>
                       	 <td colspan="3">${failReason}</td>
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
                                <option value="hj">&nbsp;&nbsp贵金属&nbsp;&nbsp;</option>
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
                    </table>
            </div>
            <div class="buttonDiv" style="margin-top:50px;">
                <input type="submit" value="提    交" class="btn btn-lg btn-danger" />
           </div>
        </form>
    </div>
    <!-- END of main -->
<!-- END of main wrapper -->
</body>
</html>