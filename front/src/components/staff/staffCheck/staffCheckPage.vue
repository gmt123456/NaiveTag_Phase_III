<template>
    <div id="serviceCheckPage">
        <div style="height: 100%;" >
        </div>

        <!--point和frames是双向绑定，如果tagInfo那边允许更改point和frames的话，那么这边的信息也会随之改变-->
        <!--标注label的改变的话只能通过子组件调用负组件changeLabel更改这里的信息，当然这里check用不到-->
        <!--tagType,options,description属于任务信息，是一直不变的-->
        <!--picUrl是当前标注的图片url-->
        <tag ref="tagPage" v-bind:tagType="tagData.tagType"
             v-bind:options="options"
             v-bind:picUrl="getPicUrl"
             v-bind:label="tagData.label"
             v-bind:description="description"
             :points.sync="tagData.points"
             :frames.sync="tagData.frames"
             @nextPic="nextPic"></tag>
                <!--@lastPic="lastPic"-->
             <!--@changeLabel="changeLabel"-->
    </div>
</template>

<script>

	import tag from './staffCheckInfo.vue'
	// import {taskInfo} from '../../../api/tagPage.js'
	// import {getLabelInfo} from '../../../api/tagPage.js'
	import {getUrl} from "../../../api/tool";
	import {checkTaskNextPicUrl} from "../../../api/staffCheck";
	import {checkTaskMark} from "../../../api/staffCheck";
	import {checkTaskLabelInfo} from "../../../api/staffCheck";

	export default {

		created: function () {
			this.fetchTagData();
		},

		computed: {
			getPicUrl(){
				return getUrl(this.picUrl);
			}
		},

		methods: {

			tagPicReflash(picUrl) {//根据拿到的picUrl重新访问这个route路径，页面也重新加载
				this.$router.push({ name: 'staffCheck', params: { taskId: localStorage.firstLevelTaskId, subPartId: this.$route.params.subPartId, taskType: this.$route.params.taskType, picUrl: picUrl}});
			},

			nextPic: function (accept) {//check中，下一张图片，先对标注结果进行评分，然后用回调函数得到下一张图片的url、和下一张图片的标注信息
				// this.saveData();
                let that = this;
				checkTaskMark(this.$route.params.subPartId, this.$route.params.picUrl, accept, res =>{

					checkTaskNextPicUrl(that.$route.params.subPartId, res2=> {

						if(res2.toString().length === 0){
							that.$router.push("/staffFirstTask/staffMyparticipation");
						}else{
							that.tagPicReflash(res2);
						}
					});
                });
			},

			fetchTagData () {//有一个route里的url去后端拿任务数据：taskType，description，options，现在都不需要拿了(╯▔皿▔)╯
				this.picUrl = this.$route.params.picUrl;
				// let result = taskInfo(this.$route.params.taskId, this.$route.params.subPartId, this.$route.params.taskType,  res=> {
                //
				// 	if(res.classes){
				// 		this.options = this.changeToOptions(res.classes);
				// 	}
				// 	if(res.taskType){
				// 		this.taskType = res.taskType;
				// 	}
				// 	if(res.description){
				// 		this.description = res.description;
				// 	}
				// });
				this.fetchLabelDataByPicUrl(this.$route.params.picUrl);
			},

			fetchLabelDataByPicUrl(picUrl) {//有一个目前正标注图片的url去后端拿标注数据tagData
				let that = this;
				checkTaskLabelInfo(this.$route.params.subPartId, picUrl, res=> {
					if(res){
						that.tagData = res;
					}else{
						that.tagData = {
							"label": null,
							"frames":[
							],
							"tagType":null,
							//若能画，points必有
							"points":[
							],
						};

						// that.tagData.tagType =  ""+that.taskType;
					}
				});
			},

			// changeToOptions(classes){//把从后端拿到的classes变成options
			// 	var options = [];
			// 	for(var value in classes){
			// 		options.push({
			// 			value: value,
			// 			label: classes[value],
			// 		})
			// 	}
			// 	return options;
			// },
		},

		data() {
			return {
				accept: false,
				taskType: null,
				description: "",
				picUrl: "",
				options: [
//                    {
//                    value: '0',
//                    label: '黄金糕'
//                }, {
//                    value: '1',
//                    label: '双皮奶'
//                }, {
//                    value: '2',
//                    label: '蚵仔煎'
//                }, {
//                    value: '3',
//                    label: '龙须面'
//                }, {
//                    value: '4',
//                    label: '北京烤鸭'
//                }
				],
				tagData: {
					"label": null,
					"frames":[
					],
					"tagType":"t_100",
					//若能画，points必有
					"points":[
					],
				},
			}
		},

		components: {
//            'simplenavi': simplenavi,
			'tag': tag,
		},

		watch: {
			// 如果路由有变化，会再次执行该方法
			'$route': 'fetchTagData'
		},

	}

</script>

<style>

</style>
