<template>
    <div id="myParticipation">
        <el-card :body-style="{ padding: '0px' }" shadow="never">
            <div style="min-height: 458px">
                <transition-group v-bind:css="false"
                                  v-on:before-enter="beforeEnter"
                                  v-on:enter="enter"
                                  name="fadeTask">
                    <el-col :span="6" v-if="show" v-for="(item, index) in this.myParticipationList" :key="index" v-bind:data-index="index">
                        <el-card :body-style="{ padding: '0px' }" style="margin: 10px;" shadow="hover">
                            <div style="width: 100%;height: 134px;">
                                <div :style="{width: '100%', height: '100%', 'background-image': 'url('+item.cover+')', 'background-size': 'cover', 'background-position': '50%'}"></div>
                            </div>
                            <div style="padding: 10px;">
                                <i class="el-icon-picture-outline"></i>
                                <span style="color: gray;font-size: 15px;">pics: </span>
                                <span>{{item.picAmount}}</span>
                                <el-progress :percentage="item.process"></el-progress>
                                <div class="center" style="margin-left: 65px;">
                                    <el-button type="text" style="padding: 0;" @click="startTag">start</el-button>
                                    <el-button type="primary" size="mini" @click="openDetails(index)">details</el-button>
                                </div>

                                <div style="color: lightgrey;font-size: 13px;padding-top: 10px;text-align: center;">
                                    <div>Expire: {{item.expiredDate}}</div>
                                    <div>Commit: {{item.commiteDate}}</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </transition-group>
            </div>
        </el-card>
    </div>
</template>

<script>
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
				myParticipationList: [
					{
                        "cover": "../../../../static/1.png", // 封面
                        "expiredDate": "2016-3-10",
                        "commiteDate": "2018-5-30", // 可能是空的，如果没提交就是空的
                        "subTaskId": 11,
                        "taskType": 400, // 任务类型
                        "process": 67, //之间的整数
                        "picAmount": 666,// 图片数量
		            },
					{
						"cover": "../../../../static/1.png", // 封面
						"expiredDate": "2016-3-10",
						"commiteDate": "2018-5-30", // 可能是空的，如果没提交就是空的
						"subTaskId": 12,
						"taskType": 400, // 任务类型
						"process": 67, //之间的整数
						"picAmount": 233,// 图片数量
					},{
						"cover": "../../../../static/1.png", // 封面
						"expiredDate": "2016-3-10",
						"commiteDate": "2018-5-30", // 可能是空的，如果没提交就是空的
						"subTaskId": 13,
						"taskType": 400, // 任务类型
						"process": 67, //之间的整数
						"picAmount": 555,// 图片数量
					},
                ],
			}
		},

		mounted() {
			this.show = true;
			this.fetchData();
		},

        methods: {
	        openDetails(index){
		        this.$router.push({name: 'subTaskDetails', params: {taskId: this.taskData.taskId, subTaskId: this.myParticipationList[index].subTaskId, taskType: this.myParticipationList[index].taskType}});
		        console.log("taskId:"+ this.taskData.taskId+" subTaskId:"+ this.myParticipationList[index].subTaskId +" taskType:"+this.myParticipationList[index].taskType);
		        // this.$router.push("/subTaskDetails/:"+this.taskData.taskId+"/:"+this.subTaskList[index].subTaskId+"/:"+this.taskData.taskTypes[parseInt(this.menuIndex)]);
	        },

	        fetchData(){
		        // subTaskInfo(this.taskData.taskId, this.taskData.taskTypes[index], res => {
			     //    this.subTaskList = res;
			     //    this.show = true;
		        // })
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

</style>