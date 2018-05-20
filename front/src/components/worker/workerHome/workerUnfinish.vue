<template>
    <div id="workerUnfinish">
        <task-list :taskListData = this.taskUnfinishData></task-list>
    </div>
</template>

<script>
	import taskList from '../taskList.vue';

	export default {
		name: "workerUnfinish",

        data(){
			return {
				taskUnfinishData: this.getDataMock(5),
            }
        },

		methods: {
			getDataMock(num) {
				let data = [];
				for (let index = 0; index < num; index++) {
					data.push({
						"name": "TrackML Particle Tracking Challenge", // 任务名
						"taskId": 2333, // 任务ID
						"taskDescription": "High Energy Physics particle tracking in CERN detectors", // 任务描述
						"taskCover": "../../../static/1.png", // 一个url，表示任务的封面，以固定的尺寸显示！
						"earnedDollors": 250.00, // 2位小数，表示挣来的钱数
						"changeOfScore": 360, // 做的这个任务引起的积分变化，可正可负
						"taskType": [401, 300], // 这个一级任务中包含的任务的类型
						"endDate": "2017-10-1", // 任务截止的时间
						"taskTag": ["nature", "history"], // 任务的一些标签信息，就是任务的主题
					})
				}
				return data;
			},
		},

        components: {
			taskList
        }
	}
</script>

<style scoped>

</style>