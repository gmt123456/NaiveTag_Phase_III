<template>
   <div style="height: 100%">
     <h2 style="text-align: center">{{title}}</h2>
     <!--<p v-if="sub" style="text-align: right;margin-right: 20%;font-size>{{sub}}</p>-->
     <div :id=this.uniqueId style="width: 70%;height: 60%;margin-left: auto;margin-right: auto;margin-top: 30px">
     </div>
   </div>
</template>

<script>

  /**
   *因为最终是将图表渲染到上面模板中的容器里，渲染的时候根据Id找到容器，
   * 所以当页面中有多个图表时，每个容器应有不相同的ID
   * echarts 官方文档  http://echarts.baidu.com/tutorial.html#5%20%E5%88%86%E9%92%9F%E4%B8%8A%E6%89%8B%20ECharts
   */

  export default {
    name: "lineChart",
    props: ['title', 'xData', 'seriesData', 'seriesName', 'uniqueId','sub'],
    watch: {
      seriesData: {
        handler: function () {
          console.log('lineChange')
          console.log(this.seriesData)
          this.drawLineChart();
        },
        deep: true

      }


    },
    mounted: function () {
      this.drawLineChart();
    },
    methods: {
      drawLineChart: function () {

        let color =['#87CEFA','#78a355','#fcaf17' ,'#B3E4A1','#d71345','#3498db','#E8B3E6', '#FF7F50'];
        let lineChart = this.$echarts.init(document.getElementById(this.uniqueId));
        let option = {

          title: {
            text: '',
            x: 'left',                 // 水平安放位置，默认为左对齐，可选为：
            // 'center' ¦ 'left' ¦ 'right'
            // ¦ {number}（x坐标，单位px）
            y: 'top',                  // 垂直安放位置，默认为全图顶端，可选为：
                                       // 'top' ¦ 'bottom' ¦ 'center'
                                       // ¦ {number}（y坐标，单位px）
            //textAlign: null          // 水平对齐方式，默认根据x设置自动调整
            backgroundColor: 'rgba(0,0,0,0)',
            borderColor: '#ccc',       // 标题边框颜色
            borderWidth: 0,            // 标题边框线宽，单位px，默认为0（无边框）
            padding: 5,                // 标题内边距，单位px，默认各方向内边距为5，
                                       // 接受数组分别设定上右下左边距，同css
            itemGap: 10,               // 主副标题纵向间隔，单位px，默认为10，
            textStyle: {
              fontSize: 18,
              fontWeight: 'bolder',
              color: '#333'          // 主标题文字颜色
            },
            subtextStyle: {
              color: '#aaa'          // 副标题文字颜色
            }
          },
          xAxis: {
            data: this.xData
          },
          legend: {
            data: [],
            orient: 'horizontal',      // 布局方式，默认为水平布局，可选为：
            // 'horizontal' ¦ 'vertical'
            x: 'right',               // 水平安放位置，默认为全图居中，可选为：
            // 'center' ¦ 'left' ¦ 'right'
            // ¦ {number}（x坐标，单位px）
            y: 'top',                  // 垂直安放位置，默认为全图顶端，可选为：
                                       // 'top' ¦ 'bottom' ¦ 'center'
                                       // ¦ {number}（y坐标，单位px）
            backgroundColor: 'rgba(0,0,0,0)',
            borderColor: '#ccc',       // 图例边框颜色
            borderWidth: 0,            // 图例边框线宽，单位px，默认为0（无边框）
            padding: 5,                // 图例内边距，单位px，默认各方向内边距为5，
                                       // 接受数组分别设定上右下左边距，同css
            itemGap: 10,               // 各个item之间的间隔，单位px，默认为10，
                                       // 横向布局时为水平间隔，纵向布局时为纵向间隔
            itemWidth: 20,             // 图例图形宽度
            itemHeight: 14,            // 图例图形高度
            textStyle: {
              color: '#333'          // 图例文字颜色
            }
          },
          tooltip: {
            trigger: 'axis',           // 触发类型，默认数据触发，见下图，可选为：'item' ¦ 'axis'
            showDelay: 20,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
            hideDelay: 100,            // 隐藏延迟，单位ms
            transitionDuration: 0.4,  // 动画变换时间，单位s
            backgroundColor: 'rgba(0,0,0,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
            borderColor: '#333',       // 提示边框颜色
            borderRadius: 4,           // 提示边框圆角，单位px，默认为4
            borderWidth: 0,            // 提示边框线宽，单位px，默认为0（无边框）
            padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
                                       // 接受数组分别设定上右下左边距，同css
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'line',         // 默认为直线，可选为：'line' | 'shadow'
              lineStyle: {          // 直线指示器样式设置
                color: 'red',
                width: 2,
                type: 'solid'
              },
              shadowStyle: {                       // 阴影指示器样式设置
                width: 'auto',                   // 阴影大小
                color: 'rgba(150,150,150,0.3)'  // 阴影颜色
              }
            },
            textStyle: {
              color: '#fff'
            }
          },
          yAxis: {
            splitLine: {show: false}
          },
          grid: [{
            top: '50',
            left: '5%',
            right: '5%'
          }],
          series: [],

        };


        for (let i = 0; i < this.seriesData.length; i++) {
          let seriesItem = {};
          seriesItem.name = this.seriesName[i];
          seriesItem.type = 'line';
          option.legend.data.push(seriesItem.name);
          seriesItem.smooth=0.1 ;
          seriesItem.data = this.seriesData[i];
          seriesItem.itemStyle = {
            normal: {
              color: color[i],
            }
          };

          option.series.push(seriesItem);
        }

        lineChart.setOption(option)
      }
    }
  }
</script>

<style scoped>

</style>
