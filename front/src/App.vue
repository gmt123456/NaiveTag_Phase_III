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
         this.$router.push('/');
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
