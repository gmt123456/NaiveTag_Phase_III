<template>
    <div id="workerUnfinish">
        <task-list :taskListData = this.taskUnfinishData></task-list>
    </div>
</template>

<script>
	import taskList from '../taskList.vue';
	import {workerUnfinish} from "../../../api/workerInfo";

	export default {
		name: "workerUnfinish",

        created() {
			this.getUnfinishDataList();
        },

        data(){
			return {
				taskUnfinishData: [],
            }
        },

		methods: {
			getUnfinishDataList(){
				let that = this;
				workerUnfinish(res =>{
					that.taskUnfinishData = res;
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