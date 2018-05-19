<template>
    <div>
      <index-navi></index-navi>
        <el-col :span="8" :offset="8">
            <el-form :model="signUpForm" :rules="rules" ref="signUpForm" status-icon>
                <el-form-item label="账号" prop="username">
                    <el-input v-model="signUpForm.username"></el-input>
                </el-form-item>
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="signUpForm.nickname"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="signUpForm.email"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="signUpForm.password" type="password"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass" >
                    <el-input v-model="signUpForm.checkPass" type="password"></el-input>
                </el-form-item>
                <el-form-item label="用户类型">
                    <el-radio-group v-model="signUpForm.userType">
                        <el-radio label="worker">众包工人</el-radio>
                        <el-radio label="requester">众包发布者</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-button @click="signUp" style="width: 100%">注册</el-button>
                </el-form-item>

            </el-form>
        </el-col>

    </div>
</template>

<script>
    import {signUp} from "../../api/signUp";
    import IndexNavi from "./IndexNavi";

    export default {
        name: "signUp",
      components: {IndexNavi},
      data: function () {
            const confirmPassword = (rule, value, callback) => {
                if (value !== this.signUpForm.password) {
                    callback(new Error('两次输入密码不一致！'));
                } else {
                    callback();
                }
            };
            const validateEmail = (rule, value, callback) => {
                const reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                if (reg.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入有效的邮箱地址'));
                }
            };


            return {
                signUpForm: {
                    username: '',
                    email: '',
                    password: '',
                    checkPass: '',
                    nickname: '',
                    userType: 'worker'
                },
                rules: {
                    checkPass: [
                        {validator: confirmPassword, trigger: 'blur'},
                        {required: true, message: '请再次输入密码', trigger: 'blur'}
                    ],
                    email: [
                        {validator: validateEmail, trigger: 'blur'},
                        {required: true, message: '请输入邮箱', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 8, message: '密码至少应包含八个字符', trigger: 'blur'}
                    ],
                    username: [
                        {required: true, message: '请输入账号', trigger: 'blur'}
                    ],
                    nickname: [
                        {required: true, message: '请输入昵称', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            fastSignUp() {
                let form = this.signUpForm;
                form.userType = 'worker';
                form.password = '123456789';
                form.checkPass = '123456789';
                form.email = '111@qq.com';
                form.nickname = 'naive';
                form.username = '1';
            },
            signUp() {
                let that = this;
                this.$refs['signUpForm'].validate((valid) => {
                    if (valid) {
                        signUp(this.signUpForm,
                            res => {
                                if (res.result === 'success') {
                                    that.$router.push('/login');
                                } else {
                                    let message;
                                    switch (res.result) {
                                        case 'repeatedUsername':
                                            message = '用户名重复';
                                            break;
                                        case 'repeatedName':
                                            message='昵称重复';
                                            break;
                                        case 'repeatedEmail':
                                            message='邮箱重复';
                                            break
                                    }
                                    this.$alert(message,'',{
                                        confirmButtonText:'确定'
                                    })
                                }
                            });
                    } else {

                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>
