<template>
  <div id="division">
    <img :src=getDivision() :width="size" height="auto">
  </div>
</template>

<script>
  import {getDivision} from "../../api/getDivision";

  export default {
    name: "divisionPic",
    props: {
      division: String,
      size: String,
    },
    methods:{
      getDivision(){
        return getDivision(this.division);
      }
    }

  }
</script>

<style scoped>

</style>
