<template>
  <div style="margin: auto;height: 100%">
      <line-chart :x-data="date" :series-data="recharge" :series-name="['Recharge']" title="The Accumulated Recharge of Requester" unique-id="00"></line-chart>
    <line-chart :x-data="date" :series-data="prize" :series-name="['Bonus','Average Bonus']" title="The Accumulated Bonuses" unique-id="01"></line-chart>
    <line-chart :x-data="date" :series-data="payWorker" :series-name="['Revenue','Average Revenue']" title="The Accumulated Revenue of Workers" unique-id="02"></line-chart>
    <line-chart :x-data="date" :series-data="advertisement" :series-name="['Advertisement']" title="The Accumulated Advertisement Revenue" unique-id="03"></line-chart>
    <div style="font-size: 28px;text-align: center;margin-bottom: 200px;margin-top: -50px">
      <p>
        On average, advertisement fees account for {{Math.round(expectedAdRate*10000)/100}}% of total bonus.
      </p>
      <p>
        And {{Math.round(expectedPayBackRate*10000)/100}}% will be refunded to requester.
      </p>
    </div>
  </div>
</template>

<script>
  import LineChart from "../../common/LineChart";
  import {getDollars} from "../../../api/adminAnalysis";

  export default {
    name: "Dollars",
    components: {LineChart},
    methods:{
      getArray(item){
        let res=[];
        for (let key in this.date){
          res.push(item);
        }

        return res;
      }
    },
    data: function () {
      return {
        recharge:[],
        prize:[],
        payWorker:[],
        advertisement:[],
        date:[],
        expectedPayBackRate:'',
        expectedAdRate:''
      }
    },
    created:function () {
      getDollars(res=>{
        this.date=res.date;
        this.recharge.push(res.recharge);
        this.prize.push(res.prize,this.getArray(res.expectedPrize));
        this.payWorker.push(res.payWorker,this.getArray(res.expectedPayWorker));
        this.advertisement.push(res.advertisement);
        this.expectedPayBackRate=res.expectedPayBackRate;
        this.expectedAdRate=res.expectedAdRate;
      })
    }
  }
</script>

<style scoped>

</style>
