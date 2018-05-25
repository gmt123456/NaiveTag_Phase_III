<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  created:function () {
   $.ajaxSetup({
     error:function(request){
       if (request){
          alert('@App.vue status:'+request.status);
       }
     }
   })

  }
}
</script>

<style>
/*#app {*/
  /*text-align: center;*/
/*}*/
</style>
