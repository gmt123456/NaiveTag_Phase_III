<template>
    <div id="staffOverview">
        <el-card :body-style="{ padding: '0px' }" shadow="never">
            <div>

                <el-col :span="5">
                    <el-menu
                            default-active="description"
                            style="min-height: 430px;"
                            @select="handleSelect">
                        <el-menu-item index="description">
                            <i class="el-icon-menu"></i>
                            <span slot="title">Description</span>
                        </el-menu-item>
                        <el-menu-item index="about">
                            <i class="el-icon-info"></i>
                            <span slot="title">About me</span>
                        </el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :span="19">
                    <div v-if="menuIndex === 'description'" style="width: 100%;min-height: 400px;padding: 20px;">
                        <div style="min-height: 200px">

                            <el-col :span="9">
                                <div style="font-weight: bold;padding-top: 10px;padding-bottom: 10px;">Task Cover</div>
                                <div class="box2-img">
                                    <img :src="getImgSrc(taskData.taskCover)" class="img2" />
                                </div>
                            </el-col>

                            <el-col :span="15"><div>
                                <div style="font-weight: bold;padding-top: 30px;"></div>
                                <!--<div style="font-weight: bold;padding-top: 10px;">First Level Task<span style="font-weight: normal;color: darkgray;font-size: 12px;padding-left: 10px;">type</span></div>-->
                                <el-card  :body-style="{ padding: '3px' }" style="width: 400px;margin-top: 10px;padding-bottom: 10px;">
                                    <div>
                                        <div class="center" style="padding-bottom: 5px;">
                                            <img src="../../../../static/sale-fill.png" width="20px"/>
                                            <span style="color: darkgray;font-size: 13px;">Tags</span>
                                        </div>
                                        <el-tag v-if="show" size="small" v-for="(item, index) in taskData.taskTags" :key="index" type="success" style="margin-left: 6px;">{{item}}</el-tag>
                                    </div>
                                </el-card>
                                <el-card  :body-style="{ padding: '3px' }" style="width: 400px;margin-top: 10px;padding-bottom: 10px;">
                                    <div>
                                        <div class="center" style="padding-bottom: 5px;">
                                            <img src="../../../../static/sale-fill.png" width="20px"/>
                                            <span style="color: darkgray;font-size: 13px;">Types</span>
                                        </div>
                                        <el-tag v-if="show" size="small" v-for="(item, index) in taskData.taskTypes" :key="index" type="primary" style="margin-left: 6px;">{{getTaskNameByID(item)}}</el-tag>
                                    </div>
                                </el-card>
                            </div></el-col>

                            <el-col :span="24"><div style="width: 90%;">
                                <div style="font-weight: bold;padding-top: 20px;padding-bottom: 10px;">Required Division</div>
                                <division-pic v-bind:division="taskData.requiredDivision" size="100px" style="padding-left: 20px;"></division-pic>
                                <div style="color: gray;font-size: 13px;display: inline;">{{"Your division needs to exceed the " + taskData.requiredDivision + " to get the job."}}</div>
                                <div style="font-weight: bold;padding-top: 20px;padding-bottom: 10px;">Description</div>
                                <div style="color: gray;font-size: 14px;padding-bottom: 50px;">{{taskData.taskBackground}}</div>
                            </div></el-col>
                        </div>
                    </div>


                    <div v-else style="width: 100%;min-height: 420px;padding: 20px;">
                        <div v-if="taskData.participated && taskData.state === 'Finished'">
                            <div style="color: darkgray;padding-top: 10px;padding-bottom: 10px;" class="center"><span style="color: black;padding-right: 8px;font-weight: bold;">Score</span>changes through the task:
                                <img src="../../../../static/favorite.png" width="15px" style="padding-left: 10px;"/>
                                <span style="color: black;font-weight: normal;">{{taskData.earnedDollars.toFixed(2)}}</span>
                            </div>
                            <div style="color: darkgray;padding-top: 10px;padding-bottom: 10px;" class="center"><span style="color: black;padding-right: 8px;font-weight: bold;">Dollars</span>you earned from the task:
                                <img src="../../../../static/dollar.png" width="15px" style="padding-left: 10px;"/>
                                <span style="color: black;font-weight: normal;">{{taskData.earnedDollars.toFixed(2)}}</span>
                            </div>
                            <div style="color: darkgray;" class="center">
                                <!--<biscuits v-bind:earned="this.taskData.earnedDollars" v-bind:rest="this.taskData.totalDollars - this.taskData.earnedDollars"></biscuits>-->
                                <img src="../../../../static/background/bg_gif.gif"/>
                            </div>
                        </div>
                        <div v-else class="center" style="width: 650px;justify-content:center;height: 350px;">
                            <div style="display: block;text-align: center;">
                                <img src="../../../../static/none.png" width="200px"/>
                                <div v-if="taskData.participated && taskData.state === 'Going'" style="color: darkgray;font-size: 15px;">
                                    <div>You have joined it</div>
                                    <div>Please wait for the end of the whole Task!</div>
                                </div>
                                <div v-else style="color: darkgray;font-size: 15px;">
                                    <div>No Message Here</div>
                                    <div>Come and join the Task!</div>
                                    <el-button style="font-size: 13px;" type="text" @click="openMessage">Why I can't join >>></el-button>
                                </div>
                            </div>
                        </div>
                    </div>
                </el-col>

            </div>
        </el-card>
    </div>
</template>

<script>
    import divisionPic from '../divisionPic.vue';
    import {getTaskName} from '../../../api/taskTypeName';
    import {getUrl} from "../../../api/tool";
    // import {taskInfo} from "../../../api/workerTaskInfo";

    export default {
		name: "staffOverview",
	    // created() {
		 //    this.fetchData();
	    // },

        props: {
	        taskData:{
		        "taskId": Number,
		        "taskCover": String,
		        "state": String, // 可能的值 Finished, Going
		        "participated": Boolean, // 是否已经参与了这个任务
		        "requiredDivision": String, // 表示段位的一个枚举值
		        "taskTags": Array,
		        "taskTypes": Array,
		        "taskBackground": String,
		        "totalDollars": Number, // 价格
		        "canAccept": Boolean, // 用户没有接过这个任务 && 用户满足接受条件 && 正在进行中，就可以接受
		        "earnedDollars": Number, // 如果用户参与了这个任务而且这个任务已经完成，这个字段才有效，否则就是0
		        "scoreChange": Number, //如果用户参与了这个任务而且这个任务已经完成，这个字段才有效，否则就是0 这两个字段如果无效，就不显示
            },

        },

        data(){
			return {
				show: false,
				menuIndex: "description",

            }
        },

		methods: {

			getImgSrc(src){
				return getUrl(src);
			},

			openMessage() {
				this.$alert('Perhaps your division does not meet the requirements of the task, please complete the other tasks to ascent your division.', '', {
					confirmButtonText: 'OK',
					callback: action => {

					}
				});
			},

            // fetchData(){
				// taskInfo(res => {
				// 	this.taskData = res;
				// })
            // },

			getTaskNameByID(taskID){
				let name = getTaskName(taskID)
				return name[0] + "-" + name[1];
			},

			handleSelect(key, keyPath) {
				this.menuIndex = key;
			}
		},

		mounted(){
			this.show = true;
		},

        components: {
	        divisionPic,
	        // biscuits
        },

	    // watch: {
		 //    // 如果路由有变化，会再次执行该方法
		 //    '$route': 'fetchData'
	    // },

	}
</script>

<style scoped>
    .center {
        display:flex;
        /*justify-content:center;*/
        align-items:center;
    }
    .box2-img{width:215px;height:215px;text-align: center;display:table-cell;vertical-align: middle;border:1px solid lightgray;}
    .box2-img .img2{max-height:100%;max-width:100%;}
</style>