<template>
   <div>
       <div style="height: 700px;width: 100%;background-color: #feffff;text-align: center;" class="center">
           <div style="margin: auto;width: 1100px;">
               <el-container>
                   <el-main style="width: 600px;">
                       <div style="margin: 20px;text-align: left;">
                           <div style="color: #47494d;font-size: 44px;font-weight: bold;width: 300px;padding-top: 20px;">Expand</br>your career</div>
                           <div style="color: #47494d;font-weight: lighter;padding-top: 40px;" class="bottom-border">Here we will recommend suitable tasks for you</div>
                           <div style="color: #47494d;font-weight: lighter;" class="bottom-border">There are hundreds of tasks waiting to be done</div>
                           <div style="color: #47494d;font-weight: lighter;" class="bottom-border">Use your free time to complete the marking tasks to get the bonus</div>
                           <div style="color: #47494d;font-weight: lighter;" class="bottom-border">You can also join our professional team to earn more money</div>
                       </div>
                   </el-main>
                   <el-aside style="width: 660px;">
                       <div style="width: 650px;height: 550px;border: solid 3px lightgray;">
                           <div style="background-image: url('../../../static/show/1.png');background-size: cover;background-position: 50%;width: 100%;height: 100%;"></div>
                       </div>
                   </el-aside>
               </el-container>
           </div>
       </div>
       <div style="height: 730px;width: 100%;background-color: #008abc;text-align: center;" class="center">
           <div style="margin: auto;width: 1100px;">
               <el-container>
                   <el-aside style="width: 660px;">
                       <div style="width: 650px;height: 550px;border: solid 3px lightgray;">
                           <div style="background-image: url('../../../static/show/4.png');background-size: cover;background-position: 50%;width: 100%;height: 100%;"></div>
                       </div>
                   </el-aside>
                   <el-main style="width: 600px;">
                       <div style="margin: 20px;text-align: left;">
                           <div style="color: white;font-size: 44px;font-weight: bold;width: 300px;padding-top: 20px;">Create</br>your task</div>
                           <div style="color: white;font-weight: lighter;padding-top: 40px;" class="bottom-border">You can also be a task publisher</div>
                           <div style="color: white;font-weight: lighter;" class="bottom-border">Your task will be send to a number of excellent workers to help with tagging</div>
                           <div style="color: white;font-weight: lighter;" class="bottom-border">Our system will help you recommend your tasks to the right people</div>
                           <div style="color: white;font-weight: lighter;" class="bottom-border">If you want to do it faster or better, you can do it here</div>
                       </div>
                   </el-main>
               </el-container>
           </div>
       </div>
   </div>
</template>

<script>
    export default {
        name: "introduction"
    }
</script>

<style scoped>
    .center {
        display: flex;
        /*justify-content:center;*/
        align-items: center;
    }

    .bottom-border {
        padding-bottom: 10px;
        margin-bottom: 25px;
        border-top-style:none;
        border-right-style:none;
        border-bottom-style:solid;
        border-left-style:none;
        border-color: lightgray;
        border-width: 2px;
    }
</style>
