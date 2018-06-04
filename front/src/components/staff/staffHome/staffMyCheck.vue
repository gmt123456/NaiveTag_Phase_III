<template>
    <div id="staffMyCheck">
        <task-list v-loading="this.isLoading"
                   :taskListData = this.myCheckList></task-list>
    </div>
</template>

<script>
	import taskList from '../staffTaskList.vue';
	import {myCheck} from "../../../api/staffTask";

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