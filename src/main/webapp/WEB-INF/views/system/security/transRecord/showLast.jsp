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
				<li><a class="shield_go" target="dialog" rel="lookup2Group" mask="true" width="830" height="580" href="${contextPath}/biz/transRecord/toReview/{id}" ><span>审核</span></a></li>
				<li><a class="shield_go" target="dialog" rel="lookup2Group" mask="true" width="830" height="580" href="${contextPath}/biz/transRecord/showReviewed/{id}" ><span>查看已审核的交易</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80">投资品种</th>
				<th width="80">大师编号</th>
				<th width="80">大师名</th>
				<th width="80">是否参赛</th>
				<th width="80">初期资金</th>
				<th width="80">当日权益</th>
				<th width="80">上日权益</th>
				<th width="80">手续费</th>
				<th width="80">当日盈亏</th>
				<th width="80">当日入金</th>
				<th >当日出金</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${records}">
			<tr target="id" rel="${item.id}">
			    <td width="22"><input name="ids" value="${item.id}" type="checkbox"></td>
				<td  width="80">${item.investTypeStr}</td>
				<td  width="80">${item.memberNo}</td>
				<td  width="80">${item.name}</td>
				<td  width="80">${item.isVal}</td>
				<td  width="80">${item.origionValue}</td>
				<td  width="80">${item.currValue}</td>
				<td  width="80">${item.lastDayValue}</td>
				<td  width="80">${item.fee}</td>
				<td  width="80">${item.gainsAndLosses}</td>
				<td  width="80">${item.currIncome}</td>
				<td  width="80">${item.currOutcome}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
</div>
