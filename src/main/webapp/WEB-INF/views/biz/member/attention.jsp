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
    <link href="${contextPath}/webresources/css/datepicker.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
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
        
        #attentDiv{
    		border: 1px #A7A4A4 solid;
    		border-radius: 4px;
        }
        
		.pageDiv{
		    font-size: 12px;
		    margin-top:0px;
		    float:right;
		    margin-right:20px;
		}
		.divContent ul li {
			display: inline;
			float: left;
			margin-left: 0px;
			font-size: 12px;
			height: 25px;
			line-height:25px;
        	text-align:center;
		}
		
		#titleDiv{
        	height:25px;
    		border-top: 1px #A7A4A4 solid;
    		border-left: 1px #A7A4A4 solid;
    		border-right: 1px #A7A4A4 solid;
    		border-radius: 4px;
    		line-height:25px;
    		font-weight:bold;
		}
		
		#ulDiv{
    		height:130px;
    		margin-left:40px;
    		margin-top:-10px;
		}
		
		#titleDiv ul li {
			display: inline;
			float: left;
			margin-left:0px;
			text-align:center;
		}
		
		#memberName {
			color:red;
		}
		
		#memberName2 {
			color:red;
		}
		
    </style>
    <script>
    
    	function showDiv(id,width,height){
    		$( "#"+id ).dialog({
					height: height,
		            width:width,
					modal: true
			});
			$( "#"+id ).parent().css({top:250});
    	}
    	var per = 0;
       	var attentMemberNum = 0;
        $(document).ready(function(){
        	var memberNo = '${memberNo}';
        	if(null != memberNo && memberNo != ''){
        		$('#memberNo').val(memberNo);
        		var formData=$("#form1").serialize();
					$.ajax({
				        type: "POST",
				        url: "/biz/member/getDailyCost",
				        data: formData,
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				var cost = obj.data.cost;
	            				$('#dailyCost').html(cost);
	            				$('#dateNum').val("");
	            				$('#memberName').html("("+obj.data.name+")");
	            				$('#totalNum').html("0");
	            				per = cost;
	            			} 
				        }
				    });
				    $('#dateNum').focus();
        	}
        	
        	$('#dateBegin').datepicker({
                format: 'yyyy-mm-dd'
            });
             $('#dateEnd').datepicker({
                format: 'yyyy-mm-dd'
            });
           	addValidateWithLeft($('#form1'),330);
           	$('#form1').Validform().addRule([{
				ele:"#memberNo",
				ajaxurl:"/biz/attentionRecord/check"
			}]);
			
			$('#query').click(function(){
	        	loadHistoryData("historyDiv");	
			});
			
			$('#memberNo').blur(
				function(){
					if($('#memberNo').val() == '' || $('#memberNo').val() == null){
            			$('#memberName').html("");
						return;
					}
					var formData=$("#form1").serialize();
					$.ajax({
				        type: "POST",
				        url: "/biz/member/getDailyCost",
				        data: formData,
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				var cost = obj.data.cost;
	            				$('#dailyCost').html(cost);
	            				$('#dateNum').val("");
	            				$('#memberName').html("("+obj.data.name+")");
	            				$('#totalNum').html("0");
	            				per = cost;
	            			} 
				        }
				    });
				}
			);
        	$("#add").click(function(){
            	troyConfirm('确定要关注该大师?',function(){
					var formData=$("#form1").serialize();
	                $.ajax({
				        type: "POST",
				        url: "/biz/member/addAttented",
				        data: formData,
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				$('#attentDiv').html("");
	            				loadAttentData("attentDiv");
	            				troyAlert(obj.resultMsg);
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
				});
            });
            loadAttentData("attentDiv");
            loadHistoryData("historyDiv");
            $('#dateNum').keyup(function(){
            	var count = $(this).val();
            	if(!isNaN(count) && (null != $('#memberNo').val() && "" != $('#memberNo').val())){
            		$('#totalNum').html(count * per);
            	}
            });
            
        });
        
        function initSelect(){
        	$.ajax({
		        type: "POST",
		        url: "/biz/attentionRecord/initSelect",
		        data: "",
		        success: function (msg) {
        			var obj = jQuery.parseJSON(msg);
        			if(obj.resultCode == "200"){
        				var selectObj = $('#memberNameSelect');
        				selectObj.html('');
			            $(obj.data).each(function(){
			            	selectObj.append("<option value='"+$(this).attr('id')+"'>"+$(this).attr('name')+"</option>");
			            });
        			} else {
        				troyAlert(obj.resultMsg);
        			}
		        }
			});
        }
        function loadAttentData(showDivId){
        	initSelect();
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 5,
		        url: "/biz/attentionRecord/findAll",
		        dealBack:"dealBackInfo",
		        data: [
		            {
		                param: "memberNo",
		                width:"20%"
		            },
		            {
		                type:"method",
		                method:"showName",
		                width:"20%"
		            },
		            {
		                param: "left",
		                width:"15%"
		            },
		            {
		                param: "starCount",
		                width:"15%"
		            },
		            {
		                type:"method",
		                method:"opertor2",
		                width:"30%"
		            }
		        ]
		    };
		    loadData(dataInfo);
    	}
        function loadHistoryData(showDivId){
        	$('#'+showDivId).html('');
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 5,
		        url: "/biz/publishMessageDetail/listByAcceptMemberId?"+$("#form4").serialize(),
		        data: [
		            {
		                param: "no",
		                width:"5%"
		            },
		            {
		                param: "acceptDate",
		                width:"15%"
		            },
		            {
		                param: "acceptTime",
		                width:"15%"
		            },
		            {
		                param: "source",
		                width:"10%"
		            },
		            {
		                param: "content",
		                width:"45%"
		            },
		            {
		                type:"method",
		                method:"opertor3",
		                width:"10%"
		            }
		        ]
		    };
		    loadData(dataInfo);
    	}
    	
    	function dealBackInfo(pageObj,data){
    		var totalCount = pageObj.totalCount;
    		$('#attentMemberNum').html(totalCount);
    	}
    	
    	function showName(rowData,index) {
    		var link = rowData.link;
    		if('' == link || null == link){
    			return rowData.name;
    		} else {
    			return"<a href=\""+link+"\" target=\"_blank\" style=\"text-decoration:underline;color:blue\">"+rowData.name+"</a>";
    		}
    		
    	}
    	
    	function opertor(rowData,index){
    		return "<a href=\"javascript:void(0)\" onclick=\"del('"+rowData.id+"')\">取消关注</a> | <a href=\"javascript:void(0)\">延长</a>"
    	}
    	
    	function opertor3(rowData,index){
    		return "<a href=\"javascript:void(0)\" onclick=\"delMsg('"+rowData.id+"')\">删除</a>";
    	}
    	
    	function opertor2(rowData){
    		return "<a href=\"javascript:void(0)\" onclick=\"del('"+rowData.id+"')\">取消关注</a> | <a href=\"javascript:void(0)\"  onclick=\"prolong('"+rowData.id+"','"+rowData.cost+"','"+rowData.memberNo+"','"+rowData.name+"')\">延长</a> | <a href=\"javascript:void(0)\"   onclick=\"assess('"+rowData.id+"','"+rowData.cost+"','"+rowData.memberNo+"','"+rowData.name+"','"+rowData.score+"','"+rowData.content+"')\">对大师评价</a>"
    	}
    	
    	function assess(id,cost,memberNo,name,score,content){
			showDiv("dialog-modal2",380,360);
			$('#memberName3').html(name+"("+memberNo+")");
			$('#score').val(score);
			$('#contentArea').val(content);
			$('#add3').click(function(){
				troyConfirm('确定要对该大师进行评价?',function(){
            		var formData=$("#form3").serialize();
	                $.ajax({
				        type: "POST",
				        url: "/biz/member/assessAttented?id="+id,
				        data: formData,
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				$('#attentDiv').html("");
	            				loadAttentData("attentDiv");
	            				troyAlert(obj.resultMsg);
	            				$(".ui-icon-closethick").click();
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
            	});
			});
    	}
    	
		function prolong(id,cost,memberNo,name){
			showDiv("dialog-modal",420,230);
			$('#memberName2').html("("+name+")");
			$('#memberNo2').html(memberNo);
			$('#dailyCost2').html(cost+"");
			$('#dateNum2').keyup(function(){
            	var count = $(this).val();
            	if(!isNaN(count)){
            		$('#totalNum2').html(count * cost);
            	}
            });
            
            $("#closeButton").click(function(){
	            	$(".ui-icon-closethick").each(function(){
	            		$(this).click();
	            	});;
            });
            $("#closeButton1").click(function(){
	            	$(".ui-icon-closethick").each(function(){
	            		$(this).click();
	            	});;
            });
            $("#add2").click(function(){
            	troyConfirm('确定要对该大师进行取消延长?',function(){
            		var formData=$("#form2").serialize();
	                $.ajax({
				        type: "POST",
				        url: "/biz/member/prolongAttented?id="+id,
				        data: formData,
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				$('#attentDiv').html("");
	            				loadAttentData("attentDiv");
	            				troyAlert(obj.resultMsg);
	            				$(".ui-icon-closethick").click();
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
            	});
            });
		}    	
    	
    	function del(id) {
    		troyConfirm('确定要对该大师进行取消关注?',function(){
    			$.ajax({
			        type: "POST",
			        url: "/biz/attentionRecord/delAttented?id="+id,
			        data: "",
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
            				$('#attentDiv').html("");
            				loadAttentData("attentDiv");
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    }); 
    		});
    	}
    	function delMsg(id) {
    		troyConfirm('确定要删除该消息?',function(){
    			$.ajax({
			        type: "POST",
			        url: "/biz/publishMessageDetail/del?id="+id,
			        data: "",
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
            				loadHistoryData("historyDiv");
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    }); 
    		});
    	}
    </script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom">
        	<div style="margin-top:50px;margin-left:50px;width: 85%">
                <div class="personalTitle">大师消息查看</div>
	                <form id="form4" style="margin-bottom:5px;">
	                    大师姓名：
	                    <select id="memberNameSelect" name="memberId">
	                    	
	                    </select>
	                   	&nbsp;&nbsp;&nbsp;接收日期：
	                    <input type="text" name="beginDate" id="dateBegin" class="span2" style="height:26px;" />  -- <input type="text" class="span2" style="height:26px;" name="endDate" id="dateEnd"/>     <input type="button" value="查询" class="btn btn-lg btn-danger" id="query" />
	            	</form>
                	<div id="titleDiv">
	                	<div style="margin-left:10px;width:93%;margin-top:-10px;">
	                    	<ul>
	                    		<li style="width:5%;">编号</li>
	                    		<li style="width:15%">接收日期</li>
	                    		<li style="width:15%">接收时间</li>
	                    		<li style="width:10%">消息来源</li>
	                    		<li style="width:45%">消息内容</li>
	                    		<li style="width:10%">操作</li>
	                    	</ul>
                    	</div>
                    </div>
                    <div class="textCenter contentDiv" id="historyDiv" style="border: 1px darkgrey solid;margin-top:-2px;">
                    </div>
                </div>
            <div style="margin-left:50px;margin-top:30px;width: 85%">
                <div class="personalTitle">我正在关注的大师</div>
            </div>
                <div  style="margin:0 auto;width: 85%">
                	<div  id="titleDiv">
	                	<div style="margin-left:10px;width:93%;margin-top:-10px;">
	                    	<ul>
	                    		<li style="width:20%;">编号</li>
	                    		<li style="width:20%">会员名</li>
	                    		<li style="width:15%">剩余天数</li>
	                    		<li style="width:15%">对大师评分</li>
	                    		<li style="width:30%">操作</li>
	                    	</ul>
	                    </div>
                    </div>
                    <div class="textCenter" id="attentDiv" style="margin-top:-3px;">
                    </div>
                    <div style="width:85%">累计已有 <span style="color:red" id="attentMemberNum"></span> 人关注</div>
                   	<br>
                </div>
                <form id="form1">
	                <div style="margin-top:0px;margin-left:50px;width: 85%">
                    	<div class="personalTitle" style="margin-top:20px;">添加大师</div>
	                    <div class="textCenter contentDiv" style="border: 1px darkgrey solid;border-radius:4px;height:190px;padding:20px">
	                        <p style="text-align: left">如果您想添加关注某位大师实盘动态请填写：</p>
	                        <table>
	                            <tr>
	                            	<td style="width:172px"><div style="text-align:right">大师编号</div></td>
						            <td   style="width:205px;text-align:left;padding-left:20px"><input type="text" value="" name="memberNo" id="memberNo" class="form-control small" datatype="s1-38" nullmsg="请输入您需要关注的大师编号！" errormsg="大师编号至少1个字符,最多38个字符！" style="width:100px;height:26px;"></td>
						            <td colspan="3">
						            <div class="info"><span class="Validform_checktip">大师编号至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
						            </td>
	                            </tr>
	                            <tr>
	                                <td></td><td class="left" style="margin-left:20px;">关注此大师<span id="memberName"></span>需金币<span style="color:red" id="dailyCost">0</span>个/天</td> <td></td>
	                            </tr>
	                            <tr>
	                            	<td style="width:172px"><div style="text-align:right;">预设关注(天)</div></td>
						            <td style="width:205px;text-align:left;padding-left:20px;"><input type="text" value="" name="dateNum" id="dateNum" class="form-control small" datatype="s1-38" nullmsg="请输入您的预设天数！" errormsg="天数至少1个字符,最多38个字符！" style="width:100px;height:26px;"></td>
						            <td colspan="3">
						            <div class="info"><span class="Validform_checktip">天数至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
						            </td>
	                            </tr>
	                            <tr>
	                                <td></td><td class="left" style="margin-left:20px;">累计扣除金币<span style="color:red" id="totalNum">0</span>个</td><td></td>
	                            </tr>
	                            <tr>
	                                <td></td><td><input type="button" value="确认添加" class="btn btn-lg btn-danger" id="add"/></td>
	                            </tr>
	                        </table>
	                    </div>
	                </form>
                </div>
        </div>
        <!-- END of main -->
    </div>
    <!-- END of main wrapper -->
    <div id="dialog-modal" title="延长大师关注时间" style="display:none;" >
        <form id="form2">
		<table style="margin-top:30px;">
            <tr>
            	<td style="width:102px"><div style="text-align:right">大师编号</div></td>
	            <td   style="width:205px;text-align:left;padding-left:20px"><span id="memberNo2"></span></td>
	            <td colspan="3">
	            <div class="info" style="margin-top:-395px;"><span class="Validform_checktip">大师编号至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
	            </td>
            </tr>
            <tr>
                <td></td><td class="left" style="margin-left:20px;width:280px;">关注此大师<span id="memberName2"></span>需金币<span style="color:red" id="dailyCost2">0</span>个/天</td> <td></td>
            </tr>
            <tr>
            	<td style="width:102px"><div style="text-align:right">预设关注(天)</div></td>
	            <td style="width:205px;text-align:left;padding-left:20px;"><input type="text" value="" name="dateNum" id="dateNum2" class="form-control small" datatype="s1-38" nullmsg="请输入您的预设天数！" errormsg="天数至少1个字符,最多38个字符！" style="width:100px;height:26px;"></td>
	            <td colspan="3">
	            <div class="info"  style="margin-top:-395px;"><span class="Validform_checktip">天数至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
	            </td>
            </tr>
            <tr>
                <td></td><td class="left" style="margin-left:20px;">累计扣除金币<span style="color:red" id="totalNum2">0</span>个</td><td></td>
            </tr>
            <tr>
                <td></td><td><input type="button" value="确认延长" class="btn btn-lg btn-danger" id="add2" style="margin-left:20px;margin-top:10px;"/><input type="button" value="关闭" class="btn btn-lg btn-danger" id="closeButton" style="margin-left:20px;margin-top:10px;"/></td>
            </tr>
        </table>
        </form>
	</div>
	<div id="dialog-modal2" title="对大师进行评价" style="display:none;" >
        <form id="form3">
		<table style="margin-top:30px;">
            <tr>
            	<td>大师姓名(编号):</td><td><span style="color:red" id="memberName3"></span></td>
            </tr>
            <tr>
            	<td>评&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;星:</td>
            	<td>
            		<select id="score" name="score" style="width:60px;" style="margin-top:10px;">
            			<option value="0">0</option>
            			<option value="1">1</option>
            			<option value="2">2</option>
            			<option value="3">3</option>
            			<option value="4">4</option>
            			<option value="5">5</option>
            		</select> &nbsp;&nbsp;星
            	</td>
            </tr>
            <tr  style="padding-top:10px">
            	<td>评价内容:</td><td><textarea rows=8 cols=30 id="contentArea" name="content" style="margin-top:10px;"></textarea></td>
            </tr>
            <tr>
                <td></td><td><input type="button" value="提交" class="btn btn-lg btn-danger" style="margin-top:10px;" id="add3"/><input type="button" value="关闭" class="btn btn-lg btn-danger" id="closeButton1" style="margin-left:20px;margin-top:10px;"/></td>
            </tr>
        </table>
        </form>
	</div>
</body>
</html>