<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:hcComboda id="buildCombodaContainer" firstData="${firstData}" secondData="${secondData}" 
		categories="${categories}" firstUnit="mm" secondUnit="°C" title="Average Monthly Temperature and Rainfall in Tokyo"
		subtitle="Source: WorldClimate.com" firstName="Rainfall" secondName="Temperature">
</dwz:hcComboda>

