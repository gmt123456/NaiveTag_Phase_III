<template>
    <div id="workerPic">
        <el-container>
            <el-main>
                <div class="wrapper">
                    <vueCropper
                            ref="cropper"
                            :img="imageSrc"
                            :outputSize="example2.size"
                            :outputType="example2.outputType"
                            :info="example2.info"
                            :canScale="example2.canScale"
                            :autoCrop="example2.autoCrop"
                            :autoCropWidth="example2.autoCropWidth"
                            :autoCropHeight="example2.autoCropHeight"
                            :fixed="example2.fixed"
                            :fixedNumber="example2.fixedNumber"
                            :full="example2.full"
                            @realTime="realTime"
                    ></vueCropper>
                </div>
            </el-main>
            <el-aside style="width: 230px;">

                <div style="height: 20px;"></div>
                <span style="padding-left: 5px;color: gray;">preview</span>
                <div :style="{'width': previews.w + 'px', 'height': previews.h + 'px',  'overflow': 'hidden', 'margin': '5px', 'border': 'lightgray solid 1px'}">
                    <div :style="previews.div">
                        <img :src="previews.url" :style="previews.img">
                    </div>
                </div>

                <div style="width: 200px;padding-left: 5px;">
                    <el-button @click="rotateLeft" circle icon="el-icon-arrow-left"></el-button>
                    <el-button @click="rotateRight" circle icon="el-icon-arrow-right"></el-button>
                    <p></p>
                    <el-button style="width: 200px;" type="primary" @click="handleUpload">upload<i class="el-icon-upload el-icon--right"></i></el-button>
                    <input type="file" id="uploads" style="position:absolute; clip:rect(0 0 0 0);" accept="image/png, image/jpeg, image/gif, image/jpg" @change="uploadImg($event, 2)">
                    <p></p>
                    <el-button style="width: 200px;" @click="down('base64')" type="primary" plain>download<i class="el-icon-download el-icon--right"></i></el-button>
                </div>


            </el-aside>
        </el-container>

        <!--<img :src="picUrl" style="height: 200px;width: auto;">-->
    </div>

</template>
<script>
	import {workerEditAvatar} from "../../api/workerInfo";
	import vueCropper from 'vue-cropper'

	export default {

		props: {
			imageSrc: "",
        },

		data: function () {
			return {
                picUrl: "",
				previews: {},
				example2: {
					img: "",
					info: true,
					size: 1,
					outputType: 'jpeg',
					canScale: true,
					autoCrop: true,
					// 只有自动截图开启 宽度高度才生效
					autoCropWidth: 200,
					autoCropHeight: 200,
					// 开启宽度和高度比例
                    full: true,
					fixed: true,
					fixedNumber: [1, 1]
				},
				downImg: '#'
			}
		},
		methods: {

			startPic() {
				this.example2.img = this.imageSrc;
            },

			savePic() {
				let that = this;
				this.$refs.cropper.getCropData((data) => {
					that.picUrl = data;
					workerEditAvatar(data, res => {
						that.$emit('save-res', res);
					});
				})
            },

			handleUpload() {
				document.getElementById('uploads').click();
			},
			rotateLeft () {
				this.$refs.cropper.rotateLeft()
			},
			rotateRight () {
				this.$refs.cropper.rotateRight()
			},
			realTime (data) {
				this.previews = data
			},

			// finish (type) {
			// 	// 输出
			// 	let test = window.open('about:blank')
			// 	test.document.body.innerHTML = '图片生成中..'
			// 	if (type === 'blob') {
			// 		this.$refs.cropper.getCropBlob((data) => {
			// 			// let test = window.open('');
			// 			test.location.href = window.URL.createObjectURL(data);
			// 			console.log(window.URL.createObjectURL(data));
			// 		})
			// 	} else {
			// 		this.$refs.cropper.getCropData((data) => {
			// 			test.location.href = data;
			// 			// console.log(data);
			// 		})
			// 	}
			// },

			down (type) {
				// event.preventDefault()
				var aLink = document.createElement('a');
				aLink.download = 'headPic';
				// 输出
				if (type === 'blob') {
					this.$refs.cropper.getCropBlob((data) => {
						this.downImg = data
						aLink.href = data
						aLink.click()
					})
				} else {
					this.$refs.cropper.getCropData((data) => {
						this.picUrl = data;
						// console.log(data);
						this.downImg = data;
						aLink.href = data;
						aLink.click()
					})
				}
			},

			uploadImg (e, num) {
				//上传图片
				// this.option.img
				var file = e.target.files[0]
				if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
					alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种')
					return false
				}
				var reader = new FileReader()
				reader.onload = (e) => {
					let data
					if (typeof e.target.result === 'object') {
						// 把Array Buffer转化为blob 如果是base64不需要
						data = window.URL.createObjectURL(new Blob([e.target.result]))
					} else {
						data = e.target.result
					}
					if (num === 1) {
						// this.option.img = data
					} else if (num === 2) {
						this.imageSrc = data
					}
				}
				// 转化为base64
				// reader.readAsDataURL(file)
				// 转化为blob
				reader.readAsArrayBuffer(file)
			}
		},
		components: {
			vueCropper
		},
	}
</script>

<style>
    .wrapper{
        height: 400px;
    }
</style>