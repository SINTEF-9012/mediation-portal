/*
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: root
 *
 * Mediation Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Mediation Portal is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Mediation Portal. If not, see
 * <http://www.gnu.org/licenses/>.
 */
var chart;



/**
 * Update the chart with the comparisons resulting from the request
 *
 * @param comparisons the list of comparisons to display 
 */
function updateChart(comparisons) {
    chart = new Highcharts.Chart({
	chart: {
            renderTo: 'chart-container',
            type: 'column',
            inverted: false
	},
	title: {
            text: "Comparison with Oracle #" + comparisons[0].oracle
	},
	subtitle: {
            style: {
		position: 'absolute',
		right: '0px',
		bottom: '10px'
            }
	},
	legend: {
            layout: 'vertical',
            borderWidth: 1,
            backgroundColor: '#FFFFFF'
	},
	xAxis: {
            categories: ["Precision", "Recall", "Accuracy", "F-Measure"]
	},
	yAxis: {
            title: {
		text: 'Unit Values'
            },
            labels: {
		formatter: function() {
                    return this.value;
		}
            },
            min: 0,
            max: 1,
            gridLineColor: "#C0C0C0"
	},
	tooltip: {
            formatter: function() {
		return ''+
                    this.x +': '+ this.y;
            }
	},
	plotOptions: {
            area: {
		fillOpacity: 0.25
            }
	},
	series: statsToSeries(comparisons)
    });
}


function statsToSeries(stats) {
    var result = [];
    for (i=0 ; i<stats.length ; i++) {
	result[i] = {
	    name: stats[i]["mapping"],
	    data: [stats[i]["precision"], stats[i]["recall"], stats[i]["accuracy"], stats[i]["f-measure"]]
	};
    }
    return result;
}
