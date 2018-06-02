<template>
  <div >
      <div>
          <el-col :span="24"><div style="background-color: #47494d;height: 50px;justify-content:center;overflow: hidden;" class="center">
              <div style="width: 900px;" class="center">
                  <logo style="display: inline"></logo>
                  <div style="width: 100%;text-align: right;">
                      <el-button type="text" @click="signIn" class="text-button">Sign in</el-button>
                      <span class="navi-text"> or </span>
                      <el-button type="text" @click="signUp" class="text-button">Sign up</el-button>
                  </div>
              </div>
          </div></el-col>
      </div>
    <Login ref="login"></Login>
    <Introduction></Introduction>

      <div style="height: 300px;width: 100%;background-color: #47494d;text-align: center;">
          <div style="color: white;font-weight: lighter;margin: auto;padding-top: 60px">Come and join us!</div>
          <div style="width: 600px;margin: auto;">
              <el-button type="primary" style="width: 280px;height: 50px;font-size: 18px;margin-top: 30px;" @click="signUp">Create an account</el-button>
              <div></div>
              <el-button type="primary" plain style="width: 280px;height: 50px;font-size: 18px;margin: 20px;" @click="signIn">Sign in</el-button>
          </div>
      </div>
      <div style="height: 100px;width: 100%;background-color: white;text-align: center;" class="center">
          <div style="color: grey;font-size: 15px;margin: auto;">@ 2018 NaiveTag</div>
          <div style="color: grey;font-size: 13px;float: right;text-align: left;padding-right: 200px;">@ LiaoJunda  @ Iznauy  @ ChenJunyu  @ GaoMengting</div>
      </div>
  </div>
</template>

<script>
  import Login from "./Login";
  import Introduction from "./Introduction";
  import IndexNavi from "./IndexNavi";
  import Logo from "../common/Logo";

  export default {
    name: "index",
    components: {IndexNavi, Introduction, Login, Logo},
      methods: {
	      signIn(){
	      	this.$refs.login.signIn();
          },
	      signUp(){
	      	this.$refs.login.signUp();
          },
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
