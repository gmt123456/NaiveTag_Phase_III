<template>
    <div id="subTasksDetails">
        <el-col :span="24"><div style="background-color: #47494d;min-height: 50px;"></div></el-col>
        <el-col :span="24"><div style="background-color: #f6f9fa;min-height: 200px;overflow:hidden;">

            <div style="width: 900px;height: 170px;margin: auto;">
                <div style="height: 30px;"></div>
                <div v-bind:style="{width:'100%',height:'100%','background-image':'url('+backgroundImg+')','background-size':'cover','background-position':'50%'}">
                    <transition name="slide-fade">
                        <el-container v-if="show" v-bind:style="{width:'100%',height:'100%'}" >

                            <el-main style="background-color: rgba(0,0,0,0.3);padding-left: 30px;">
                                <div style="color: white;font-weight: 800;font-size: 20px;margin-top: 20px;">{{detailsData.taskName}}</div>
                                <div style="color: white;font-weight: lighter;font-size: 17px;margin-top: 5px;">{{detailsData.taskDescription}}</div>
                                <div style="color: white;font-weight: lighter;font-size: 14px;margin-top: 25px;">end at {{detailsData.endDate}}</div>
                            </el-main>

                        </el-container>
                    </transition>
                </div>
            </div>

        </div></el-col>

        <el-col :span="24"><div style="background-color: #f6f9fa;overflow:hidden;">
            <div style="width: 900px;min-height: 490px;margin: auto;">
                <el-card :body-style="{ padding: '0px' }" shadow="never" style="margin-top: 10px;margin-bottom: 20px;min-height: 450px;">

                    <div style="height: 40px;">
                        <el-tabs v-model="activeName2" type="card" @tab-click="handleClick" style="width: 740px;display: inline-block;">
                            <el-tab-pane label="unfinish" name="unfinish"></el-tab-pane>
                            <el-tab-pane label="finish" name="finish"></el-tab-pane>
                        </el-tabs>
                        <el-button v-if="this.detailsData.taskState === 'unAccepted'" type="primary" style="float: right;height: 40px;width: 150px;">Accept</el-button>
                        <el-button v-else-if="this.detailsData.unFinishedPicList && this.detailsData.unFinishedPicList.length === 0" type="primary" style="float: right;height: 40px;width: 150px;">Submit</el-button>
                        <el-button v-else type="primary" disabled style="float: right;height: 40px;width: 150px;">Already Accept</el-button>
                    </div>

                    <div style="width: 100%;">
                        <div v-if="this.detailsData.unFinishedPicList">
                            <el-col :span="4" v-for="(item, index) in this.detailsData.unFinishedPicList" :key="index" style="margin: 10px;">
                                <img :src="item" style="width: 100%;height: auto;"/>
                            </el-col>
                        </div>
                    </div>


                </el-card>
            </div>
        </div></el-col>

        <!--<el-col>-->
            <!--<div style="width: 100%;background-color: #f6f9fa;">-->
                <!--<div style="width: 900px;min-height: 450px;margin: auto;">-->
                <!--</div>-->
            <!--</div>-->
        <!--</el-col>-->
    </div>
</template>

<script>
	export default {
		name: "subTasksDetails",
        data(){
			return {
				activeName2: 'unfinish',
				show: false,
				backgroundImg: "../../../../static/background/bg"+parseInt(Math.random()*3+1)+".jpg",
				detailsData: {
					"taskId": 1,//
					"subTaskId": 2,//
					"taskState": "Accepted",
					"taskName": "Featured Prediction Competition",
					"taskType": 401,
					"taskDescription": "WebStorm is a powerful IDE for modern JavaScript development perfectly equipped .",
					"finishedPicList": [
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
					],
					"unFinishedPicList": [
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
						"../../../../static/1.png",
					],
					"picList": [
						"../../../../static/1.png",
						"../../../../static/1.png"
					],
					"endDate": "2018-5-24"
				},
            }
        },

        mounted(){
			this.show = true;
        },

        methods: {
	        handleClick(tab, event) {
		        // console.log(tab);
	        }
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