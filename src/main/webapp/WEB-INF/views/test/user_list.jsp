<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type=”text/javascript”>
	function dbltable(rel){
			$.pdialog.open("${contextPath}/system/security/user/update/"+rel,"pdialogid", "用户信息", { max:true, mask:false, width: 500, height: 300});
	}
</script>

<dwz:paginationForm action="${contextPath}/system/security/user/list" page="${page }">
	<input type="hidden" name="keywords" value="${keywords}"/>
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
					<dwz:codeList type="moduleType" tableName="sys_code" where=" code_type='MODULE_TYPE'"  value="0"
					orderBy=" ord_no asc"  displayfield="code_name" valuefield="code_value" disabled="disabled" />
				</li>
			</ul>
			<ul class="searchContent">
				<li>
					<label>城区：</label>
                    <dwz:sysarea type="area" areaLevel="1"  
			    onchange="getSelectJson('street','${contextPath}/system/config/area/getjson','navTab',this.value,'') ;"/>
				</li>
				<li>
					<label>街道：</label>
					<dwz:select type="street"
					onchange="getSelectJson('park','${contextPath}/tcsf/xtgl/pakPark/getParks','navTab',this.value,'') ;" />
				</li>
				<li>
					<label>停车点：</label>
					<dwz:select type="park"  />
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
			<shiro:hasPermission name="User:save">
				<li><a class="add" target="dialog" rel="lookup2Group"  drawable="false" max="true" mask="false" width="530" height="380" href="${contextPath}/system/security/user/create" ><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:edit">
				<li><a class="edit" target="dialog" rel="lookup2Group" mask="true" width="530" height="380" href="${contextPath}/system/security/user/update/{slt_uid}" ><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:delete">
				<li><a class="delete" target="selectedTodo"  href="${contextPath}/system/security/user/delete" title="确认要删除该用户?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:edit">
				<li class="line">line</li>
				<li><a class="edit" target="ajaxTodo" href="${contextPath}/system/security/user/reset/password/{slt_uid}" title="确认重置密码为123456?"><span>重置密码</span></a></li>
				<li><a class="edit" target="ajaxTodo" href="${contextPath}/system/security/user/reset/status/{slt_uid}" title="确认更新状态"><span>更新账户状态</span></a></li>
				<li class="line">line</li>
				<li><a class="add" href="${contextPath}/system/security/user/lookup2create/userRole/{slt_uid}" target="dialog" mask="true" width="400" height="500" title="分配角色"><span>分配角色</span></a></li>
				<li><a class="delete" href="${contextPath}/system/security/user/lookup2delete/userRole/{slt_uid}" target="dialog" mask="true" width="400" height="500" title="删除已分配角色"><span>删除已分配角色</span></a></li>
			</shiro:hasPermission>
			<li><a class="icon" href="${contextPath}/system/security/user/exportExcel" target="dwzExport" targetType="navTab" title="Are you sure export?"><span>导出</span></a></li>
			<li><a class="icon" href="${contextPath}/system/security/user/exportExcel?type=all" target="dwzExport" targetType="navTab" title="Are you sure export all?"><span>全导</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100">登录名称</th>
				<th width="100">真实名字</th>
				<th width="100">电话</th>
				<th width="200">邮箱地址</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}">
			<tr target="slt_uid" rel="${item[0]}" ondblclick="dbltable('${item[0]}')" title="双击查看">
			     <td><input name="ids" value="${item[0]}" type="checkbox"></td>
				<td>${item[1]}</td>
				<td>${item[2]}</td>
				<td>${item[3]}</td>
				<td>${item[4]}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
	
</div>
