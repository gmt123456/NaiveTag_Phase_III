<template>
  <div class="taskList">
    <el-tabs>
      <el-tab-pane label="Ongoing">
        <task-box v-for="(item,key) in ongoing" :task="item" :key="key"></task-box>
        <div v-if="!ongoing.length" style="text-align: center">
          <img src="/static/none.png" class="none-pic" />
          <p class="none-word">There are no ongoing tasks </p>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Completed">
        <task-box v-for="(item,key) in done" :task="item" :key="key"></task-box>
        <div v-if="!done.length" style="text-align: center">
          <img src="/static/none.png" class="none-pic" />
          <p class="none-word">There are no completed tasks </p>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import {getTaskList} from "../../api/getTaskList";
  import TaskBox from "./TaskBox";


  export default {
    name: "TaskList",
    components: {TaskBox},
    data: function () {
      return {
        ongoing: [],
        done: []
      }
    },
    methods: {
      refresh: function () {
        console.log('refresh');
        getTaskList(localStorage.token, (ongoing, done) => {
          this.ongoing = ongoing;
          this.done = done;
        })
      }
    },
    created: function () {
      this.refresh();
    }
  }
</script>

<style scoped>
  .taskList {
    min-width: 900px;
    margin-top: 50px;
  }
  .none-word{
    margin-top: 30px;
    font-size: 24px;
    font-weight: bold;
    color: gray;
  }
  .none-pic{
    margin-top: 40px;

  }

</style>
