<template>
    <div id="staffNavi">
        <el-col :span="24"><div style="background-color: #47494d;height: 50px;justify-content:center;overflow: hidden;" class="center">
            <div style="width: 900px;" class="center">
                <logo style="display: inline"></logo>
                <el-menu :default-active="$route.path" mode="horizontal" :router="true"
                         background-color="#47494d"
                         text-color="lightgray"
                         active-text-color="#1B9CFC"
                         style="height: 55px;position: relative;top: -2px;margin-left: 26px;">
                    <el-menu-item index="/staff/allCheck" style="height: 55px;">AllCheck</el-menu-item>
                    <el-menu-item index="/staff/myCheck" style="height: 55px;">MyCheck</el-menu-item>
                    <el-menu-item index="/staff/allTasks" style="height: 55px;">AllTasks</el-menu-item>
                    <el-menu-item index="/staff/myTasks" style="height: 55px;">MyTasks</el-menu-item>
                </el-menu>
                <div style="width: 322px;">

                </div>
                <el-button type="text" style="color: lightgrey;margin-left: 15px;" @click="signOut">Sign out</el-button>
            </div>
        </div></el-col>

        <router-view></router-view>
    </div>
</template>

<script>

	import {getUrl} from '../../api/tool'
    import {workerSignOut} from "../../api/workerInfo";
	import logo from '../common/Logo.vue'

	export default {
		name: "staffNavi",

        data() {
			return {
				searchKey: "",
				avatar: "",
                searchReady: false,
            }
        },

        methods: {

	        signOut() {
		        this.$confirm('Are you sure you want to quit?', 'Prompt', {
			        confirmButtonText: 'yes',
			        cancelButtonText: 'no',
			        type: 'info'
		        }).then(() => {
			        // workerSignOut(() => {
			        	this.$router.push("/makeNaiveTagGreat");
			        // })
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