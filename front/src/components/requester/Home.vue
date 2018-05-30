<template>
  <div>

   <div style="min-width: 900px; max-width: 70%; margin: auto">
     <banner title="Task List">
       <div slot="buttonGroup">
         <el-button class="my-button" >Learn More</el-button>
         <el-button class="my-button" @click="createTaskVisible=true">New Task</el-button>
       </div>
     </banner>
     <task-list>

     </task-list>
   </div >

    <el-dialog title="Create Task" :visible.sync="createTaskVisible"
      @close="initTaskForm"
    width="865px">

        <CreateTask ref="createTask"></CreateTask>

    </el-dialog>
  </div>
</template>

<script>
  import Banner from "./Banner";
  import TaskList from "./TaskList";
  import CreateTask from "./CreateTask";
  export default {
    name: "home",
    components: {CreateTask, TaskList, Banner},
    data:function () {
      return{
        createTaskVisible:false,
      }
    },
    methods:{
      initTaskForm:function () {
        this.$refs['createTask'].resetForm();

      }
    }
  }
</script>

<style scoped>
  .my-button{
    margin-left: 40px;
    color: #1B9CFC;
    font-weight: bold;
  }



</style>
