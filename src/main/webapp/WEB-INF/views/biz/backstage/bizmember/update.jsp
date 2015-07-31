<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/biz/backstage/bizmember/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${ member.id}"/>
	<div class="pageFormContent" layoutH="100">
		         <dl>
			<dt>会员编号：</dt>
			<dd>
			 <input type="text" name="memberNo" value="${ member.memberNo}" size="20" maxlength="30" alt="请输入会员编号"/>
		   </dd>
		  </dl>
		         <dl>
			<dt>姓名：</dt>
			<dd>
			 <input type="text" name="name" value="${ member.name}" size="20" maxlength="100" alt="请输入姓名"/>
		   </dd>
		  </dl>
		         <dl>
			<dt>被关注每天花费的资金：</dt>
			<dd>
			 <input type="text" name="attentDailyCost" value="${ member.attentDailyCost}" size="20" maxlength="19" alt="请输入被关注每天花费的资金"/>
		   </dd>
		  </dl>
		  <dl>
			<dt>大师工作室跳转链接</dt>
			<dd>
			 <input type="text" name="memberLink" value="${ member.memberLink}" alt="请输入大师的跳转链接"/>
		   </dd>
		  </dl>
		  <dl>
			<dt>充值金币数</dt>
			<dd>
			 <input type="text" name="coinValue" value="" alt="请输入会员的金币总数"/>
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