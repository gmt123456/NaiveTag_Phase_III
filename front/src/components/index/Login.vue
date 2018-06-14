<template>
  <div style="height: 800px;background-color: #2b2b2b;text-align: center;">
      <div style="width: 100%;text-align: center;padding-top: 140px;">
          <div style="width: 500px;color: white;font-size: 36px;font-weight: bold;margin: auto;">
              The Platform of Data Crowdsourcing Labeling
          </div>
          <div style="width: 500px;color: #c0c0c0;font-size: 18px;margin: auto;padding-top: 10px;">
              NaiveTag helps you work, earn, and label
          </div>
          <div style="width: 1000px;margin: auto;padding-top: 60px;">
              <el-carousel :interval="4000" type="card" height="250px" trigger="click">
                  <el-carousel-item v-for="item in 5" :key="item">
                      <img :src="'../../../static/show/'+item+'.png'" width="500px" style="background-position: -50px 0px;"/>
                  </el-carousel-item>
              </el-carousel>
          </div>
          <div style="width: 600px;margin: auto;padding-top: 60px">
              <el-button type="primary" style="width: 250px;height: 50px;font-size: 18px;" @click="signUpVisible = true">Create an account</el-button>
              <span style="color: white">or</span>
              <el-button type="primary" plain style="width: 250px;height: 50px;font-size: 18px;" @click="signInVisible = true">Sign in</el-button>
          </div>
      </div>

      <el-dialog
              title="Sign in"
              :visible.sync="signInVisible"
              style="width: 700px;margin: auto;">
          <div style="height: 340px;">
              <el-col :span="24">
                  <el-form>

                      <el-form-item label="email">
                          <el-input clearable v-model="email"></el-input>
                      </el-form-item>
                      <el-form-item label="password">
                          <el-input clearable v-model="password" type="password"></el-input>
                      </el-form-item>
                      <el-form-item label="Sign in as ">
                          <el-radio-group v-model="userType">
                              <el-radio label="worker">Worker</el-radio>
                              <el-radio label="requester">Requester</el-radio>
                          </el-radio-group>
                      </el-form-item>
                      <el-form-item>
                          <el-button style="width: 100%" type="primary" @click="login">Sign in</el-button>
                      </el-form-item>


                  </el-form>
                  <el-button class="signUp-button" type="text" @click="signUp">Create an account</el-button>

              </el-col>
          </div>
          <span slot="footer">
          </span>
      </el-dialog>

      <el-dialog
              title="Create your personal account"
              :visible.sync="signUpVisible"
              style="width: 900px;margin: auto;">
          <div style="height: 340px;">
              <sign-up></sign-up>
          </div>
          <span slot="footer">
          </span>
      </el-dialog>

  </div>
</template>

<script>
  import {login} from '../../api/login.js'
  import SignUp from './SignUp.vue';
  import Introduction from "./Introduction";
  import IndexNavi from "./IndexNavi";

  export default {
    name: "login",
    components: {SignUp, IndexNavi, Introduction},
    data: function () {
      return {
        email: '',
        password: '',
        userType: 'worker',
	      signInVisible: false,
	      signUpVisible: false,
      }
    },
    methods: {
      login: function (event) {
        var router = this.$router;
        console.log(this.email);
        console.log(this.userType);
        login(this.email, this.password, this.userType,
          res => {
            if (res.status === 'success') {
              localStorage.token = res.token;
              if (this.userType === 'worker') {
                router.push('/worker/home');
              } else {
                router.push('/requester/home');
              }
            } else {
              this.$alert(res.message, '', {
                confirmButtonText: 'confirm'
              })

            }
          });

      },
      signUp() {
      	this.signInVisible = false;
      	this.signUpVisible = true;
        // this.$router.push('/signUp')
      },
	    signIn(){
		    this.signInVisible = true;
		    this.signUpVisible = false;
	    },
    },

      components: {
	      SignUp,
      }
  }
</script>

<style scoped>

  .container{
    background-size: cover;
    height: 600px;
  }

  .signUp-button {
    float: right;
    padding-bottom: 25px;
  }
  .center {
      display: flex;
      /*justify-content:center;*/
      align-items: center;
  }
</style>
