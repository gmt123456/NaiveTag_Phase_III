<template>
    <div id="staffMyParticipation">
        <div style="min-height: 460px">

            <div class="center" style="width: 100%;height: 40px;background-color: #47494d;font-size: 13px;">
                <el-container>
                    <el-main style="background-color: transparent;">
                        <span style="margin-left: 20px;color: white;">{{myParticipationList.length}} Assignments Matched</span>
                    </el-main>
                    <el-aside style="width: 150px;background-color: transparent;">
                        <!--<el-select v-model="value" placeholder="请选择" size="mini" @change="fetchData" style="margin-top: 13px;width: 100px;">-->
                            <!--<el-option-->
                                    <!--v-for="item in options"-->
                                    <!--:key="item.value"-->
                                    <!--:label="item.label"-->
                                    <!--:value="item.value">-->
                            <!--</el-option>-->
                        <!--</el-select>-->
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
                        <el-col :span="6" v-if="show" v-for="(item, index) in this.myParticipationList" :key="index" v-bind:data-index="index">
                            <el-card :body-style="{ padding: '0px' }" style="margin: 0px;margin-top: 10px;margin-bottom: 10px;" shadow="hover">
                                <div style="width: 100%;height: 134px;">
                                    <div :style="{width: '100%', height: '100%', 'background-image': 'url('+getImgSrc(item.cover)+')', 'background-size': 'cover', 'background-position': '50%'}"></div>
                                </div>
                                <div style="padding: 10px;">
                                    <i></i>
                                    <span style="color: gray;font-size: 15px;">pics: </span>
                                    <span>{{item.picAmount}}</span>
                                    <div class="center" style="margin-left: 150px;">
                                        <el-button type="text" style="padding: 0;" @click="startTag(index)">start</el-button>
                                        <el-button v-if="isShowDetails()" type="primary" size="mini" style="margin-left: 10px;" @click="openDetails(index)">details</el-button>
                                    </div>
                                </div>
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
    import {checkMyParticipation} from "../../../api/staffTask";
    import {checkFirstPicUrl} from "../../../api/staffTask";
    import {staffSubTaskDetailsInfo} from "../../../api/staffTask";

    export default {
		name: "staffMyParticipation",
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

			isShowDetails(){
				if(localStorage.taskState === 'tag'){
					return true;
                }else{
					return false;
                }
            },

			getImgSrc(src){
				return getUrl(src);
            },

	        getTaskNameByID(taskID){
		        let name = getTaskName(taskID);
		        return name[0] + "-" + name[1];
	        },

	        openDetails(index){
		        this.$router.push({name: 'staffSubTaskDetails', params: {taskId: this.taskData.taskId, subTaskId: this.myParticipationList[index].subPartId, taskType: this.myParticipationList[index].taskType}});
	        },

	        fetchData(state){
				let that = this;
		        checkMyParticipation(localStorage.firstLevelTaskId, state, res => {
			        that.myParticipationList = res;
			        that.show = true;
		        })
	        },

	        startTag(index){
				if(localStorage.taskState === 'check'){
					checkFirstPicUrl(this.myParticipationList[index].subPartId, res =>{
						this.$router.push({ name: 'staffCheck', params: { taskId: localStorage.firstLevelTaskId, subPartId: this.myParticipationList[index].subPartId, taskType: this.myParticipationList[index].taskType, picUrl: res}});
					});
                }
		        else{
					staffSubTaskDetailsInfo(localStorage.firstLevelTaskId, this.myParticipationList[index].subTaskId, this.myParticipationList[index].taskType, res =>{
						let url;
						if(res.unFinishedPicList && res.unFinishedPicList.length > 0){
							url = res.unFinishedPicList[0];
						}
						this.$router.push({ name: 'staffTag', params: { taskId: localStorage.firstLevelTaskId, subPartId: this.myParticipationList[index].subPartId, taskType: this.myParticipationList[index].taskType, picUrl: url}});
					});
                }
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
