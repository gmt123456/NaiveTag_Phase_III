<template>
  <el-collapse-item >
    <div slot="title" style="display: flex">
      <el-col :span="2" :offset="2">
        <img :src="getUrl(requester.avatar)" style="margin-top: 5px;height: 40px">

      </el-col>
      <el-col :span="13">
        <span style="font-weight: bold">{{requester.name}}</span>
      </el-col>
      <el-col :span="6">
        <div style="float: right">
          <span ><b>{{requester.tasksNum}} </b>task(s) have been released</span>
        </div>
      </el-col>
    </div>
    <div style="margin: 20px">
      <el-form size="medium" label-width="90px" :inline="true" label-position="left"  class="worker-details">
        <el-form-item label="email">{{requester.email}}</el-form-item>
        <el-form-item label="dollars">
          <span>{{requester.dollars}}   <modify-dollars :email="requester.email" style="margin-left: 30px"></modify-dollars> </span>
        </el-form-item>
        <el-form-item label="Joined at">{{requester.signupTime}}</el-form-item>
        <modify-password :email="requester.email"></modify-password>
      </el-form>
    </div>

  </el-collapse-item>
</template>

<script>
  import ModifyDollars from "./ModifyDollars";
  import ModifyPassword from "./ModifyPassword";
  import {getUrl} from "../../../api/tool";
  export default {
    name: "RequesterListItem",
    components: {ModifyPassword, ModifyDollars},
    props: ['requester'],
    methods:{
      getUrl(url){
        return getUrl(url);
      }
    }
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
