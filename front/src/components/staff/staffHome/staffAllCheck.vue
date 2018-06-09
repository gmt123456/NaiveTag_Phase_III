<template>
    <div id="staffAllCheck" style="text-align: left">
        <task-list v-loading="this.isLoading"
                   :taskListData = this.allCheckList @saveTaskState="saveTaskState"></task-list>
    </div>
</template>

<script>
	import taskList from '../staffTaskList.vue';
	import {allCheck} from "../../../api/staffCheck";

	export default {
		name: "staffAllCheck",

		created(){
			this.fetchDataList();
		},

		data(){
			return {
				allCheckList: [],
				isLoading: true,
			}
		},

		methods: {

			saveTaskState(){
				localStorage.taskState = "check";
				this.$router.push('/staffCheckFirstTask/staffCheckOverview');
            },

			fetchDataList(){
				let that = this;
				allCheck(res =>{
					that.allCheckList = res;
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
