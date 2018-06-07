<template>
    <div id="staffTaskBlock" @click="jumpFirstTask">
        <el-card class="task-block" :body-style="{ padding: '0px' }" shadow="hover" style="margin-bottom: 10px;margin-top: 10px;">
            <el-container>
                <el-aside style="width: 120px;height: 120px;background-color: transparent;">
                    <div v-bind:style="{width:'100%',height:'100%','background-image':'url('+getImgSrc(taskCover)+')','background-size':'cover','background-position':'50%'}"></div>
                    <!--<img :src="taskCover" width="90px" height="auto" style="margin: 10px;padding-left: 10px;overflow: hidden;">-->
                </el-aside>
                <el-main style="background-color: transparent;">
                    <div style="font-weight: 800;font-size: 15px;">{{name}}</div>
                    <div style="color: #717478;font-size: 13px;">{{taskDescription}}</div>
                    <div style="text-align: left;">
                        <span v-for="(item,index) in taskType" style="color: dodgerblue;font-size: 13px;">{{getTaskNameByID(item)}}{{(index === taskType.length-1)? "":",\ "}}</span>
                        <div class="center">
                            <span style="color: #bfbfbf;font-size: 13px;padding-right: 5px;">end at {{endDate}} ·</span>
                            <img src="../../../static/sale-fill.png" width="15px" style="padding-right: 3px;"/>
                            <span v-for="(theme,index) in taskTag" style="color: #bfbfbf;font-size: 13px;padding-right: 2px;">{{theme}}{{(index === taskTag.length-1)? "":","}}</span>
                        </div>
                    </div>

                </el-main>
                <el-aside style="width: 150px;background-color: transparent;">
                    <div style="margin-top: 30px;">
                        <div v-if="earnedDollors">
                            <img src="../../../static/dollar.png" width="15px"/>
                            <span style="font-weight: 600;color: #47494d">{{earnedDollors.toFixed(2)}}</span>
                            <span style="color: #bfbfbf;font-size: 13px;">earned</span>
                        </div>
                        <div v-if="totalDollars">
                            <img src="../../../static/dollar.png" width="15px"/>
                            <span style="font-weight: 600;color: #47494d;">{{totalDollars.toFixed(2)}}</span>
                            <span style="color: #bfbfbf;font-size: 13px;">total</span>
                        </div>
                        <div v-if="changeOfScore">
                            <img src="../../../static/favorite.png" width="15px"/>
                            <span style="color: #47494d;">{{changeOfScore}}</span>
                        </div>
                    </div>
                </el-aside>
            </el-container>
        </el-card>
    </div>
</template>

<script>
    import {getTaskName} from "../../api/taskTypeName";
    import {getUrl} from "../../api/tool";

    export default {
		props:{
			"name": String, // 任务名
			"taskId": Number, // 任务ID
			"taskDescription": String, // 任务描述
			"taskCover": String, // 一个url，表示任务的封面，以固定的尺寸显示！
			"earnedDollors": Number, // 2位小数，表示挣来的钱数
			"changeOfScore": Number, // 做的这个任务引起的积分变化，可正可负
			"taskType": Array, // 这个一级任务中包含的任务的类型
			"endDate": String, // 任务截止的时间
			"taskTag": Array, // 任务的一些标签信息，就是任务的主题

			"totalDollars": Number
		},

		name: "staffTaskBlock",

        data(){
			return {

            }
        },

        methods: {

			getImgSrc(src){
				return getUrl(src);
            },

	        jumpFirstTask(){
		        this.$emit('saveTaskState');
		        localStorage.firstLevelTaskId = this.taskId;
	        	this.$router.push('/staffFirstTask/staffOverview');
            },

			getTaskNameByID(taskID){
				let name = getTaskName(taskID)
				return name[0] + "-" + name[1];
            }
        },
	}
</script>

<style scoped>
    .task-block{
        cursor: pointer;
    }

    .center {
        display:flex;
        /*justify-content:center;*/
        align-items:center;
    }
</style>