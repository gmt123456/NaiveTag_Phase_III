<template>
    <div id="tagPage">
        <div style="height: 100%;" >
            <!--<el-row style="background-image: url('/src/assets/bar.png');width: 100%;height: 60px;" >-->

                <!--<div class="height center" style="width: 250px;float: left;">-->
                    <!--<img src="../../assets/naive_tag.png" style="height: 50px;width: auto;"-->
                         <!--ondragstart="return false;"-->
                         <!--oncontextmenu="return false;"-->
                         <!--v-on:click="back">-->
                <!--</div>-->

                <!--&lt;!&ndash;<div class="height" style="width: 100px;float: left;">&ndash;&gt;-->
                    <!--&lt;!&ndash;<button type="text" class="height center back"  v-on:click="back">返回</button>&ndash;&gt;-->
                <!--&lt;!&ndash;</div>&ndash;&gt;-->

            <!--</el-row>-->
        </div>
        <tag ref="tagPage" v-bind:tagType="tagData.tagType"
             v-bind:options="options"
             v-bind:picUrl="getPicUrl"
             v-bind:label="tagData.label"
             v-bind:description="description"
             :points.sync="tagData.points"
             :frames.sync="tagData.frames"
             @lastPic="lastPic"
             @nextPic="nextPic"
             @changeLabel="changeLabel"></tag>
    </div>
</template>

<script>

    import tag from './workerTagInfo.vue'
    import {taskInfo} from '../../../api/tagPage.js'
    import {getLabelInfo} from '../../../api/tagPage.js'
    import {save} from '../../../api/tagPic.js'
    import {next} from '../../../api/tagPic.js'
    import {previous} from '../../../api/tagPic.js'
    import {getUrl} from "../../../api/tool";

    export default {

        created: function () {
            this.fetchTagData();
        },

        computed: {
            getPicUrl(){
                return getUrl(this.picUrl);
            }
        },

        methods: {

            changeLabel(newLabel){
                this.tagData.label = newLabel;
            },

            tagPicReflash(picUrl) {
                console.log("picUrl: " + picUrl);
              this.$router.push({ name: 'workerTag', params: { taskId: localStorage.firstLevelTaskId, subTaskId: this.$route.params.subTaskId, taskType: this.$route.params.taskType, picUrl: picUrl}});
            },

            lastPic: function () {
                console.log(this.tagData);
                this.saveData();
                let result = previous(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, this.picUrl, res=> {
                    console.log("previousResult:");
                    console.log(res);
                    if(res.url){
                        this.tagPicReflash(res.url);
                    }else{
                        this.$message.info(res.description);
                    }
                });
            },

            nextPic: function () {
                this.saveData();
                let result = next(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, this.picUrl, res=> {

                    if(res.url){
                        this.tagPicReflash(res.url);
                    }else{
                        this.$message.info(res.description);
                    }
                });
            },

            saveData(){
                var json = JSON.stringify(this.tagData);
                let result = save(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, this.picUrl,json, res=> {
                    console.log("saveResult success!");
                });
            },

            fetchTagData () {
                this.picUrl = this.$route.params.picUrl;
                let result = taskInfo(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType,  res=> {

                    if(res.classes){
                        this.options = this.changeToOptions(res.classes);
                    }
                    if(res.taskType){
                        this.taskType = res.taskType;
                    }
                    if(res.description){
                        this.description = res.description;
                    }
                    console.log("options:");
                    console.log(this.options);
                    this.fetchLabelDataByPicUrl(this.$route.params.picUrl);
                });
            },

            fetchLabelDataByPicUrl(picUrl) {
              let that = this;
                let result = getLabelInfo(this.$route.params.taskId, this.$route.params.subTaskId, this.$route.params.taskType, picUrl, res=> {
                    if(res){
                      that.tagData = res;
                    }else{
                      that.tagData = {
                            "label": null,
                            "frames":[
                            ],
                            "tagType":null,
                            //若能画，points必有
                            "points":[
                            ],
                        };

                      that.tagData.tagType =  ""+that.taskType;
                        console.log(that.tagData.tagType)
                    }
                });
            },

            changeToOptions(classes){
                var options = [];
                for(var value in classes){
                    options.push({
                        value: value,
                        label: classes[value],
                    })
                }
                return options;
            },
        },

        data() {
            return {
                taskType: null,
                description: "",
                picUrl: "",
                options: [
//                    {
//                    value: '0',
//                    label: '黄金糕'
//                }, {
//                    value: '1',
//                    label: '双皮奶'
//                }, {
//                    value: '2',
//                    label: '蚵仔煎'
//                }, {
//                    value: '3',
//                    label: '龙须面'
//                }, {
//                    value: '4',
//                    label: '北京烤鸭'
//                }
                ],
                tagData: {
                    "label": null,
                    "frames":[
                    ],
                    "tagType":"t_100",
                    //若能画，points必有
                    "points":[
                    ],
                },
            }
        },

        components: {
//            'simplenavi': simplenavi,
            'tag': tag,
        },

        watch: {
            // 如果路由有变化，会再次执行该方法
            '$route': 'fetchTagData'
        },

    }

</script>

<style>
    .back {
        color: white;
        padding-left: 10px;
        background-color: transparent;
        font-size: 14px;
        border: none;
        cursor: pointer;
        cursor: hand;
    }

    .back:hover {
        color: #336fff;
    }

    .logo {
        text-align: center;
        font-family: 'Caviar Dreams';
        font-size: 36px;
        color: white;
    }

    .center {
        display:flex;
        justify-content:center;
        align-items:center;
    }

    .height {
        height: 100%;
    }
</style>
