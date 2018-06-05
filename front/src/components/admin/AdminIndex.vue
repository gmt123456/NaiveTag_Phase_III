<template>
  <div style="width: 100%;display: flex">
    <div >
      <el-menu default-active="/makeNaiveTagGreatAgain/workers"
               background-color="#545c64"
               text-color="#fff"
               active-text-color="#ffd04b"
               :router="true"
               style="width: 200px;height: calc(100vh);position: relative;">
        <el-submenu index="0">
          <span slot="title">User</span>
          <el-menu-item index="/makeNaiveTagGreatAgain/worker">Worker</el-menu-item>
          <el-menu-item index="/makeNaiveTagGreatAgain/requester">Requester</el-menu-item>
          <el-menu-item index="/makeNaiveTagGreatAgain/admin">Admin</el-menu-item>
          <el-menu-item index="/makeNaiveTagGreatAgain/stuff">Stuff</el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <span slot="title">Analysis</span>
          <el-menu-item index="/makeNaiveTagGreatAgain/activeUser">Active User</el-menu-item>
          <el-menu-item index="/makeNaiveTagGreatAgain/totalUser">Total User</el-menu-item>
          <el-menu-item index="/makeNaiveTagGreatAgain/task">Task</el-menu-item>
        </el-submenu>
        <div style="position: absolute;bottom: 10px;width: 200px">
          <img src="/static/logout.png" style="width: 30px;margin-left:80px"/>
        </div>
      </el-menu>
    </div>

     <div style="margin-top: 20px;width: 100%;display: inline">
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
