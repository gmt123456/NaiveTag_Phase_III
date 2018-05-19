<template>
  <div>
    <div style="height: 100%;">
      <el-row style="height: 100%;display:flex;justify-content:center;align-items:center;" >
        <el-col :span="5" :offset="13">
          <el-card class="login-card">
            <el-col :span="22" :offset="1">
              <el-form>

                <el-form-item label="用户名">
                  <el-input clearable v-model="username"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                  <el-input clearable v-model="password" type="password"></el-input>
                </el-form-item>
                <br>
                <el-form-item>
                  <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
                </el-form-item>


              </el-form>
              <el-button class="signUp-button" type="text" @click="signUp">注册新用户</el-button>

            </el-col>
          </el-card>
        </el-col>
      </el-row>
    </div>
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
        username: '',
        password: ''
      }
    },
    methods: {
      login: function (event) {
        var router = this.$router;
        let result = login(this.username, this.password,
          res=> {
            console.log(res.result);
            if (res.result === 'success') {

              localStorage.username=this.username;
              switch (res.userType) {
                case 'admin':
                  router.push('/admin/home');
                  break;
                case 'worker':
                  router.push('/worker/home');
                  console.log('this is worker');
                  break;
                case 'requester':
                  router.push('/requester/Home');
                  console.log('this is requester');
                  break;
                default:
                  console.log('login error' + result.userType);
                  break;
              }
            }else {
              let message;
              switch (res.result){
                case  'invalid username':
                  message='用户名错误';
                  break;
                case  'invalid password':
                  message='密码错误';
                  break;
              }

              this.$alert(message,'',{
                confirmButtonText:'确定'
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
  }

  .signUp-button {
    float: right;
    padding-bottom: 25px;
  }
</style>
