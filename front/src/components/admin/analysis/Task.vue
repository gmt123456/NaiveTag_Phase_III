<template>
  <div style="margin: auto;height: 100%">
    <line-chart
      unique-id="0" :xData="time" :series-data="seriesData"
      :series-name="seriesName" title="Trend of Tasks"></line-chart>
  </div>
</template>

<script>
  import {getTasks} from "../../../api/adminAnalysis";
  import LineChart from "../../common/LineChart";

  export default {
    name: "Task",
    components: {LineChart},
    data: function () {
      return {
        time: [],
        seriesData: [],
        seriesName: [],
      }
    },
    created: function () {
      getTasks(res => {
        this.seriesData.push(res.doneNumber, res.commitNumber, res.releaseNumber);
        this.seriesName.push('Completed Task', 'Committed Task', 'Released Task');
        this.time = res.time;
      })
    }
  }
</script>

<style scoped>

</style>
