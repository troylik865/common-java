<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/biz/backstage/news/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
				 <dl>
		    <dt>新闻标题：</dt>
			<dd>
				<input type="text" name="title"  size="20" maxlength="30" class="required"  alt="请输入新闻标题"/>
			</dd>
		  </dl>
		  <dl>
		    <dt>排序号：</dt>
			<dd>
			<input type="text" name="turn"  size="20" maxlength="100" class="required" alt="请输入排序号"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>新闻内容：</dt>
			<dd>
				<textarea name="content" rows=10 cols=50  size="20" maxlength="1000" class="required" alt="请输入新闻内容"/><br>
			</dd>
		  </dl>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>