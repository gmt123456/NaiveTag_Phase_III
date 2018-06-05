<template>
  <el-button type="text" @click="change">Change Dollars</el-button>
</template>

<script>
  import {changeDollars} from "../../api/adminUsers";

  export default {
    name: "ModifyDollars",
    props: ['email'],
    methods: {
      change() {
        this.$prompt('Please input dollars', 'Change Password', {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',
          inputPattern: /^[0-9]+(.[0-9]{2})?$/,
          inputErrorMessage:'The dollars should be a nonnegative value'
        }).then(({value}) => {
          changeDollars(this.email, value, res => {
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
