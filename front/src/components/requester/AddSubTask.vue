<template>
  <el-form :model="subTask" label-position="left" label-width="150px"
           :rules="subTaskRules" ref="subTaskForm">
    <el-form-item label="type" prop="type">
      <el-select v-model="subTask.type" @change="typeChangeHandle">
        <el-option v-for="(item,key) in types "
                   :key="key"
                   :value="item.value"
                   :label="item.label"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="description" prop="description">
      <el-input v-model="subTask.description"
                placeholder="Please input your task description"
                type="textarea"
                autosize clearable>
      </el-input>
    </el-form-item>
    <el-form-item label="labels" v-if="needLabel" prop="labels">
      <el-tag v-for="(item,key) in subTask.labels"
              :key="key"
              closable
              style="margin-right: 5px"
              @close="handleCloseTag(item)">
        {{item}}
      </el-tag>
      <el-input
        v-if="labelInputVisible"
        v-model="labelInput"
        ref="TagInput"
        size="small"
        type="textarea"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
        placeholder="Please input your labels, separate your labels with spaces if you have multiple labels"
      >
      </el-input>
      <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Label</el-button>

    </el-form-item>
  </el-form>
</template>

<script>
  import {getAllTypes, needLabel} from "../../api/taskType";
  import {deepClone} from "../../api/tool";

  export default {
    name: "AddSubTask",
    computed: {

      needLabel: function () {
        let type=this.subTask.type;
        console.log(this.subTask.type);
       return  needLabel(type);
      }

    },
    data: function () {
      const validateLabels = (rule, value, callback) => {
        if (this.subTask.labels.length > 0) {
          callback();
        } else {
          callback(new Error('please add your labels'));
        }
      };
      return {
        labelInput: '',
        labelInputVisible: false,
        types: [],
        subTask: {
          description: '',
          type: '100',
          labels: []
        },
        subTaskRules: {
          description: [
            {required: true, message: 'please describe your task', trigger: 'blur'}
          ],
          labels: [
            {validator: validateLabels, trigger: 'blur'}
          ]
        },
      }
    },
    methods: {
      showInput() {
        this.labelInputVisible = true;

        // this.$refs.TagInput.$refs.input.focus();

      },
      handleCloseTag: function (tag) {
        this.subTask.labels.pop(tag);
      },
      handleInputConfirm: function () {
        let inputValue = this.labelInput;
        if (inputValue) {
          let res = [];
          let array = this.subTask.labels.concat(inputValue.split(/\s+/));

          for (let index in array) {
            if (array.indexOf(array[index]) == index && array[index] !== '') {
              res.push(array[index]);
            }

          }
          this.subTask.labels = res;
        }
        this.labelInputVisible = false;
        this.labelInput = '';
      },
      typeChangeHandle(){
        this.$refs['subTaskForm'].clearValidate();
        this.subTask.labels=[];
        this.subTask.description=''
      },
      handleSubTaskClose: function () {
        this.$refs['subTaskForm'].resetFields();
        this.labelInputVisible=false;
      },
      addSubTaskHandle() {
        console.log('inside');

        this.$refs['subTaskForm'].validate((valid) => {
          if (valid) {
            let newTask = deepClone(this.subTask);
            this.$emit('add-task',newTask);
            this.handleSubTaskClose();

          }
        })

      }
    },
    created: function () {
      this.types = getAllTypes();
    }
  }
</script>

<style scoped>

</style>
