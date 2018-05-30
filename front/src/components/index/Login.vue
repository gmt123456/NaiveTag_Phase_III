<template>
  <div class="container" >
    <el-card class="login-card">
      <el-col :span="22" :offset="1">
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
    </el-card>

  </div>
</template>

<script>
  import {login} from '../../api/login.js'
  import Introduction from "./Introduction";
  import IndexNavi from "./IndexNavi";

  export default {
    name: "login",
    components: {IndexNavi, Introduction},
    data: function () {
      return {
        email: '',
        password: '',
        userType: 'worker'
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
                router.push('/requester/Home');
              }
            } else {
              this.$alert(res.message, '', {
                confirmButtonText: '确定'
              })

            }
          });

      },
      signUp() {
        this.$router.push('/signUp')
      }
    }
  }
</script>

<style scoped>
  .login-card {
    background-color: rgba(255, 255, 255, 0.6);
    border-width: 0;
    width: 350px;

    float: right;
    margin-right: 10%;
    margin-top: 50px;

  }

  .container{
    background-size: cover;
    height: 600px;
  }

  .signUp-button {
    float: right;
    padding-bottom: 25px;
  }
</style>
