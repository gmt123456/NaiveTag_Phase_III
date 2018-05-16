import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import workerHome from '../components/worker/workerHome.vue'

Vue.use(Router)

export default new Router({
  routes:[{
    path: '/home', component: workerHome
  },]
})
