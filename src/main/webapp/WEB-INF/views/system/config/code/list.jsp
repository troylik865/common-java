<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/system/config/code/list" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<form method="post" action="${contextPath}/system/config/code/list" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>代码类型：</label>
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
			<shiro:hasPermission name="Code:save">
				<li><a class="add" target="dialog" mask="true" width="500" height="400" href="${contextPath}/system/config/code/create" ><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Code:edit">
				<li><a class="edit" target="dialog" mask="true" width="500" height="400" href="${contextPath}/system/config/code/update/{slt_uid}" ><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Code:delete">
				<li><a class="delete" target="selectedTodo"  href="${contextPath}/system/config/code/delete" title="确认要删除该代码?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100">代码类型</th>
				<th>类别名称</th>
				<th>代码名称</th>
				<th>代码值</th>
				<th>状态</th>
				<th>序号</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${codes}">
			<tr target="slt_uid" rel="${item.id}">
			     <td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.codeType}</td>
				<td>${item.typeName}</td>
				<td>${item.codeName}</td>
				<td>${item.codeValue}</td>
				<td>${item.codeState == "0" ? "正常":"停用"}</td>
				<td>${item.ordNo}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
</div>


