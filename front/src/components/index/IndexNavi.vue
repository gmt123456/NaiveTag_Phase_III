<template>
  <div class="navi">
    <div class="button-group">
      <el-button type="text" @click="jumpToIndex" class="text-button">sign in</el-button>
      <span class="navi-text"> or </span>
      <el-button type="text" @click="jumpToSignUp" class="text-button">sign up</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "IndexNavi",
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
    color: white;
    font-size: 20px;
  }
  .navi-text{
    color: black;
    margin: 0px 5px 0px 5px;
    font-size: 16px;
  }

  .button-group {
    float: right;
    padding-top: 8px;
    padding-right: 200px;
  }

  .navi {
    background-color: #47494d;
    min-height: 60px;
  }

</style>
