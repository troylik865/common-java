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
					<label>姓名：</label>
					<input type="text" name="name" value="${ member.name}"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="BizMember:save">
			<li><a class="add" target="dialog" mask="true" width="530" height="600" href="${ contextPath }/biz/backstage/bizmember/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<li><a class="edit" target="dialog" mask="true" width="530" height="400" href="${ contextPath }/biz/backstage/bizmember/update/{slt_uid}"><span>编辑</span></a></li>
			<li><a class="add" target="dialog" mask="true" width="530" height="350" href="${ contextPath }/biz/backstage/attach/create/{slt_uid}?type=member"><span>上传附件</span></a></li>
			<li><a class="edit" target="dialog" mask="true" width="530" height="600" href="${ contextPath }/biz/backstage/bizmember/showDetail/{slt_uid}"><span>查看资金交易明细</span></a></li>
			<li><a class="edit" target="dialog" mask="true" width="530" height="280" href="${ contextPath }/biz/transRecord/modifyLast/{slt_uid}"><span>修改最后一条交易记录</span></a></li>
			<li><a class="delete" target="selectedTodo"  href="${ contextPath }/biz/transRecord/clear" title="确认要删除所有的交易记录?"><span>删除所有交易记录</span></a></li>
			<li><a class="edit" target="dialog" mask="true" width="530" height="580" href="${contextPath}/biz/transRecord/showReviewed1/{slt_uid}"><span>查看已审核的交易记录</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
					   	 <th>会员编号</th>
					   	 <th>姓名</th>
					   	 <th>性别</th>
					   	 <th>年龄</th>
					   	 <th>手机号码</th>
					   	 <th>邮箱</th>
					   	 <th>QQ</th>
					   	 <th>投资方向</th>
					   	 <th>用户名</th>
					   	 <th>地址</th>
					   	 <th>创建时间</th>
					   	 <th>被关注花费资金</th>
					   	 <th>剩余金额</th>
						</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${ data }">
			<tr target="slt_uid" rel="${item.id}">
			 <td><input name="ids" value="${item.id}" type="checkbox"></td>
							  <td>${ item.memberNo }</td>
							  <td>${ item.name }</td>
							  <td>${ item.sexStr }</td>
							  <td>${ item.age }</td>
							  <td>${ item.phoneNumber }</td>
							  <td>${ item.email }</td>
							  <td>${ item.qq }</td>
							  <td>${ item.investDirection }</td>
							  <td>${ item.userName }</td>
							  <td>${ item.address }</td>
							  <td>${ item.gmtCreate }</td>
							  <td>${ item.attentDailyCost }</td>
							  <td>${ item.coinValue }</td>
						</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page}"/>
</div>