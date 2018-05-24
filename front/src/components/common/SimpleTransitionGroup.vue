<template>
  <transition-group v-bind:css="false"
                    v-on:before-enter="beforeEnter"
                    v-on:enter="enter"
                    v-on:leave="leave"
                    name="fadeTask">
   <slot></slot>
  </transition-group>

</template>

<script>
  import Velocity from 'velocity-animate'

  export default {

    name: "SimpleTransitionGroup",


    methods: {
      beforeEnter: function (el) {
        el.style.opacity = 0;
        el.style.translateX = 170;
      },

      enter: function (el, done) {
        let delay = el.dataset.index * 50;
        setTimeout(function () {
          Velocity(
            el,
            {opacity: 0, translateX: 240},
            {duration: 20}
          );
          Velocity(
            el,
            {opacity: 1, translateX: 0},
            {complete: done}
          )
        }, delay)
      },

      leave: function (el, done) {
        let delay = el.dataset.index * 50;
        setTimeout(function () {
          Velocity(
            el,
            {opacity: 0, translateX: 170},
            {complete: done}
          )
        }, delay)
      }
    },

  }
</script>

<style scoped>
  /*.fadeTask-enter-active, .fadeTask-leave-active {*/
  /*transition: all 600ms;*/
  /*}*/
  /*.fadeTask-enter, .fadeTask-leave-to !* .fade-leave-active below version 2.1.8 *! {*/
  /*opacity: 0;*/
  /*}*/
  /*.fadeTask-enter {*/
  /*transform: translateX(-170px);*/
  /*}*/
  /*.fadeTask-leave-active {*/
  /*transform: translateX(-170px);*/
  /*}*/
  .center {
    display: flex;
    /*justify-content:center;*/
    align-items: center;
  }
</style>
