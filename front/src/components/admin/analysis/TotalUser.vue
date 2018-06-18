<template>
  <div style="margin: auto;height: 100%">
    <line-chart
      unique-id="0" :xData="time" :series-data="seriesData"
      :series-name="seriesName" title="Trend of Enrollment"></line-chart>
  </div>
</template>

<script>
  import LineChart from "../../common/LineChart";
  import {getTotalUser} from "../../../api/adminAnalysis";

  export default {
    name: "TotalUser",
    components: {LineChart},
    data: function () {
      return {
        time: [],
        seriesData: [],
        seriesName: [],
      }
    },
    created: function () {
      getTotalUser(res => {
        this.seriesData.push(res.totalData, res.workerData, res.requesterData);
        this.seriesName.push('Total Enrollment', 'Worker Enrollment', 'Requester Enrollment');
        this.time = res.time;
      })
    }
  }
</script>

<style scoped>

</style>
