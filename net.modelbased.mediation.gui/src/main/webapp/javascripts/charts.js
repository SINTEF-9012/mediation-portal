var chart;
chart = new Highcharts.Chart({
    chart: {
        renderTo: 'chart-container',
        type: 'column',
        inverted: false
    },
    title: {
        text: 'Comparison with Oracle #550e8400-e29b-41d4-a716-446655440000'
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
        name: 'Mapping #f81d4fae-7dec-11d0-a765-00a0c91e6bf6',
        data: [0.23, 0.67, 0.78, 0.45]
    }, {
        name: 'Mapping #89ed71ce-5a66-4c10-a781-899f59614e25',
        data: [0.47, 0.74, 0.46, 0.84]
    }, {
        name: 'Mapping #523abe18-cb2c-406c-9893-6f61ccf9190a',
        data: [0.43, 0.27, 0.98, 0.65]
    }]
});

