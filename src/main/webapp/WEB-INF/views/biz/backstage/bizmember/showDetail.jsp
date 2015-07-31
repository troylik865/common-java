<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${ contextPath}/biz/backstage/bizmember/list" page="${ page}">
	<input type="hidden" name="name" value="${member.name}"/>
</dwz:paginationForm>

<form method="post" action="${ contextPath }/biz/backstage/bizmember/list" onsubmit="return navTabSearch(this)">
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
		</ul>
	</div>
	
		<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="3%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="20%">记账日</th>
				<th width="15%">交易时间</th>
				<th width="10%">交易摘要	</th>
				<th width="10%">金币收入</th>
				<th width="12%">金币支出</th>
				<th width="12%">用户余额</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${data}">
			<tr target="slt_uid" rel="${item.id}">
			    <td><input name="ids" value="${item.id}" type="checkbox"  ></td>
				<td>${item.date}</td>
				<td>${item.time}</td>
				<td>${item.type}</td>
				<td>${item.out}</td>
				<td>${item.in}</td>
				<td>${item.left}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page}"/>
</div>