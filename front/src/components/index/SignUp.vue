<template>
  <div>
    <div style="min-width: 400px !important;max-width: 35%; margin: auto">
      <!--<div class="top">-->
        <!--<h3> Create your personal account</h3>-->
      <!--</div>-->

      <el-form :model="signUpForm" :rules="rules" ref="signUpForm" status-icon label-width="160px"
               label-position="left">
        <el-form-item label="Name" prop="nickname">
          <el-input v-model="signUpForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="signUpForm.email"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="signUpForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="Confirm Password" prop="checkPass">
          <el-input v-model="signUpForm.checkPass" type="password"></el-input>
        </el-form-item>
        <el-form-item label="User Type">
          <el-radio-group v-model="signUpForm.userType">
            <el-radio label="worker">Worker</el-radio>
            <el-radio label="requester">Requester</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>

      <el-button @click="signUp" style="width: 100%" type="primary">Sign Up</el-button>
    </div>

  </div>
</template>

<script>
  import {signUp} from "../../api/signUp";
  import IndexNavi from "./IndexNavi";

  export default {
    name: "signUp",
    components: {IndexNavi},
    data: function () {
      const confirmPassword = (rule, value, callback) => {
        if (value !== this.signUpForm.password) {
          callback(new Error("Password doesn't match！"));
        } else {
          callback();
        }
      };
      const validateEmail = (rule, value, callback) => {
        const reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if (reg.test(value)) {
          callback();
        } else {
          callback(new Error('Invalid email!'));
        }
      };


      return {
        signUpForm: {
          username: '',
          email: '',
          password: '',
          checkPass: '',
          nickname: '',
          userType: 'worker'
        },
        rules: {
          checkPass: [
            {validator: confirmPassword, trigger: 'blur'},
            {required: true, message: 'Please confirm your password', trigger: 'blur'}
          ],
          email: [
            {validator: validateEmail, trigger: 'blur'},
            {required: true, message: 'Pleas input your email', trigger: 'blur'}
          ],
          password: [
            {required: true, message: 'Pleas input your password', trigger: 'blur'},
            {min: 8, message: 'Use at least 8 characters', trigger: 'blur'}
          ],
          nickname: [
            {required: true, message: 'Pleas input your name', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      fastSignUp() {
        let form = this.signUpForm;
        form.userType = 'worker';
        form.password = '123456789';
        form.checkPass = '123456789';
        form.email = '111@qq.com';
        form.nickname = 'naive';
        form.username = '1';
      },
      signUp() {
        let that = this;
        this.$refs['signUpForm'].validate((valid) => {
          if (valid) {
            signUp(this.signUpForm,
              res => {
                if (res.status === 'success') {
                  localStorage.token = res.token;
                  if (this.signUpForm.userType === 'worker') {
                    that.$router.push('/worker/home');
                  } else {
                    that.$router.push('/requester/Home');
                  }

                } else {
                  let message;
                  switch (res.result) {
                    case 'repeatedEmail':
                      message = 'email is taken';
                      break
                  }
                  this.$alert(message, '', {
                    confirmButtonText: 'confirm'
                  })
                }
              });
          } else {

          }
        })
      }
    }
  }
</script>

<style scoped>

  .top {
    margin-bottom: 30px;
  }
</style>
