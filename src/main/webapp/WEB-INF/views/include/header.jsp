<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include/var.jsp"%>
	  <div id="header">
		<div class="headerNav">
			<a class="logo" href="${contextPath}/system/index" style="width:458px;">Logo</a>
			<ul class="nav">
				<li><a href="${contextPath}/system/index">主页</a></li>
				<li><a href="${contextPath}/system/index/updateBase" target="dialog" mask="true">修改用户信息</a></li>
				<li><a href="${contextPath}/system/index/updatePwd" target="dialog" mask="true" width="550" height="200">修改密码</a></li>
				<li><a href="${contextPath}/logout">退出</a></li>
			</ul>
			<ul class="themeList" id="themeList">
				<li theme="default"><div>blue</div></li>
<!-- 				<li theme="green"><div>green</div></li> -->
				<li theme="purple"><div>purple</div></li>
<!-- 				<li theme="silver"><div>silver</div></li> -->
				<li theme="azure"><div class="selected">天蓝</div></li>
			</ul>
		</div>
		
		<!-- <div id="navMenu">
			<ul>
			    <c:forEach var="menu" items="${menuModules}" varStatus="status"> 
			      <c:if test="${status.index== 0}">
			         <li class="selected"><a href="${contextPath}/system/index/tree/${menu.id}" target="navTab"><span>${menu.name}</span></a></li>
                  </c:if>
                  <c:if test="${status.index!= 0}">
			         <li><a href="${contextPath}/system/index/tree/${menu.id}" target="navTab"><span>${menu.name}</span></a></li>
                  </c:if>
			    </c:forEach>
			</ul>
		</div> -->
	</div>
