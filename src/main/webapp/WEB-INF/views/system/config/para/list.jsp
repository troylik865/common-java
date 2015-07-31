<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/system/config/para/list" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<form method="post" action="${contextPath}/system/config/para/list" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>参数名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="Para:save">
				<li><a class="add" target="dialog" mask="true" width="500" height="400" href="${contextPath}/system/config/para/create" ><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Para:edit">
				<li><a class="edit" target="dialog" mask="true" width="500" height="400" href="${contextPath}/system/config/para/update/{slt_uid}" ><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Para:delete">
				<li><a class="delete" target="selectedTodo"  href="${contextPath}/system/config/para/delete" title="确认要删除该代码?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100">参数关键字</th>
				<th>参数值</th>
				<th>参数名称</th>
				<th>状态</th>
				<th>参数描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${paras}">
			<tr target="slt_uid" rel="${item.id}">
			     <td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.paraKey}</td>
				<td>${item.paraValue}</td>
				<td>${item.paraName}</td>
				<td>${item.paraState == "0" ? "正常":"停用"}</td>
				<td>${item.paraDesc}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
</div>


