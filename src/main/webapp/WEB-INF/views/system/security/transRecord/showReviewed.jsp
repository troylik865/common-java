<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/biz/transRecord/reviewList" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<form method="post" action="${contextPath}/biz/transRecord/reviewList" onsubmit="return navTabSearch(this)">
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
				<th width="100">投资方向</th>
				<th width="100">导入时间</th>
				<th width="200">当日入金(万)</th>
				<th width="100">当日出金(万)</th>
				<th width="150">当日权益(万)</th>
				<th width="150">上日权益(万)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${data}">
			<tr target="id" rel="${item.id}">
			    <td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.investTypeStr}</td>
				<td>${item.importDate}</td>
				<td>${item.currIncomeWYuan}</td>
				<td>${item.currOutcomeWYuan}</td>
				<td>${item.currValueWYuan}</td>
				<td>${item.lastDayValueWYuan}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }" step="1"/>
	
</div>
