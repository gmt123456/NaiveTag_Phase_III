<template>
    <div id="staffMain">
        <el-col :span="24"><div :style="{'background-color': getBackGroundColor(), 'min-height': '200px', 'overflow':'hidden'}">

        </div></el-col>

        <el-col :span="24"><div style="background-color: #f6f9fa;text-align: center;overflow:hidden;">
            <div style="width: 900px;height: 40px;margin: auto;">

                <el-menu :default-active="$route.path" mode="horizontal" :router="true"
                         style="height: 50px;position: relative;top: -10px;">
                    <el-menu-item index="/staff/allCheck" style="height: 50px;">AllCheck</el-menu-item>
                    <el-menu-item index="/staff/myCheck" style="height: 50px;">MyCheck</el-menu-item>
                    <el-menu-item index="/staff/allTasks" style="height: 50px;">AllTasks</el-menu-item>
                    <el-menu-item index="/staff/myTasks" style="height: 50px;">MyTasks</el-menu-item>
                </el-menu>

            </div>
        </div></el-col>

        <el-col>
            <div style="width: 100%;background-color: #f6f9fa;">
                <div style="width: 900px;min-height: 740px;margin: auto;">
                    <router-view></router-view>
                </div>
            </div>
        </el-col>


    </div>
</template>

<script>

	export default {
		methods: {
			getBackGroundColor(){
				return "#8cd6b4";
				// switch (this.userInfo.division){
				// 	case "Novice":
				// 		return "#8cd6b4";
				// 	case "Contributor":
				// 		return "#6ad4ff";
				// 	case "Expert":
				// 		return "#967492";
				// 	case "Master":
				// 		return "#f99970";
				// 	case "Grandmaster":
				// 		return "#ddb74b";
				// }
			},
		},
	}
</script>

<style>

    input {
        border: none;
    }

    .bg-purple-dark {
        background: #99a9bf;
    }

    .grid-content {
        min-height: 36px;
    }

    .fade-enter-active, .fade-leave-active {
        transition: all 400ms;
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
        opacity: 0;
    }
    .fade-enter {
        transform: translateY(-170px);
    }
    .fade-leave-active {
        transform: translateY(-170px);
    }

    .left-border {
        border-top-style:none;
        border-right-style:none;
        border-bottom-style:none;
        border-left-style:solid;
        border-color: lightgray;
        border-width: 1px;
    }

    .LR-border {
        border: lightgray solid 1px;
        border-radius: 5px;
    }

    .el-header, .el-footer {
        /*background-color: #B3C0D1;*/
        color: #333;
        text-align: center;
        /*line-height: 50px;*/
    }

    .el-aside {
        /*background-color: #D3DCE6;*/
        background-color: #f6f9fa;
        color: #333;
        /*text-align: center;*/
        /*line-height: 200px;*/
    }

    .el-main {
        background-color: #f6f9fa;
        color: #333;
        /*text-align: center;*/
        /*line-height: 160px;*/
    }
    .center {
        display: flex;
        /*justify-content:center;*/
        align-items: center;
    }

</style>
