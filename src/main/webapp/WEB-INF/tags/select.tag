<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"  trimDirectiveWhitespaces="true"%>
<!-- combox:select下拉标签 -->
<!-- 标签名 ：名称，和对象的字段名称一致 -->
<%@ attribute name="type" type="java.lang.String" required="true"%>
<!-- target:判断dialog还是navTab -->
<%@ attribute name="target" type="java.lang.String" description="navTab/dialog/auto" %>
<!-- 样式 -->
<%@ attribute name="style" type="java.lang.String"%>
<!-- 默认样式 -->
<%@ attribute name="defultStyle" type="java.lang.String"%>
<!-- 不可编辑 -->
<%@ attribute name="disabled" type="java.lang.String"%>
<!-- 父级值 -->
<%@ attribute name="parentValue" type="java.lang.String"%>
<!-- 默认值 -->
<%@ attribute name="defultValue" type="java.lang.String"%>
<!-- onchange -->
<%@ attribute name="onchange" type="java.lang.String"%>
<!-- 请求数据url -->
<%@ attribute name="url" type="java.lang.String"%>

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
<c:if test="${target == null}" >
	<c:set var="target" value="navTab"/>
</c:if>

<SCRIPT type="text/javascript">
<!--
$(document).ready(function(){
	if("${parentValue}" != ''){
		if("${url}" != ''){
			getSelectJson('${type}','${url}','${target}','${parentValue}','${defultValue}');
		}else{
			getSelectJson('${type}','<%=request.getContextPath()%>/system/config/area/getjson','${target}','${parentValue}','${defultValue}');
		}
	}
});
//-->
</SCRIPT>

<select class="${style}" style="${defultStyle}" id="${type}" name="${type}"  onchange="${onchange}" ${disabled}>
<option value='' selected> 请选择</option>
</select>
