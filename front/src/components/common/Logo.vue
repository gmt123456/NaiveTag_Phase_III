<template>
  <div @click="goToHome">
    <span class="logo">NaiveTag</span>
  </div>
</template>

<script>
  export default {
    name: "Logo",
    methods: {
      goToHome: function () {
        let path=this.$route.path;
        if (path.includes('requester'))
          this.$router.push("/requester/about");
        else if (!path.includes('makeNaiveTagGreat')&&path!=='/')
          this.$router.push("/about")
      }
    }
  }
</script>

<style scoped>
  .logo {
    color: #20beff;
    font-size: 20px;
    margin: 5px 0px 0px;
    cursor: pointer;
  }

</style>
