<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type=”text/javascript”>
	function dbltable(rel){
			$.pdialog.open("${contextPath}/test/info_list","pdialogid", "标题", { max:true, mask:false, width: 500, height: 300});
	}
</script>
<dwz:paginationForm action="${contextPath}/test/log_list" page="${page }">
							
</dwz:paginationForm>
<form method="post" action="${contextPath}/system/security/user/list" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>TESTCODE：</label>
					<dwz:syscode type="moduleType" codeType="MODULE_TYPE" notIn="0" value="1"></dwz:syscode>
				</li>
				<li>
					<span class="yes_red"></span>
				</li>
			</ul>
			<ul class="searchContent">
				<li>
					<label>城区：</label>
                    <dwz:codeList type="area" tableName="sys_area" where=" area_level='1'" 
					orderBy=" area_code asc"  displayfield="area_name" valuefield="area_code" 
					onchange="getSelectJson('street','${contextPath}/system/config/area/getjson/','navTab',this.value,'') ;"/>
				</li>
				<li>
					<label>街道：</label>
					<dwz:select type="street"></dwz:select>
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
			<shiro:hasPermission name="Log:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath}/system/config/log/delete" title="确认要删除?"><span>删除日志</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="163" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100">登录名称</th>
				<th width="100">登录IP</th>
				<th width="100" orderField="log_level" class="${page.orderField eq 'log_level' ? page.orderDirection : ''}">日志等级</th>
				<th >日志内容</th>
				<th width="130" orderField="create_time" class="${page.orderField eq 'create_time' ? page.orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${logs}">
			<tr target="rid" rel="${item.id}" ondblclick="dbltable('${item.id}')" title="双击查看">
				<td><input name="ids" value="${item['id']}" type="checkbox"></td>
				<td>${item['username']}</td>
				<td>${item['ip_address']}</td>
				<td>${item['log_level']}</td>
				<td>${item['message']}</td>
				<td><fmt:formatDate value="${item['create_time']}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>			
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>