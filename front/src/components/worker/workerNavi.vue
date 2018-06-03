<template>
    <div id="workerNavi">
        <el-col :span="24"><div style="background-color: #47494d;height: 50px;justify-content:center;overflow: hidden;" class="center">
            <div style="width: 900px;" class="center">
                <logo style="display: inline"></logo>
                <el-input placeholder="Search assignments" v-model="searchKey" size="mini" style="width: 225px;margin-left: 20px;" clearable>
                    <el-button slot="append" icon="el-icon-search" size="mini"></el-button>
                </el-input>
                <el-menu :default-active="$route.path" mode="horizontal" :router="true"
                         background-color="#47494d"
                         text-color="lightgray"
                         active-text-color="#1B9CFC"
                         style="height: 55px;position: relative;top: -2px;margin-left: 26px;">
                    <el-menu-item index="/recommendation" style="height: 55px;">TaskHall</el-menu-item>
                    <el-menu-item index="/worker/home" style="height: 55px;">Home</el-menu-item>
                    <el-menu-item index="/worker/unfinish" style="height: 55px;">Unfinish</el-menu-item>
                    <el-menu-item index="/worker/finish" style="height: 55px;">Finish</el-menu-item>
                    <el-menu-item index="/worker/rank" style="height: 55px;">Rank</el-menu-item>
                </el-menu>
                <el-button type="text" style="color: lightgrey;margin: 15px;" @click="signOut">Sign out</el-button>
                <img :src="avatar" width="30px" height="30px" style="border-radius: 3px;"/>
            </div>
        </div></el-col>

        <router-view v-on:uploadImage='loadImage'></router-view>
    </div>
</template>

<script>

	import {getUrl} from '../../api/tool'
    import {workerSignOut} from "../../api/workerInfo";
	import logo from '../common/Logo.vue'

	export default {
		name: "workerNavi",

        data() {
			return {
				searchKey: "",
				avatar: ""
            }
        },

        methods: {
	        loadImage(src){
		        this.avatar = getUrl(src);
	        },

	        signOut() {
		        this.$confirm('Are you sure you want to quit?', 'Prompt', {
			        confirmButtonText: 'yes',
			        cancelButtonText: 'no',
			        type: 'info'
		        }).then(() => {
			        workerSignOut(() => {
			        	this.$router.push("/");
			        })
		        }).catch(() => {

		        });
	        }

        },

        components: {
		    logo
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