<template>
  <el-collapse-item>
    <div slot="title" style="display: flex">
      <el-col :span="2" :offset="2">
        <img :src="getUrl(worker.avatar)" style="margin-top: 5px;height: 40px">

      </el-col>
      <el-col :span="17">
        <span style="font-weight: bold">{{worker.name}}</span>
      </el-col>
      <el-col :span="4">
        <division-pic :division="worker.division" size="50px"></division-pic>
      </el-col>
    </div>
    <div style="margin: 20px">
      <el-form size="medium" label-width="90px" :inline="true" label-position="left"  class="worker-details">
        <el-form-item label="email">{{worker.email}}</el-form-item>
        <el-form-item label="dollars">
          <span>{{worker.dollars}}  <modify-dollars :email="worker.email" style="padding-left: 30px"></modify-dollars> </span>
        </el-form-item>

        <el-form-item label="score">{{worker.score}}</el-form-item>
        <el-form-item label="Joined at">{{worker.signupTime}}</el-form-item>
        <modify-password :email="worker.email"></modify-password>

      </el-form>
    </div>

  </el-collapse-item>

</template>

<script>

  import DivisionPic from "../../worker/divisionPic";
  import ModifyDollars from "./ModifyDollars";
  import ModifyPassword from "./ModifyPassword";
  import {getUrl} from "../../../api/tool";
  export default {
    name: "WorkerListItem",
    components:{ModifyPassword, ModifyDollars, DivisionPic},
    props: ['worker'],
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
