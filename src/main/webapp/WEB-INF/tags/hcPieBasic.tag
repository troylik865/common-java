<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="style" type="java.lang.String"%>
<%@ attribute name="exportType" type="java.lang.String"%>
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
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '${title}'
            },
            <c:if test="${exportType == 'custom'}" >
	            exporting:{
				    filename:'custom-pie-chart',
				    url:'<%=request.getContextPath()%>/servlet/SaveAsImage'
				},
			</c:if>
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: '${name}',
                data: ${data}
            }]
        });
    });
    
});
</script>
<div id="${id}" style="${style}"></div>