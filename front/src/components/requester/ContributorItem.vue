<template>
  <div>
    <el-col :span="4" :offset="1">
      <img style="width: 60px; height: 60px" :src="getUrl(contributor.avatar)">

    </el-col>
    <el-col :span="6">
      <div class="username">
        <span>{{contributor.name}}</span>
      </div>
    </el-col>
    <el-col :offset="5" :span="5">
      <division-pic :division="contributor.level" size="60px"></division-pic>
    </el-col>
    <el-col :span="3">
      <img-with-label url="/static/requester/pictures.svg" :label="contributor.pictureNum"
                      style="margin-top: 20px"></img-with-label>
    </el-col>
  </div>
</template>

<script>
  import DivisionPic from "../worker/divisionPic";
  import ImgWithLabel from "./ImgWithLabel";
  import {getUrl} from "../../api/tool";
  export default {
    name: "ContributorItem",
    components: {ImgWithLabel,DivisionPic},
    props:['contributor'],
    methods:{
      getUrl(url){
        return getUrl(url);
      },
    }
  }
</script>

<style scoped>
  .username {
    margin-top: 20px;
    /*font-weight: bold;*/
    color: gray;
  }

</style>
