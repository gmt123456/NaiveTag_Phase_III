<template>
    <div style="margin: auto;height: 100%">
     <div  style="height: 100%">

       <pie-chart :name="distributionName" :data="distributionValue"  title="Distribution of Workers" unique-id="00"></pie-chart>
       <!--<radar :dimension="dimension" :data="radarData" unique-id="01"></radar>-->

       <bar-chart :data="data1" :name="name1" unique-id="01" :x-data="xData" title="Tag Error of Workers"></bar-chart>

       <bar-chart :data="data2" :name="name2" unique-id="02" :x-data="xData" title="Ability of Workers"></bar-chart>

       <bar-chart :data="data3" :name="name3" unique-id="03" :x-data="xData" title="Accepted Task Of Workers"></bar-chart>

     </div>

    </div>
</template>

<script>
    import PieChart from "../../common/PieChart";
    import Radar from  "../../common/Radar"
    import {
      getWorkerDistribution,
      getWorkerEvaluation,
      getWorkerQuality,
      getWorkerSpeed
    } from "../../../api/adminAnalysis";
    import {getAllTypes, getTypes} from "../../../api/taskType";
    import BarChart from "../../common/BarChart";
    export default {
        name: "Worker",
      components: {BarChart, PieChart,Radar},
      data:function () {
        return{
          distributionName:[],
          distributionValue:[],
          data1:[],
          name1:[],
          data2:[],
          name2:[],
          data3:[],
          name3:[],
          dimension:[],
          radarData:[],
          radarSeriesName:[],
          xData:['Novice','Contributor','Expert','Master','GrandMaster']
        }
      },
      methods:{
          refresh(){
            this.dimension=getTypes();
            getWorkerDistribution(res=>{
              for(let key in res){
                this.distributionName.push(key);
                this.distributionValue.push((res[key]));
              }
            })

            getWorkerQuality(res=>{
              this.radarData.push(res);
              this.radarSeriesName.push('Quality');
              getWorkerSpeed(r=>{
                this.radarData.push(r);
                this.radarSeriesName.push('Speed');
              })
            });

            getWorkerEvaluation(res=>{
            this.data1.push(res.tagError);
            this.name1.push('Tag Error');
            this.data2.push(res.speed);
            this.data2.push(res.ability);
            this.name2=['speed(pictures per day)','ability'];
            this.data3=(res.acceptedTasks);
            this.name3=getTypes();

            });
          }
      },
      created:function () {
        this.refresh();
      }
    }
</script>

<style scoped>

</style>
