<template>
  <div>
    <banner title="Task List">
      <div slot="buttonGroup">
        <el-button class="my-button">Learn More</el-button>
        <el-button class="my-button">New Task</el-button>
      </div>
    </banner>
   <el-col :offset="4" :span="14">
     <task-list>

     </task-list>
   </el-col>
  </div>
</template>

<script>
  import Banner from "./Banner";
  import TaskList from "./TaskList";
  export default {
    name: "home",
    components: {TaskList, Banner}
  }
</script>

<style scoped>
  .my-button{
    margin-left: 40px;
    color: #1B9CFC;
    font-weight: bold;
  }

</style>
