<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"  trimDirectiveWhitespaces="true"%>
<!-- 
   HQL查询行政区域，显示下拉标签
 -->
<!-- 标签名 ：名称，和对象的字段名称一致 -->
<%@ attribute name="type" type="java.lang.String" required="true"%>
<!-- target:判断dialog还是navTab -->
<%@ attribute name="target" type="java.lang.String" description="navTab/dialog/auto" %>
<!-- 查询条件:区域级别1、城区；2、街道 -->
<%@ attribute name="areaLevel" type="java.lang.String" required="true"%>
<!-- 样式 -->
<%@ attribute name="style" type="java.lang.String"%>
<!-- 默认样式 -->
<%@ attribute name="defultStyle" type="java.lang.String"%>
<!-- 不可编辑 -->
<%@ attribute name="disabled" type="java.lang.String"%>
<!-- 默认选中值 -->
<%@ attribute name="value" type="java.lang.String"%>
<!-- onchange -->
<%@ attribute name="onchange" type="java.lang.String"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${style == null}" >
	<c:set var="style" value=""/>
</c:if>
<c:if test="${defultStyle == null}" >
	<c:set var="defultStyle" value=""/>
</c:if>
<c:if test="${disabled == null}" >
	<c:set var="disabled" value=""/>
</c:if>
<c:if test="${value == null}" >
	<c:set var="value" value=""/>
</c:if>
<c:if test="${target == null}" >
	<c:set var="target" value="navTab"/>
</c:if>

<SCRIPT type="text/javascript">
<!--
$(document).ready(function(){
	$.ajax({
		type : 'POST',
		dataType : "json",
		url :"<%=request.getContextPath()%>/system/config/area/sysarea",
		cache : false,
		data : {areaLevel:"${areaLevel}",areaCode:"${value}"},
		success : function(json) {
			 if (!json)
				return;
			// alert(json["name"]);
			 var html = '';
			 $.each(json, function(i) {
						if (json[i]
								&& json[i].length > 1) {
							if("${value}" == json[i][0] ){
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
			 //alert(html);
			 if("${target}" == 'dialog' ){
				 $("#${type}",  $.pdialog.getCurrent()).append(html); 
			 }else if("${target}" == 'auto' ){
				 $("#${type}").append(html); 
			 }else{
				 $("#${type}", navTab.getCurrentPanel()).append(html);  
			 }
			
		},
		error : DWZ.ajaxError
	});
});
//-->
</SCRIPT>
<select id="${type}" name="${type}" class="${style}" style="${defultStyle}" onchange="${onchange}" ${disabled}>
<option value='' selected> 请选择</option>
</select>
