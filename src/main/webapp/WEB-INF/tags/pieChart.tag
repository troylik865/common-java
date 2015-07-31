<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="dataMap" type="java.util.Map" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" charset="utf-8">

/* Title settings */		
title = "October Browser Statistics";
titleXpos = 390;
titleYpos = 85;

/* Pie Data */
pieRadius = 130;
pieXpos = 150;
pieYpos = 180;
pieData = ${dataMap.data};
pieLegend = ${dataMap.legend};
pieLegendPos = "east";

$(function () {
	var r = Raphael("${name}");
	 
	r.text(titleXpos, titleYpos, title).attr({"font-size": 20});
	
	var pie = r.piechart(pieXpos, pieYpos, pieRadius, pieData, {legend: pieLegend, legendpos: pieLegendPos});
	pie.hover(function () {
		this.sector.stop();
		this.sector.scale(1.1, 1.1, this.cx, this.cy);
		if (this.label) {
			this.label[0].stop();
			this.label[0].attr({ r: 7.5 });
			this.label[1].attr({"font-weight": 800});
		}
	}, function () {
		this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
		if (this.label) {
			this.label[0].animate({ r: 5 }, 500, "bounce");
			this.label[1].attr({"font-weight": 400});
		}
	});
	
});
</script>

<div id="${name}"></div>