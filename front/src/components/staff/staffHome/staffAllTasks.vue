<template>
    <div id="staffAllTasks" style="text-align: left">
        <task-list v-loading="this.isLoading"
                   :taskListData = this.allTasksList @saveTaskState="saveTaskState"></task-list>
    </div>
</template>

<script>
	import taskList from '../staffTaskList.vue';
	import {allTasks} from "../../../api/staffTask";

	export default {
		name: "staffAllTasks",

		created(){
			this.fetchDataList();
		},

		data(){
			return {
				allTasksList: [],
				isLoading: true,
			}
		},

		methods: {

			saveTaskState(){
				localStorage.taskState = "tag";
			},

			fetchDataList(){
				let that = this;
				allTasks(res =>{
					that.allTasksList = res;
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