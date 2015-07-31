<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/biz/backstage/bizmember/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
				 <dl>
		    <dt>会员编号：</dt>
			<dd>
				<input type="text" name="memberNo"  size="20" maxlength="30" alt="请输入会员编号"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>姓名：</dt>
			<dd>
				<input type="text" name="name"  size="20" maxlength="100" class="required" alt="请输入姓名"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>性别：</dt>
			<dd>
				<dwz:syscode type="sex" codeType="SEX" target="dialog"></dwz:syscode>
			</dd>
		  </dl>
				 <dl>
		    <dt>年龄：</dt>
			<dd>
				<input type="text" name="age"  size="20" maxlength="10" alt="请输入年龄"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>手机号码：</dt>
			<dd>
				<input type="text" name="phoneNumber"  size="20" maxlength="30" alt="请输入手机号码"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>邮箱：</dt>
			<dd>
				<input type="text" name="email"  size="20" maxlength="100" alt="请输入邮箱"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>QQ：</dt>
			<dd>
				<input type="text" name="qq"  size="20" maxlength="30" alt="请输入QQ"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>投资方向：</dt>
			<dd>
				<input type="text" name="investDirection"  size="20" maxlength="20" alt="请输入投资方向"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>用户名：</dt>
			<dd>
				<input type="text" name="userName"  size="20" maxlength="100" alt="请输入用户名"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>密码：</dt>
			<dd>
				<input type="text" name="password"  size="20" maxlength="100" alt="请输入密码"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>地址：</dt>
			<dd>
				<input type="text" name="address"  size="20" maxlength="200" alt="请输入地址"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>否是认证用户：</dt>
			<dd>
				<dwz:syscode type="isValidated" codeType="ISVALIDATED" target="dialog" style="required"></dwz:syscode>
			</dd>
		  </dl>
				 <dl>
		    <dt>通过验证日期：</dt>
			<dd>
				<input type="text" name="gmtValidated" class="date" dateFmt="yyyy-MM-dd" readonly="true"/>
      			<a class="inputDateButton" href="javascript:;">选择</a>
			</dd>
		  </dl>
				 <dl>
		    <dt>被关注每天花费的资金：</dt>
			<dd>
				<input type="text" name="attentDailyCost"  size="20" maxlength="19" alt="请输入被关注每天花费的资金"/>
			</dd>
		  </dl>
				 <dl>
		    <dt>资金类型：</dt>
			<dd>
				<dwz:syscode type="costType" codeType="COST_TYPE" target="dialog" style="required"></dwz:syscode>
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