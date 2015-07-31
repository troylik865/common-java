<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
	<div id="container">
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">主页</span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:void(0)">主页</a></li>
			</ul>
			<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page unitBox">
					<div class="accountInfo">
						<div class="right">
							<p><fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd EEEE"/></p>
						</div>
						<p><span>欢迎, ${user.username } .</span></p>
					</div>
					<div class="pageFormContent" layoutH="80">
						<fieldset>
							<legend>基本信息</legend>
							<dl>
								<dt>登录名称：</dt>
								<dd><span class="unit">${user.username }</span></dd>
							</dl>
							<dl>
								<dt>真实名字：</dt>
								<dd><span class="unit">${user.realname }</span></dd>
							</dl>							
							<dl>
								<dt>电话：</dt>
								<dd><span class="unit">${user.phone }</span></dd>
							</dl>							
							<dl>
								<dt>E-Mail：</dt>
								<dd><span class="unit">${user.email }</span></dd>
							</dl>
							<dl>
								<dt>创建时间：</dt>
								<dd><span class="unit"><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></dd>
							</dl>
							<dl>
								<dt>可用状态：</dt>
								<dd><span class="unit">${user.status == "enabled" ? "可用":"不可用"}</span></dd>
							</dl>
							<dl>
								<dt>所属机构：</dt>
								<dd><span class="unit">${user.group.name}</span></dd>
							</dl>																														
						</fieldset>
						
					</div><!-- end div class="pageFormContent" -->
				</div>
			</div>
		</div>
	</div>
