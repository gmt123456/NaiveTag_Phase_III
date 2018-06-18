<template>
  <div style="height: 100%">
    <h2 style="text-align: center">{{title}}</h2>
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
    name: "Radar",
    props: ['title', 'dimension', 'data', 'uniqueId','name'],
    watch: {
      seriesData: {
        handler: function () {
          console.log(this.data)
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

        let color = ['#87CEFA', '#B3E4A1', '#3498db', '#e74c3c']
        let radar = this.$echarts.init(document.getElementById(this.uniqueId));
        let gradientColor = [];
        let baseColor = 'rgba(204,204,204,';
        for (let i = 0; i < 6; i++) {
          // gradientColor.push(baseColor+String(i*0.2)+')');
          gradientColor.push(baseColor + '0.8)');
        }
        let option = {

          title: {
            text: '',
            x: 'left',                 // 水平安放位置，默认为左对齐，可选为：
            // 'center' ¦ 'left' ¦ 'right'
            // ¦ {number}（x坐标，单位px）
            y: 'top',
          },
          tooltip: {},

          radar: {
            indicator: [],
            shape: 'circle',
            splitNumber: 5,
            nameGap: 30,
            name: {
              textStyle: {
                color: '#535353',
                fontSize: 16
              }
            },
            splitLine: {
              lineStyle: {
                color: gradientColor.reverse()
                // color: [
                //   'rgba(238, 197, 102, 0.1)', 'rgba(238, 197, 102, 0.2)',
                //   'rgba(238, 197, 102, 0.4)', 'rgba(238, 197, 102, 0.6)',
                //   'rgba(238, 197, 102, 0.8)', 'rgba(238, 197, 102, 1)'
                // ].reverse()                                 //由外圈到内圈透明度依次增加

              }
            },
            splitArea: {
              show: false
            },
            axisLine: {
              lineStyle: {
                color: '#CCCCCC'
              }
            }
          },
          series: []
        };


        for (let key in this.dimension) {
          option.radar.indicator.push({name: this.dimension[key], max: 60});
        }

        for (let key in this.data) {
          let seriesItem = {
            name: '',
            type: 'radar',
            lineStyle: {
              normal: {
                width: 1,
                opacity: 0.5
              }
            },
            data: this.data[key],
            // symbol: 'none',
            itemStyle: {
              normal: {
                color: color[key]
              }
            },
            areaStyle: {
              normal: {
                opacity: 0.1
              }
            }
          }
          option.series.push(seriesItem);
        }



        radar.setOption(option)
      }
    }
  }
</script>

<style scoped>

</style>
