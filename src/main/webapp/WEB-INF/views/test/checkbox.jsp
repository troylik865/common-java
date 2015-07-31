<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="97">
		<dl class="nowrap">
				<dt>部门名称C：</dt>
				<dd>
					<input name="org3.id" value="" type="hidden">
					<input name="org3.orgName" type="text"/>
					<a class="btnLook" href="${contextPath}/test/look_group" lookupGroup="org3">查找带回</a>
					<span class="info">(lookup 通过复选框选择多个值查找回带)</span>
				</dd>
			</dl>
	</div>
</div>