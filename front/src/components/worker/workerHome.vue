<template>
    <div id="workerHome">
        <div id="calendar" :style="{width: '100%', height: '200px'}"></div>
        <div style="height: 300px;"></div>
        <div id="dollarChanges" :style="{width: '100%', height: '300px'}"></div>
        <div style="height: 20px;"></div>
        <div id="scoreChanges" :style="{width: '100%', height: '400px'}"></div>
    </div>
</template>

<script>
    export default {

        data () {
            return {
	            dataDollarChanges : [["2000-06-05",116],["2000-06-06",129],["2000-06-07",135],["2000-06-08",86],["2000-06-09",73],["2000-06-10",85],["2000-06-11",73],["2000-06-12",68],["2000-06-13",92],["2000-06-14",130],["2000-06-16",139],["2000-06-17",115],["2000-06-18",111],["2000-06-20",206],["2000-06-21",137],["2000-06-22",128],["2000-06-23",85],["2000-06-24",94],["2000-06-25",71],["2000-06-26",106],["2000-06-27",84],["2000-06-28",93],["2000-06-29",85],["2000-06-30",73],["2000-07-01",83],["2000-07-02",125],["2000-07-03",107],["2000-07-04",82],["2000-07-05",44],["2000-07-06",72],["2000-07-07",106],["2000-07-08",107],["2000-07-09",66],["2000-07-10",91],["2000-07-11",92],["2000-07-12",113],["2000-07-13",107],["2000-07-14",131],["2000-07-15",111],["2000-07-16",64],["2000-07-17",69],["2000-07-18",88],["2000-07-19",77],["2000-07-20",83],["2000-07-21",111],["2000-07-22",57],["2000-07-23",55],["2000-07-24",60]],

	            dataScoreChanges : [["2000-06-05",56],["2000-06-06",31.8],["2000-06-07",88],["2000-06-08",10],["2000-06-09",73],["2000-06-10",85],["2000-06-11",73],["2000-06-12",68],["2000-06-13",92],["2000-06-14",130],["2000-06-16",139],["2000-06-17",115],["2000-06-18",111],["2000-06-19",309],["2000-06-20",206],["2000-06-21",137],["2000-06-22",128],["2000-06-23",85],["2000-06-24",94],["2000-06-25",71],["2000-06-26",106],["2000-06-27",84],["2000-06-28",93],["2000-06-29",85],["2000-06-30",73],["2000-07-01",83],["2000-07-02",125],["2000-07-03",107],["2000-07-04",82],["2000-07-05",44],["2000-07-06",72],["2000-07-07",106],["2000-07-08",107],["2000-07-09",66],["2000-07-10",91],["2000-07-11",92],["2000-07-12",113],["2000-07-13",107],["2000-07-14",131],["2000-07-15",111],["2000-07-16",64],["2000-07-17",69],["2000-07-18",88],["2000-07-19",77],["2000-07-20",83],["2000-07-21",111],["2000-07-22",57],["2000-07-23",55],["2000-07-24",60]],


	            optionDollarChanges : {

		            // Make gradient line here
		            visualMap: [{
			            show: false,
			            type: 'continuous',
			            seriesIndex: 0,
			            min: 0,
			            max: 400
		            }],


		            title: [{
			            left: 'center',
			            text: ''
		            }],
		            tooltip: {
			            trigger: 'axis'
		            },
		            xAxis: [{
			            data: []
		            }],
		            yAxis: [{
			            splitLine: {show: false}
		            }],
		            grid: [{
		            	top: '50',
		            	left: '5%',
                        right: '5%'
		            }],
		            series: [{
			            type: 'line',
			            showSymbol: false,
			            data: [],
			            areaStyle: {
				            normal: {
					            color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						            offset: 0,
						            color: '#d68262'
					            }, {
						            offset: 1,
						            color: '#ffe'
					            }])
				            }
			            },
		            }]
	            },

                optionCalendar : {
                    calendar: {
                        left: 'center',
                        range: 2018,
                        splitLine: {
                            show: false,
                        },
                        cellSize: 15,
                        itemStyle: {
                            borderWidth: 4,
                            borderColor: '#f6f9fa'
                        },
                        yearLabel: {show: false}
                    },
                    visualMap: {
                        show: false,
                        min: 0,
                        max: 1,
                        inRange: {
                            color: ['#ededed', '#5ac995']
                        },
                    },
                    series: [{
                        type: 'graph',
                    }, {
                        type: 'heatmap',
                        coordinateSystem: 'calendar',
                        data: this.getVirtulData(2018),
                    }]
                }
            }
        },

        mounted(){
            this.drawCalendar();
            this.drawDollarChanges();
            this.drawScoreChanges();
        },

        methods: {
            getVirtulData(year) {
                let date = + this.$echarts.number.parseDate(year + '-01-01');
                let end = + this.$echarts.number.parseDate(year + '-12-31');
                let dayTime = 3600 * 24 * 1000;
                let data = [];
                for (let time = date; time <= end; time += dayTime) {
                    data.push([
                        this.$echarts.format.formatTime('yyyy-MM-dd', time),
                        Math.random()*200
                    ]);
                }
                return data;
            },

            getDateList(data){
	            let dateList = [];
	            console.log(data);
	            for(let index in data){
		            dateList.push(data[index][0]);
                }
	            return dateList;
            },

            getValueList(data){
	            let valueList = [];
	            for(let index in data){
		            valueList.push(data[index][1]);
	            }
	            return valueList;
            },

            getMax(data) {
                let max = 1;
                for(let index in data){
                    if(data[index][1] > max){
                        max = data[index][1];
                    }
                }
                return max;
            },

            drawCalendar(){
                let calendar = this.$echarts.init(document.getElementById('calendar'));
                this.optionCalendar.visualMap.max = this.getMax(this.optionCalendar.series[1].data);
                calendar.setOption(this.optionCalendar);
            },

	        drawDollarChanges(){
		        let dollarChanges = this.$echarts.init(document.getElementById('dollarChanges'));
		        this.optionDollarChanges.title[0].text = 'Gradient along your dollars';
		        this.optionDollarChanges.visualMap[0].max = this.getDateList(this.dataDollarChanges).length;
		        this.optionDollarChanges.xAxis[0].data = this.getDateList(this.dataDollarChanges);
		        this.optionDollarChanges.series[0].data = this.getValueList(this.dataDollarChanges);

		        dollarChanges.setOption(this.optionDollarChanges);
	        },

	        drawScoreChanges(){
		        let scoreChanges = this.$echarts.init(document.getElementById('scoreChanges'));
		        this.optionDollarChanges.title[0].text = 'Gradient along your score';
		        this.optionDollarChanges.visualMap[0].max = this.getDateList(this.dataScoreChanges).length;
		        this.optionDollarChanges.xAxis[0].data = this.getDateList(this.dataScoreChanges);
		        this.optionDollarChanges.series[0].data = this.getValueList(this.dataScoreChanges);

		        scoreChanges.setOption(this.optionDollarChanges);
	        },
        }

    }
</script>

<style>

</style>
