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
			<li><a class="edit" target="dialog" mask="true" width="780" height="580" href="${ contextPath }/biz/transRecord/showDetailPer/{id}"><span>查看明细</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80">日期</th>
				<th width="80">总收入</th>
				<th width="80">总支出</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${records}">
			<tr target="id" rel="${item.importDate}">
			    <td width="22"><input name="ids" value="${item.id}" type="checkbox"></td>
				<td  width="80">${item.importDate}</td>
				<td  width="80">${item.currIncome}</td>
				<td  width="80">${item.currOutcome}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
</div>
