<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/views/include/var.jsp" %>
<dwz:paginationForm action="${ contextPath}/system/security/secretkey/list" page="${page}">
	<input type="hidden" name="source_id" value="${source_id}"/>
</dwz:paginationForm>

<form method="post" action="${ contextPath }/system/security/secretkey/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
			    <li>
			        <label style="width: 100px;">第三方支付编号：</label>
                    <input type="text" name="source_id" value="${source_id}" />
			    </li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <shiro:hasPermission name="SecretKey:create">
                <li><a class="add" target="dialog" mask="true" width="430" height="280" href="${contextPath}/system/security/secretkey/create" ><span>添加</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="SecretKey:delete">
                <li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath}/system/security/secretkey/delete" title="确认要删除所选密钥对?"><span>删除</span></a></li>
            </shiro:hasPermission>
        </ul>
    </div>
	<table class="table" layoutH="135" width="100%">
		<thead>
			<tr>
                <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
			    <th width="100"  orderField="source_id" class="${page.orderField eq 'source_id' ? page.orderDirection : ''}">第三方支付id</th>
			    <th width="100">公钥</th>
				<th width="100">私钥</th>
                <th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
        <c:if test="${! empty list}">
            <c:forEach var="item" items="${list}">
                <tr target="slt_uid" rel="${item.source_id}">
                    <td><input name="ids" value="${item.source_id}" type="checkbox"></td>
                    <td>${item.source_id}</td>
                    <td>*</td>
                    <td>*</td>
                    <td>
                    <shiro:hasPermission name="SecretKey:export">
                        <a href="${contextPath}/system/security/secretkey/export/${item.source_id}/1" target="dwzExport" targetType="navTab" title="确认导出?"><span>导出私钥</span></a>
                    </shiro:hasPermission></td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty list}">
        <tr>
            <td colspan="5" style="color: red;" align="center">暂无数据</td>
        </tr>
        </c:if>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page}"/>
</div>