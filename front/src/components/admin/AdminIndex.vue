<template>
  <div style="width: 100%;display: flex">
    <div>
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
        </el-submenu>
        <el-submenu index="2">
          <span slot="title">Analysis</span>
          <el-menu-item index="/makeNaiveTagGreatAgain/activeUser">Active User</el-menu-item>
          <el-menu-item index="/makeNaiveTagGreatAgain/totalUser">Enrollment</el-menu-item>
          <el-menu-item index="/makeNaiveTagGreatAgain/task">Task</el-menu-item>
        </el-submenu>
        <div style="position: absolute;bottom: 10px;width: 200px">
          <img src="/static/logout.png" class="admin-logout" @click="logout"/>
        </div>
        <el-button class="menu-button" @click="function() {dialogShow=true;addAdminVisible=true;}">Add Admin
        </el-button>
        <el-button class="menu-button" @click="function() {dialogShow=true; addStuffVisible=true;}">Add Stuff
        </el-button>
      </el-menu>

    </div>

    <el-dialog :visible.sync="dialogShow" width="450px" @close="reset">
      <span v-if="addAdminVisible" slot="title">Create An Administrator Account</span>
      <span v-else slot="title">Create An Stuff Account</span>
      <el-form :model="addForm" :rules="rule" ref="addForm" label-width="100px" label-position="left">
        <el-form-item v-if="addAdminVisible" label="username" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item v-else label="email" prop="email">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="password" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" style="width: 100%" @click="handleAdd">confirm</el-button>
    </el-dialog>


    <div style="margin-top: 20px;width: 100%;display: inline">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import {addAdmin, addStuff} from "../../api/adminUsers";

  export default {
    name: "AdminIndex",

    data: function () {

      const validateEmail = (rule, value, callback) => {
        const reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if (this.addAdminVisible) {
          if (this.addForm.email === '') {
            callback(new Error('Please input username'))
          } else {
            callback();
          }
        }
        else if (reg.test(this.addForm.username)) {
          callback();
        } else {
          callback(new Error('Invalid email!'));
        }
      };


      return {
        addStuffVisible: false,
        addAdminVisible: false,
        dialogShow: false,
        addForm: {
          username: '',
          password: '',
        },
        rule: {
          email: [
            {validator: validateEmail, trigger: 'blur'},
          ],
          username: [
            {required: true, message: 'Please input your username', trigger: 'blur'},
          ],
          password: [
            {required: true, message: 'Please input your password', trigger: 'blur'},
            {min: 8, message: 'Use at least 8 characters', trigger: 'blur'}
          ],
        },

      }
    },
    methods: {

      reset() {
        this.addStuffVisible = false;
        this.addAdminVisible = false;
        this.addForm.username = '';
        this.addForm.password = '';
        this.$refs['addForm'].clearValidate();
      },
      logout() {
        this.$router.push('/makeNaiveTagGreat');
        this.localStorage.token = null;
      },
      handleAdd() {
        this.$refs['addForm'].validate((valid) => {
          if (this.addStuffVisible) {
            addStuff(this.username,this.password,res=>{
              if (res.status==='success'){
                this.dialogShow=false;
                this.$message({
                  type:'success',
                  message:'Add stuff successfully'
                })
              } else {
                this.$message.error(res.message);
              }
            });
          } else if (this.addAdminVisible) {
            addAdmin(this.username,this.password,res=>{
              if (res.status==='success'){
                this.dialogShow=false;
                this.$message({
                  type:'success',
                  message:'Add admin successfully'
                })
              } else {
                this.$message.error(res.message);
              }
            });
          }
        })
      }
    }
  }
</script>

<style scoped>
  .admin-logout {
    width: 30px;
    margin-left: 80px;
    cursor: pointer;
  }

  .menu-button {
    background-color: transparent;
    border-width: 0;
    color: white;
    width: 100%;
    text-align: left;
    font-size: 14px;
    height: 56px;
    margin: 0;

  }

</style>
