<template>
    <div id="recommendation" style="background-color: #f6f9fa;min-height: 760px;">
        <!--<div style="width: 100%;height: 150px;background-color: #0088c0;text-align: center">-->
            <!--<div style="width: 900px;height: 100px;margin: auto;" class="center">-->
                <!--<div style="color: white;font-size: 24px;">Task Hall</div>-->
                <!--<el-button plain style="margin-left: 620px;width: 170px;" size="small">Learn more</el-button>-->
            <!--</div>-->
        <!--</div>-->
        <div style="width: 100%;text-align: center;min-height: 650px;">
            <div style="width: 900px;margin: auto;">

                <!--<el-tabs v-model="activeName3" type="card" @tab-click="handleClick" style="margin-top: 30px">-->

                    <!--<el-tab-pane label="Recommendation" name="Recommendation">-->

                        <task-list v-loading=false :taskListData=this.recommendationList style="text-align: left;padding-top: 70px;"></task-list>
                        <!--<infinite-loading @infinite="addRecommendationList" ref="infiniteLoading">-->
                            <!--<span slot="no-more">No more tasks</span>-->
                        <!--</infinite-loading>-->
                    <!--</el-tab-pane>-->

                <!--</el-tabs>-->

            </div>
        </div>
    </div>
</template>

<script>
	// import InfiniteLoading from 'vue-infinite-loading';
	import taskList from "../taskList.vue";
    // import {searchResult} from "../../../api/workerTaskInfo";
    import {workerRecommendation} from "../../../api/workerInfo";

	export default {
		name: "recommendation",

        data(){
			return {
                step: 10,
                recommendationBegin: 0,
				activeName3: "Recommendation",
                recommendationList: [],
            }
        },

        created(){
			this.getRecommendation();
			console.log(this.recommendationList);
        },

        methods: {

			getRecommendation(){
				this.recommendationBegin = 0;
				let that = this;
				workerRecommendation(res =>{
					that.recommendationList = res;
                })
            },

	        addRecommendationList($state){
		        this.recommendationBegin = 0;

		        let that = this;
		        // console.log("this.typeValue: "+this.typeValue+" this.tagValue: "+this.tagValue+" this.sortValue: "+this.sortValue+" this.searchKey: "+this.searchKey+" this.valueAccept: "+this.valueAccept);
		        // that.$refs.infiniteLoading.$emit('$InfiniteLoading:loaded');
		        // searchResult(this.typeValue, this.tagValue, this.sortValue, this.recommendationBegin, this.step, this.searchKey, this.valueAccept, res =>{
		        workerRecommendation(res =>{
			        setTimeout(() => {
				        console.log(this.recommendationBegin);
				        if(res.length === 0 ){
					        $state.complete();
				        }else{
					        that.recommendationList = that.recommendationList.concat(res);
					        that.recommendationBegin += that.step;
					        $state.loaded();
				        }
			        }, 1000);

		        })
	        },

	        handleClick(){

            }

        },

        components: {
	        taskList,
	        // InfiniteLoading,
        }
	}
</script>

<style scoped>
    .center {
        display: flex;
        /*justify-content:center;*/
        align-items: center;
    }
</style>