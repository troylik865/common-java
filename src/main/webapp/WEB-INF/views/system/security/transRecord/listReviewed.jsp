<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/biz/transRecord/reviewList" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<form method="post" action="${contextPath}/biz/transRecord/reviewList" onsubmit="return navTabSearch(this)">

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
				<th width="100">投资品种</th>
				<th width="100">初期资金</th>
				<th width="200">当日权益</th>
				<th width="100">上日权益</th>
				<th width="150">手续费</th>
				<th width="150">当日盈亏</th>
				<th width="100">当日入金</th>
				<th >当日出金</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${records}">
			<tr target="id" rel="${item.id}">
			    <td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.investTypeStr}</td>
				<td>${item.origionValueWYuan}</td>
				<td>${item.currValueWYuan}</td>
				<td>${item.lastDayValueWYuan}</td>
				<td>${item.feeWYuan}</td>
				<td>${item.gainsAndLossesWYuan}</td>
				<td>${item.currIncomeWYuan}</td>
				<td>${item.currOutcomeWYuan}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }" step="1"/>
	
</div>
