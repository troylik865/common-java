<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!-- div id -->
<%@ attribute name="id" type="java.lang.String" required="true"%>
<!-- 图标标题 -->
<%@ attribute name="title" type="java.lang.String" required="true"%>
<!-- 图标小标题 -->
<%@ attribute name="subtitle" type="java.lang.String"%>
<!-- 柱状图名称-->
<%@ attribute name="firstName" type="java.lang.String" required="true"%>
<!-- 折线图名称 -->
<%@ attribute name="secondName" type="java.lang.String" required="true"%>
<!-- 柱状图单位 -->
<%@ attribute name="firstUnit" type="java.lang.String" required="true"%>
<!-- 折线图单位 -->
<%@ attribute name="secondUnit" type="java.lang.String" required="true"%>
<!-- div 样式-->
<%@ attribute name="style" type="java.lang.String"%>
<!-- 图表导出类型 -->
<%@ attribute name="exportType" type="java.lang.String"%>
<!-- x轴数据 -->
<%@ attribute name="categories" type="java.lang.String" required="true"%>
<!-- y轴数据 -->
<%@ attribute name="firstData" type="java.lang.String" required="true"%>
<%@ attribute name="secondData" type="java.lang.String" required="true"%>

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
                zoomType: 'xy'
            },
            title: {
                text: '${title}'
            },
            <c:if test="${exportType == 'custom'}" >
            exporting:{
			    filename:'custom-combada-chart',
			    url:'<%=request.getContextPath()%>/servlet/SaveAsImage'
			},
			</c:if>
            subtitle: {
                text: '${subtitle}'
            },
            xAxis: [{
                categories: ${categories}
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    formatter: function() {
                        return this.value +'${secondUnit}';
                    },
                    style: {
                        color: '#89A54E'
                    }
                },
                title: {
                    text: '${secondName}',
                    style: {
                        color: '#89A54E'
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: '${firstName}',
                    style: {
                        color: '#4572A7'
                    }
                },
                labels: {
                    formatter: function() {
                        return this.value +' ${firstUnit}';
                    },
                    style: {
                        color: '#4572A7'
                    }
                },
                opposite: true
            }],
            tooltip: {
                formatter: function() {
                    return ''+
                        this.x +': '+ this.y +
                        (this.series.name == '${firstName}' ? ' ${firstUnit}' : '${secondUnit}');
                }
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                x: 120,
                verticalAlign: 'top',
                y: 100,
                floating: true,
                backgroundColor: '#FFFFFF'
            },
            series: [{
                name: '${firstName}',
                color: '#4572A7',
                type: 'column',
                yAxis: 1,
                data: ${firstData}
    
            }, {
                name: '${secondName}',
                color: '#89A54E',
                type: 'spline',
                data: ${secondData}
            }]
        });
    });
    
});
</script>
<div id="${id}" style="${style}"></div>