<template>

  <div class="task-box">
    <el-card class="task-card">
      <router-link :to="{name:'taskDetail',params:{taskId:this.task.taskId}}">
        <el-col :span="12"  style="float: left">

          <img :src="task.cover" class="cover"/>

          <div class="title">
            <h4>{{task.title}}</h4>
            <p class="description">{{task.description}}</p>
          </div>
        </el-col>
      </router-link>
      <el-col :span="4" :offset="1">
        <div v-if="task.tags.length<=4" class="tag-box">
          <div v-for="item in task.tags" class="tag">
            <span>{{item}}</span>
          </div>
        </div>
        <div v-else class="tag-box">
          <div v-for="(item,key) in task.tags" v-if="key<3" class="tag">
            <span>{{item}}</span>
          </div>
          <div class="tag">
            <el-tooltip placement="bottom" effect="light">
              <div slot="content">
                <div v-for="(item,key) in task.tags" v-if="key>=3" class="tag">
                  <span>{{item}}</span>
                </div>
              </div>
              <span>+ {{task.tags.length-3}} more...</span>
            </el-tooltip>
          </div>
        </div>
      </el-col>

      <el-col :span="4">
        <div class="remarks-box1">
          <div class="division-box">
            <division-pic :division="task.workerRequirement" size="60px"></division-pic>
            <div class="division-label">
              <span>at least</span>
            </div>
          </div>
          <img-with-label url="/static/requester/金币.svg" :label="task.dollars"></img-with-label>
        </div>
      </el-col>

      <el-col :span="2">
        <div class="remarks-box2">
          <img-with-label url="/static/requester/participants.svg" :label="task.participantsNum"></img-with-label>
          <img-with-label url="/static/requester/pictures.svg" :label="task.pictureNum"
                          style="margin-top: 39px"></img-with-label>
        </div>
      </el-col>

      <div class="time">
        <span> {{task.timeInfo}}</span>
      </div>

    </el-card>
  </div>

</template>

<script>
  import {getDivision} from "../../api/getDivision";
  import ImgWithLabel from "./ImgWithLabel";
  import DivisionPic from "../worker/divisionPic";

  export default {
    name: "TaskBox",
    components: {DivisionPic, ImgWithLabel},
    props: ['task'],
    methods: {
      getDivision(division) {
        return getDivision(division);
      }
    },
    created: function () {
      console.log(this.task.workerRequirement);
    }
  }
</script>

<style scoped>
  .task-box {
    min-height: 150px;
    margin-bottom: 30px;
    width: 99.7%;
    float: left;
  }

  .task-card {
    min-height: 150px;
    width: 100%;
    float: left;
  }

  .description {
    font-size: 14px;
    margin-top: -10px;
  }

  .time {
    color: lightgray;
    font-size: 13px;
    float: right;
    margin-top: 15px;
  }
  .cover{
    width: 100px;
    margin-top: 5px;
  }

  .title {
    margin-top: -120px;
    margin-left: 125px;
    color: black !important;
  }

  .tag {
    color: gray;
    font-size: 14px;
    margin-top: 3px;
  }

  .tag-box {
    margin-top: 10px;

  }

  .division-label {
    color: gray;
    margin-top: -50px;
    margin-left: 60px;
  }

  .division-box {
    min-height: 70px;
    margin-left: -15px;
  }

  .remarks-box1 {
    min-width: 100px;
    margin-top: -10px;
  }

  .remarks-box2 {
    min-width: 100px;
  }

  .icon {
    width: 25px;
  }


</style>
