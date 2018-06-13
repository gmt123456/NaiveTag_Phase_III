<template>
  <div class="navi">
    <el-row>
      <el-col :span="2" :offset="4">
        <div class="center">
          <logo></logo>
        </div>
      </el-col>
      <el-col :span="8" :offset="4" >
        <slot name="naviMenu">
        </slot>
      </el-col>
      <el-col :span="4">
        <el-dropdown class="center">
          <img :src=this.avatarUrl class="avatar">
          <el-dropdown-menu slot="dropdown">
            <slot name="dropdownMenu"></slot>
            <el-dropdown-item>
              <el-button type="text" @click="logout">log out</el-button>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import Logo from "./Logo";
  import {getAvatar} from "../../api/getAvatar";

  export default {
    name: "Navi",
    components: {Logo},
    data: function () {
      return {
        avatarUrl: ''
      }
    },
    created: function () {
      this.getAvatar();
    },
    methods: {
      getAvatar() {
        getAvatar('', res => {
          this.avatarUrl = res;
        })
      },
      logout() {
        this.$router.push('/');
      }
    }
  }
</script>

<style scoped>


  .center {
    padding-top: 15px;
  }

  .navi {
    background-color: #47494d;
    min-height: 50px;
    min-width: 1000px;
  }

  .avatar {
    height: 40px;
    width: 40px;
  }
</style>
