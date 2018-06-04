<template>
  <div>
    <p>requester</p>
    <el-collapse>
      <requester-list-item v-for="(item,key) in requesterList"
                           :key="key" :requester="item"></requester-list-item>
    </el-collapse>
    <infinite-loading @infinite="infiniteHandle" ref="infiniteLoading"></infinite-loading>
  </div>
</template>

<script>
  import RequesterListItem from "./RequesterListItem";
  import InfiniteLoading from 'vue-infinite-loading';
  import {getRequester, searchUser} from "../../api/adminUsers";

  export default {
    name: "AdminRequester",
    components: {RequesterListItem, InfiniteLoading},
    data: function () {
      return {
        requesterList: [],
        page:1,
        pageSize:20,
        key:''
      }
    },
    methods: {
      reset() {
        this.requesterList=[];
        this.page=1;
        this.$nextTick(() => {
          this.$refs.infiniteLoading.$emit('$InfiniteLoading:reset');
        });
      },
      addList($state, res) {
        this.requesterList = this.requesterList.concat(res);
        $state.loaded();
        if (res.length < this.pageSize) {
          $state.complete();
        }
      },
      infiniteHandle: function ($state) {
        setTimeout(() => {
          if (this.key === '') {
            getRequester(this.page, this.pageSize, res => {
              this.addList($state, res);
            })
          } else {
            searchUser(this.key, 'requester', this.page, this.pageSize, res => {
              this.addList($state, res);
            })
          }
        }, 1000)
      }
    }
  }
</script>

<style scoped>

</style>
