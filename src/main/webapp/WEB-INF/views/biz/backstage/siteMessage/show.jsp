<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/biz/backstage/bizmember/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="60">
		<dl>
		    <dt>发送人：</dt>
			<dd>
				<div>${data.name}</div>
			</dd>
		  </dl>
		  <dl>
		    <dt>站内信类型：</dt>
			<dd>
				<div>${data.type}</div>
			</dd>
		  </dl>
		  <dl>
		    <dt>站内信内容：</dt>
			<dd>
				<div>${data.content}</div>
			</dd>
		  </dl>
			<dl>
		    <dt>状态：</dt>
			<dd>
				<div>${data.status}</div>
			</dd>
		  </dl>
		  <dl>
		    <dt>创建时间：</dt>
			<dd>
				<div>${data.createTime}</div>
			</dd>
		  </dl>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>