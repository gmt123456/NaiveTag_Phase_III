<template>
  <div style="margin-bottom: 100px" v-if="task">
    <div style="display: flow;width: 100%">
      <div style="width: 560px">
        <h3>Description</h3>
        <p>{{task.description}}</p>
      </div>
      <div class="process-box">
        <el-progress type="circle" :percentage="Number(task.process)*100"></el-progress>
      </div>
    </div>

    <div style="margin-top: 30px">
      <h3>Participants</h3>
      <span>This task was done by <b>{{task.participantsNum}}</b> people</span>
      <br>
      <span v-if="task.participants.length>0"> Hers is the list.</span>
      <contributor-rank :worker-list="task.participants" style="margin-top: 30px; margin-left: -18px"></contributor-rank>
    </div>

  </div>
</template>

<script>
  import ContributorRank from "./ContributorRank";

  export default {
    name: "SubTaskDetail",
    components: {ContributorRank},
    props: ['subTask'],
    data: function () {
      return {
        task: ''
      }
    },
    created: function () {
      this.task = this.subTask;
    },
    methods: {
      refresh: function (task) {
        this.task = task;
      }
    }
  }
</script>

<style scoped>

  .process-box {
    float: right;
    margin-right: 30px;
    margin-top: -80px;
  }
</style>
