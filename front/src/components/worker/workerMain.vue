<template>
    <div id="workerMain">

        <el-col :span="24"><div style="background-color: #47494d;min-height: 50px;"></div></el-col>
        <el-col :span="24"><div style="background-color: #8cd6b4;min-height: 200px;overflow:hidden;">

            <div style="width: 900px;height: 40px;margin: auto;">
                <div style="height: 30px;"></div>
                <el-container>

                    <el-aside width="170px" height="170px" style="background-color: #D3DCE6;">
                        <transition name="fade">
                            <div v-if="isEditing" @click="changePicVisible = true" style="font-size: 14px;color: white;width: 170px;height: 170px;z-index: 1;position: absolute;background-color:rgba(0,0,0,0.2);text-align: center;cursor: pointer;">
                                <div style="padding-top: 66px;">Change your</div>
                                <div>profile photo</div>
                            </div>
                        </transition>
                        <img :src="userInfo.avaster" width="170px" height="170px">
                    </el-aside>

                    <el-main>

                        <div style="position: absolute;">
                          <transition name="fade">

                                <div v-if="isEditing" style="width: 250px;position: absolute;" key="edit">
                                    <el-form status-icon :model="userInfo" :rules="rules" ref="userInfo" label-width="0px" size="mini">
                                        <el-form-item prop="userName">
                                            <el-input v-model="userInfo.userName" style="font-size: 15px;"></el-input>
                                        </el-form-item>
                                    </el-form>
                                    <el-button type="text" style="padding-left: 5px;" @click="dialogVisible = true">change password</el-button>
                                </div>

                                <div v-else style="width: 380px;position: absolute;" key="save">
                                    <div style="font-weight: 800;font-size: 15px;">{{userInfo.userName}}</div>

                                    <div style="color: darkgrey;font-size: 13px;">{{userInfo.email}}</div>

                                    <div style="padding-top: 66px;color: darkgrey;font-size: 13px;">{{userInfo.joint}} · {{userInfo.lastVisit}}</div>
                                </div>

                            </transition>
                        </div>

                    </el-main>

                    <el-aside width="150px" height="170px" style="float: right;">
                        <img src="static/data.png" width="15px" style="padding-top: 34px;">
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
                    </el-aside>

                    <el-aside width="150px" class="left-border">
                        <img v-if="this.userInfo.division === 'Contributor'" src="static/grade/contributor.png" width="148px" height="auto">
                        <img v-if="this.userInfo.division === 'Novice'" src="static/grade/novice.png" width="148px" height="auto">
                        <img v-if="this.userInfo.division === 'Expert'" src="static/grade/expert.png" width="148px" height="auto">
                        <img v-if="this.userInfo.division === 'Master'" src="static/grade/master.png" width="148px" height="auto">
                        <img v-if="this.userInfo.division === 'Grandmaster'" src="static/grade/grandmaster.png" width="148px" height="auto">
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
                    <el-button v-if="!isEditing" type="primary" style="float: right;height: 40px;width: 150px;margin-top: 10px;" @click="editHandle">Edit Profile</el-button>
                    <el-button v-if="isEditing" type="success" style="float: right;height: 40px;width: 150px;margin-top: 10px;" @click="submitForm('userInfo')">Save Profile</el-button>
                </el-menu>

            </div>
        </div></el-col>

        <div style="width: 100%;text-align: center;background-color: #f6f9fa;">
            <div style="width: 900px;min-height: 740px;margin: auto;background-color: #fbfbfb;border: lightgray solid 1px;border-radius: 5px;">
                <router-view></router-view>
            </div>
        </div>


        <el-dialog
                title="change password"
                :visible.sync="dialogVisible"
                width="400px">
            <div>
                <el-form :model="password" status-icon :rules="rulesPassword" ref="password" label-width="100px">
                    <el-form-item label="oldPassword" prop="oldPassword">
                        <el-input type="password" v-model="password.oldPassword" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="newPassword" prop="newPassword">
                        <el-input type="password" v-model="password.newPassword" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="check" prop="check">
                        <el-input type="password" v-model="password.check" auto-complete="off"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer">
                <el-button size="medium" @click="dialogVisible = false">cancel</el-button>
                <el-button size="medium" type="primary" @click="submitPassword('password')">submit</el-button>
            </span>
        </el-dialog>

        <el-dialog
                title="change your profile photo"
                :visible.sync="changePicVisible"
                width="900px">
            <div>
                <change-pic :img="this.userInfo.avaster"></change-pic>
            </div>
            <span slot="footer">
                <el-button size="medium" @click="changePicVisible = false">cancel</el-button>
                <el-button size="medium" type="primary" @click="submitPic">save</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>

    import changePic from './workerPic.vue';

    export default {
        data() {

	        let validatePass = (rule, value, callback) => {
		        if (value === '') {
			        callback(new Error('Please type your password!'));
		        } else {
			        if (this.password.check !== '') {
				        this.$refs.password.validateField('check');
			        }
			        callback();
		        }
	        };
	        let validateCheckPass = (rule, value, callback) => {
		        if (value === '') {
			        callback(new Error('Please type your password again!'));
		        } else if (value !== this.password.newPassword) {
			        callback(new Error('The two passwords you typed do not match!'));
		        } else {
			        callback();
		        }
	        };

            return {
                // activeIndex: '/home',
	            dialogVisible: false,
	            changePicVisible: false,
	            isEditing: false,
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
                },
                password: {
	            	oldPassword: "",
                    newPassword: "",
                    check: ""
                },
	            rules: {
		            userName: [
			            { required: true, message: '请输入你的名字', trigger: ['blur', 'change'] },
			            { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
		            ],
                    email: [
	                    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
	                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                    ],

	            },
	            rulesPassword: {
		            oldPassword: [
			            { validator: validatePass, trigger: 'blur' }
		            ],
		            newPassword: [
			            { validator: validatePass, trigger: 'blur' }

		            ],
		            check: [
			            { validator: validateCheckPass, trigger: 'blur' }
		            ]
                },

            };
        },

        methods: {

        	//提交图片进行的处理
	        submitPic(){
		        this.changePicVisible = false;
            },

	        editHandle(){
	        	this.isEditing = true;
            },

	        getVirtulData(year) {
		        let date = + this.$echarts.number.parseDate(year + '-01-01');
		        let end = + this.$echarts.number.parseDate(year + '-12-31');
		        let dayTime = 3600 * 24 * 1000;
		        let data = [];
		        for (let time = date; time <= end; time += dayTime) {
			        data.push([
				        this.$echarts.format.formatTime('yyyy-MM-dd', time),
				        Math.random()*200
			        ]);
		        }
		        return data;
	        },

	        submitForm(formName) {
		        this.$refs[formName].validate((valid) => {
			        if (valid) {

				        this.isEditing = false;
			        } else {
				        console.log('error submit!!');
				        return false;
			        }
		        });
	        },

	        submitPassword(formName) {
		        this.$refs[formName].validate((valid) => {
			        if (valid) {
				        alert('submit!');
				        this.dialogVisible = false;
			        } else {
				        console.log('error submit!!');
				        return false;
			        }
		        });
	        },


        },

        components:{
	        changePic
        }

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
        transition: all 1s;
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

</style>
