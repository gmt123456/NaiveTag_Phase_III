<template>
    <div class="login-container">
      <el-form label-position="left" label-width="100px" size="medium">

        <el-form-item label="email">
          <el-input clearable v-model="email"></el-input>
        </el-form-item>
        <el-form-item label="password">
          <el-input clearable v-model="password" type="password"></el-input>
        </el-form-item>
      </el-form>
      <el-button style="width: 100%" type="primary" @click="login">Sign in</el-button>
    </div>
</template>

<script>
    import {insiderLogin} from "../../api/login";

    export default {
        name: "InsiderLogin",
      data:function () {
        return {
          email:'',
          password:''
        }
      },
      methods:{
          login(){
            insiderLogin(this.email,this.password,res=>{
              console.log(res.status);
              if (res.status==='success'){
                if (res.userType==='admin'){
                  this.$router.push('/makeNaiveTagGreatAgain/worker');
                } else {
                  this.$router.push('/staff/allCheck')
                }
                localStorage.token=res.token;
              } else {
                this.$message(res.message);
              }
            })
          }
      }
    }
</script>

<style scoped>
.login-container{
  width: 300px;
  margin: auto;
  padding-top: 200px;
}
</style>
