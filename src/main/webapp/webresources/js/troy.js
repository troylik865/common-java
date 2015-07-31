function clearText(obj) {
    $(obj).val($.trim($(obj).val()));
}


/**
 * 分页方法
 * @param data            url属性URL？numPerPage=10&pageNum=1
 * @param type
 */
function loadDataByType(dataInfo, type) {
    var pageNum = 1;
    var divObj = $('#' + $(dataInfo).attr('divId'));
    if (typeof divObj.data("pageNum") != "undefined") {
        pageNum = divObj.data("pageNum");
    }
    var pageSize = $(dataInfo).attr("pageSize");
    var url = $(dataInfo).attr("url");
    if (url.indexOf("?") != -1) {
        if (url.indexOf(url.length - 1, url.length) == "?") {
        } else {
            url += "&";
        }
    } else {
        url += "?";
    }
    url += "pageNum=" + pageNum + "&numPerPage=" + pageSize;
    $.ajax({
        type: type,
        url: url,
        data: dataInfo,
        success: function (msg) {
            var obj = jQuery.parseJSON(msg);
            loadRealDate(dataInfo, obj);
        }
    });
}

function loadData(data) {
    loadDataByType(data, "POST");
}

var showCountValue = 3;
function loadRealDate(dataInfo, obj) {
    var pageNum = 1;
    var divShowId = $(dataInfo).attr('divId');
    var divObj = $('#' + divShowId);
    if (typeof divObj.data("pageNum") != "undefined") {
        pageNum = divObj.data("pageNum");
    }
    pageNum = parseInt(pageNum, 10);
    var pageObj = $(obj).attr("page");
    var dataObj = $(obj).attr("data");
    var size = $(dataObj).size();
    /*if (size <= 0) {
        //返回空数据的展示
        return;
    }*/
    var ulObj = $("<ul></ul>");
    var ulDiv = $("<div id=\"ulDiv\"></div>");
    divObj.append(ulDiv);
    ulDiv.append(ulObj);
    //遍历返回的数据
    var index = 0;
    $(dataObj).each(function (i) {
        var dataSingle = this;

        //根据配置的json对象信息 进行dataObj内的数据筛选
        var dataObj = dataInfo.data;
        $(dataInfo.data).each(function (j) {
            var liValue = dataSingle[this.param];
            var width;
            if(typeof this.width != "undefined"){
            	width = "style=\"width:"+this.width + " \" ";
            } else {
            	width = '';
            }
            if(this.type == "method"){
            	ulObj.append("<li "+ width +">"+eval(this.method+"(dataSingle,(i+1))")+"</li>");
            }else {
            	var temp = liValue;
            	var showLength = 10;
            	if(null != liValue && '' != liValue && liValue.length > showLength){
            		temp = temp.substring(0,showLength) + "...";
            	}
           	 	ulObj.append("<li "+width+" title='"+liValue+"'>" + temp + "</li>");
            }
        });
    });
    if (!divObj.hasClass("divContent")) {
        divObj.addClass("divContent");
    }
    if(typeof dataInfo.hidePageDiv != 'undefined'){
    	return;
    }
	var pagesItemValue = "";
	var totalPage = pageObj.totalPage;
    if(typeof dataInfo.hideNum == 'undefined'){
	    if(totalPage<=showCountValue){
	    		for (var i = totalPage-1; i >= 0; i--) {
	                var tp = (totalPage - i);
	                if (tp == pageNum) {
	                    pagesItemValue += "<a href=\"javascript:void(0)\" class=\"tempCount" + divShowId + " selected\" data=" + tp + ">" + tp + "</a>";
	                } else {
	                    pagesItemValue += "<a href=\"javascript:void(0)\" class=\"tempCount" + divShowId + "\" data=" + tp + ">" + tp + "</a>";
	                }
	            }
	    }else {
	    	for (var i = 0; i <= showCountValue; i++) {
	        //如果页数大于2 还是只显示3页
	        if (totalPage - pageNum < 4) {
	            for (var i = showCountValue; i >= 0; i--) {
	                var tp = (totalPage - i);
	                if (tp == pageNum) {
	                    pagesItemValue += "<a href=\"javascript:void(0)\" class=\"tempCount" + divShowId + " selected\" data=" + tp + ">" + tp + "</a>";
	                } else {
	                    pagesItemValue += "<a href=\"javascript:void(0)\" class=\"tempCount" + divShowId + "\" data=" + tp + ">" + tp + "</a>";
	                }
	            }
	            break;
	        }
	        if (i > showCountValue - 1) {
	            if (totalPage - showCountValue == pageNum) {
	                pagesItemValue += "<a href=\"javascript:void(0)\" class=\"tempCount" + divShowId + "\" data=" + totalPage + ">" + totalPage + "</a>";
	            } else {
	                pagesItemValue += "<a href=\"javascript:void(0)\">...</a>";
	            }
	            break;
	        }
	        var temp = (pageNum + i);
	        if (temp == pageNum) {
	            pagesItemValue += "<a href=\"javascript:void(0)\" class=\"tempCount" + divShowId + " selected\" data=" + temp + ">" + temp + "</a>";
	        } else {
	            pagesItemValue += "<a href=\"javascript:void(0)\" class=\"tempCount" + divShowId + "\" data=" + temp + ">" + temp + "</a>";
	        }
	    }
	    }
    }
    
    var first = "";
    var last = "";
    if(typeof dataInfo.hideNum == 'undefined'){
    	first = "<a href='javascript:void(0)' id=\"first" + divShowId + "\">首页</a>";
    	last = "<a href='javascript:void(0)' id=\"last" + divShowId + "\">末页</a>";
    }
	
    var pageDiv = "<div class=\"pageDiv\">" +
        first +
        "<a href='javascript:void(0)' id=\"pre" + divShowId + "\">上一页</a>" + pagesItemValue +
        "<a href='javascript:void(0)' id=\"next" + divShowId + "\">下一页</a>" +
        last +
        "</div>";
    divObj.append(pageDiv);
    divObj.data("pageNum", pageNum);
    $('#pre' + divShowId).click(function () {
        var curPageNum = divObj.data("pageNum");
        if (curPageNum < showCountValue - 1) {
            return;
        } else {
            $(divObj).html("");
            divObj.data("pageNum", curPageNum - 1);
            loadData(dataInfo);
        }
    });
    $('#next' + divShowId).click(function () {
        var curPageNum = divObj.data("pageNum");
        if (curPageNum >= totalPage) {
            return;
        }
        $(divObj).html("");
        divObj.data("pageNum", curPageNum + 1);
        loadData(dataInfo);
    });
    $('#last' + divShowId).click(function () {
        divObj.data("pageNum", totalPage);
        $(divObj).html("");
        loadData(dataInfo);
    });
    $('#first' + divShowId).click(function () {
        divObj.data("pageNum", 1);
        $(divObj).html("");
        loadData(dataInfo);
    });
    $(".tempCount" + divShowId).each(function () {
        $(this).click(function () {
            var count = $(this).attr("data");
            divObj.data("pageNum", count);
            $(divObj).html("");
            loadData(dataInfo);
        });
    });
    if(typeof dataInfo.dealBack != 'undefined'){
    	eval(dataInfo.dealBack+"(pageObj,dataObj)");
    }
}


function troyAlert(showMsg,title) {
	top.jAlert(showMsg, title);
}
function troyAlert(showMsg) {
	top.jAlert(showMsg, "温馨提示");
}

function troyConfirm(showMsg,confirmMethod) {
	top.jConfirm(showMsg, '确定对话框', function(r) {
         if(r){
			 evalMethod(confirmMethod);       
         } 
    });
}

function evalMethod(methodName){
	if(typeof methodName == 'function'){
		methodName();
	}
}

function addValidateWithLeft(formObj,leftLength) {
	formObj.Validform({
		tiptype:function(msg,o,cssctl){
			//msg：提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;

			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.parents("td").next().find(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
				
				var infoObj=o.obj.parents("td").next().find(".info");
				if(o.type==2){
					infoObj.fadeOut(200);
				}else{
					if(infoObj.is(":visible")){return;}
					var left=o.obj.offset().left,
						top=o.obj.offset().top;
	
					infoObj.css({
						left:leftLength,
						top:top-45
					}).show().animate({
						top:top-35	
					},200);
				}
				
			}	
		},
		usePlugin:{
			passwordstrength:{
				minLen:6,
				maxLen:18,
				trigger:function(obj,error){
					if(error){
						obj.parents("td").next().find(".passwordStrength").hide();
					}else{
						obj.parents("td").next().find(".info").hide();
						obj.parents("td").next().find(".passwordStrength").show();	
					}
				}
			}
		}
	});
}

function addValidateResize(formObj,leftLength) {
	formObj.Validform({
		tiptype:function(msg,o,cssctl){
			//msg：提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;

			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.parents("td").next().find(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
				
				var infoObj=o.obj.parents("td").next().find(".info");
				if(o.type==2){
					infoObj.fadeOut(200);
				}else{
					if(infoObj.is(":visible")){return;}
					var left=o.obj.offset().left,
						top=o.obj.offset().top;
					infoObj.css({
						left:left + leftLength,
						top:top-45
					}).show().animate({
						top:top-35	
					},200);
				}
				
			}	
		},
		usePlugin:{
			passwordstrength:{
				minLen:6,
				maxLen:18,
				trigger:function(obj,error){
					if(error){
						obj.parents("td").next().find(".passwordStrength").hide();
					}else{
						obj.parents("td").next().find(".info").hide();
						obj.parents("td").next().find(".passwordStrength").show();	
					}
				}
			}
		}
	});
}

function addValidate(formObj) {
	addValidateWithLeft(formObj,125);
}

function checkValidate(formObj){
	return formObj.Validform().check();
}

function checkLogin(){
    		var flag = false;
    		$.ajax({
			        type: "POST",
			        url: "/biz/member/checkSession",
			        data: "",
			        async:false,
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
            				if(obj.data == true){
            					flag = true;
            				}
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    });
			    return flag;
}
