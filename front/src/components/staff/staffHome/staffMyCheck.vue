<template>
    <div id="staffMyCheck" style="text-align: left">
        <task-list v-loading="this.isLoading"
                   :taskListData = this.myCheckList @saveTaskState="saveTaskState"></task-list>
    </div>
</template>

<script>
	import taskList from '../staffTaskList.vue';
	import {myCheck} from "../../../api/staffCheck";

	export default {
		name: "staffMyCheck",

		created(){
			this.fetchDataList();
		},

		data(){
			return {
				myCheckList: [],
				isLoading: true,
			}
		},

		methods: {

			saveTaskState(){
				localStorage.taskState = "check";
			},

			fetchDataList(){
				let that = this;
				myCheck(res =>{
					that.myCheckList = res;
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
