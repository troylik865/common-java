<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/biz/backstage/bizmember/showAllInvest" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>
<script>
	$(function(){
		$("#searchName").val("");
	});
</script>
<form method="post" action="${contextPath}/biz/backstage/bizmember/showAllInvest" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>会员名：</label>
					<input type="text" name="name"  id="searchName"/>
				</li>
				<li>
					<label style="width:130px;">是否拥有参赛资格：</label>
					<select name="isValidated">
						<option value="">--请选择--</option>
						<option value="1">有</option>
						<option value="0">无</option>
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
				<li><a class="user_edit" target="selectedTodo"  href="${contextPath}/biz/backstage/bizmember/memberPass" title="确认要通过参赛资格审核?"><span>参赛资格审核通过</span></a></li>
				<li><a class="user_edit" target="selectedTodo"  href="${contextPath}/biz/backstage/bizmember/memberCancel" title="确认要取消参赛资格?"><span>取消参赛资格</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			    <th width="3%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="15%">大师编号</th>
				<th width="20%">会员名</th>
				<th width="15%">投资方向</th>
				<th width="10%">是否拥有参赛资格</th>
				<th width="12%" >创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${data}">
			<tr target="slt_uid" rel="${item.id}">
			    <td><input name="ids" value="${item.id}" type="checkbox"  ></td>
				<td>${item.memberNo}</td>
				<td>${item.name}</td>
				<td>${item.type}</td>
				<td>${item.status}</td>
				<td>${item.createTime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>
