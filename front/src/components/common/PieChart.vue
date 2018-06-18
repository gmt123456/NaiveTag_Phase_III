<template>
<div style="height: 100%">
  <h2 style="text-align: center">{{title}}</h2>
  <div :id=this.uniqueId style="width: 70%;height: 60%;margin-left: auto;margin-right: auto;margin-top: 30px">
  </div>
</div>
</template>

<script>
  export default {
    name: "PieChart",
    props: ['name', 'data', 'uniqueId','title'],
    mounted: function () {
      this.drawPieChart();
    },
    watch:{
      'data':function () {
        this.drawPieChart();
      }
    },
    methods: {

      drawPieChart: function () {
        let myColor=['#87CEFA', '#B3E4A1','#d71345','#3498db','#E8B3E6', '#FF7F50','#78a355','#fcaf17'];
        let option = {
          tooltip:{           //提示框

          },
          series: {
            type: 'pie',
            center : ['50%', '50%'],    // 默认全局居中
            radius : [0, '75%'],
            clockWise : false,          // 默认逆时针
            startAngle: 90,
            minAngle: 0,                // 最小角度改为0
            selectedOffset: 10,         // 选中是扇区偏移量
            itemStyle: {
              normal: {
                // color: 各异,
                borderColor: '#fff',
                borderWidth: 1,
                label: {
                  show: true,
                  position: 'outer'
                  // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                },
                labelLine: {
                  show: true,
                  length: 20,
                  lineStyle: {
                    // color: 各异,
                    width: 1,
                    type: 'solid'
                  }
                }
              },
              emphasis: {
                // color: 各异,
                borderColor: 'rgba(0,0,0,0)',
                borderWidth: 1,
                label: {
                  show: false
                  // position: 'outer'
                  // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                },
                labelLine: {
                  show: false,
                  length: 20,
                  lineStyle: {
                    // color: 各异,
                    width: 1,
                    type: 'solid'
                  }
                }
              }
            },
            data: [],
            // itemStyle:{
            //     normal:{
            //         color:function () {
            //             return
            //         }
            //     }
            // }
          },
          color:myColor

        };

        for (let i = 0; i < this.name.length; i++) {
          option.series.data.push({
            name: this.name[i],
            value: this.data[i]
          })

        }

        this.$echarts.init(document.getElementById(this.uniqueId)).setOption(option)
      }
    }
  }
</script>

<style scoped>

</style>
