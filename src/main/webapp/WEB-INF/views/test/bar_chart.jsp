<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<script type="text/javascript">
    var options = {
    	stacked: false,
    	gutter:20,
		axis: "0 0 1 1", // Where to put the labels (trbl)
		axisystep: 10 // How many x interval labels to render (axisystep does the same for the y axis)
	};
	
	$(function() {
        // Creates canvas
        var r = Raphael("barChartHolder");
        var data = [[10,20,30,50],[15,25,35,50]]
        
        // stacked: false
		var chart1 = r.barchart(40, 10, 320, 220, data, options).hover(function() {
            this.flag = r.popup(this.bar.x, this.bar.y, this.bar.value).insertBefore(this);
        }, function() {
            this.flag.animate({opacity: 0}, 500, ">", function () {this.remove();});
        });
		chart1.label([["A1",  "A2", "A3", "A4"],["B1",  "B2", "B3", "B4"]],true);
		
		
		// stacked: true
		options.stacked=true;
		
		var chart2 = r.barchart(400, 10, 320, 220, data, options).hoverColumn(function() {
    		var y = [], res = [];
            for (var i = this.bars.length; i--;) {
                y.push(this.bars[i].y);
                res.push(this.bars[i].value || "0");
            }
            this.flag = r.popup(this.bars[0].x, Math.min.apply(Math, y), res.join(", ")).insertBefore(this);
        }, function() {
            this.flag.animate({opacity: 0}, 500, ">", function () {this.remove();});
        });
		chart2.label([["A"],["B"],["C"],["D"]],true);
	});
</script>

<div id="barChartHolder"></div>