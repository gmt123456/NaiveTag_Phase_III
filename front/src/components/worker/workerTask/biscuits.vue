<template>
    <div id="biscuits">
        <div id="myPieChart" :style="{width: '400px', height: '200px'}"></div>
    </div>
</template>

<script>
	export default {
		props: {
			earned: Number,
            rest: Number,
        },
		data(){
			return{
				biscuitsData:{
					tooltip : {
						trigger: 'item',
						formatter: "{b} : ${c} ({d}%)"
					},
					series : [
						{
							name: 'biscuits',
							type: 'pie',
							radius: '65%',
							data: [{
								value:400,
								name: "you've earned",
								itemStyle: {
									color: 'orange'
								},
                                label: {
									fontSize: 15,
                                }
							},{
								value:400,
								name:'the rest',
								itemStyle: {
									color: 'lightgray'
								},
								label: {
									fontSize: 15,
								}
							},]
						}
					],
				},
            }
        },

        created(){
			this.biscuitsData.series[0].data[0].value = this.earned;
	        this.biscuitsData.series[0].data[1].value = this.rest;
        },

        mounted(){
	        let biscuits = this.$echarts.init(document.getElementById('myPieChart'));
	        biscuits.setOption(this.biscuitsData);
        },

        methods: {

        },


		name: "biscuits"
	}
</script>

<style scoped>

</style>