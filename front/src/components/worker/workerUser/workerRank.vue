<template>
    <div id="workerRank">
        <el-card :body-style="{ padding: '0px' }" style="min-height: 400px;padding-top: 20px;padding-bottom: 20px;">

            <transition-group v-bind:css="false"
                              v-on:before-enter="beforeEnter"
                              v-on:enter="enter"
                              v-on:leave="leave"
                              name="fadeRank">
                <div v-if="show" v-for="(item, index) in rankDataList" :key="index" v-bind:data-index="index" style="text-align: center;margin: 10px;">
                    <el-row style="width: 700px;margin: auto;">
                        <el-col :span="2"><div class="centerR" style="height: 50px;float: right;">
                            <span style="color: gray">{{index + 1}}</span>
                        </div></el-col>
                        <el-col :span="3"><div class="centerR" style="height: 50px;">
                            <img :src="getImgSrc(item.avatar)" width="44px" height="44px" style="border-radius: 6px;"/>
                        </div></el-col>
                        <el-col :span="10"><div class="centerR" style="height: 50px;float: left;">
                            <span style="font-weight: 800;font-size: 15px;">{{item.userName}}</span>
                        </div></el-col>
                        <el-col :span="4"><div class="centerR" style="height: 50px;">
                            <img src="../../../../static/favorite.png" width="15px"/>
                            <span style="color: gray;padding-left: 5px;">{{item.score.toFixed(2)}}</span>
                        </div></el-col>
                        <el-col :span="5"><div class="centerR" style="height: 50px;">
                            <division-pic v-bind:division="item.division" size="50px"></division-pic>
                        </div></el-col>
                    </el-row>
                </div>
            </transition-group>

        </el-card>
    </div>
</template>

<script>

    import divisionPic from '../divisionPic.vue';
	import {workerRank} from "../../../api/workerInfo";
	import {getUrl} from "../../../api/tool";

    export default {
		name: "workerRank",

		mounted(){
			this.show = true;
		},

        data() {
			return {
				rankDataList: [],
				show: false,
            }
        },

		created: function() {
			this.getWorkerRank();
		},

		watch: {
			// 如果路由有变化，会再次执行该方法
			'$route': 'getWorkerRank'
		},

        methods: {
			getImgSrc(src){
				return getUrl(src);
            },

			getWorkerRank(){
				workerRank(res => {
                    this.rankDataList = res;
				})
            },

	        beforeEnter: function (el) {
		        el.style.opacity = 0;
	        },

	        enter: function (el, done) {
		        let delay = el.dataset.index * 50;
		        setTimeout(function () {
			        Velocity(
				        el,
				        { opacity: 0, translateY: 240 },
				        { duration: 20 }
			        )
			        Velocity(
				        el,
				        { opacity: 1, translateY: 0 },
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
	        divisionPic
        }
	}
</script>

<style scoped>
    .centerR {
        display:flex;
        justify-content:center;
        align-items:center;
    }
</style>