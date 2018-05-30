<template>
  <div >
    <IndexNavi></IndexNavi>
    <Login></Login>
    <Introduction></Introduction>
  </div>
</template>

<script>
  import Login from "./Login";
  import Introduction from "./Introduction";
  import IndexNavi from "./IndexNavi";

  export default {
    name: "index",
    components: {IndexNavi, Introduction, Login},

  }
</script>

<style scoped>

</style>
