<template>
    <div id="staffMyTasks" style="text-align: left">
        <task-list v-loading="this.isLoading"
                   :taskListData = this.myTasksList @saveTaskState="saveTaskState"></task-list>
    </div>
</template>

<script>
	import taskList from '../staffTaskList.vue';
	import {myTasks} from "../../../api/staffTask";

	export default {
		name: "staffMyTasks",

		created(){
			this.fetchDataList();
		},

		data(){
			return {
				myTasksList: [],
				isLoading: true,
			}
		},

		methods: {

			saveTaskState(){
				localStorage.taskState = "tag";
			},

			fetchDataList(){
				let that = this;
				myTasks(res =>{
					that.myTasksList = res;
					that.isLoading = false;
				});
			},
		},

		watch: {
			// 如果路由有变化，会再次执行该方法
			'$route': 'fetchDataList'
		},

		components: {
			taskList
		}
	}
</script>

<style scoped>

</style>