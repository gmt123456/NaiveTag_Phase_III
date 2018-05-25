<template>
  <div class="container">
    <img :src=url class="icon">
    <div class="label-box">
      <span class="label">{{label}}</span>
    </div>

  </div>
</template>

<script>
  export default {
    name: "ImgWithLabel",
    props: ['url', 'label']
  }
</script>

<style scoped>
  .icon {
    width: 25px;
  }

  .label {
    color: gray;
  }
.container{
  float: left;
  margin-top: 5px;
}
  .label-box{
  margin-left: 45px;
    margin-top: -29px;
  }
</style>
