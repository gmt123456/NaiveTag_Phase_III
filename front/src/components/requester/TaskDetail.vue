<template>
  <div style="min-width: 900px; max-width: 70%; margin: auto">
      <div class="top" :style="{'background-image':'url('+taskSketch.backgroundImage+')'}">
        <el-col :span="13" :offset="1">
          <div class="text-box">
            <h3>{{taskSketch.title}}</h3>
            <span>{{taskSketch.description}}</span>
          </div>
        </el-col>
      </div>
      <el-card class="tag-box">
        <el-aside>
          <span>Tags</span>
        </el-aside>
        <div class="tags">
          <el-tag type="info" v-for="(item,key) in taskSketch.tags"  :key="key" class="tag" >{{item}}</el-tag>
        </div>
      </el-card>

      <el-tabs type="card" class="tabs">
        <el-tab-pane label="Overview">
        <div class="download-group">

            <el-progress :percentage="100" class="progress" ></el-progress>


            <el-button style="float: left">Download</el-button>

        </div>
        </el-tab-pane>
        <el-tab-pane label="Detail">
        </el-tab-pane>
      </el-tabs>

  </div>

</template>

<script>
  import {getTaskSketch} from "../../api/getTaskDetail";

  export default {
    name: "TaskDetail",
    data: function () {
      return {
        taskId: '',
        taskSketch: '',
        backgroundImage: '',
        list:['asd','ad','fds']
      }
    },
    created: function () {
      this.taskId = this.$route.params.taskId;
      getTaskSketch(this.taskId, res => {
        this.taskSketch = res;
        console.log('here');
        console.log(res.backgroundImage);
      })
    }
  }
</script>

<style scoped>
  .top {
    min-width: 800px;
    min-height: 200px;
    margin-top: 20px;
  }

  .menu-box {

  }
  .progress{
    width: 200px;
  }
  .download-group{
   display: flex;
    float: right;

  }

  .tag-box {
    margin-top: 20px;
    min-height: 40px;
  }

  .tags{
    margin-left: 100px;
    margin-top: -35px;

  }
  .tag{
    margin-left: 10px;
    margin-top: 10px;
  }

  .tabs{
    margin-top: 30px;
  }

  .text-box {
    color: white;

  }

</style>
