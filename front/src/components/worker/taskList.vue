<template>
    <div id="taskList" style="width: 100%;">
        <div class="center" style="width: 100%;height: 40px;background-color: #47494d;color: white;font-size: 13px;">
            <span style="margin-left: 20px;">{{taskListData.length}} Assignments Matched</span>
        </div>
        <div>
            <transition-group v-bind:css="false"
                              v-on:before-enter="beforeEnter"
                              v-on:enter="enter"
                              v-on:leave="leave"
                              name="fadeTask">
                <task-block v-if="show" v-for="(item,index) in taskListData" v-bind="item" :key="index" v-bind:data-index="index"></task-block>
                <infinite-loading v-on:infinite="addList" ref="infiniteLoading" key="loading"><span slot="no-more">没有更多数据了！</span></infinite-loading>
            </transition-group>
        </div>
    </div>
</template>

<script>
	import InfiniteLoading from 'vue-infinite-loading';
    import taskBlock from './taskBlock.vue';
    import Velocity from 'velocity-animate'

	export default {
    	props: {
    		updateList: Boolean,
		    taskListData: Array,
        },

		name: "taskList",

        data(){
			return{
				show: false,
            }
        },

        mounted(){
			this.show = true;
        },

        methods:{
	        addList: function(){
                console.log("addList");
                // if(this.updateList){
			     //    this.$emit('updateList');
                // }
            },

	        beforeEnter: function (el) {
		        el.style.opacity = 0;
		        el.style.translateX = 170;
	        },

	        enter: function (el, done) {
		        let delay = el.dataset.index * 50;
		        setTimeout(function () {
			        Velocity(
				        el,
				        { opacity: 0, translateX: 240 },
				        { duration: 20 }
			        )
			        Velocity(
				        el,
				        { opacity: 1, translateX: 0 },
				        { complete: done }
			        )
		        }, delay)
	        },

	        leave: function (el, done) {
		        let delay = el.dataset.index * 50;
		        setTimeout(function () {
			        Velocity(
				        el,
				        { opacity: 0, translateX: 170 },
				        { complete: done }
			        )
		        }, delay)
	        }


        },

        components: {
			taskBlock,
	        InfiniteLoading
        }
	}
</script>

<style scoped>
    /*.fadeTask-enter-active, .fadeTask-leave-active {*/
        /*transition: all 600ms;*/
    /*}*/
    /*.fadeTask-enter, .fadeTask-leave-to !* .fade-leave-active below version 2.1.8 *! {*/
        /*opacity: 0;*/
    /*}*/
    /*.fadeTask-enter {*/
        /*transform: translateX(-170px);*/
    /*}*/
    /*.fadeTask-leave-active {*/
        /*transform: translateX(-170px);*/
    /*}*/
    .center {
        display:flex;
        /*justify-content:center;*/
        align-items:center;
    }
</style>