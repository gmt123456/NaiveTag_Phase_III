<template>
  <el-collapse-item style="width: 1000px">
    <div slot="title" style="display: flex">
      <el-col :span="2" :offset="2">
        <img :src="requester.avatar" style="margin-top: 5px;height: 40px">

      </el-col>
      <el-col :span="17">
        <span style="font-weight: bold">{{requester.name}}</span>
      </el-col>
      <el-col :span="4">
        <!--<division-pic :division="worker.division" size="50px"></division-pic>-->
      </el-col>
    </div>
    <div style="margin: 20px">
      <el-form size="medium" label-width="90px" :inline="true" label-position="left"  class="worker-details">
        <el-form-item label="email">{{requester.email}}</el-form-item>
        <el-form-item label="dollars">
          <span>{{requester.dollars}}   <el-button type="text" style="margin-left: 20px;padding: 0">modify</el-button></span>
        </el-form-item>

        <!--<el-form-item label="score">{{worker.score}}</el-form-item>-->
        <el-form-item label="Joined at">{{requester.signupTime}}</el-form-item>

      </el-form>
    </div>

  </el-collapse-item>
</template>

<script>
  export default {
    name: "RequesterListItem",
    props: ['requester']
  }
</script>

<style scoped>
  .worker-details{
    width: 600px;
    margin: auto;
  }

  .worker-details .label{
    font-weight: bolder;
  }

  .worker-details .el-form-item{
    margin-right: 0;
    margin-bottom: 0;
    width: 49%;
  }

</style>
