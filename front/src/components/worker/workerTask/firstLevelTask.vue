<template>
    <div id="taskPageI">

        <el-col :span="24"><div style="background-color: #f6f9fa;min-height: 200px;overflow:hidden;">

            <div style="width: 900px;height: 170px;margin: auto;">
                <div style="height: 30px;"></div>
                <div v-bind:style="{width:'100%',height:'100%','background-image':'url('+backgroundImg+')','background-size':'cover','background-position':'50%'}">
                <transition name="slide-fade">
                    <el-container v-if="show" v-bind:style="{width:'100%',height:'100%'}" >

                        <el-main style="background-color: rgba(0,0,0,0.3);padding-left: 30px;">
                            <div style="color: white;font-weight: 800;font-size: 20px;margin-top: 20px;">{{taskData.name}}</div>
                            <div style="color: white;font-weight: lighter;font-size: 17px;margin-top: 5px;">{{taskData.taskDescription}}</div>
                            <div style="color: white;font-weight: lighter;font-size: 14px;margin-top: 25px;">end at {{taskData.endDate}}</div>
                        </el-main>

                        <el-aside width="150px" height="170px" style="background-color: rgba(0,0,0,0.3);">
                            <div class="center" style="padding-bottom:20px;padding-top: 50px;">
                                <img src="../../../../static/dollar_white.png" height="25px">
                                <span style="color: white;font-weight: 500;font-size: 20px;">{{taskData.totalDollars.toFixed(2)}}</span>
                            </div>
                            <div class="center" v-if="taskData.state === 'Finished'" style="padding-left: 3px;">
                                <img src="../../../../static/complete.png" height="20px">
                                <span style="padding-left: 3px;color: white;font-weight: lighter;font-size: 14px;">Finished</span>
                            </div>
                            <div class="center" v-if="taskData.state === 'Going'" style="padding-left: 3px;">
                                <img src="../../../../static/unfinished.png" height="20px">
                                <span style="padding-left: 3px;color: white;font-weight: lighter;font-size: 14px;">Going</span>
                            </div>
                        </el-aside>

                    </el-container>
                </transition>
                </div>
            </div>

        </div></el-col>

        <el-col :span="24"><div style="background-color: #f6f9fa;text-align: center;overflow:hidden;">
            <div style="width: 900px;height: 40px;margin: auto;">

                <el-menu :default-active="$route.path" mode="horizontal" :router="true"
                         style="height: 50px;position: relative;top: -10px;" @select="handleSelect">
                    <el-menu-item index="/firstTask/overview" style="height: 50px;">Overview</el-menu-item>
                    <el-menu-item v-if="taskData.participated && taskData.participated === true" index="/firstTask/subtasks" style="height: 50px;">Tasks</el-menu-item>
                    <el-menu-item v-if="taskData.participated && taskData.participated === true" index="/firstTask/myparticipation" style="height: 50px;">My Participation</el-menu-item>
                    <!--<el-menu-item index="/worker/task" style="height: 50px;">Rank</el-menu-item>-->
                    <el-button v-if="taskData.canAccept" type="primary" style="float: right;height: 40px;width: 150px;margin-top: 10px;" @click="joinHandle">Join Assignment</el-button>
                    <el-button v-else-if="taskData.participated" type="success" disabled style="float: right;height: 40px;width: 150px;margin-top: 10px;">Already Joined</el-button>
                    <el-button v-else type="info" disabled style="float: right;height: 40px;width: 150px;margin-top: 10px;">Cannot Joined</el-button>
                </el-menu>

            </div>
        </div></el-col>

        <el-col>
            <div style="width: 100%;background-color: #f6f9fa;">
                <div style="width: 900px;min-height: 450px;margin: auto;">
                    <router-view v-bind:taskData="this.taskData"></router-view>
                </div>
            </div>
        </el-col>


    </div>
</template>

<script>
    import {taskInfo} from "../../../api/workerTaskInfo";
    import {taskJoin} from "../../../api/workerTaskInfo";

    export default {
		name: "taskPageI",
        created() {
			this.getTaskInfo();
        },

        mounted(){
			this.show = true;
        },

		data() {
			return {
				show: false,
				backgroundImg: "../../../../static/background/bg"+parseInt(Math.random()*3+1)+".jpg",
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
            }
        },

        methods: {

			getTaskInfo(){
				let that = this;
				taskInfo(localStorage.firstLevelTaskId, res => {
					that.taskData = res;
                })
            },

	        joinHandle(){
		        let that = this;
		        taskJoin(this.taskData.taskId, res => {
		        	if(res.result === true){
				        that.$message({
					        message: 'join success! ψ(｀∇´)ψ',
					        type: 'success'
				        });
				        that.getTaskInfo();
                    }else{
				        that.$message.error(res.reason);
                    }
                })
            },

	        handleSelect(key, keyPath) {

	        }

        },

	    watch: {
		    // 如果路由有变化，会再次执行该方法
		    '$route': 'getTaskInfo'
	    },

	}
</script>

<style scoped>
    .center {
        display: flex;
        /*justify-content:center;*/
        align-items: center;
    }
    .slide-fade-enter-active, .slide-fade-leave-active {
        transition: all 600ms;
    }
    .slide-fade-enter {
        transform: translateY(170px);
    }
</style>
