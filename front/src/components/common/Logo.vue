<template>
  <div @click="goToHome">
    <span class="logo">NaiveTag</span>
  </div>
</template>

<script>
    export default {
        name: "Logo",
      methods:{
          goToHome:function () {
            this.$router.push("/");
          }
      }
    }
</script>

<style scoped>
  .logo{
    color:#1B9CFC;
    font-size: 24px;
    margin: 5px 0px 0px ;
    cursor:pointer;
  }

</style>
