<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!-- div id -->
<%@ attribute name="id" type="java.lang.String" required="true"%>
<!-- 图标标题 -->
<%@ attribute name="title" type="java.lang.String" required="true"%>
<!-- 图标小标题 -->
<%@ attribute name="subtitle" type="java.lang.String"%>
<!-- y轴名称-->
<%@ attribute name="name" type="java.lang.String" required="true"%>
<!-- div 样式-->
<%@ attribute name="style" type="java.lang.String"%>
<!-- 图表导出类型 -->
<%@ attribute name="exportType" type="java.lang.String"%>
<!-- x轴数据 -->
<%@ attribute name="categories" type="java.lang.String" required="true"%>
<!-- y轴数据 -->
<%@ attribute name="data" type="java.lang.String" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${style == null}" >
	<c:set var="style" value="min-width: 400px; height: 400px; margin: 0 auto"/>
</c:if>
<c:if test="${exportType == null}" >
	<c:set var="exportType" value="custom"/>
</c:if>

<script type="text/javascript" charset="utf-8">
$(function () {
    var chart;
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: '${id}',
                type: 'column'
            },
            title: {
                text: '${title}'
            },
            <c:if test="${exportType == 'custom'}" >
            exporting:{
			    filename:'custom-columnBasic-chart',
			    url:'<%=request.getContextPath()%>/servlet/SaveAsImage'
			},
			</c:if>
            subtitle: {
                text: '${subtitle}'
            },
            xAxis: {
                categories: ${categories}
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${name}'
                }
            },
            legend: {
                layout: 'vertical',
                backgroundColor: '#FFFFFF',
                align: 'left',
                verticalAlign: 'top',
                x: 100,
                y: 70,
                floating: true,
                shadow: true
            },
            tooltip: {
                formatter: function() {
                    return ''+
                        this.x +': '+ this.y;
                }
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
                series: ${data}
        });
    });
    
});
</script>
<div id="${id}" style="${style}"></div>