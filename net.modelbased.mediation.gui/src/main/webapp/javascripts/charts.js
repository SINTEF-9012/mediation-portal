var chart;
chart = new Highcharts.Chart({
    chart: {
        renderTo: 'chart-container',
        type: 'column',
        inverted: false
    },
    title: {
        text: 'Comparison Results'
    },
    subtitle: {
        style: {
            position: 'absolute',
            right: '0px',
            bottom: '10px'
        }
    },
    legend: {
        layout: 'horizontal',
        borderWidth: 1,
        backgroundColor: '#FFFFFF'
    },
    xAxis: {
        categories: [
            'Precision',
            'Recall',
            'Accuracy',
            'F-Measure'
        ]
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
    series: [{
        name: 'Mapping #1',
        data: [0.23, 0.67, 0.78, 0.45]
    }, {
        name: 'Mapping #2',
        data: [0.47, 0.74, 0.46, 0.84]
    }, {
        name: 'Mapping #3',
        data: [0.43, 0.27, 0.98, 0.65]
    }]
});

