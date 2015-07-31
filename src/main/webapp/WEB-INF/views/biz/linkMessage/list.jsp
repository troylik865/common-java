<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${ contextPath}/biz/linkMessage/list" page="${ page}">
</dwz:paginationForm>

<form method="post" action="${ contextPath }/biz/linkMessage/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="delete" target="selectedTodo"  href="${ contextPath }/biz/linkMessage/delete" title="确认要删除?"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
					   	 <th>留言人姓名</th>
					   	 <th>留言人电话</th>
					   	 <th>留言内容</th>
					   	 <th>创建时间</th>
						</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${ data }">
			<tr target="slt_uid" rel="${item.id}">
			 <td><input name="ids" value="${item.id}" type="checkbox"></td>
							  <td>${ item.name }</td>
							  <td>${ item.phoneNumber }</td>
							  <td>${ item.content }</td>
							  <td>${ item.gmtCreate }</td>
						</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page}"/>
</div>