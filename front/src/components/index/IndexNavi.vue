<template>
  <div>
      <el-col :span="24"><div style="background-color: #47494d;height: 50px;justify-content:center;overflow: hidden;" class="center">
          <div style="width: 900px;" class="center">
              <logo style="display: inline"></logo>
              <div style="width: 100%;text-align: right;">
                  <el-button type="text" @click="jumpToIndex" class="text-button">Sign in</el-button>
                  <span class="navi-text"> or </span>
                  <el-button type="text" @click="jumpToSignUp" class="text-button">Sign up</el-button>
              </div>
          </div>
      </div></el-col>
  </div>
</template>

<script>
  import Logo from "../common/Logo";
  export default {
    name: "IndexNavi",
    components: {Logo},
    methods: {
      jumpToSignUp: function () {
        this.$router.push('/signUp')
      },
      jumpToIndex: function () {
        this.$router.push('/')
      }
    }
  }
</script>

<style scoped>
  .text-button {
      color: lightgrey;
    font-size: 16px;
  }
  .navi-text{
    color: black;
    margin: 0px 5px 0px 5px;
    font-size: 16px;
  }

  /*.button-group {*/
    /*float: right;*/
    /*padding-top: 8px;*/
    /*padding-right: 200px;*/
  /*}*/

  .center {
      display: flex;
      /*justify-content:center;*/
      align-items: center;
  }
</style>
