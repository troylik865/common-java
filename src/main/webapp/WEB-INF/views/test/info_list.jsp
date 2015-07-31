<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type=”text/javascript”>
	function cltable(rel){
             //   alert(rel);
			//$("#test"+rel).click();
 			document.getElementById("test"+rel).click();
	}
</script>

<form method="post" action="${contextPath}/system/security/user/list" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
			</ul>
			<ul class="searchContent">
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
			</ul>
			<ul class="searchContent">
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
				<li>
					<label>登录名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
			</ul>
		</div>
	</div>
</form>

<div class="pageContent">
	
	<div selector="h1" layoutH="100">
		<table class="table" width="100%">
			<thead>
				<tr>
					<th width="100">登录名称</th>
					<th width="100">真实名字</th>
					<th width="100">电话</th>
					<th width="200">邮箱地址</th>
				</tr>
			</thead>
			<tbody>
				<tr target="test_uid" rel="1" onclick="cltable('1')">
					<td><a id="test1" rel="jbsxBox2test" target="ajax" href="${contextPath}/test/info" style="display:none;"></a>
					    1</td>
					<td>2</td>
					<td>3</td>
					<td>4</td>
				</tr>
				<tr target="test_uid" rel="2" onclick="cltable('2')">
					<td><a id="test2" rel="jbsxBox2test" target="ajax" href="${contextPath}/test/info" style="display:none;"></a>
					   1</td>
					<td>2</td>
					<td>3</td>
					<td>4</td>
				</tr>
			</tbody>
		</table>
		
	    <div id="jbsxBox2test" class="unitBox" style="padding-top: 10px;">
		</div>
	</div>
</div>
	
