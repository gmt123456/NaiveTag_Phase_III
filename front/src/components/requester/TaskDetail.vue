<template>
  <div style="min-width: 900px; max-width: 70%; margin: auto">
    <div class="top" :style="{'background-image':'url('+getUrl(taskSketch.backgroundImage)+')'}">
      <el-col :span="13" :offset="1" style="height: 170px">
        <div class="text-box" style="height: 100%">
          <h3>{{taskSketch.title}}</h3>
          <span>{{taskSketch.description}}</span>
        </div>

      </el-col>


      <el-col :offset="1" class="time-info">
        <span>{{taskSketch.timeInfo}}</span>
      </el-col>

    </div>
    <el-card class="tag-box">
      <el-aside>
        <span>Tags</span>
      </el-aside>
      <div class="tags" >
        <el-tag type="info" v-for="(item,key) in taskSketch.tags" :key="key" class="tag">{{item}}</el-tag>
      </div>
    </el-card>


    <el-tabs class="tabs">
      <el-tab-pane label="Overview">
        <el-card style="margin-bottom: 40px">

          <el-container>
            <el-aside>
              <div style="width: 100px; margin-top: 30px; margin-left: 80px">
                <el-progress type="circle" v-if="taskSketch.process!==undefined"
                             :percentage="Math.round(Number(taskSketch.process)*10000)/100" :width="120"></el-progress>
                <el-button v-bind:disabled="taskSketch.state!=='finished'" icon="el-icon-download"
                           style="margin-top: 15px" @click.prevent="exportResult"> download
                </el-button>
              </div>
            </el-aside>
            <el-main>
              <el-form label-position="left" label-width="150px">
                <el-form-item label="Dollars">{{Math.round(taskSketch.dollars*100)/100}}</el-form-item>
                <el-form-item label="Deadline">{{taskSketch.deadline}}</el-form-item>
                <el-form-item label="Participants">{{taskSketch.participantsNum}}</el-form-item>
                <el-form-item label="Mode">{{taskSketch.taskRequirement.toLocaleLowerCase()}}
                  <el-button type="text" style="margin-left: 30px" @click="changeModeVisible=true">Change</el-button>
                </el-form-item>
              </el-form>
            </el-main>
          </el-container>
        </el-card>

        <el-dialog :visible.sync="changeModeVisible" width="400px" title="Change mode">
          <el-form label-width="100px" label-position="left">
            <el-form-item label="Mode" style="width: 100%">
              <el-select v-model="taskRequirement" style="width: 100%;">
                <el-option v-for="(item,key) in defaultTaskRequirement"
                           :value="item"
                           :key="key">
                </el-option>
              </el-select>
            </el-form-item>
            <el-button @click="changeMode" type="primary" style="width: 100%;">Confirm</el-button>
          </el-form>
        </el-dialog>


        <el-card style="margin-bottom: 40px">
          <div slot="header">
            <span>Top Contributors</span>
            <span style="font-size: 12px"></span>
          </div>
          <contributor-rank :workerList="workerList" style="margin-top: 30px"></contributor-rank>
        </el-card>


        <el-card style="margin-bottom: 40px">
          <div slot="header">
            <span>ReadMe</span>
            <el-button type="text" style="float: right;padding-top: 0" @click="editing=true">edit</el-button>
          </div>

          <div>
            <span>{{readMe}}</span>

          </div>
        </el-card>

      </el-tab-pane>
      <el-tab-pane label="Details">
        <div style="display: flex">
          <el-menu :default-active="activeIndex" @select="handleIndexChange">
            <el-menu-item v-for="(item,key) in subTasks" :key="key" :index="String(key)">
              <i class="el-icon-menu"></i>
              <span style="font-size: 12px">{{getTypeString(item.type)}}</span>
            </el-menu-item>
          </el-menu>

          <div style="margin-left: 30px;width: 100%">
            <sub-task-detail :sub-task="activeSubTask" ref="subTaskDetail"></sub-task-detail>
          </div>

        </div>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :visible.sync="editing" title="Editing ReadMe">
      <el-input type="textarea" autosize v-model="editInput" placeholder="Readme Introduction"></el-input>
      <span slot="footer">
        <el-button @click="handleReadmeCancel">cancel</el-button>
        <el-button @click="handleReadmeSubmit">save</el-button>
      </span>
    </el-dialog>


  </div>

</template>

<script>
  import {editReadme, getParticipants, getReadme, getSubTask, getTaskSketch, changeMode} from "../../api/getTaskDetail";
  import ContributorRank from "./ContributorRank";
  import {convertTypeToString} from "../../api/taskType";
  import SubTaskDetail from "./SubTaskDetail";
  import ImgWithLabel from "./ImgWithLabel";
  import {getUrl} from "../../api/tool";

  export default {
    name: "TaskDetail",
    components: {ImgWithLabel, SubTaskDetail, ContributorRank},
    computed: {
      activeSubTask: function () {
        return this.subTasks[Number(this.activeIndex)];
      }
    },
    data: function () {
      return {
        taskId: '',
        taskSketch: '',
        backgroundImage: '',
        readMe: '',
        editing: false,
        editInput: '',
        list: ['asd', 'ad', 'fds'],
        workerList: [],
        subTasks: [],
        activeIndex: String(0),
        changeModeVisible: false,
        taskRequirement: '',
        defaultTaskRequirement: ['common', 'speed', 'quality'],
      }
    },
    methods: {
      exportResult() {
        $.get(getUrl('requester/task/download.html'), {taskId: this.taskId}, res => {

          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = getUrl(res);
          link.setAttribute('download', '*');
          document.body.appendChild(link);
          link.click()

        });
      },
      getUrl(url) {
        return getUrl(url);
      },
      handleReadmeCancel: function () {
        this.$confirm('Your changes will be revoked', 'prompt', {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',

        }).then(() => {
          this.editInput = this.readMe;
        }).catch(() => {

        })
      },
      handleReadmeSubmit: function () {
        this.$confirm('Your changes will be saved', 'prompt', {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',

        }).then(() => {
          editReadme(this.taskId, this.editInput, res => {
            if (res.status === 'success') {
              this.readMe = this.editInput;
              this.editing = false;
              this.$message({
                message: 'Modified successfully',
                type: 'success'
              });
            } else {
              this.$message(res.message);
            }
          })

        }).catch(() => {

        })
      },

      getTypeString: function (taskType) {
        return convertTypeToString(taskType);
      },

      handleIndexChange: function (index, indexpath) {
        this.$refs.subTaskDetail.refresh(this.subTasks[Number(index)]);
      },
      changeMode: function () {
        changeMode(this.taskId, this.taskRequirement, res => {
          if (res.status === 'success') {
            this.$message({
              message: 'Change mode successfully!',
              type: 'success'
            });
            this.changeModeVisible = false;
            this.refresh();

          } else {
            this.$message(res.message);
          }
        })
      },

      refresh: function () {
        getTaskSketch(this.taskId, res => {
          this.taskSketch = res;
          this.taskRequirement = res.taskRequirement.toLocaleLowerCase();

        });

        getParticipants(this.taskId, res => {
          this.workerList = res;
        });

        getSubTask(this.taskId, res => {
          this.subTasks = res;
        });

        getReadme(this.taskId, res => {
          console.log('getReadMe');
          console.log(res);
          this.readMe = res;
          this.editInput = res;
        });
      }

    },
    created: function () {
      if (this.$route.params.taskId) {
        localStorage.taskId = this.$route.params.taskId;
      }
      this.taskId = localStorage.taskId;
      this.refresh();

    }
  }
</script>

<style scoped>
  .top {
    min-width: 800px;
    height: 200px;
    margin-top: 20px;
  }

  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 190px;
    min-height: 400px;
  }

  .menu-box {

  }

  .progress {
    width: 200px;
  }

  .time-info {
    color: white;

    font-weight: bold;

  }

  .download-group {
    display: flex;
    float: right;

  }

  .tag-box {
    margin-top: 20px;
    min-height: 40px;
  }

  .tags {
    margin-left: 100px;
    margin-top: -35px;

  }

  .tag {
    margin-left: 10px;
    margin-top: 10px;
  }

  .tabs {
    margin-top: 30px;
    width: 100%;
  }

  .white {
    color: white;
  }

  .label {
    width: 100px;
  }

  .text-box {
    color: white;

  }

</style>
