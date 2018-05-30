<template>
  <div class="taskList">
    <el-tabs >
      <el-tab-pane label="Ongoing">
        <task-box v-for="(item,key) in ongoing" :task="item" :key="key" ></task-box>
      </el-tab-pane>
      <el-tab-pane label="Completed">
        <task-box v-for="(item,key) in done" :task="item" :key="key"></task-box>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
    import {getTaskList} from "../../api/getTaskList";
    import TaskBox from "./TaskBox";


    export default {
        name: "TaskList",
      components: { TaskBox},
      data:function () {
        return{
          ongoing:[],
          done:[]
        }
      },
      created:function () {
        getTaskList(localStorage.token, (ongoing,done)=> {
          console.log('ongoing'+ongoing);
          console.log(this.ongoing);
          console.log(ongoing);

          this.ongoing=ongoing;
          this.done=done;
        })
      }
    }
</script>

<style scoped>
  .taskList{
    min-width: 900px;
    margin-top: 50px;
  }

</style>
