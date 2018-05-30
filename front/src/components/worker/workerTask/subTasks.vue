<template>
    <div id="overview">
        <el-card :body-style="{ padding: '0px' }" shadow="never">
            <div>

                <el-col :span="5">
                    <el-menu
                            default-active="0"
                            style="min-height: 430px;"
                            @select="handleSelect">
                        <el-menu-item v-for="(item, index) in taskData.taskTypes" :key=index :index="index.toString()">
                            <span slot="title">{{getTaskNameByID(item)}}</span>
                        </el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :span="19">
                    <transition-group v-bind:css="false"
                                      v-on:before-enter="beforeEnter"
                                      v-on:enter="enter"
                                      name="fadeTask">
                        <el-col :span="8" v-if="show" v-for="(item, index) in this.subTaskList" :key="menuIndex+index" v-bind:data-index="index">
                            <el-card :body-style="{ padding: '0px' }" style="margin: 10px;" shadow="hover">
                                <div style="width: 100%;height: 134px;">
                                    <div :style="{width: '100%', height: '100%', 'background-image': 'url('+getImgSrc(item.cover)+')', 'background-size': 'cover', 'background-position': '50%'}"></div>
                                </div>
                                <div style="padding: 10px;">
                                    <i class="el-icon-picture-outline"></i>
                                    <span style="color: gray;font-size: 15px;">pics: </span>
                                    <span>{{item.picCount}}</span>
                                    <div class="bottom clearfix center" style="margin-left: 76px;">
                                        <el-button type="text" style="padding: 0;" @click="open2(index)">accept</el-button>
                                        <el-button type="primary" size="mini" style="margin-left: 10px;" @click="openDetails(index)">details</el-button>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                    </transition-group>
                </el-col>
            </div>
        </el-card>
    </div>
</template>

<script>
    import {getTaskName} from "../../../api/taskTypeName";
    import {subTaskInfo} from "../../../api/workerTaskInfo";
    import {acceptSubTask} from "../../../api/workerTaskInfo";
    import {getUrl} from "../../../api/tool";

    export default {
	    name: "subTasks",
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
			    menuIndex: "0",
                subTaskList: [],
            }
        },

        mounted() {
	    	this.fetchData(0);
        },

        methods: {

	        getImgSrc(src){
		        return getUrl(src);
	        },

	        openDetails(index){
		        this.$router.push({name: 'subTaskDetails', params: {taskId: this.taskData.taskId, subTaskId: this.subTaskList[index].subTaskId, taskType: this.taskData.taskTypes[parseInt(this.menuIndex)]}});
	        	// this.$router.push("/subTaskDetails/:"+this.taskData.taskId+"/:"+this.subTaskList[index].subTaskId+"/:"+this.taskData.taskTypes[parseInt(this.menuIndex)]);
            },

	    	fetchData(index){
	        	let that = this;
			    subTaskInfo(this.taskData.taskId, this.taskData.taskTypes[index], res => {
				    that.subTaskList = res;
				    that.show = true;
                })
            },

	        open2(index) {
		        let that = this;
		        this.$confirm('Are you sure you accept this task?', 'Prompt', {
			        confirmButtonText: 'yes',
			        cancelButtonText: 'no',
			        type: 'info'
		        }).then(() => {
			        acceptSubTask(this.taskData.taskId, this.subTaskList[index].subTaskId, this.taskData.taskTypes[parseInt(that.menuIndex)], res =>{
				        if(res.result === true){
					        that.$message.success("accept success! Good Luck~(￣▽￣)");
					        that.subTaskList.splice(index,1);
				        }else{
					        that.$message.error("accept fail！（；´д｀）ゞ");
				        }
			        });
		        }).catch(() => {

		        });
	        },

	        getTaskNameByID(taskID){
		        let name = getTaskName(taskID);
		        return name[0] + "-" + name[1];
	        },

	        handleSelect(key, keyPath) {
	    		this.show = false;
		        this.menuIndex = key;
		        this.fetchData(parseInt(key));
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

	    watch: {
		    // 如果路由有变化，会再次执行该方法
		    '$route': 'fetchData'
	    },

	}
</script>

<style scoped>
    .bottom {
        margin-top: 5px;
        line-height: 10px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .center {
        display: flex;
        /*justify-content:center;*/
        align-items: center;
    }

</style>
