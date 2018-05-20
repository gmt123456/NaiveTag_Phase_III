import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/index/Login.vue'
import SignUp from '../components/index/SignUp.vue'
import WorkerHome from '../components/worker/workerHome/workerHome.vue'
import WorkerMain from '../components/worker/workerMain.vue'
import WorkerUnfinish from '../components/worker/workerHome/workerUnfinish.vue'
import WorkerFinish from '../components/worker/workerHome/workerFinish.vue'
import Index from '../components/index/Index.vue'
import RequesterIndex from '../components/requester/RequesterIndex.vue'
import RequesterHome from '../components/requester/Home.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',

  routes: [
    {
	    path: '/worker', component: WorkerMain,
	    children: [
		    {path: 'home', component: WorkerHome},
		    {path: 'unfinish', component: WorkerUnfinish},
		    {path: 'finish', component: WorkerFinish}
	    ]
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
