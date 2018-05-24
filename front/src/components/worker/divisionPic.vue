<template>
    <div id="division">
        <img v-if="division === 'Contributor'" src="../../../static/grade/contributor.png" :width="size" height="auto">
        <img v-if="division === 'Novice'" src="../../../static/grade/novice.png" :width="size" height="auto">
        <img v-if="division === 'Expert'" src="../../../static/grade/expert.png" :width="size" height="auto">
        <img v-if="division === 'Master'" src="../../../static/grade/master.png" :width="size" height="auto">
        <img v-if="division === 'Grandmaster'" src="../../../static/grade/grandmaster.png" :width="size" height="auto">
    </div>
</template>

<script>
	export default {
		name: "divisionPic",
        props: {
			division: String,
            size: String,
        }
	}
</script>

<style scoped>

</style>