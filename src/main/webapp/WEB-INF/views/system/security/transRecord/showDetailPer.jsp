<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/biz/transRecord/showLast" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<form method="post" action="${contextPath}/biz/transRecord/showLast" onsubmit="return navTabSearch(this)">
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
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80">记账日</th>
				<th width="80">交易时间</th>
				<th width="160">交易摘要</th>
				<th width="80">金币收入</th>
				<th width="80">金币支出</th>
				<th width="80">剩余库存</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${data}">
			<tr target="id" rel="${item.importDate}">
			    <td width="22"><input name="ids" value="${item.id}" type="checkbox"></td>
				<td  width="80">${item.date}</td>
				<td  width="80">${item.time}</td>
				<td  width="160">${item.summary}</td>
				<td  width="80">${item.in}</td>
				<td  width="80">${item.out}</td>
				<td  width="80">${item.left}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
</div>
