<template>
    <div id="workerFinish">
        <task-list :taskListData = this.taskFinishData></task-list>
    </div>
</template>

<script>
	import taskList from '../taskList.vue';
	import {workerFinish} from "../../../api/workerInfo";

	export default {
		name: "workerFinish",

        created(){
			this.getFinishDataList();
        },

		data(){
			return {
				taskFinishData: [],
			}
		},

		methods: {
			getFinishDataList(){
				let that = this;
				workerFinish(res =>{
					that.taskFinishData = res;
				});
			},
		},

		watch: {
			// 如果路由有变化，会再次执行该方法
			'$route': 'getFinishDataList'
		},

		components: {
			taskList
		}
	}
</script>

<style scoped>

</style>