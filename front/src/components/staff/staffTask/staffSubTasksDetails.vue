<template>
    <div id="staffSubTasksDetails">
        <el-col :span="24"><div style="background-color: #f6f9fa;min-height: 200px;overflow:hidden;">

            <div style="width: 900px;height: 170px;margin: auto;">
                <div style="height: 30px;"></div>
                <div v-bind:style="{width:'100%',height:'100%','background-image':'url('+backgroundImg+')','background-size':'cover','background-position':'50%'}">
                    <transition name="slide-fade">
                        <el-container v-if="show" v-bind:style="{width:'100%',height:'100%'}" >

                            <el-main style="background-color: rgba(0,0,0,0.3);padding-left: 30px;">
                                <div style="color: white;font-weight: 800;font-size: 20px;margin-top: 10px;">{{detailsData.taskName}}</div>
                                <div style="color: white;font-weight: lighter;font-size: 17px;margin-top: 5px;">{{detailsData.taskDescription}}</div>
                                <div style="color: white;font-weight: lighter;font-size: 14px;margin-top: 20px;">end at {{detailsData.endDate}}</div>
                            </el-main>
                            <el-aside style="background-color: rgba(0,0,0,0.3);height: 170px;overflow: hidden;width: 180px;">
                                <img src="../../../../static/subtask.png" height="170px" width="auto"/>
                            </el-aside>

                        </el-container>
                    </transition>
                </div>
            </div>

        </div></el-col>

        <el-col :span="24"><div style="background-color: #f6f9fa;overflow:hidden;">
            <div style="width: 900px;min-height: 490px;margin: auto;">
                <el-card :body-style="{ padding: '0px' }" shadow="never" style="margin-top: 10px;margin-bottom: 20px;min-height: 450px;">

                    <div style="height: 40px;">
                        <el-tabs v-if="detailsData.picList" v-model="activeName3" type="card" @tab-click="handleClick" style="width: 740px;display: inline-block;">
                            <el-tab-pane label="picList" name="picList"></el-tab-pane>
                        </el-tabs>
                        <el-tabs v-else-if="detailsData.finishedPicList && detailsData.unFinishedPicList" v-model="activeName2" type="card" @tab-click="handleClick" style="width: 740px;display: inline-block;">
                            <el-tab-pane label="unfinish" name="unfinish"></el-tab-pane>
                            <el-tab-pane label="finish" name="finish"></el-tab-pane>
                        </el-tabs>
                        <el-button v-if="detailsData.taskState === 'UN_PART'" type="primary" style="float: right;height: 40px;width: 150px;" @click="accept" :loading="loadingAccept">Accept</el-button>
                        <el-button v-else-if="detailsData.unFinishedPicList && detailsData.unFinishedPicList.length === 0" type="primary" style="float: right;height: 40px;width: 150px;" :loading="loadingSubmit" @click="submit">Submit</el-button>
                        <el-button v-else type="primary" style="float: right;height: 40px;width: 150px;" @click="startTag">Start Tag</el-button>
                    </div>

                    <div>
                        <div v-if="detailsData.unFinishedPicList && detailsData.unFinishedPicList.length === 0 && tabLabel === 'unfinish'" :style="getNoneStyle()"><div style="margin: 300px;"></div><span>No Picture Here</span></div>
                        <transition-group v-bind:css="false"
                                          v-on:before-enter="beforeEnter"
                                          v-on:enter="enter"
                                          name="fadeTask1">
                            <el-col v-if="detailsData.unFinishedPicList && (tabLabel === 'unfinish') && show" :span="lengthN" v-for="(item, index) in this.detailsData.unFinishedPicList" :key="index" v-bind:data-index="index" style="padding: 10px;">
                                <img :src="getImgSrc(item)" style="width: 100%;height: auto;cursor: pointer;" @click="startTagByPicUrl(item)"/>
                            </el-col>
                        </transition-group>
                    </div>

                    <div>
                        <div v-if="detailsData.finishedPicList && detailsData.finishedPicList.length === 0 && tabLabel === 'finish'" :style="getNoneStyle()"><div style="margin: 300px;"></div><span>No Picture Here</span></div>
                        <transition-group v-bind:css="false"
                                          v-on:before-enter="beforeEnter"
                                          v-on:enter="enter"
                                          name="fadeTask2">
                            <el-col v-if="detailsData.finishedPicList && (tabLabel === 'finish') && show" :span="lengthN" v-for="(item, index) in this.detailsData.finishedPicList" :key="index" v-bind:data-index="index" style="padding: 10px;">
                                <img :src="getImgSrc(item)" style="width: 100%;height: auto;cursor: pointer;" @click="startTagByPicUrl(item)"/>
                            </el-col>
                        </transition-group>
                    </div>

                    <div>
                        <div v-if="detailsData.picList && detailsData.picList.length === 0" :style="getNoneStyle()"><div style="margin: 300px;"></div><span>No Picture Here</span></div>
                        <transition-group v-bind:css="false"
                                          v-on:before-enter="beforeEnter"
                                          v-on:enter="enter"
                                          name="fadeTask3">
                            <el-col v-if="detailsData.picList && show" :span="lengthN" v-for="(item, index) in this.detailsData.picList" :key="index" v-bind:data-index="index" style="padding: 10px;">
                                <img :src="getImgSrc(item)" style="width: 100%;height: auto;"/>
                            </el-col>
                        </transition-group>
                    </div>


                </el-card>
            </div>
        </div></el-col>

    </div>
</template>

<script>

    import {staffSubTaskDetailsInfo} from "../../../api/staffTask";
    import {staffAcceptSubTask} from "../../../api/staffTask";
    import {checkAcceptSubTask} from "../../../api/staffTask";
    import {staffCommitSubTask} from "../../../api/staffTask";
	import {getUrl} from "../../../api/tool";

	export default {

		name: "staffSubTasksDetails",

		data(){
			return {
				lengthN: 4,
				loadingAccept: false,
				loadingSubmit: false,
				showList: false,
				tabLabel: 'unfinish',
				activeName2: 'unfinish',
				activeName3: 'picList',
				show: false,
				backgroundImg: "../../../../static/background/bg"+parseInt(Math.random()*3+1)+".jpg",
				detailsData: {
					"taskId": 0,
					"subTaskId": 0,
					"taskState": "unAccepted",
					"taskName": "",
					"taskType": 100,
					"taskDescription": "",
					"finishedPicList": [],
					"unFinishedPicList": [],
					"picList": [],
					"endDate": ""
				},
			}
		},

		created(){
			this.fetchData();
		},

		mounted(){
			this.show = true;
			this.showList = true;
		},

		methods: {

			getImgSrc(src){
				return getUrl(src);
				// return src;
			},

			startTagByPicUrl(url){
				this.$router.push({ name: 'staffTag', params: { taskId: localStorage.firstLevelTaskId, subPartId: this.$route.params.subTaskId, taskType: this.$route.params.taskType, picUrl: url}});
			},

			startTag(){
				if(this.detailsData.unFinishedPicList && this.detailsData.unFinishedPicList.length > 0){
					this.$router.push({ name: 'staffTag', params: { taskId: localStorage.firstLevelTaskId, subPartId: this.$route.params.subTaskId, taskType: this.$route.params.taskType, picUrl: this.detailsData.unFinishedPicList[0]}});
					console.log("subTaskId: "+this.$route.params.subTaskId+" taskType: "+this.$route.params.taskType);
				}else{
					console.log("no unFinishedPicList!")
				}

			},

			accept(){
				let that = this;
				this.loadingAccept = true;
				if(localStorage.taskState === 'check'){
					checkAcceptSubTask(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, res =>{
						if(res.result === true){
							that.$message.success("accept success! Good Luck~(￣▽￣)");
							that.fetchData();
						}else{
							that.$message.error("accept fail！（；´д｀）ゞ");
						}
					});
				}else{
					staffAcceptSubTask(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, res =>{
						if(res.result === true){
							that.$message.success("accept success! Good Luck~(￣▽￣)");
							that.fetchData();
						}else{
							that.$message.error("accept fail！（；´д｀）ゞ");
						}
					});
                }
				this.loadingAccept = false;
			},

			submit(){
				this.loadingSubmit = true;
				let that = this;
				staffCommitSubTask(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, res =>{
					if(res.result === true){
						that.$message.success("submit success! Well done!(￣▽￣)");
						that.$router.push("/firstTask/myparticipation");
					}else{
						that.$message.error("submit fail！（；´д｀）ゞ");
					}
				});
				this.loadingSubmit = false;
			},

			fetchData() {
				let that = this;
				staffSubTaskDetailsInfo(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, res =>{
					that.detailsData = res;
				});
			},

			getNoneStyle() {
				let data = {
					width: '100%',
					height: '380px',
					'text-align': 'center',
					color: 'gray',
					border: 'dashed 1px transparent',
				};

				data['background'] = "url('../../../../static/none.png') no-repeat center";
				data['background-size'] =  '30%';

				return data;
			},

			beforeEnter: function (el) {
				el.style.opacity = 0;
				el.style.translateX = 170;
			},

			enter: function (el, done) {
				let delay = el.dataset.index * 30;
				setTimeout(function () {
					Velocity(
						el,
						{ opacity: 0, translateX: 240 },
						{ duration: 20 }
					)
					Velocity(
						el,
						{ opacity: 1, translateX: 0 },
						{ complete: done }
					)
				}, delay)
			},

			handleClick(tab, event) {
				this.tabLabel = tab.label;
			}
		},

		watch: {
			// 如果路由有变化，会再次执行该方法
			'$route': 'fetchData'
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
