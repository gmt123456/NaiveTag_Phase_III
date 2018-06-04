<template>
  <div class="container">
    <el-collapse>
      <account-change-item v-for="(item,key) in list"
                           :item="item" :key="key">
      </account-change-item>
      <infinite-loading @infinite="infiniteHandle"></infinite-loading>
    </el-collapse>
  </div>

</template>

<script>
  import {getAccountInfo} from "../../../api/requesterDetail";
  import AccountChangeItem from "./AccountChangeItem";
  import InfiniteLoading from 'vue-infinite-loading';

  export default {
    name: "AccountChangeList",
    components: {AccountChangeItem, InfiniteLoading},
    data: function () {
      return {
        list: [],
        page: 0
      }
    },
    methods: {
      getData: function () {
        getAccountInfo(localStorage.token, this.page, res => {
          this.list = this.list.concat(res);
          this.page++;
          console.log(res);
        })
      },
      infiniteHandle($state) {
        setTimeout(() => {
          let pageSize = 10;
          getAccountInfo(localStorage.token, this.page, pageSize ,res=> {
            this.list = this.list.concat(res);
            this.page++;
            $state.loaded();
            if (res.length < pageSize) {
              $state.complete();
            }
          });
        }, 1000)


      }
    },
    created: function () {
     // this.getData();
    }
  }
</script>

<style scoped>
  .container {
    min-width: 500px;
    max-width: 70%;
    margin: auto;
  }
</style>
