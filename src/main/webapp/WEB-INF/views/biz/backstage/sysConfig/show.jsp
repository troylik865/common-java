<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div>
	<form method="post" action="${ contextPath }/biz/backstage/sysConfig/update" onsubmit="return navTabSearch(this)" >
		<div class="pageHeader">
			<div class="searchBar">
				<ul class="searchContent">
					<li>
						<label>首页固定电话：</label>
						<input type="text" name="headPhone1" value="${headPhone1}"/>
					</li>
					<li>
						<label>首页投资服务热线：</label>
						<input type="text" name="headPhone2" value="${headPhone2}"/>
					</li>
					<li>
						<label>全国服务热线：</label>
						<input type="text" name="footPhone" value="${footPhone}"/>
					</li>
				</ul>
				<ul class="searchContent" style="margin-top:30px;">
					<li>
						<label>客服QQ：</label>
						<input type="text" name="qq" value="${qq}"/>
					</li>
					<li>
						<label>TEL(左下角联系电话)：</label>
						<input type="text" name="tel" value="${tel}"/>
					</li>
				</ul>
				<div class="subBar">
					<ul>						
						<li><div class="button"><div class="buttonContent"><button type="submit">修改</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
	</form>
</div>