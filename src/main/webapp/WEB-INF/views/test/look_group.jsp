<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath}/test/look_group" page="${page }">
   <input type="hidden" name="selectorgId" value="${selectorgId}"/>	
</dwz:paginationForm>

<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>部门名称:</label>
				<input class="textInput" name="orgName" value="" type="text">
			</li>	  
			<li>
				<label>部门编号:</label>
				<input class="textInput" name="orgNum" value="" type="text">
			</li>
			<li>
				<label>部门经理:</label>
				<input class="textInput" name="leader" value="" type="text">
			</li>
				<li>
				<label>上级部门:</label>
				<input class="textInput" name="parentOrg.orgName" value="" type="text">
			</li> 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="orgId" warn="请选择部门">选择带回</button></div></div></li>
			</ul>
		</div>
	</div>
</div>

<div class="pageContent">
	<table class="table" layoutH="163" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="orgId" onclick="checkallbox(this,'orgId')" ></th>
				<th width="100">登录名称</th>
				<th width="100">登录IP</th>
				<th width="100" orderField="logLevel" class="${page.orderField eq 'logLevel' ? page.orderDirection : ''}">日志等级</th>
				<th >日志内容</th>
				<th width="130" orderField="createTime" class="${page.orderField eq 'createTime' ? page.orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${logs}">
				<c:set var="selectid" value="${item.id}"/>
				<tr onclick="getSelectedInfoRows('${item.id}','selectorgId','orgId')">
					<td><input type="checkbox" name="orgId" value="{'id':'${item.id}','orgName':'${item.id}'}"  
				    ${empty idsMap[selectid] ?  '' : 'checked' } /></td>
					<td>${item.username}</td>
					<td>${item.ipAddress}</td>
					<td>${item.logLevel}</td>
					<td>${item.message}</td>
					<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>
	<div style="display: none;">
	    <c:forEach var="ids" items="${idsMap}">
			<input type="checkbox" id="ids" name="orgId" value="{'id':'${ids.key}','orgName':'${ids.value}'}"   checked  />
		</c:forEach>
	</div>	
	<!-- 分页 -->
	<dwz:pagination page="${page }" target="dialog"/>
</div>