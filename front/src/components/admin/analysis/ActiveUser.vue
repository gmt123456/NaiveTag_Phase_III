<template>
  <div style="margin: auto;height: 100%">
    <line-chart
      unique-id="0" :xData="time" :series-data="seriesData"
      :series-name="seriesName" title="Trend of Active Users"></line-chart>
  </div>

</template>

<script>
  import LineChart from "../../common/LineChart";
  import {getActiveUser} from "../../../api/adminAnalysis";

  export default {
    name: "ActiveUser",
    components: {LineChart},
    data: function () {
      return {
        time: [],
        seriesData: [],
        seriesName: [],
      }
    },
    created: function () {
      getActiveUser(res => {
        this.seriesData.push(res.totalData, res.workerData, res.requesterData);
        this.seriesName.push('Active User', 'Active Worker', 'Active Requester');
        this.time = res.time;
      })
    }
  }
</script>

<style scoped>

</style>
