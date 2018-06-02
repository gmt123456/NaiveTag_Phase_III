<template>
    <div id="myParticipation">
        <div style="min-height: 460px">

            <div class="center" style="width: 100%;height: 40px;background-color: #47494d;color: white;font-size: 13px;">
                <el-container>
                    <el-main>
                        <span style="margin-left: 20px;">{{myParticipationList.length}} Assignments Matched</span>
                    </el-main>
                    <el-aside style="width: 150px;">
                        <el-select v-model="value" placeholder="请选择" size="mini" @change="fetchData" style="margin-top: 13px;width: 100px;">
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-aside>
                </el-container>
            </div>

            <el-row :gutter="20">
                <div v-if="myParticipationList.length === 0" class="center" style="width: 900px;justify-content:center;height: 410px;background-color: white;margin-left: 10px;">
                    <div style="display: block;text-align: center;">
                        <img src="../../../../static/none.png" width="200px"/>
                    </div>
                </div>

                <!--<div v-else>-->
                    <transition-group v-bind:css="false"
                                      v-on:before-enter="beforeEnter"
                                      v-on:enter="enter"
                                      name="fadeTask">
                        <el-col :span="12" v-if="show" v-for="(item, index) in this.myParticipationList" :key="index" v-bind:data-index="index">
                            <el-card class="task-block" :body-style="{ padding: '0px' }" shadow="hover" style="margin-bottom: 10px;margin-top: 10px;overflow: hidden;height: 120px;">
                                <el-container>
                                    <el-aside style="width: 120px;height: 120px;background-color: white">
                                        <div v-bind:style="{width:'100%',height:'100%','background-image':'url('+getImgSrc(item.cover)+')','background-size':'cover','background-position':'50%'}"></div>
                                        <!--<img :src="taskCover" width="90px" height="auto" style="margin: 10px;padding-left: 10px;overflow: hidden;">-->
                                    </el-aside>
                                    <el-main style="background-color: white;">
                                        <div style="float: left;">
                                            <div><el-tag size="mini">{{getTaskNameByID(item.taskType)}}</el-tag></div>
                                            <i class="el-icon-picture-outline"></i>
                                            <!--<span style="color: gray;font-size: 14px;"></span>-->
                                            <span style="font-size: 14px">{{item.picCount}}</span>
                                            <span style="color: #6f7180;font-size: 15px;">{{item.picAmount}}</span>
                                        </div>
                                        <div v-if="!item.commitDate" class="center" style="float: right;position:relative;z-index: 2">
                                            <el-button type="text" style="padding: 0;" @click="startTag">start</el-button>
                                            <el-button type="primary" size="mini" style="margin-left: 10px;" @click="openDetails(index)">details</el-button>
                                        </div>
                                        <div>
                                            <el-progress v-if="item.process === 100" :percentage="100" status="success"></el-progress>
                                            <el-progress v-else :percentage="item.process"></el-progress>
                                        </div>
                                        <div style="text-align: center;color: gray;font-size: 13px;width: 100%;float: bottom">
                                            <div>expired: {{item.expiredDate}}</div>
                                            <div v-if="item.commitDate" style="color: lightgrey">commit: {{item.commitDate}}</div>
                                        </div>
                                    </el-main>
                                </el-container>
                            </el-card>
                        </el-col>
                    </transition-group>
                <!--</div>-->

            </el-row>
        </div>
    </div>
</template>

<script>
    import {getTaskName} from "../../../api/taskTypeName";
    import {getUrl} from "../../../api/tool";
    import {myParticipation} from "../../../api/workerTaskInfo";

    export default {
		name: "myParticipation",
		props:{
			taskData: {
				"name": "",
				"taskId": 0,
				"taskCover": "",
				"taskDescription": "",
				"endDate": "",
				"state": "", // 可能的值 Finished, Going
				"participated": false, // 是否已经参与了这个任务
				"requiredDivision": "", // 表示段位的一个枚举值
				"taskTags": [],
				"taskTypes": [],
				"taskBackground": "",
				"totalDollars": 0.00, // 价格
				"canAccept": false, // 用户没有接过这个任务 && 用户满足接受条件 && 正在进行中，就可以接受
				"earnedDollars": 0.00, // 如果用户参与了这个任务而且这个任务已经完成，这个字段才有效，否则就是0
				"scoreChange": 0, //如果用户参与了这个任务而且这个任务已经完成，这个字段才有效，否则就是0 这两个字段如果无效，就不显示
			}
		},

		data() {
			return {
				show: false,
				myParticipationList: [],
				options: [{
					value: 0,
					label: 'doing'
				}, {
					value: 2,
					label: 'finished'
				},{
					value: 1,
					label: 'expired'
				}],
				value: 0
			}
		},

		created() {
			this.fetchData(0);
		},

        methods: {

			getImgSrc(src){
				return getUrl(src);
            },

	        getTaskNameByID(taskID){
		        let name = getTaskName(taskID);
		        return name[0] + "-" + name[1];
	        },

	        openDetails(index){
		        this.$router.push({name: 'subTaskDetails', params: {taskId: this.taskData.taskId, subTaskId: this.myParticipationList[index].subTaskId, taskType: this.myParticipationList[index].taskType}});
	        },

	        fetchData(state){
				let that = this;
		        myParticipation(this.taskData.taskId, state, res => {
			        that.myParticipationList = res;
			        that.show = true;
		        })
	        },
	        startTag(){

            },

	        beforeEnter: function (el) {
		        el.style.opacity = 0;
		        el.style.translateX = 170;
	        },

	        enter: function (el, done) {
		        let delay = el.dataset.index * 50;
		        setTimeout(function () {
			        Velocity(
				        el,
				        { opacity: 0, translateX: 240 },
				        { duration: 20 }
			        );
			        Velocity(
				        el,
				        { opacity: 1, translateX: 0 },
				        { complete: done }
			        )
		        }, delay)
	        },
        },


	}
</script>

<style scoped>
    .center {
        display: flex;
        /*justify-content:center;*/
        align-items: center;
    }
</style>
