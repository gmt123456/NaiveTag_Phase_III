<template>
    <div id="serviceCheckInfo" class="cannotselect">
        <el-row>


            <!--左侧画框画线界面-->
            <el-col :span="17"><div class="grid-content bg-purple">
                <div style="padding: 10px;">

                    <div style="padding: 10px;border: dashed;">
                        <div ref="divBlock" style="height: 650px;width: 100%;text-align: center;display:flex;justify-content:center;align-items:center;">

                            <div style="border: 1px solid gray;position: relative;display: inline-flex;">

                                <!--背景图片-->
                                <img :src="picUrl" alt="picture" v-bind:style="getPicStyle()"
                                     ondragstart="return false;"
                                     oncontextmenu="return false;"
                                     ref="image">

                                <!--已画的框-->
                                <div class="rect" v-for="(item, index) in frames"
                                     v-bind:style="{
                                        'border-color': rectColor,
                                        left: item.leftTop.x + 'px',
                                        top: item.leftTop.y + 'px',
                                        width: item.rightDown.x - item.leftTop.x + 'px',
                                        height: item.rightDown.y - item.leftTop.y + 'px',
                                        'z-index': index,
                                        color: rectColor,
                                     }"
                                     v-bind:index="index"
                                     v-bind:key="item.id"><span v-if="isMoreThanOne">#{{index + 1}}</span></div>

                                <!--框画板-->
                                <div v-if="isRectsTypeNoLabel" ref="canvas" v-bind:style="getCanvasStyle()"></div>
                                     <!--v-on:mousedown="onMouseDown($event)"-->
                                     <!--v-on:mouseup="onMouseUp($event)"-->
                                     <!--v-on:mousemove="onMouseMove($event)"-->

                                <!--正在画的框-->
                                <div v-if="this.drawRect" v-bind:style="getChangeRectStyle()"></div>

                                <!--<tagCanvas :points.sync="this.points"-->
                                <!--v-bind:width="this.picWidth"-->
                                <!--v-bind:height="this.picHeight"-->
                                <!--v-bind:style="getTagCanvasStyle()"-->
                                <!--&gt;</tagCanvas>-->
                                <canvas ref="canvas" class="canvas"
                                        ondragstart="return false;" oncontextmenu="return false;"
                                        v-bind:width="this.picWidth"
                                        v-bind:height="this.picHeight"
                                        v-bind:style="getTagCanvasStyle()"></canvas>
                                <!--v-on:mousedown="onCanvasMouseDown($event)"-->
                                <!--v-on:mousemove="onCanvasMouseMove($event)"-->
                                <!--v-on:mouseup="onCanvasMouseUp($event)"-->

                            </div>


                        </div>
                    </div>

                </div>


            </div></el-col>


            <!--右侧标注界面-->
            <el-col :span="7"><div class="grid-content bg-purple-light">
                <div v-bind:style="getBlocksStyle()">
                    <div class="tagblocks">

                        <!--&lt;!&ndash;颜色选择器&ndash;&gt;-->
                        <!--<div v-if="isRectsTypeNoLabel" class="block center" style="padding-top: 20px;">-->
                        <!--<span class="demonstration">颜色</span>-->
                        <!--<el-color-picker v-model="rectColor"></el-color-picker>-->
                        <!--</div>-->
                        <div>
                            <el-button icon="el-icon-back" round size="mini" style="margin: 10px" @click="back">back</el-button>
                        </div>
                        <div v-if="description" class="block center" style="padding: 20px 20px 0px 20px;justify-content:center;">
                            Description：{{description}}
                        </div>

                        <!--标注块-->
                        <div style="text-align: center;padding-top: 10px;padding-bottom: 10px">

                            <!--输入框-->
                            <div v-if="isInputType">
                                <div v-if="!isRectsTypeNoLabel">
                                    <el-input v-model="labelInput" placeholder="Please input content" class="input" disabled/>
                                </div>
                                <div v-if="isRectsTypeNoLabel"
                                     v-for="(item, index) in frames"
                                     v-bind="item"
                                     v-bind:index="index"
                                     v-bind:key="item.id">
                                    <span v-if="isMoreThanOne">{{index + 1}}：</span>
                                    <el-input v-model="item.label" placeholder="Please input content" class="input" disabled @change="changeInputValue($event,index)"></el-input>
                                    <!--<el-button type="danger" icon="el-icon-delete" circle-->
                                               <!--v-on:click="deleteFramesItem(index)"></el-button>-->
                                </div>
                            </div>


                            <!--选择框-->
                            <div v-if="isSelectType">
                                <div v-if="!isRectsTypeNoLabel">
                                    <el-input v-model="labelInput" placeholder="Please input content" class="input" disabled/>
                                    <!--<el-select v-model="labelSelect" filterable placeholder="Please choose" class="select" disabled>-->
                                        <!--<el-option-->
                                                <!--v-for="item in options"-->
                                                <!--:key="item.value"-->
                                                <!--:label="item.label"-->
                                                <!--:value="item.value">-->
                                        <!--</el-option>-->
                                    <!--</el-select>-->
                                </div>
                                <div v-if="isRectsTypeNoLabel"
                                     v-for="(item, index) in frames"
                                     v-bind="item"
                                     v-bind:index="index"
                                     v-bind:key="item.id"
                                     v-on:remove="frames.splice(index, 1)">
                                    <span v-if="isMoreThanOne">{{index + 1}}：</span>
                                    <el-input v-model="item.label" placeholder="Please input content" class="input" disabled/>
                                    <!--<el-select v-model="item.label" filterable placeholder="Please choose" class="select" disabled @change="changeSelectValue($event,index)">-->
                                        <!--<el-option-->
                                                <!--v-for="item in options"-->
                                                <!--:key="item.value"-->
                                                <!--:label="item.label"-->
                                                <!--:value="item.value">-->
                                        <!--</el-option>-->
                                    <!--</el-select>-->
                                    <!--<el-button type="danger" icon="el-icon-delete" circle-->
                                               <!--v-on:click="deleteFramesItem(index)" />-->
                                </div>
                            </div>


                        </div>

                        <!--最后的按钮-->
                        <div class="center" style="padding-bottom: 20px;justify-content:center;">
                            <el-button type="danger" plain icon="el-icon-error" v-on:click="failPic" style="width: 100px;">Fail</el-button>
                            <el-button type="success" plain icon="el-icon-success" v-on:click="passPic" style="width: 100px;">Pass</el-button>
                        </div>

                    </div>
                </div>
            </div></el-col>

        </el-row>
    </div>
</template>

<script>
	import Vue from 'vue'

	export default {


		mounted: function() {
			this.$nextTick(function () {
				this.picWidth = this.$refs.image.getBoundingClientRect().width;
				this.picHeight = this.$refs.image.getBoundingClientRect().height;
				this.ctx = this.$refs.canvas.getContext('2d');

				if(this.points && (this.points.length > 0)){//如果有points，就画出来；而frames是自动画的
					this.drawPolygon();
				}

				this.updatePic(this.picUrl);

			})
		},

		created: function(){
			if(this.label){
				this.updateLabel();
			}
		},

		watch: {
			label: function (newLabel) {
				this.updateLabel();
			},

			picUrl: function (newPicUrl) {
				this.updatePic(newPicUrl);
			},

			points: function (newPoints) {
				if(this.points){
					if(this.points.length > 0){
						this.drawPolygon();
					}else{
						this.ctx.clearRect(0, 0, this.$refs.canvas.width, this.$refs.canvas.height);
					}
				}
			},
		},

		props: {
			tagType: String,
			label: String,
			frames: Array,
			points: Array,
			options: Array,
			description: String,

			picUrl: String
		},

		data() {
			return {
				labelInput: "",
				labelSelect: null,
				rectColor : 'red',
				defaultColor:[
					'red',
					'black',
					'blue',
					'green',
					'yellow'
				],

				picHeight :  800,
				picWidth : 600,
				picWidthStyle: 'auto',
				picHeightStyle: 'auto',

				drawRect : false,
				startPoint: {
					x:0,
					y:0
				},
				endPoint: {
					x:0,
					y:0
				},
				changeRect: {
					width:0,
					height:0
				},

				rectStyle: {
					'border-color': 'yellow',
					left: '100px',
					top: '100px',
					width: '100px',
					height: '100px',
					'z-index': '0',
				},

				isDrawing : false,
				ctx : Object,
			}
		},

		computed: {

			getRandomColor: function () {
				return this.defaultColor[parseInt((this.defaultColor.length-1)*Math.random())];
			},

			getTagTypeNum: function () {
				return parseInt(this.tagType.slice(2,5));
			},

			getRectLeft: function () {
				return Math.min(this.startPoint.x , this.endPoint.x);
			},

			getRectTop: function () {
				return Math.min(this.startPoint.y , this.endPoint.y);
			},

			getRectWidth: function () {
				return Math.abs(this.startPoint.x - this.endPoint.x);
			},

			getRectHeight: function () {
				return Math.abs(this.startPoint.y - this.endPoint.y);
			},

			isCanvasType: function () {
				switch (this.getTagTypeNum) {
					case 401:
						return true;
						break;
					case 400:
						return true;
						break;
					default:
						return false;
						break;
				}
			},

			isRectsTypeNoLabel: function () {

				switch (this.getTagTypeNum) {
					case 100:
						return false;
						break;
					case 101:
						return false;
						break;
					case 401:
						return false;
						break;
					case 400:
						return false;
						break;
					default:
						return true;
						break;
				}
			},

			isInputType: function () {
				switch (this.getTagTypeNum) {
					case 100:
						return false;
						break;
					case 101:
						return true;
						break;
					case 200:
						return false;
						break;
					case 201:
						return true;
						break;
					case 300:
						return false;
						break;
					case 301:
						return true;
						break;
					case 401:
						return true;
						break;
					case 400:
						return false;
						break;
				}
			},

			isSelectType: function () {
				switch (this.getTagTypeNum) {
					case 100:
						return true;
						break;
					case 101:
						return false;
						break;
					case 200:
						return true;
						break;
					case 201:
						return false;
						break;
					case 300:
						return true;
						break;
					case 301:
						return false;
						break;
					case 401:
						return false;
						break;
					case 400:
						return false;
						break;
				}
			},

			isMoreThanOne: function () {
				switch (this.getTagTypeNum) {
					case 300:
						return true;
						break;
					case 301:
						return true;
						break;
					default:
						return false;
						break;
				}
			},
		},

		methods: {
			back(){
				this.$router.push("/staffFirstTask/staffMyparticipation");
			},

			updatePic: function (picUrl) {
				this.picWidthStyle = 'auto';
				this.picHeightStyle = 'auto';
				var newImg = new Image();
				newImg.src = picUrl;
//                newImg.onerror = () => { // 图片加载错误时的替换图片
//                    newImg.src = 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489486509807&di=22213343ba71ad6436b561b5df999ff7&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F77%2F31%2F20300542906611142174319458811.jpg'
//                }
				newImg.onload = () => { // 图片加载成功后把地址给原来的img
					this.picWidth = this.$refs.image.getBoundingClientRect().width;
					this.picHeight = this.$refs.image.getBoundingClientRect().height;
					if(this.picWidth > this.$refs.divBlock.getBoundingClientRect().width){
						this.picWidthStyle = this.$refs.divBlock.getBoundingClientRect().width+'px';
						this.picWidth = this.$refs.divBlock.getBoundingClientRect().width;
					}
					if(this.picHeight > this.$refs.divBlock.getBoundingClientRect().height){
						this.picHeightStyle = this.$refs.divBlock.getBoundingClientRect().height+'px';
						this.picHeight = this.$refs.divBlock.getBoundingClientRect().height;
					}
				};
			},

			updateLabel: function () {
				this.labelInput = this.label;
				if(this.labelInput === null){
					this.labelInput = "";
				}
				// if(!this.isRectsTypeNoLabel && this.getTagTypeNum!=400){
				// 	if(this.isInputType){
				// 		this.labelInput = this.label;
				// 		if(this.labelInput === null){
				// 			this.labelInput = "";
				// 		}
				// 	}
				// 	if(this.isSelectType){
				// 		for(var index in this.options){
				// 			if(this.options[index].label === this.label){
				// 				this.labelSelect = index;
				// 				return;
				// 			}
				// 		}
				// 		this.labelSelect = null;
				// 	}
				// }
			},

			// checkDraw: function () {
			// 	if(this.frames.length === 0){
			// 		return true;
			// 	}else if(this.frames[this.frames.length - 1].label){
			// 		return true;
			// 	}else{
			// 		return false;
			// 	}
			// },

            //对是否进行标注且标注合法的检测，在客服check的时候是不需要检测的
            // checkNext: function () {
				// if(this.isRectsTypeNoLabel){
				// 	if(this.frames.length === 0){
				// 		this.$message.error('请填写至少一个标注信息！');
				// 		return false;
				// 	}else if(this.frames[this.frames.length - 1].label){
				// 		return true;
				// 	}else{
				// 		this.$message.error('有标注信息未填写！');
				// 		return false;
				// 	}
				// }else{
				// 	if(!this.isRectsTypeNoLabel && this.getTagTypeNum != 400 && (this.labelSelect === null && this.labelInput.length === 0)){
				// 		this.$message.error('请填写标注信息！');
				// 		return false;
				// 	}
				// 	if(this.isCanvasType){
				// 		if(this.points && this.points.length > 0){
				// 			return true;
				// 		}else{
				// 			this.$message.error('请画出标注区域！');
				// 			return false;
				// 		}
				// 	}
				// 	return true;
				// }
            //
            // },

			failPic:function () {
				this.$emit('nextPic', false);
			},

            passPic: function () {
	            this.$emit('nextPic', true);
            },


// 			lastPic: function () {
// 				if(this.checkNext()){
// //                    this.label = this.labelInput;
// 					this.changeLabel();
// 					this.$emit('lastPic');
// 				}
// 			},

// 			nextPic: function () {
// 				if(this.checkNext()){
// //                    this.label = this.labelInput;
// 					this.changeLabel();
// 					this.$emit('nextPic');
// 				}
// 			},

			// changeLabel: function () {
			// 	if(!this.isRectsTypeNoLabel && this.getTagTypeNum != 400){
			// 		if(this.labelInput.length > 0){
			// 			this.$emit('changeLabel', "" + this.labelInput);
			// 		}else{
			// 			this.$emit('changeLabel', "" + this.options[this.labelSelect].label);
			// 		}
			// 	}
			// },

			// onCanvasMouseDown: function (event) {
			// 	this.points.splice(0, this.points.length);
			// 	this.isDrawing = true;
            //
			// 	this.points.push({
			// 		x: event.offsetX,
			// 		y: event.offsetY
			// 	});
            //
			// },

			// onCanvasMouseMove: function (event) {
			// 	if (this.isDrawing) {
			// 		this.ctx.lineTo(event.offsetX, event.offsetY);
			// 		this.ctx.stroke();
            //
			// 		this.points.push({
			// 			x: event.offsetX,
			// 			y: event.offsetY
			// 		});
            //
			// 	} else {
			// 		this.ctx.beginPath();
			// 		this.ctx.moveTo(event.offsetX, event.offsetY);
			// 	}
			// },
            //
			// onCanvasMouseUp: function (event) {
			// 	this.isDrawing = false;
            //
			// 	this.points.push({
			// 		x: event.offsetX,
			// 		y: event.offsetY
			// 	});
            //
			// 	this.drawPolygon();
			// },

			drawPolygon: function() {
				this.ctx.clearRect(0, 0, this.$refs.canvas.width, this.$refs.canvas.height);

				this.ctx.beginPath();
				this.ctx.moveTo(this.points[0].x, this.points[0].y);
				for (var value of this.points) {
					var a = value.x;
					var b = value.y;
					this.ctx.lineTo(a, b);
				}
				this.ctx.fillStyle = 'rgba(0,191,255,0.6)';
				this.ctx.fill();

			},

			// deleteFramesItem(index) {
			// 	this.frames.splice(index, 1);
			// },

			changeInputValue(value,index) {
				Vue.set(this.frames[index], 'label', value);
			},
//
//            changeInputLabel(value){
//                this.label = value;
//            },

			changeSelectValue(value,index) {
				let obj = {};
				obj = this.options.find((item)=>{
					return item.value === value;
				});
				Vue.set(this.frames[index], 'label', obj.label);
			},
//
//            changeSelectLabel(value){
//                let obj = {};
//                obj = this.options.find((item)=>{
//                    return item.value === value;
//                });
//                this.label = obj.label;
//            },

			// onMouseDown: function (event) {
			// 	if(this.checkDraw()){
			// 		this.drawRect = true;
			// 		this.startPoint.x = event.offsetX;
			// 		this.startPoint.y = event.offsetY;
			// 		this.endPoint.x = event.offsetX;
			// 		this.endPoint.y = event.offsetY;
			// 	}else{
			// 		this.$message.error('有标注信息未填写！');
			// 	}
            //
			// },

			// onMouseMove: function (event) {
			// 	if(this.drawRect){
            //
			// 		this.endPoint.x = event.offsetX;
			// 		this.endPoint.y = event.offsetY;
            //
			// 	}
			// },

			// onMouseUp: function () {
			// 	if(this.checkDraw()){
			// 		this.drawRect = false;
            //
			// 		if(this.getRectWidth > 5 && this.getRectHeight > 5){
			// 			var label = null;
			// 			if(!this.isMoreThanOne){
			// 				if(this.frames.length === 1){
			// 					label = this.frames[0].label;
			// 					this.frames.pop();
			// 				}
			// 			}
			// 			this.frames.push({
			// 				"leftTop": {x:this.getRectLeft,y:this.getRectTop},//left and top
			// 				"rightDown": {x:this.getRectLeft + this.getRectWidth,y:this.getRectTop + this.getRectHeight},
			// 				"label":label,
			// 			});
			// 		}
			// 	}
			// },

			getPicStyle: function () {
				return {
					width: this.picWidthStyle,
					height: this.picHeightStyle,
					position:'relative',
					left:'0px',
					top:'0px',
					'z-index':'-1',
				}
			},

			getChangeRectStyle: function () {
				var zIndex = 0;
				if(this.frames){
					zIndex = this.frames.length;
				}
				return {
					'z-index': zIndex,
					left: this.getRectLeft + 'px',
					top: this.getRectTop + 'px',
					width: this.getRectWidth + 'px',
					height: this.getRectHeight + 'px',
					position: 'absolute',
					border:'2px solid',
					color: this.rectColor,
				}
			},

			getTagCanvasStyle: function () {
				if(this.isCanvasType){
					var zIndex = 0;
					if(this.frames){
						zIndex = this.frames.length;
					}
					return {
						'z-index': zIndex + 3,
						left: '0px',
						top: '0px',
						position: 'absolute',
					}
				}else{
					return {
						'z-index': -1,
						left: '0px',
						top: '0px',
						position: 'absolute',
					}
				}

			},

			getCanvasStyle: function () {
				var zIndex = 0;
				if(this.frames){
					zIndex = this.frames.length;
				}
				return {
					'z-index': zIndex + 2,
					left: '0px',
					top: '0px',
					width: this.picWidth + 'px',
					height: this.picHeight + 'px',
					position: 'absolute',
//                    border: 'dashed red',
				}
			},

			getBlocksStyle: function () {
				var zIndex = 0;
				if(this.frames){
					zIndex = this.frames.length;
				}
				return {
					'z-index': zIndex + 1,
					position:'absolute',
					left: '0px',
					top: '10px',
					width: '95%',
				}
			}


		}
	}

</script>

<style>

    .input {
        padding-top: 5px;
        padding-bottom: 5px;
        display: inline-block;
        width: 65%;
    }

    .select {
        padding-top: 5px;
        padding-bottom: 5px;
        display: inline-block;
        width: 65%;
    }

    .cannotselect {
        -moz-user-select: none;
        -khtml-user-select: none;
        user-select: none;
    }

    .tagblocks {
        width: 20%;
        border-radius: 10px 10px 10px 10px;
        background-color: #eeeeee;
        margin-top: 50px;
        margin-left:auto;
        margin-right:0px;
        text-align: left;
    }

    .picture {
        /*width: 800px;*/
        width: auto;
        position:relative;
        left:0px;
        top:0px;
        z-index:-1;
        /*background-color: brown;*/
        /*background-image: url('/src/components/test.jpg');*/
    }

    .center {
        display:flex;
        /*justify-content:center;*/
        align-items:center;
    }

    .rect {
        background-color: transparent;
        border:2px solid;
        position:absolute;
    }

    .canvas {
        background-color: transparent;
    }
</style>
