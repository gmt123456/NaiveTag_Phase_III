<template>
    <div id="workerHome">

        <el-card style="height: 180px;width: 100%;">
            <div id="calendar" :style="{width: '100%', height: '200px', top: '-30px'}"></div>
        </el-card>
        <el-card style="width: 100%;margin-top: 10px;">
            <div id="dollarChanges" :style="getDollarsStyle()"></div>
        </el-card>
        <el-card style="width: 100%;margin-top: 10px;">
            <div id="scoreChanges" :style="getScoreStyle()"></div>
        </el-card>
        <div style="height: 20px;"></div>

    </div>
</template>

<script>

	import {workerChanges} from "../../../api/workerInfo";

    export default {

        data () {
            return {
            	//后端传过来的数据要先用getDataList转换位数组格式
            	userChanges:{
		            "dollarChanges": [],
                    "scoreChanges": [],
                    "activity": [], // 365个数字，表示活跃度，瑞年要不要考虑回头再议，反正2018年不是瑞年
                },

                optionChanges : {

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
	                tooltip: {
		                formatter: function (params) {
			                return 'date: ' + params.value[0]+'</br>commit: '+params.value[1];
		                }
	                },
                    calendar: {
                        left: 'center',
                        range: 2018,
                        splitLine: {
                            show: false,
                        },
                        cellSize: 15,
                        itemStyle: {
                            borderWidth: 4,
                            borderColor: 'white'
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
                        data: [],
                    }]
                }
            }
        },

        created(){
        },

        mounted(){
          this.$echarts.init(document.getElementById('calendar'));
          this.$echarts.init(document.getElementById('dollarChanges'));
          this.$echarts.init(document.getElementById('scoreChanges'));
            this.getChangesData();
        },

        methods: {

        	getDollarsStyle(){
        		let data = {
			        width: '100%',
			        height: '280px',
		        };
        		if(this.userChanges.dollarChanges.length < 3){
			        data['background'] = "url('../../../../static/background/bg_data.png') no-repeat center";
			        data['background-size'] =  '50%';
                }
        		return data;
            },

            getScoreStyle(){
	            let data = {
		            width: '100%',
		            height: '380px',
	            };
	            if(this.userChanges.scoreChanges.length < 3){
		            data['background'] = "url('../../../../static/background/bg_data.png') no-repeat center";
		            data['background-size'] =  '50%';
	            }
	            return data;
            },

        	getChangesData(){
		        let that = this;
		        workerChanges(res => {
		          console.log(res.activity[0]);
			        that.userChanges.activity = that.getDataList(res.activity);
			        that.userChanges.dollarChanges = that.getDataList(res.dollarChanges);
			        that.userChanges.scoreChanges = that.getDataList(res.scoreChanges);
			        console.log(that.userChanges.activity[0]);

              that.drawCalendar();
              that.drawDollarChanges();
              that.drawScoreChanges();
		        });
            },

            //将后端传过来的dataInfo转化成图表可以接受的data
            getDataList(dataInfo){
            	let data = [];
            	for(let index in dataInfo){
            		let dataItem = [];
            		dataItem.push(dataInfo[index].date);
            		dataItem.push(dataInfo[index].value);
            		data.push(dataItem);
                }
                return data;
            },

            getDateList(data){
	            let dateList = [];
	            // console.log(data);
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
                this.optionCalendar.series[1].data = this.userChanges.activity;
                this.optionCalendar.visualMap.max = this.getMax(this.optionCalendar.series[1].data);
                calendar.setOption(this.optionCalendar);
            },

	        drawDollarChanges(){
		        let dollarChanges = this.$echarts.init(document.getElementById('dollarChanges'));
		        this.optionChanges.title[0].text = 'Changing curve of your dollars';
		        this.optionChanges.visualMap[0].max = this.getDateList(this.userChanges.dollarChanges).length;
		        this.optionChanges.xAxis[0].data = this.getDateList(this.userChanges.dollarChanges);
		        this.optionChanges.series[0].data = this.getValueList(this.userChanges.dollarChanges);

		        dollarChanges.setOption(this.optionChanges);
	        },

	        drawScoreChanges(){
		        let scoreChanges = this.$echarts.init(document.getElementById('scoreChanges'));
		        this.optionChanges.title[0].text = 'Changing curve of your score';
		        this.optionChanges.visualMap[0].max = this.getDateList(this.userChanges.scoreChanges).length;
		        this.optionChanges.xAxis[0].data = this.getDateList(this.userChanges.scoreChanges);
		        this.optionChanges.series[0].data = this.getValueList(this.userChanges.scoreChanges);

		        scoreChanges.setOption(this.optionChanges);
	        },
        }

    }
</script>

<style>

</style>
