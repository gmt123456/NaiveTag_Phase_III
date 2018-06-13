<template>
  <el-collapse-item>
    <div slot="title" style="display: flex">
      <el-col :span="5">
        <div >
              <span style="color: gray;font-weight: bold">{{dateStr}}</span>
        </div>

      </el-col>
      <el-col :span="15">
        <span>{{item[1]}}</span>
      </el-col>
      <el-col :span="3" style="float: right; text-align: right">
        <span v-if="item[0]>0" class="positive">+{{item[0]}}</span>
        <span v-else class="negative">{{item[0]}}</span>
      </el-col>
    </div>
    <div style="margin: auto">
      <el-form size="mini" label-width="120px" label-position="left">
        <el-form-item label="time">
          <span>{{item[3]}}</span>
        </el-form-item>
        <el-form-item label="balance">
          <span>{{item[2]}}</span>
        </el-form-item>

      </el-form>
    </div>

  </el-collapse-item>
</template>

<script>
  export default {
    name: "AccountChangeItem",
    props: ['item'],
    data: function () {
      return {
        date: '',
        day:'',
        dateStr:'',
      }
    },
    created: function () {
      this.date = new Date(this.item[3]);
      this.dateStr=this.date.toLocaleDateString().substring(5);
      this.day=this.date.toString().split(' ')[0];
    }

  }
</script>

<style scoped>

  .positive {
    color: #5BC994;

  }

  .negative {
    color: #F39699;
  }
</style>
