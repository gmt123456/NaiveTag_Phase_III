<template>
  <div>
    <div class="top-container">
      <div class="bottom-container" style="width: 100%">
        <div class="profile-top">
          <img :src="getUrl(main.avatar)" style="width: 170px"/>
          <div class="text-container" style="margin-left: 30px">
            <div style="margin-top: 20px">
              <span style="font-weight: bold">{{main.name}} <el-button type="text" style="padding: 0;margin-left: 20px"
                                                                       @click="changeName">Change</el-button> </span>
              <div style="margin-top: 10px">
                <span style="font-size: 14px">{{main.email}}</span>
              </div>
              <el-button type="text" @click="changePasswordVisible=true">Change Password</el-button>
            </div>
            <div class="bottom-container" style="bottom: 10px">
              <span style="color: darkgray;">{{main.signMessage}}</span>
            </div>

          </div>

          <div style="">
            <div style=" margin-top: 30px; margin-left: 150px; max-width: 200px; float: left">
              <img-with-label url="/static/requester/金币.svg" :label="main.dollars"></img-with-label>
              <el-button type="text" style="margin-left: 20px;margin-top: -5px" @click="recharge">Recharge</el-button>

            </div>
          </div>
        </div>
      </div>

    </div>

    <div class="container" style="margin-top: 30px">
      <el-card>
        <div slot="header">
          <span>Account Change</span>
        </div>
        <div>
          <account-change-list ref="accountList"></account-change-list>
        </div>
      </el-card>
    </div>

    <el-dialog :visible.sync="changePasswordVisible" @close="resetForm" title="Change Password" width="400px">
      <div>
        <el-form ref="passwordForm" :rules="rule" :model="passWordForm" label-width="150px" label-position="left">
          <el-form-item label="Old Password" prop="oldPassword">
            <el-input type="password" v-model="passWordForm.oldPassword"></el-input>
          </el-form-item>
          <el-form-item label="New Password" prop="newPassword">
            <el-input type="password" v-model="passWordForm.newPassword"></el-input>
          </el-form-item>
          <el-form-item label="Confirm Password" prop="checkPassword">
            <el-input type="password" v-model="passWordForm.checkPassword"></el-input>
          </el-form-item>
        </el-form>
        <el-button type="primary" style="width: 100%;" @click="changePassword">Submit</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import {changeName, getRequesterMain, recharge, changePassword} from "../../../api/requesterDetail";
  import AccountChangeList from "./AccountChangeList";
  import ImgWithLabel from "../ImgWithLabel";
  import {getUrl} from "../../../api/tool";

  export default {
    name: "profile",
    components: {ImgWithLabel, AccountChangeList},
    data: function () {


      const confirmPassword = (rule, value, callback) => {
        if (value !== this.passWordForm.newPassword) {
          callback(new Error("Password doesn't match！"));
        } else {
          callback();
        }
      };

      return {
        main: '',
        changePasswordVisible: false,
        passWordForm: {
          oldPassword: '',
          newPassword: '',
          checkPassword: ''
        },
        rule: {
          oldPassword: [
            {required: true, message: 'Please input your  old password', trigger: 'blur'},
            {min: 8, message: 'Use at least 8 characters', trigger: 'blur'}
          ],
          checkPassword: [
            {validator: confirmPassword, trigger: 'blur'},
            {required: true, message: 'Please confirm your password', trigger: 'blur'}
          ],
          newPassword: [
            {required: true, message: 'Please input your password', trigger: 'blur'},
            {min: 8, message: 'Use at least 8 characters', trigger: 'blur'}
          ]
        }

      }
    },
    methods: {
      getUrl(url) {
        return getUrl(url);
      },
      resetForm() {
        this.passWordForm = {
          oldPassword: '',
          newPassword: '',
          checkPassword: ''
        };
        this.$refs.passwordForm.clearValidate();
      },
      callback(res, message) {
        if (res.status === 'success') {
          this.$message({
            type: 'success',
            message: message
          });
          this.refresh();
        } else {
          this.$message.error(res.message);
        }
      },


      recharge() {
        this.$prompt('Please input the dollars', 'Recharge', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          inputPattern: /^[0-9]+(.[0-9]{2})?$/,
          inputErrorMessage: 'The dollars should be a nonnegative value'
        }).then(({value}) => {
          recharge(value, res => {
            this.callback(res, 'Recharge successfully!');
            this.$refs.accountList.refresh();
          })
        });
      },
      changeName() {
        this.$prompt('Please input new name', 'Change Name', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
        }).then(({value}) => {
          changeName(value, res => {
            this.callback(res, 'Change name successfully!')
          })
        });
      },
      changePassword() {
        this.$refs.passwordForm.validate((valid) => {
          if (valid) {
            changePassword(this.passWordForm.oldPassword, this.passWordForm.newPassword, res => {
              this.callback(res, 'Change password successfully!');
              if (res.status === 'success') {
                this.changePasswordVisible = false;
                this.refresh();
              }
            })
          }
        })
      },
      refresh() {
        getRequesterMain(localStorage.token, res => {
          this.main = res;
        })
      }
    },
    created: function () {
      this.refresh();
    }
  }
</script>

<style scoped>

  .container {
    width: 900px;
    margin: auto
  }

  .profile-top {
    height: 170px;
    display: flex;
    width: 900px;
    background-color: #f6f9fa;
    margin: auto;
  }

  .top-container {
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

    position: absolute;
    bottom: 0;
  }

</style>
