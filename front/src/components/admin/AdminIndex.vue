<template>
  <div style="height: 100%;display: flex">
    <el-menu default-active="/makeNaiveTagGreatAgain/workers"
             background-color="#545c64"
             text-color="#fff"
             active-text-color="#ffd04b"
             :router="true"
             style="width: 200px;height: calc(100vh)">
      <el-submenu index="0">
        <span slot="title">User</span>
        <el-menu-item index="/makeNaiveTagGreatAgain/workers">Worker</el-menu-item>
        <el-menu-item index="/makeNaiveTagGreatAgain/requesters">Requester</el-menu-item>
        <el-menu-item index="/">Admin</el-menu-item>
      </el-submenu>
      <el-menu-item index="2">Analysis</el-menu-item>
    </el-menu>
    <div style="margin-top: 20px;margin-left: 20px">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  export default {
    name: "AdminIndex"
  }
</script>

<style scoped>

</style>
