<template>
    <div id="recommendation" style="background-color: #f6f9fa;min-height: 760px;">
        <div style="width: 100%;height: 150px;background-color: #0088c0;text-align: center">
            <div style="width: 900px;height: 100px;margin: auto;" class="center">
                <div style="color: white;font-size: 24px;">Task Hall</div>
                <el-button plain style="margin-left: 620px;width: 170px;" size="small">Learn more</el-button>
            </div>
        </div>
        <div style="width: 100%;text-align: center;min-height: 500px;">
            <div style="width: 900px;margin: auto;">

                <el-tabs v-model="activeName3" type="card" @tab-click="handleClick" style="margin-top: 30px">
                    <el-tab-pane label="Recommendation" name="Recommendation"></el-tab-pane>
                    <el-tab-pane label="SearchResult" name="SearchResult">
                        <el-container>
                            <el-aside style="padding-bottom: 15px;text-align: left;width: auto;">
                                <el-switch
                                        v-model="valueAccept"
                                        active-text="Accept-Only"
                                        inactive-text=""
                                        @change="search">
                                </el-switch>
                            </el-aside>
                            <el-aside style="width: 766px;color: gray;font-size: 14px;text-align: right;">

                                <span style="padding-left: 20px;">type: </span>
                                <el-select v-model="typeValue" placeholder="请选择" size="mini" @change="search" style="width: 100px;">
                                    <el-option
                                            v-for="item in typeOptions"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>

                                <span style="padding-left: 20px;">tag: </span>
                                <el-select v-model="tagValue" placeholder="请选择" size="mini" @change="search" style="width: 100px;">
                                    <el-option
                                            v-for="item in tagOptions"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>

                                <span style="padding-left: 20px;">sort by: </span>
                                <el-select v-model="sortValue" placeholder="请选择" size="mini" @change="search" style="width: 100px;">
                                    <el-option
                                            v-for="item in sortOptions"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>

                                <el-input placeholder="Search assignments" v-model="searchKey" size="mini" style="width: 225px;margin-left: 20px;" clearable
                                          @keyup.enter.native="search">
                                    <el-button slot="append" icon="el-icon-search" size="mini" @click="search"></el-button>
                                </el-input>

                            </el-aside>
                        </el-container>
                    </el-tab-pane>
                </el-tabs>

                <div></div>
                <task-list v-loading=false :taskListData=this.searchList style="text-align: left;"></task-list>

                <infinite-loading @infinite="addList" ref="infiniteLoading">
                    <span slot="no-more">No more tasks</span>
                </infinite-loading>
            </div>
        </div>
    </div>
</template>

<script>
	import InfiniteLoading from 'vue-infinite-loading';
	import taskList from "../taskList.vue";
    import {searchResult} from "../../../api/workerTaskInfo";

    export default {
		name: "recommendation",

        data(){
			return {
				step: 10,
                begin: 0,
				activeName3: "Recommendation",
				searchList: [],
				valueAccept: true,
				searchKey: "",
				typeOptions: [{
					value: 0,
					label: 'All'
				},{
					value: 100,
					label: 'Global_Input'
				},{
					value: 101,
					label: 'Global_Select'
				}, {
					value: 200,
					label: 'Single_Frame_Input'
				},{
					value: 201,
					label: 'Single_Frame_Select'
				},{
					value: 300,
					label: 'Multiple_Frame_Input'
				},{
					value: 301,
					label: 'Multiple_Frame_Select'
				},{
					value: 400,
					label: 'Area_Only'
				}, {
					value: 401,
					label: 'Area_Input'
				}],
				typeValue: 0,
				tagOptions: [{
					value: 'all',
					label: 'All'
				},{
					value: 'military',
					label: 'Military'
				}, {
					value: 'nature',
					label: 'Nature'
				},{
					value: 'sports',
					label: 'Sports'
				},{
					value: 'humanity',
					label: 'Humanity'
				},{
					value: 'science',
					label: 'Science'
				},{
					value: 'politics',
					label: 'Politics'
				},{
					value: 'others',
					label: 'Others'
				}],
				tagValue: 'all',
				sortOptions: [{
					value: 'DEFAULT',
					label: 'Default'
				},{
					value: 'MONEY_DESCEND',
					label: 'Money_Descend'
				}, {
					value: 'MONEY_ASCEND',
					label: 'Money_Ascend'
				}],
				sortValue: "DEFAULT",
            }
        },

        mounted(){
	        this.$emit('searchReady');
        },

        methods: {
            search(){
            	let that = this;
            	// console.log("this.typeValue: "+this.typeValue+" this.tagValue: "+this.tagValue+" this.sortValue: "+this.sortValue+" this.searchKey: "+this.searchKey+" this.valueAccept: "+this.valueAccept);
	            searchResult(this.typeValue, this.tagValue, this.sortValue, 0, this.step, this.searchKey, this.valueAccept, res =>{
	            	that.searchList = res;
		            that.begin += that.step;
	            })
            },

            searchByKey(key){
	            let that = this;
	            console.log(key);
	            searchResult(this.typeValue, this.tagValue, this.sortValue, 0, this.step, key, this.valueAccept, res =>{
		            that.searchList = res;
		            that.begin += that.step;
	            })
            },

	        addList(){

		        this.$refs.infiniteLoading.$emit('$InfiniteLoading:loaded');
		        let that = this;
		        // console.log("this.typeValue: "+this.typeValue+" this.tagValue: "+this.tagValue+" this.sortValue: "+this.sortValue+" this.searchKey: "+this.searchKey+" this.valueAccept: "+this.valueAccept);
		        // that.$refs.infiniteLoading.$emit('$InfiniteLoading:loaded');
		        searchResult(this.typeValue, this.tagValue, this.sortValue, this.begin, this.step, this.searchKey, this.valueAccept, res =>{
		        	console.log(this.begin);
			        if(res.length === 0 ){
				        that.$refs.infiniteLoading.$emit('$InfiniteLoading:complete');
                    }else{
				        that.searchList = that.searchList.concat(res);
				        that.begin += that.step;
                    }
		        })
            },

	        handleClick(){

            }

        },

        components: {
	        taskList,
	        InfiniteLoading,
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