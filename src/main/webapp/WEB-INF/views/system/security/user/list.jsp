<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/system/security/user/list" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<form method="post" action="${contextPath}/system/security/user/list" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>登录名称：</label>
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
			<shiro:hasPermission name="User:save">
				<li><a class="user_add" target="dialog" rel="lookup2Group" mask="true" width="530" height="380" href="${contextPath}/system/security/user/create" ><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:edit">
				<li><a class="user_edit" target="dialog" rel="lookup2Group" mask="true" width="530" height="380" href="${contextPath}/system/security/user/update/{slt_uid}" ><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:delete">
				<li><a class="user_delete" target="selectedTodo"  href="${contextPath}/system/security/user/delete" title="确认要删除该用户?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:edit">
				<li class="line">line</li>
				<li><a class="arrow_refresh" target="ajaxTodo" href="${contextPath}/system/security/user/reset/password/{slt_uid}" title="确认重置密码为123456?"><span>重置密码</span></a></li>
				<li><a class="user_go" target="ajaxTodo" href="${contextPath}/system/security/user/reset/status/{slt_uid}" title="确认更新状态"><span>更新账户状态</span></a></li>
				<li class="line">line</li>
				<li><a class="shield_add" href="${contextPath}/system/security/user/lookup2create/userRole/{slt_uid}" target="dialog" mask="true" width="400" height="500" title="分配角色"><span>分配角色</span></a></li>
				<li><a class="shield_delete" href="${contextPath}/system/security/user/lookup2delete/userRole/{slt_uid}" target="dialog" mask="true" width="400" height="500" title="删除已分配角色"><span>删除已分配角色</span></a></li>
			</shiro:hasPermission>
			<li><a class="icon" href="${contextPath}/system/security/user/exportExcel" target="dwzExport" targetType="navTab" title="确认导出?"><span>导出</span></a></li>
			<%-- <li><a class="icon" href="${contextPath}/system/security/user/exportExcel?type=all" target="dwzExport" targetType="navTab" title="确认全导?"><span>全导</span></a></li> --%>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100">登录名称</th>
				<th width="100">真实名字</th>
				<th width="200">邮箱地址</th>
				<th width="100" orderField="phone" class="${page.orderField eq 'phone' ? page.orderDirection : ''}">电话</th>
				<th width="150" orderField=group.name class="${page.orderField eq 'group.name' ? page.orderDirection : ''}">所在组织</th>
				<th width="150" >角色</th>
				<th width="100" orderField="status" class="${page.orderField eq 'status' ? page.orderDirection : ''}">账户状态</th>
				<th orderField="createTime" class="${page.orderField eq 'createTime' ? page.orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}">
			<tr target="slt_uid" rel="${item.id}">
			    <td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.username}</td>
				<td>${item.realname}</td>
				<td>${item.email}</td>
				<td>${item.phone}</td>
				<td>${item.group.name}</td>
				<td>
					<c:forEach var="ur" items="${item.userRoles }">
						${ur.role.name }&nbsp;&nbsp;
					</c:forEach>
				</td>
				<td>${item.status == "enabled" ? "可用":"不可用"}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
</div>
