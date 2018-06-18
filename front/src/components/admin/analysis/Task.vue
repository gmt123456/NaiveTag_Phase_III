<template>
  <div style="margin: auto;height: 100%">
    <line-chart
      unique-id="0" :xData="time" :series-data="seriesData"
      :series-name="seriesName" title="Trend of Tasks"></line-chart>

    <pie-chart :name="distributionName" :data="distributionValue"  title="Distribution of Tasks" unique-id="00"></pie-chart>
    <bar-chart :x-data="xData" :data="tagError" :name="['Tag Error']" unique-id="01" title="Tag Error of Tasks"></bar-chart>
    <bar-chart :x-data="xData" :data="speed" :name="['Speed(pictures per day)']" unique-id="02" title="Speed of Tasks"></bar-chart>
    <bar-chart :x-data="xData" :data="participantsNum" :name="['Participants']" unique-id="03" title="Participants of Tasks"></bar-chart>
  </div>
</template>

<script>
  import {getTaskEvaluation, getTaskPie, getTasks} from "../../../api/adminAnalysis";
  import LineChart from "../../common/LineChart";
  import BarChart from "../../common/BarChart";
  import {convertTypeToString, getTypes} from "../../../api/taskType";
  import PieChart from "../../common/PieChart";

  export default {
    name: "Task",
    components: {PieChart, BarChart, LineChart},
    data: function () {
      return {
        time: [],
        seriesData: [],
        seriesName: [],
        xData:[],
        tagError:[],

        distributionName:[],
        distributionValue:[],

        speed:[],
        participantsNum:[]
      }
    },
    created: function () {
      this.xData=getTypes();
      getTasks(res => {
        this.seriesData.push(res.doneNumber, res.commitNumber, res.releaseNumber);
        this.seriesName.push('Completed Task', 'Committed Task', 'Released Task');
        this.time = res.time;
      });

      getTaskEvaluation(res=>{
        this.tagError.push(res.tagError);
        this.speed.push(res.speed);
        this.participantsNum.push(res.participantsNum);
      });

      getTaskPie(res=>{
        for (let key in res){
          this.distributionName.push(convertTypeToString(key));
          this.distributionValue.push(res[key]);
        }
      })
    }
  }
</script>

<style scoped>

</style>
