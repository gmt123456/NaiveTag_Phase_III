<template>
    <div id="workerMain">
        <el-col :span="24"><div :style="{'background-color': getBackGroundColor(), 'min-height': '200px', 'overflow':'hidden'}">

            <div style="width: 900px;height: 40px;margin: auto;">
                <div style="height: 30px;"></div>
                <el-container>

                    <el-aside width="170px" height="170px" style="background-color: #D3DCE6;">
                        <transition name="fade">
                            <div v-if="isEditing" @click="changePicStart" style="font-size: 14px;color: white;width: 170px;height: 170px;z-index: 1;position: absolute;background-color:rgba(0,0,0,0.2);text-align: center;cursor: pointer;">
                                <div style="padding-top: 66px;">Change your</div>
                                <div>profile photo</div>
                            </div>
                        </transition>
                        <img :src="getImage()" width="170px" height="170px">
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
                        <img src="../../../static/data.png" width="15px" style="padding-top: 34px;">
                        <span> {{userInfo.rank}}</span>
                        <span style="color: darkgrey;font-size: 13px;">rank</span>
                        <p></p>
                        <img src="../../../static/dollar.png" width="15px">
                        <span> {{userInfo.dollars.toFixed(2)}}</span>
                        <span style="color: darkgrey;font-size: 13px;">dollars</span>
                        <p></p>
                        <img src="../../../static/favorite.png" width="15px">
                        <span> {{userInfo.score.toFixed(0)}}</span>
                        <span style="color: darkgrey;font-size: 13px;">score</span>
                    </el-aside>

                    <el-aside width="150px" class="left-border">
                        <division-pic v-bind:division="this.userInfo.division" size="148px"></division-pic>
                    </el-aside>

                </el-container>
            </div>



        </div></el-col>

        <el-col :span="24"><div style="background-color: #f6f9fa;text-align: center;overflow:hidden;">
            <div style="width: 900px;height: 40px;margin: auto;">

                <el-menu :default-active="$route.path" mode="horizontal" :router="true"
                         style="height: 50px;position: relative;top: -10px;">
                    <el-menu-item index="/worker/home" style="height: 50px;">Home</el-menu-item>
                    <el-menu-item index="/worker/unfinish" style="height: 50px;">Unfinish</el-menu-item>
                    <el-menu-item index="/worker/finish" style="height: 50px;">Finish</el-menu-item>
                    <el-menu-item index="/worker/rank" style="height: 50px;">Rank</el-menu-item>
                    <!--<el-menu-item index="/worker/test" style="height: 50px;">Recommendation</el-menu-item>-->
                    <el-button v-if="!isEditing" type="primary" style="float: right;height: 40px;width: 150px;margin-top: 10px;" @click="editHandle">Edit Profile</el-button>
                    <el-button v-if="isEditing" type="success" style="float: right;height: 40px;width: 150px;margin-top: 10px;" @click="submitForm('userInfo')">Save Profile</el-button>
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
                <change-pic :imageSrc=getImage() ref="changePic" v-on:save-res="savePic"></change-pic>
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
    import divisionPic from './divisionPic.vue';
    import {getUrl} from '../../api/tool'
    import {workerInfo} from "../../api/workerInfo";
    import {workerEditPassword} from "../../api/workerInfo";
    import {workerEditUserName} from "../../api/workerInfo";

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
                name: "",
                userInfo: {
                    "avatar": "", // url
                    "userName": "",
                    "email": "",
                    "lastVisit": "",
                    "rank": 0,
                    "joint": "",
                    "dollars": 0,
                    "division": "Novice", //(可能的取值：Novice, Contributor, Expert, Master, Grandmaster)
                    "score": 0,
                },
                password: {
	            	oldPassword: "",
                    newPassword: "",
                    check: ""
                },
	            rules: {
		            userName: [
			            { required: true, message: '请输入你的名字', trigger: ['blur', 'change'] },
			            // { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: ['blur', 'change'] }
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

        	getBackGroundColor(){
        		switch (this.userInfo.division){
                    case "Novice":
                    	return "#8cd6b4";
			        case "Contributor":
				        return "#6ad4ff";
			        case "Expert":
				        return "#967492";
			        case "Master":
				        return "#f99970";
			        case "Grandmaster":
				        return "#ddb74b";
                }
            },

	        getImage(){
            return getUrl(this.userInfo.avatar);
          },

        	editFail(){
		        this.$message.error('change failed！(；′⌒`)');
	        },
            editSuccess(){
	            this.$message({
		            message: 'change success! ψ(｀∇´)ψ',
		            type: 'success'
	            });
            },

        	getUserInfo(){
        		let that = this;
		        workerInfo(res => {
			        that.userInfo = res;
			        that.$emit('uploadImage',that.userInfo.avatar);
		        });
            },

	        changePicStart(){
		        this.changePicVisible = true;
		        // this.$refs.changePic.startPic();
	        },

        	//提交图片进行的处理
	        submitPic(){
		        this.$refs.changePic.savePic();
            },

            savePic(res){
	            if(res.state === "success"){
		            this.getUserInfo();
		            this.editSuccess();
		            this.changePicVisible = false;
		            this.isEditing = false;
	            }else{
		            this.editFail();
	            }
            },

	        editHandle(){
        		this.name = this.userInfo.userName;
	        	this.isEditing = true;
            },

	        submitForm(formName) {
		        let that = this;
		        this.$refs[formName].validate((valid) => {
			        if (valid) {
			        	if(!(that.userInfo.userName === that.name)){
					        workerEditUserName(that.userInfo.userName, result => {
						        if(result.state === "success"){
							        that.editSuccess();
						        }else{
							        that.editFail();
						        }
						        that.getUserInfo();
					        });
                        }
				        that.isEditing = false;
			        } else {
				        console.log('error submit!!');
				        return false;
			        }
		        });
	        },

	        submitPassword(formName) {
		        let that = this;
		        this.$refs[formName].validate((valid) => {
			        if (valid) {
				        workerEditPassword(that.password.oldPassword, that.password.newPassword, result => {
				        	if(result.state === "success"){
						        that.editSuccess();
						        that.dialogVisible = false;
                            }else if(result.state === "invalid password"){
				        		that.$message.error("invalid password！ (；′⌒`)");
                            }else{
				        		that.editFail();
                            }
					        that.getUserInfo();
                        });
			        } else {
				        console.log('error submit!!');
				        return false;
			        }
		        });
	        },


        },

        created: function() {
	        this.getUserInfo();
        },

	    watch: {
		    // 如果路由有变化，会再次执行该方法
		    '$route': 'getUserInfo'
	    },

        components:{
	        changePic,
	        divisionPic
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
