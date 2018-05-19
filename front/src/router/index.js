import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/index/Login.vue'
import SignUp from '../components/index/SignUp.vue'
import workerHome from '../components/worker/workerHome.vue'
import Index from '../components/index/Index.vue'
import RequesterIndex from '../components/requester/RequesterIndex.vue'
import RequesterHome from '../components/requester/Home.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',

  routes: [
    {
      path: '/home', component: workerHome
    },
    {path: '/', component: Index},
    {
      path: '/login', component:Login
    },
    {path: '/signUp', component: SignUp},
    {
      path: '/requester', component: RequesterIndex,
      children: [
        {path: 'home', component: RequesterHome}
      ]
    }

  ]
})
