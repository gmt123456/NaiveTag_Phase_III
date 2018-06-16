<template>
    <div id="workerSearch" style="background-color: #f6f9fa;min-height: 760px;">
        <div style="width: 100%;text-align: center;min-height: 650px;">
            <div style="width: 900px;margin: auto;">
                <el-container style="border: 1px dotted transparent;padding-top: 20px;">
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
                <div></div>
                <task-list v-loading=false :taskListData=this.searchList style="text-align: left;"></task-list>
                <infinite-loading @infinite="addSearchList" ref="infiniteLoading">
                    <span slot="no-more">No more tasks</span>
                </infinite-loading>
            </div>
        </div>

    </div>
</template>

<script>
	import taskList from "../taskList.vue";
	import {searchResult} from "../../../api/workerTaskInfo";
	import InfiniteLoading from 'vue-infinite-loading';

	export default {
		name: "workerSearch",

        data(){
			return {
				step: 10,
				searchBegin: 0,
				searchList: [],
				searchKey: "",
				valueAccept: true,
				typeOptions: [{
					value: 0,
					label: 'All'
				},{
					value: 100,
					label: 'Global_Select'
				},{
					value: 101,
					label: 'Global_Input'
				}, {
					value: 200,
					label: 'Single_Frame_Select'
				},{
					value: 201,
					label: 'Single_Frame_Input'
				},{
					value: 300,
					label: 'Multiple_Frame_Select'
				},{
					value: 301,
					label: 'Multiple_Frame_Input'
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

        methods:{

	        search(){
		        this.searchBegin = 0;
		        let that = this;
		        // console.log("this.typeValue: "+this.typeValue+" this.tagValue: "+this.tagValue+" this.sortValue: "+this.sortValue+" this.searchKey: "+this.searchKey+" this.valueAccept: "+this.valueAccept);
		        searchResult(this.typeValue, this.tagValue, this.sortValue, 0, this.step, this.searchKey, this.valueAccept, res =>{
			        that.searchList = res;
			        that.searchBegin += that.step;
		        })
	        },

	        searchByKey(key){
		        this.searchBegin = 0;
		        let that = this;
		        console.log(key);
		        searchResult(this.typeValue, this.tagValue, this.sortValue, 0, this.step, key, this.valueAccept, res =>{
			        that.searchList = res;
			        that.searchBegin += that.step;
		        })
	        },

	        addSearchList($state){

		        let that = this;
		        // console.log("this.typeValue: "+this.typeValue+" this.tagValue: "+this.tagValue+" this.sortValue: "+this.sortValue+" this.searchKey: "+this.searchKey+" this.valueAccept: "+this.valueAccept);
		        // that.$refs.infiniteLoading.$emit('$InfiniteLoading:loaded');
		        searchResult(this.typeValue, this.tagValue, this.sortValue, this.searchBegin, this.step, this.searchKey, this.valueAccept, res =>{
			        setTimeout(() => {
				        console.log(this.searchBegin);
				        if(res.length === 0 ){
					        $state.complete();
				        }else{
					        that.searchList = that.searchList.concat(res);
					        that.searchBegin += that.step;
					        $state.loaded();
				        }
			        }, 1000);

		        })
	        },

        },

        components: {
			taskList,
            InfiniteLoading,
        },
	}
</script>

<style scoped>

</style>