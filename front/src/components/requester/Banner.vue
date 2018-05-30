<template>
    <div class="banner-box">
      <el-col :offset="1" :span="6">
        <div class="title-box">
          <span class="title">{{this.title}}</span>
        </div>
      </el-col>
      <el-col :offset="11" :span="4">
        <div class="button-group">
          <slot name="buttonGroup">

          </slot>
        </div>
      </el-col>
    </div>
</template>

<script>
    export default {
        name: "banner",
      props:['title']
    }
</script>

<style scoped>
  .title{
    color: white;
    font-size: 24px;
  }

  .button-group{
    margin-top: 70px;
    float: right;
    min-width: 350px;
    margin-right: -35px;
  }
  .title-box{
    padding-top: 70px;
    font-weight: bolder;
  }
.banner-box{
  background-image: url("/static/background/banner_background.png");
  background-size: cover;
  height: 200px;
  margin-top: 20px;
  /*width: 70%;*/
  /*margin-left: 15%;*/
}
</style>
