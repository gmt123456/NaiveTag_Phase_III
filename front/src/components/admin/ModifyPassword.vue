<template>
  <div>
    <el-button type="text">Change Password</el-button>
  </div>
</template>

<script>
  import {changePassword} from "../../api/adminUsers";

  export default {
    name: "ModifyPassword",
    props: ['email'],
    methods: {
      change() {
        this.$prompt('Please input new password', 'Change Password', {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',
        }).then(({value}) => {
          changePassword(this.email, value, res => {
            if (res.status === 'success') {
              this.$message({
                type: 'success',
                message: 'Change Password successfully'
              });
            } else {
              this.$message.error(res.message);
            }
          })

        }).catch(() => {

        });
      }
    }
  }
</script>

<style scoped>

</style>
