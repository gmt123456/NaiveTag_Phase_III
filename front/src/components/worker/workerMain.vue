<template>
    <div id="workerMain">

        <el-col :span="24"><div style="background-color: #47494d;min-height: 50px;"></div></el-col>
        <el-col :span="24"><div style="background-color: #8cd6b4;min-height: 190px;overflow:hidden;">

            <div style="width: 900px;height: 40px;margin: auto;">
                <div style="height: 30px;"></div>
                <el-container>

                    <el-aside width="170px" height="170px" style="background-color: #D3DCE6;">
                        <img :src="userInfo.avaster" width="170px" height="170px">
                    </el-aside>

                    <el-main>
                        <div style="font-weight: 800;display: inline-block;">{{userInfo.userName}}</div>
                        <div style="float: right;padding-top: 10px;font-size: 15px;">
                            <img src="static/data.png" width="15px">
                            <span> {{userInfo.rank}}</span>
                            <span style="color: darkgrey;font-size: 13px;">rank</span>
                            <p></p>
                            <img src="static/dollar.png" width="15px">
                            <span> {{userInfo.dollars}}</span>
                            <span style="color: darkgrey;font-size: 13px;">dollars</span>
                            <p></p>
                            <img src="static/favorite.png" width="15px">
                            <span> {{userInfo.score}}</span>
                            <span style="color: darkgrey;font-size: 13px;">score</span>
                        </div>

                        <div style="color: darkgrey;font-size: 13px;padding-bottom: 60px;">{{userInfo.email}}</div>


                        <div style="color: darkgrey;font-size: 13px;">{{userInfo.joint}} · {{userInfo.lastVisit}}</div>
                    </el-main>

                    <el-aside width="150px" class="left-border">
                        <img v-if="this.userInfo.division === 'Contributor'" src="static/grade/contributor.png" width="150px" height="auto">
                        <img v-if="this.userInfo.division === 'Novice'" src="static/grade/novice.png" width="150px" height="auto">
                        <img v-if="this.userInfo.division === 'Expert'" src="static/grade/expert.png" width="150px" height="auto">
                        <img v-if="this.userInfo.division === 'Master'" src="static/grade/master.png" width="150px" height="auto">
                        <img v-if="this.userInfo.division === 'Grandmaster'" src="static/grade/grandmaster.png" width="150px" height="auto">
                    </el-aside>

                </el-container>
            </div>



        </div></el-col>

        <el-col :span="24"><div style="background-color: #f6f9fa;text-align: center;overflow:hidden;">
            <div style="width: 900px;height: 40px;margin: auto;">

                <el-menu :default-active="$route.path" mode="horizontal" :router="true"
                         style="height: 50px;position: relative;top: -10px;">
                    <el-menu-item index="/home" style="height: 50px;">Home</el-menu-item>
                    <el-menu-item index="/unfinish" style="height: 50px;">Unfinish</el-menu-item>
                    <el-menu-item index="/finish" style="height: 50px;">Finish</el-menu-item>
                    <el-button type="primary" style="float: right;height: 40px;width: 150px;margin-top: 10px;">Edit Profile</el-button>
                </el-menu>

            </div>
        </div></el-col>

        <div style="width: 100%;text-align: center;background-color: #f6f9fa;">
            <div style="width: 900px;min-height: 740px;margin: auto;background-color: #fbfbfb;" class="LR-border">
                <router-view></router-view>
            </div>
        </div>


    </div>
</template>

<script>

    export default {
        data() {
            return {
                activeIndex: '/home',
                userInfo: {
                    "avaster": "static/1.png", // url
                    "userName": "Junda",
                    "email": "123456@qq.com",
                    "lastVisit": "last seen in the past day",
                    "rank": 8048000,
                    "joint": "joined a minute ago",
                    "dollars": 6.66,
                    "division": "Expert", //(可能的取值：Novice, Contributor, Expert, Master, Grandmaster)
                    "score": 88.9,
                    "dollarChanges": [
                        {"date": "17-03-01", "value": 13.3},
                        {"date": "17-03-01", "value": 13.3},
                        {"date": "17-03-01", "value": 13.3},
                    ],
                    "scoreChanges": [
                        {"date": "17-03-01", "value": 18.3},
                        {"date": "17-03-01", "value": 18.3},
                        {"date": "17-03-01", "value": 18.3},
                        {"date": "17-03-01", "value": 18.3},
                    ],
                    "Activity": [0, 1, 0, 0, 1] // 365个数字，表示活跃度，瑞年要不要考虑回头再议，反正2018年不是瑞年
                }
            };
        },

        methods: {
            startHacking () {
                this.$notify({
                    title: 'It works!',
                    type: 'success',
                    message: 'We\'ve laid the ground work for you. It\'s time for you to build something epic!',
                    duration: 5000
                })
            }
        }
    }
</script>

<style>

    .bg-purple-dark {
        background: #99a9bf;
    }

    .grid-content {
        min-height: 36px;
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
        border-top-style:none;
        border-right-style:solid;
        border-bottom-style:none;
        border-left-style:solid;
        border-color: lightgray;
        border-width: 1px;
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
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        background-color: #f6f9fa;
        color: #333;
        /*text-align: center;*/
        /*line-height: 160px;*/
    }

</style>
