<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!-- div id -->
<%@ attribute name="id" type="java.lang.String" required="true"%>
<!-- 图标标题 -->
<%@ attribute name="title" type="java.lang.String" required="true"%>
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
    
        var colors = Highcharts.getOptions().colors,
            categories = ${categories},
            name = '${name}',
            data = ${data};
    
    
        // Build the data arrays
        var parentData = [];
        var childData = [];
        for (var i = 0; i < data.length; i++) {
    
            // add parentData data
            parentData.push({
                name: categories[i],
                y: data[i].y,
                color: data[i].color
            });
    
            // add childData data
            for (var j = 0; j < data[i].drilldown.data.length; j++) {
                var brightness = 0.2 - (j / data[i].drilldown.data.length) / 5 ;
                childData.push({
                    name: data[i].drilldown.categories[j],
                    y: data[i].drilldown.data[j],
                    color: Highcharts.Color(data[i].color).brighten(brightness).get()
                });
            }
        }
    
        // Create the chart
        chart = new Highcharts.Chart({
            chart: {
                renderTo: '${id}',
                type: 'pie'
            },
            title: {
                text: '${name}'
            },
            <c:if test="${exportType == 'custom'}" >
            exporting:{
			    filename:'custom-fanChart-chart',
			    url:'<%=request.getContextPath()%>/servlet/SaveAsImage'
			},
			</c:if>
            yAxis: {
                title: {
                    text: '${title}'
                }
            },
            plotOptions: {
                pie: {
                    shadow: false
                }
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
                }
            },
            series: [{
                name: 'ParentName',
                data: parentData,
                size: '60%',
                dataLabels: {
                    formatter: function() {
                        return this.y > 5 ? this.point.name : null;
                    },
                    color: 'white',
                    distance: -30
                }
            }, {
                name: 'ChildName',
                data: childData,
                innerSize: '60%',
                dataLabels: {
                    formatter: function() {
                        // display only if larger than 1
                        return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y +'%'  : null;
                    }
                }
            }]
        });
    });
    
});
</script>
<div id="${id}" style="${style}"></div>