<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"  trimDirectiveWhitespaces="true"%>
<!-- 
  通用sql查询数据，显示下拉标签
 -->
<!-- 标签名 ：名称，和对象的字段名称一致 -->
<%@ attribute name="type" type="java.lang.String" required="true"%>
<!-- target:判断dialog还是navTab -->
<%@ attribute name="target" type="java.lang.String" description="navTab/dialog/auto" %>
<!-- 表名 -->
<%@ attribute name="tableName" type="java.lang.String" required="true"%>
<!-- 查询条件 -->
<%@ attribute name="where" type="java.lang.String" required="true"%>
<!-- 排序 -->
<%@ attribute name="orderBy" type="java.lang.String"%>
<!-- 下拉框字段值 -->
<%@ attribute name="valuefield" type="java.lang.String" required="true"%>
<!-- 下拉框显示值 -->
<%@ attribute name="displayfield" type="java.lang.String" required="true"%>
<!-- 样式 -->
<%@ attribute name="style" type="java.lang.String"%>
<!-- 不可编辑 -->
<%@ attribute name="disabled" type="java.lang.String"%>
<!-- 默认选中值 -->
<%@ attribute name="value" type="java.lang.String"%>
<!-- onchange -->
<%@ attribute name="onchange" type="java.lang.String"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${orderBy == null}" >
	<c:set var="orderBy" value=""/>
</c:if>
<c:if test="${style == null}" >
	<c:set var="style" value=""/>
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
		url :"<%=request.getContextPath()%>/system/config/code/code_list",
		cache : false,
		data : {tableName:"${tableName}",where:"${where}",orderBy:"${orderBy}",valuefield:"${valuefield}",displayfield:"${displayfield}"},
		success : function(json) {
			 if (!json)
				return;
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
<select id="${type}" name="${type}" class="${style}" onchange="${onchange}"  ${disabled} >
<option value='' selected> 请选择</option>
</select>
