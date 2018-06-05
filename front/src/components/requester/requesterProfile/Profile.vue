<template>
  <div >
    <div class="top-container">
      <div class="bottom-container" >
        <div class="top">
          <img :src="main.avatar" style="width: 170px"/>
          <div class="text-container" style="margin-left: 30px">
            <div  style="margin-top: 20px">
              <span style="font-weight: bold">{{main.name}}</span>
              <div style="margin-top: 10px">
                <span style="font-size: 14px">{{main.email}}</span>
              </div>
            </div>
            <div class="bottom-container" style="bottom: 10px">
              <span style="color: darkgray;">{{main.signMessage}}</span>
            </div>

          </div>

          <div style="">
            <div style=" margin-top: 30px; margin-left: 150px; max-width: 200px; float: left" >
              <img-with-label url="/static/requester/金币.svg" :label="main.dollars"></img-with-label>
              <el-button type="text" style="margin-left: 20px;margin-top: -5px">recharge</el-button>
            </div>
          </div>
        </div>

      </div>
    </div>

    <div  class="container" style="margin-top: 30px">
      <el-card>
        <div slot="header">
          <span>Account Change</span>
        </div>
        <div>
          <account-change-list></account-change-list>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
  import {getRequesterMain} from "../../../api/requesterDetail";
  import AccountChangeList from "./AccountChangeList";
  import ImgWithLabel from "../ImgWithLabel";

  export default {
    name: "profile",
    components: {ImgWithLabel, AccountChangeList},
    data: function () {
      return {
        main: '',
      }
    },
    created: function () {
      getRequesterMain(localStorage.token, res => {
        this.main = res;
      })
    }
  }
</script>

<style scoped>

  .container {
    width: 900px;
    margin:auto
  }

  .top{
    height: 170px;
    display: flex;
    width: 900px;
    background-color: #f6f9fa;
    margin: auto;
  }


  .top-container{
    background-size: cover;
    height: 200px;
    position: relative;

    background-image: url("/static/background/bg3.jpg");
  }

  .text-container {
    height: 170px;
    position: relative;
    min-width: 300px;
  }

  .bottom-container {
    width: 100%;
    position: absolute;
    bottom: 0;
  }

</style>
