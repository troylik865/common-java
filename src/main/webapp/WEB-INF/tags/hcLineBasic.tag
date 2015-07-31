<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="subtitle" type="java.lang.String"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="style" type="java.lang.String"%>
<%@ attribute name="exportType" type="java.lang.String"%>
<%@ attribute name="categories" type="java.lang.String" required="true"%>
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
                type: 'line',
                marginRight: 130,
                marginBottom: 25
            },
            title: {
                text: '${title}',
                x: -20 //center
            },
            <c:if test="${exportType == 'custom'}" >
            exporting:{
			    filename:'custom-line-chart',
			    url:'<%=request.getContextPath()%>/servlet/SaveAsImage'
			},
			</c:if>
            subtitle: {
                text: '${subtitle}',
                x: -20
            },
            xAxis: {
                categories: ${categories}
            },
            yAxis: {
                title: {
                    text: '${name}'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        this.x +': '+ this.y;
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 100,
                borderWidth: 0
            },
            series: ${data}
        });
    });
    
});
</script>
<div id="${id}" style="${style}"></div>