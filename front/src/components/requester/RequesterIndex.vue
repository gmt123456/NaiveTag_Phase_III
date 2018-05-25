<template>
  <div>
    <requester-navi></requester-navi>
    <router-view></router-view>
  </div>
</template>

<script>
    import RequesterNavi from "./RequesterNavi";
    export default {
        name: "requesterIndex",
      components: {RequesterNavi}
    }
</script>

<style scoped>

</style>

