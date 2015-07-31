/******************************************dwz相关自定义js***********************************************/
/**
 * 得到当前活动的navtab
 * @returns
 */
function getCurrentNavtab(){
	return $("ul.navTab-tab li.selected");
}

/**
 * 得到当前活动的navtab的局部区域
 * @returns
 */
function getCurrentNavtabRel(){
	var $pDiv = $('.tabsPage div[class="page unitBox"][style*="block"]').first();
	var $ub = $("div.unitBox", $pDiv);
	if ($ub.length > 0) {
		return $ub.first();
	}
	return $pDiv;
}

/**
 * 自动刷新当前活动的navTab
 * @param json
 */
function dialogReloadNavTab(json){
	DWZ.ajaxDone(json);
	var tabId = getCurrentNavtab().attr("tabid");
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.navTabId || tabId!=null){
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		} else if (json.rel) {
			var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
			var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {};
			navTabPageBreak(args, json.rel);
		}
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
		}
	}
}

/**
 * 自动刷新当前活动的navTab的局部区域
 * @param json
 */
function dialogReloadRel(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		var rel = getCurrentNavtabRel().attr("id");
		var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
		var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {};
		navTabPageBreak(args, rel);

		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
		}
	}
}

/**
 * 根据rel自动局部刷新
 * @param json
 */
function reloadRel(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlag(json.navTabId);
		} else { //重新载入当前navTab页面
			var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
			var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {};
			if (json.rel != null && json.rel!="") {
				navTabPageBreak(args, json.rel);
			} else {
				var rel = getCurrentNavtabRel().attr("id");
				navTabPageBreak(args, rel);
			}
		}
		
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab(json.navTabId);}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		} else if ("forwardConfirm" == json.callbackType) {
			alertMsg.confirm(json.confirmMsg || DWZ.msg("forwardConfirmMsg"), {
				okCall: function(){
					navTab.reload(json.forwardUrl);
				}
			});
		} else {
			navTab.getCurrentPanel().find(":input[initValue]").each(function(){
				var initVal = $(this).attr("initValue");
				$(this).val(initVal);
			});
		}
	}
	
}

/**
 * 根据id自动局部刷新，用于Group页面
 * @param json
 */
function dialogReloadRel2Group(json){
	if (json.statusCode == DWZ.statusCode.ok) {
		$("#refreshJbsxBox2GroupTree").click();
	}
	dialogReloadRel(json);
}

/**
 * 根据id自动局部刷新，用于module页面
 * @param json
 */
function dialogReloadRel2Module(json){
	if (json.statusCode == DWZ.statusCode.ok) {
		$("#refreshJbsxBox2moduleTree").click();
	}
	dialogReloadRel(json);
}

/**
 * 根据id自动局部刷新，用于Area页面
 * @param json
 */
function dialogReloadRel2Area(json){
	if (json.statusCode == DWZ.statusCode.ok) {
		$("#refreshJbsxBox2AreaTree").click();
	}
	dialogReloadRel(json);
}

/**
 * onchange更新获取下拉框数据
 * @param {} ref
 * @param {} refUrl
 * @param {} paramValue
 * @param {} defultvalue
 */
function getSelectJson(ref,refUrl,target,paramValue,defultvalue){
	$.ajax({
		type : 'POST',
		dataType : "json",
		url : refUrl,
		global:false,
		cache : false,
		data : {paramValue:paramValue},
		success : function(json) {
			     if(target == 'dialog' ){
					 $("#"+ref+" option[value!='']",  $.pdialog.getCurrent()).remove();
					 if(json == '' || json == null){
					    $("#"+ref,  $.pdialog.getCurrent()).change(); 
					 }
				 }else if(target == 'auto' ){
					 $("#"+ref+" option[value!='']").remove();
					 if(json == '' || json == null){
					 	 if (navigator.appName.indexOf("Microsoft")!= -1) {
					 	 	document.getElementById(ref).fireEvent('onchange');//IE
						 }else{
						    document.getElementById(ref).onchange();//FF
						 }
					 }
				 }else{
					 $("#"+ref+" option[value!='']", navTab.getCurrentPanel()).remove();  
					 if(json == '' || json == null){
					    $("#"+ref, navTab.getCurrentPanel()).change(); 
					 }
				 }
			 if (!json)
				return;
			 var html = '';
			 $.each(json, function(i) {
				if (json[i]	&& json[i].length > 1) {
					if(defultvalue == json[i][0] ){
						html += '<option value="'
							+ json[i][0]
							+ '" selected>'
							+ json[i][1]
							+ '</option>';
					}else{
						html += '<option value="'
							+ json[i][0]
							+ '">'
							+ json[i][1]
							+ '</option>';
					}
					
				} 
			}); 
			 if(target == 'dialog' ){
				 $("#"+ref,  $.pdialog.getCurrent()).append(html); 
			 	 $("#"+ref,  $.pdialog.getCurrent()).change(); 
			 }else if(target == 'auto' ){
				 $("#"+ref).append(html);
				 if (navigator.appName.indexOf("Microsoft")!= -1) {
			 	 	document.getElementById(ref).fireEvent('onchange');//IE
				 }else{
				    document.getElementById(ref).onchange();//FF
				 }
			 }else{
				 $("#"+ref, navTab.getCurrentPanel()).append(html);
				 $("#"+ref, navTab.getCurrentPanel()).change(); 
			 }

		},
		error : DWZ.ajaxError
	});
	
	
    
}

/********************************通用js函数********************************************/
/**
 * 转换成数组，去掉重复，再组合好。
 * @param {} str  12,23,34
 * @return {}
 */ 
function okd(str) { 
	 var ary = str.split(","); 
	 // 去重的算法 
 	var json = {}; 
 	for (var i = 0; i < ary.length; i++) { 
 		if (ary[i] != "") { 
  		  json[ary[i]] = ary[i]; 
  		} 
 	} 
	// 查看结果 
	var str2 = ""; 
	for (var key in json) { 
		str2 += "," + json[key]; 
	} 

	var s = str2.toString(); 
	//去掉第一个逗号 
	if (s.substr(0, 1) == ',') { 
		s = s.substr(1); 
	} 
	return s; 
} 

/**
 * 转换成数组，去掉指定字符串，再组合好。
 * @param {} str
 * @return {}
 */ 
function delstr(strs,str) { 
	 var ary = strs.split(","); 
	 // 去重的算法 
 	var json = {}; 
 	for (var i = 0; i < ary.length; i++) { 
 		if (ary[i] != "" && ary[i] != str) { 
  		  json[ary[i]] = ary[i]; 
  		} 
 	} 
	// 查看结果 
	var str2 = ""; 
	for (var key in json) { 
		str2 += "," + json[key]; 
	} 
	var s = str2.toString(); 
	//去掉第一个逗号 
	if (s.substr(0, 1) == ',') { 
		s = s.substr(1); 
	} 
	return s; 
}

/**
 * 全选
 * @param {} box
 * @param {} str:checkbox name
 */
function checkallbox(box,str) {
	if(document.all(str)){
		if(document.all(str).length){
			for(var i=0;i<document.all(str).length;i++){
				document.all(str)[i].checked=box.checked;
			}
		}else{
			document.all(str).checked=box.checked;
		}
	}
    getSelectedInfoRows('');
}

/**
 * 获取已选
 * @param {} selectid
 * @param {} selectOrgId:已选表单值
 * @param {} str:checkbox name
 */
function getSelectedInfoRows(selectid,selectOrgId,str){
  var temp = "";
  var flag = false;//判断是否选中checkbox
  for ( var i=0; i<document.all(str).length; i++ ){
     var e = document.all(str)[i];
   if ( (e.type=='checkbox')&&(!e.disabled) ){
       var o = eval('(' +e.value+ ')');
     if ( e.checked && e.id == ''){
        temp += temp==""? o.id: "," + o.id ;
        flag = true; 
     }else {
        if(e.id=='ids' && o.id == selectid){
           e.checked = false;
            document.all(selectOrgId).value = delstr(document.all(selectOrgId).value,o.id);
        }
	 }
   }
 }
  var selectOrgIds = document.all(selectOrgId).value;
  if(flag){
     selectOrgIds = selectOrgIds == '' ? temp : selectOrgIds + "," + temp;
  }
  document.all(selectOrgId).value = okd(selectOrgIds);//过滤重复
}