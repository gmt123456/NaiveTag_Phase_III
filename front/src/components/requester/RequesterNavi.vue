<template>
  <navi>
    <div slot="naviMenu">
      <el-col :offset="4" :span="5" >
        <el-menu mode="horizontal" class="el-menu-demo"
                 default-active="/requester/home"
                 background-color=" #47494d"
                 text-color="gray"
                 active-text-color="white"
                 :router="true"
                 style="border-bottom: 0" >
          <el-menu-item index="/requester/home">Tasks</el-menu-item>
        </el-menu>
      </el-col>
    </div>
    <div slot="dropdownMenu">
      <el-dropdown-item>
        <el-button type="text" @click="jumpToProfile">my profile</el-button>
      </el-dropdown-item>
    </div>
  </navi>
</template>

<script>
    import Navi from "../common/Navi";
    export default {
        name: "requesterNavi",
      components: {Navi},
      methods:{
          jumpToProfile:function () {
            this.$router.push('/requester/profile')
          }
      }
    }
</script>

<style scoped>

</style>
