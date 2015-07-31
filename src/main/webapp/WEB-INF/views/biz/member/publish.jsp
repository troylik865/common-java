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
        
        #alreadyPublish{
    		border-radius: 4px;
        }
        
        #alreadyPublish ul li{
        	height:25px;
        	line-height:25px;
        	text-align:center;
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
		}
		
		#titleDiv{
        	height:25px;
    		border-top: 1px #A7A4A4 solid;
    		border-left: 1px #A7A4A4 solid;
    		border-right: 1px #A7A4A4 solid;
    		border-radius: 4px;
    		line-height:22px;
    		font-size:13px;
    		font-weight:bold;
		}
		
		#ulDiv{
    		height:130px;
    		border: 1px #A7A4A4 solid;
    		margin-top:-3px;
		}
		
		#titleDiv ul li {
			display: inline;
			float: left;
			margin-left:0px;
			text-align:center;
		}
		
		.bottomLine{
		}
		
    </style>
    <script>
        $(function () {
        	var isValidate = '${isValidated}';
        	if( isValidate == '1'){
        		$('#validateInfo').show();
        	}
        
        	$("#detail").click(function(){
                window.location.href = "/biz/member/detail";
            });
        
            $('#dateBegin').datepicker({
                format: 'yyyy-mm-dd'
            });
             $('#dateEnd').datepicker({
                format: 'yyyy-mm-dd'
            });
			$("#publish").click(function(){
				    troyConfirm('确定要发布此信息吗?',function(){
				    	var formData=$("#form1").serialize();
		                $.ajax({
					        type: "POST",
					        url: "/biz/publishMessage/create",
					        data: formData,
					        success: function (msg) {
		            			var obj = jQuery.parseJSON(msg);
		            			if(obj.resultCode == "200"){
		            				troyAlert(obj.resultMsg);
		            				window.location.reload();
		            			} else {
		            				troyAlert(obj.resultMsg);
		            			}
					        }
					    });
				    });
	            });
	            $('#query').click(function(){
	           		$('#leiji').html('');
	            	loadRealVisitData('leiji');
	            });
	            loadPublishData('alreadyPublish');
	            loadVisitData('visitStatus');
	            loadRealVisitData('leiji');
        });
        

        
        function loadPublishData(showDivId){
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 5,
		        url: "/biz/publishMessage/listByMemberId",
		        data: [
		            {
		                param: "content",
		                width:"50%"
		            },
		            {
		                param: "gmtCreateDate",
		                width:"20%"
		            },
		            {
		                param: "gmtCreateTime",
		                width:"15%"
		            },
		            {
		                param: "personNum",
		                width:"15%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
        function loadVisitData(showDivId){
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 5,
		        url: "/biz/attentionRecord/findAllWithVisityHistory",
		        dealBack:"countVisitNum",
		        data: [
		            {
		                param: "no",
		                width:"25%"
		            },
		            {
		                param: "name",
		                width:"25%"
		            },
		            {
		                param: "lastVisit",
		                width:"25%"
		            },
		            {
		                param: "left",
		                width:"25%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
    	function loadRealVisitData(showDivId){
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 5,
		        url: "/biz/attentionRecord/findAllWithRealVisityHistory?"+$("#form2").serialize(),
		        dealBack:"countHistoryVisitNum",
		        data: [
		            {
		                param: "no",
		                width:"25%"
		            },
		            {
		                param: "name",
		                width:"25%"
		            },
		            {
		                param: "lastVisit",
		                width:"25%"
		            },
		            {
		                param: "left",
		                width:"25%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
    	function countVisitNum(pageObj){
    		$('#totalAttent').html(pageObj.totalCount);
    	}
    	function countHistoryVisitNum(pageObj){
    		$('#leijiNum').html(pageObj.totalCount);
    	}

    </script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom1">
            <div style="margin-left:50px;margin-top:30px;width: 85%">
                <p>会员用户：${name}<span style="display:none;color:red" id="validateInfo">(赛)</span>    &nbsp;您好
                </p>
                <p>您还剩余金币：<span style="color:red">${finance}</span> &nbsp;&nbsp;&nbsp;<input type="button" value="" class="btn goldButton" id="detail"/>
                <div class="personalTitle">实盘信息发布</div>
            </div>
                <div  style="margin:0 auto;width: 85%">
            		<form id="form1">
                    <div style="margin-left: 100px;margin-top:10px;">
                        <textarea rows="10" cols="70" onfocus="clearText(this)" name="content">
                        </textarea>
                    </div>
                    <div style="margin-left: 245px;margin-top:10px;"><input type="button" value="发 送" class="btn btn-lg btn-danger" id="publish"/></div>
           			</form>
                    <div class="personalTitle bottomLine" >已发布的信息</div>
                    <div id="titleDiv">
                    	<ul>
                    		<li style="width:50%;margin-left:-30px;">发布的内容</li>
                    		<li style="width:20%">发布日期</li>
                    		<li style="width:15%">发布时间</li>
                    		<li style="width:15%">关注人数</li>
                    	</ul>
                    </div>
                    <div id="alreadyPublish" style="height:154px;padding-top:1px;"></div>
                </div>
                <div style="margin-top:10px;margin-left:50px;width: 85%">
                    <div class="textCenter contentDiv" >
                    </div>
                    <div class="personalTitle">访客实时状态</div>
                </div>
                <div style="margin-top:10px;margin-left:50px;width: 85%">
                    <div>已有 <span style="color:red" id="totalAttent"></span> 人关注</div>
                    <div id="titleDiv">
                    	<ul>
                    		<li style="width:25%;margin-left:-30px;">编号</li>
                    		<li style="width:25%">会员名</li>
                    		<li style="width:25%">访问日期</li>
                    		<li style="width:25%">剩余天数</li>
                    	</ul>
                    </div>
                    <div id="visitStatus" class="textCenter contentDiv" style="margin-top:-1px;">
                    </div>
                </div>
                <div style="margin-top:50px;margin-left:50px;width: 85%">
                <div class="personalTitle">访客历史累计查阅</div>
                	<form id="form2">
                    <input type="text" name="beginDate" id="dateBegin" class="span2" />  -- <input type="text"  class="span2" name="endDate" id="dateEnd"/>     <input type="button" value="查询" class="btn btn-lg btn-danger" id="query"/>
                    </form>
                    <div id="titleDiv" style="margin-top:10px;">
                    	<ul>
                    		<li style="width:25%;margin-left:-30px;">编号</li>
                    		<li style="width:25%">会员名</li>
                    		<li style="width:25%">访问日期</li>
                    		<li style="width:25%">剩余天数</li>
                    	</ul>
                    </div>
                    <div class="textCenter " id="leiji">
                    </div>
                    <div>累计已有 <span style="color:red" id="leijiNum"></span> 人关注</div>
                </div>
        </div>
        <!-- END of main -->
    </div>
    <!-- END of main wrapper -->
</body>
</html>