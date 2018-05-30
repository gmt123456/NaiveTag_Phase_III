<template>
    <div id="test">
        <task-list v-loading="this.isLoading"
                   :taskListData = this.taskUnfinishData></task-list>
    </div>
</template>

<script>
	import taskList from '../taskList.vue';
    import {testTaskAll} from "../../../api/workerInfo";

	export default {
		name: "test",

		created() {
			this.getUnfinishDataList();
		},

		data(){
			return {
				taskUnfinishData: [],
				isLoading: true,
			}
		},

		methods: {
			getUnfinishDataList(){
				let that = this;
				testTaskAll(0, "all", "DEFAULT", 0, 100, res =>{
					that.taskUnfinishData = res;
					this.isLoading = false;
				});
			},
		},

		watch: {
			// 如果路由有变化，会再次执行该方法
			'$route': 'getUnfinishDataList'
		},

		components: {
			taskList
		}
	}
</script>

<style scoped>

</style>