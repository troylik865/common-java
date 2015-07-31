<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/biz/backstage/siteMessage/list" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<form method="post" action="${contextPath}/biz/backstage/siteMessage/list" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>站内信类型：</label>
					<select name="keywords" value="${keywords}">
						<c:forEach var="item" items="${types}">
						<option value="${item.value}">${item.name}</option>
						</c:forEach>
					</select>
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
				<li><a class="user_edit" target="dialog" rel="lookup2Group" mask="true" width="530" height="270" href="${contextPath}/biz/backstage/siteMessage/show/{slt_uid}" ><span>查看</span></a></li>
				<li><a class="delete" target="selectedTodo"  href="${ contextPath }/biz/backstage/siteMessage/delete" title="确认要删除?"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="3%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="20%">站内信类型</th>
				<th width="15%">发送方</th>
				<th width="40%">内容</th>
				<th width="10%">状态</th>
				<th width="12%" >创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${data}">
			<tr target="slt_uid" rel="${item.id}">
			    <td><input name="ids" value="${item.id}" type="checkbox"  ></td>
				<td>${item.type}</td>
				<td>${item.name}</td>
				<td>${item.content}</td>
				<td>${item.status}</td>
				<td>${item.time}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>
