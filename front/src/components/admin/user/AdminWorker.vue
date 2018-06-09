<template>
  <div style="margin: auto;min-width: 800px; max-width: 90%">
    <el-input v-model="key" clearable @blur="clearList" @keyup.enter.native="$event.target.blur"><i
      class="el-icon-search" style="height: 100%;margin-top: 15px;margin-right: 10px" slot="suffix"></i></el-input>
    <el-collapse style="margin-top: 10px">
      <worker-list-item v-for="(item,key) in workers"
                        :key="key" :worker="item">
      </worker-list-item>
      <infinite-loading @infinite="infiniteHandle" ref="infiniteLoading"></infinite-loading>
    </el-collapse>
  </div>
</template>

<script>
  import WorkerListItem from "./WorkerListItem";
  import InfiniteLoading from 'vue-infinite-loading';
  import {getWorkers, searchUser} from "../../../api/adminUsers";

  export default {
    name: "AdminWorker",
    components: {WorkerListItem, InfiniteLoading},
    data: function () {
      return {
        workers: [],
        page: 1,
        pageSize: 20,
        key: ''
      }
    },
    methods: {
      addList($state, res) {
        this.workers = this.workers.concat(res);
        $state.loaded();
        if (res.length < this.pageSize) {
          $state.complete();
        }
      },
      clearList() {
        this.workers = [];
        this.page = 1;
        this.$nextTick(() => {
          this.$refs.infiniteLoading.$emit('$InfiniteLoading:reset');
        });
      },
      infiniteHandle($state) {
        setTimeout(() => {
          if (this.key === '') {
            getWorkers(this.page, this.pageSize, res => {
              this.addList($state, res);
            })
          } else {
            searchUser(this.key, 'worker', this.page, this.pageSize, res => {
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
