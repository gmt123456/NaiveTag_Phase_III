<template>
    <div id="test">
        <el-input v-model="input1" placeholder="请输入内容"></el-input>
        <el-input v-model="input2" placeholder="请输入内容"></el-input>

        <el-input v-model="input3" placeholder="请输入内容"></el-input>
        <el-input v-model="input4" placeholder="请输入内容"></el-input>

        <el-input v-model="input5" placeholder="请输入内容"></el-input>
        <el-input v-model="input6" placeholder="请输入内容"></el-input>

        <el-input v-model="input7" placeholder="请输入内容"></el-input>
        <el-input v-model="input8" placeholder="请输入内容"></el-input>

        <el-input v-model="input9" placeholder="请输入内容"></el-input>
        <el-input v-model="input10" placeholder="请输入内容"></el-input>

        <el-button @click="click">button</el-button>
    </div>
</template>

<script>
    import {getUrl} from "../api/tool";

    export default {
		data() {
			return {
				input1: '',
				input2: '',
				input3: '',
				input4: '',
				input5: '',
				input6: '',
				input7: '',
				input8: '',
				input9: '',
				input10: '',
			}
		},

        methods:{
	        click(){
	        	let that = this;
		        that.fromServer(this.input1, res =>{
		        	console.log("successtest");
                })
            },

	        fromServer(input, callback) {
		        $.get(getUrl('login.html'), {
			        token: localStorage.token,
                    input: input,
		        }, function (result) {
			        callback(JSON.parse(result))
		        })
	        }
        }
	}
</script>

<style scoped>

</style>