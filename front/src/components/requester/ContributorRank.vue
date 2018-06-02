<template>
  <div class="container">

    <div v-for="(item,key) in workerList" v-if="(key<3)" :key="key" class="item">
      <contributor-item :contributor="item"></contributor-item>
    </div>

    <el-collapse-transition>
      <div v-show="expand">
        <div v-for="(item,key) in workerList" v-if="(key>=3)" :key="key" class="item">
          <contributor-item :contributor="item"></contributor-item>
        </div>
      </div>
    </el-collapse-transition>
    <div class="rank-bottom" v-if="workerList.length>3">
      <i v-if="!expand" class="el-icon-arrow-down" @click="expand= true"></i>
      <i v-if="expand" class="el-icon-arrow-up" @click="expand=false"></i>
    </div>


  </div>
</template>

<script>
  import {getParticipants} from "../../api/getTaskDetail";

  import ElCollapseTransition from "element-ui/src/transitions/collapse-transition";
  import ContributorItem from "./ContributorItem";


  export default {
    name: "ContributorRank",
    components: {ContributorItem, ElCollapseTransition},
    props: ['workerList'],
    data: function () {
      return {
        expand: false
      }
    },
    methods: {}
  }
</script>

<style scoped>
  .item {
    margin-bottom: 20px;
    height: 60px;
  }



  .container {
    margin: auto;
    max-width: 60%;
    min-width: 400px;
  }

  .rank-bottom {
    cursor: pointer;
    margin-left: 45%;
  }

</style>
