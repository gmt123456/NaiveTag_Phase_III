<template>
  <div class="container">
    <div v-if="activeIndex===0">
      <el-form :model="taskForm" label-position='left' ref="taskForm"
               :rules="rules" label-width="180px">
        <el-form-item label="Title" class="colorful-label" prop="title">
          <el-input v-model="taskForm.title" clearable></el-input>
        </el-form-item>
        <el-form-item label="Description" class="colorful-label" prop="description">
          <el-input v-model="taskForm.description"
                    type="textarea" autosize clearable>

          </el-input>
        </el-form-item>
        <el-form-item label="Tags" class="colorful-label" prop="tags">
          <el-select v-model="taskForm.tags" filterable multiple placeholder="please select tags" class="tag-select">
            <el-option v-for="(item ,key) in defaultTags"
                       :key="key"
                       :value="item">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Deadline" class="colorful-label" required prop="deadLine"
                      value-format="yyyy-MM-dd">
          <el-date-picker v-model="taskForm.deadLine"></el-date-picker>
        </el-form-item>

        <el-form-item label="Lowest Division" class="colorful-label">
          <el-select v-model="taskForm.lowestDivision">
            <el-option v-for="(item,key) in defaultDivisions"
                       :value="item"
                       :key="key">

            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Task Cover" class="colorful-label">
          <el-button @click="uploadCover=true" class="upload-cover"
                     v-bind:style="{'background-image':'url('+taskForm.cover+')'}"></el-button>
        </el-form-item>

        <el-form-item label="DataSet" prop="file" class="colorful-label" v-model="taskForm.file">
          <el-upload
            action="null"
            :http-request="uploadDataSet"
            :limit="1">
            <el-button icon="el-icon-upload" style="width: 100%">upload</el-button>
          </el-upload>
        </el-form-item>


        <el-form-item class="colorful-label" prop="tasks" v-model="taskForm.tasks">
          <span slot="label">
            Specific Task
            <el-button icon="el-icon-circle-plus"
                       class="plus-button"
                       @click="addSubTaskVisible=true"
            ></el-button>
          </span>

          <el-carousel indicator-position="outside"
                       :autoplay="false" height="250px">
            <el-carousel-item v-for="(item,key) in taskForm.tasks" :key="key">
              <el-form label-position="left" label-width="150px" class="sub-task-card">
                <el-form-item label="type">
                  <span>{{ item.type }}</span>
                  <el-button icon="el-icon-delete" class="delete-btn" @click="deleteSubTask(item)"></el-button>
                </el-form-item>
                <el-form-item label="description">
                  <span>{{ item.description }}</span>
                </el-form-item>
                <el-form-item label="labels" v-if="needLabel(item.type)">
                  <el-tag v-for="(tag,key) in item.labels"
                          :key="key" type="info"
                          style="margin-right: 5px">
                    {{tag}}
                  </el-tag>
                </el-form-item>
              </el-form>
            </el-carousel-item>
          </el-carousel>
        </el-form-item>


        <el-button type="primary" style="width: 100%" @click="submit">submit</el-button>

      </el-form>

      <el-dialog :visible.sync="uploadCover" append-to-body>
        <worker-pic :imageSrc="taskForm.cover" ref="uploadCover" v-on:save-res="saveCover"></worker-pic>
        <el-button slot="footer" @click="triggerInsideSave">save</el-button>
      </el-dialog>


      <el-dialog :visible.sync="addSubTaskVisible"
                 append-to-body
                 :before-close="beforeAddSubTaskClose">
        <add-sub-task ref="addSubTask" v-on:add-task="onAddSubTask"></add-sub-task>
        <el-button slot="footer" @click="triggerAddSubTask">OK</el-button>
      </el-dialog>
    </div>

    <div v-if="activeIndex===1">
      <div class="order">

        <el-form :model="orderForm" label-width="160px" label-position="left"
                 ref="order" :rules="orderRules">
          <el-form-item label="Extract Fee" prop="extractFee">
            <el-input v-model="orderForm.extractFee"></el-input>
          </el-form-item>

          <el-form-item label="Advertisement Fee" prop="adFee">
            <el-input v-model="orderForm.adFee"></el-input>
          </el-form-item>

          <el-form-item label="Total">
            <span>{{Number(orderForm.extractFee)+
              Number(orderForm.adFee)+
              Number(orderInfo.payLowerBound)}}</span>
          </el-form-item>
        </el-form>

        <el-button type="primary" style="width: 100%; margin-top: 30px" @click="payOrder">Pay</el-button>

        <div class="info">
          <p style=""> There are <b>{{orderInfo.pictureNum}}</b> pictures in your task</p>

          <p> At least, your need to pay <b>{{orderInfo.payLowerBound}}</b> dollars </p>

          <p><b>{{Number(orderInfo.payLowerBound)
            +Number(orderForm.extractFee)}}</b> dollars will be paid to the workers</p>
          <p><b> {{orderForm.adFee}}</b> will donate to the <b>NaiveTag</b></p>
        </div>
      </div>
    </div>

    <div v-if="activeIndex===2">
      <div>

      </div>
    </div>
    <el-steps :active="activeIndex" class="steps" finish-status="success">
      <el-step title="create" description="create the task order"></el-step>
      <el-step title="pay" description="pay for the task order"></el-step>
      <el-step title="finish"></el-step>
    </el-steps>
  </div>
</template>

<script>
  import {getDefaultInfo, payOrder, submitTaskOrder} from "../../api/createTask";
  import WorkerPic from "../worker/workerPic";
  import {convertTypeToString, getAllTypes, needLabel} from "../../api/taskType";
  import AddSubTask from "./AddSubTask";


  export default {
    name: "CreateTask",
    components: {AddSubTask, WorkerPic},
    data: function () {
      const tagsValidator = (rule, value, callback) => {

        if (this.taskForm.tags.length > 0) {
          callback();
        } else {
          callback(new Error('please add your labels'));
        }
      };

      const dataSetValidator = (rule, value, callback) => {
        if (this.taskForm.file !== '') {
          callback();
        } else {
          callback(new Error('please upload your DataSet'));
        }
      };

      const taskValidator = (rule, value, callback) => {
        if (this.taskForm.tasks.length > 0) {
          callback();
        } else {
          callback(new Error('please add your task'));
        }
      };


      const adFeeValidator = (rule, value, callback) => {
        if (Number(this.orderForm.adFee) >= 0) {
          callback();
        } else {
          callback(new Error('pleas input the number above zero'));
        }
      };

      const extractValidator = (rule, value, callback) => {
        if (Number(this.orderForm.extractFee) >= 0) {
          callback();
        } else {
          callback(new Error('pleas input the number above zero'));
        }
      };

      return {
        activeIndex: 0,
        defaultTags: '',
        uploadCover: false,
        addSubTaskVisible: false,
        defaultDivisions: [],
        orderInfo: '',

        taskForm: {
          tags: [],
          cover: '',
          tasks: [],
          title: '',
          description: '',
          file: '',
          deadLine: '',
          lowestDivision: '',
        },
        orderForm: {
          status: String,
          orderId: '',
          extractFee: 0,
          adFee: 0,
        },
        orderRules: {
          extractFee: [
            {validator: extractValidator, trigger: 'blur'}
            // {required: true, message: 'please  add your tag', trigger: 'blur'},
          ],
          adFee: [
            {validator: adFeeValidator, trigger: 'blur'}
            //{required: true, message: 'please  add your tag', trigger: 'blur'},
          ]
        },
        rules: {
          tags: [
            //  {required: true, message: 'please  add your tag', trigger: 'blur'},
            {validator: tagsValidator, trigger: 'blur'}
          ],
          tasks: [
            //{required: true, message: 'please add specific tasks', trigger: 'blur'},
            {validator: taskValidator, trigger: 'blur'}
          ],
          title: [
            {required: true, message: 'please input the title', trigger: 'blur'},
          ],
          description: [
            {required: true, message: 'pleas input the description', trigger: 'blur'},
          ],
          file: [
            // {required: true, message: 'pleas upload your DataSet', trigger: 'blur'},
            {validator: dataSetValidator, trigger: 'blur'}
          ],
        }

      }
    },
    methods: {
      triggerInsideSave: function () {
        this.$refs.uploadCover.savePicAndUrl();
      },
      saveCover: function (res) {
        this.taskForm.cover = res;
        this.uploadCover = false;
      },
      convertType: function (type) {
        return convertTypeToString(type);
      },
      onAddSubTask: function (subtask) {
        this.taskForm.tasks.push(subtask);
        console.log('herhe');
        console.log(subtask.labels);
        this.addSubTaskVisible = false;
      },
      triggerAddSubTask: function () {
        console.log('ok');
        this.$refs.addSubTask.addSubTaskHandle();
      },
      beforeAddSubTaskClose: function () {
        this.$refs.addSubTask.handleSubTaskClose();
        this.addSubTaskVisible = false;
      },
      resetForm: function () {
        if (this.$refs.taskForm) {
          this.$refs.taskForm.clearValidate();
        }
        if(this.$refs.order){
          this.$refs.order.clearValidate();
        }
        this.activeIndex = 0;

        this.taskForm = {
          tags: [],
          cover: '',
          tasks: [],
          title: '',
          description: '',
          file: '',
          deadLine: '',
          lowestDivision: this.defaultDivisions[0],
        };

        this.orderForm={
          status: String,
          orderId: '',
          extractFee: 0,
          adFee: 0,
        }
      },
      uploadDataSet(item) {
        this.taskForm.file = item.file;
      },
      needLabel: function (type) {
        return needLabel(type);
      },
      deleteSubTask: function (task) {
        this.$confirm('would you like to delete this task?', 'waring', {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',
          type: 'warning'
        }).then(() => {
          let index = this.taskForm.tasks.indexOf(task);
          if (index > -1) {
            this.taskForm.tasks.splice(index, 1);
          }
        })
      },
      submit: function () {

        this.$refs.taskForm.validate((valid) => {
          console.log(valid);
          if (valid) {
            submitTaskOrder(this.taskForm, res => {
              if (res.status === 'success') {
                console.log(this.orderInfo);
                this.orderInfo = res;
                this.activeIndex = 1;

              }
            })
          }
        })
      },
      payOrder: function () {
        let pointer =this;
        this.$refs.order.validate((valid) => {
          if (valid) {
            this.orderForm.status = "accept";
            this.orderForm.dollars = this.orderInfo.payLowerBound;
            this.orderForm.orderId=this.orderInfo.orderId;
            payOrder(this.orderForm, function () {
            pointer.$message({
              message:'you have create the task',
              type:'success'
            })
            })
          }
        })
      }

    },
    created: function () {
      console.log('created');

      getDefaultInfo(res => {
        this.defaultTags = res.tags;
        this.defaultDivisions = res.divisions;
        this.taskForm.lowestDivision = res.divisions[0];
      });


      // //mock
      submitTaskOrder('', res => {
        if (res.status === 'success') {
          console.log(this.orderInfo);
          this.orderInfo = res;

        }
      })
    }
  }
</script>

<style scoped>
  .steps {
    margin-top: 80px;
    margin-left: 15%;
    margin-right: 15%;
  }

  .order {
    margin-left: 15%;
    margin-right: 15%;
  }

  .info {
    text-align: center;
    margin-top: 60px;
    font-size: 15px;
    color: gray;
  }

  .container {
    min-width: 550px;
    max-width: 850px;
    margin-left: 15%;
    margin-right: 15%;

  }

  .tag-select {
    min-width: 400px;
  }

  .plus-button {
    border: 0;

  }

  .plus-button:hover {
    background: white;
  }

  .colorful-label {
    /*color: red !important;*/
    font-weight: bold;
  }

  .delete-btn {
    float: right;
    border: 0;
  }

  .upload-cover {
    width: 100px;
    height: 100px;
    background-size: 100% 100%;
  }

</style>
