import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/index/Login.vue'
import SignUp from '../components/index/SignUp.vue'
import WorkerHome from '../components/worker/workerUser/workerHome.vue'
import WorkerMain from '../components/worker/workerMain.vue'
import WorkerUnfinish from '../components/worker/workerUser/workerUnfinish.vue'
import WorkerFinish from '../components/worker/workerUser/workerFinish.vue'
import WorkerRank from '../components/worker/workerUser/workerRank.vue'
import SubTask from '../components/worker/workerTask/subTasks.vue'
import SubTaskDetails from '../components/worker/workerTask/subTasksDetails.vue'
import FirstLevelTask from '../components/worker/workerTask/firstLevelTask.vue'
import Overview from '../components/worker/workerTask/overview.vue'
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
				{path: 'finish', component: WorkerFinish},
				{path: 'rank', component: WorkerRank},

			]
		},
		{
			path: '/firstTask', component: FirstLevelTask,
			children: [
				{path: 'overview', component: Overview},
				{path: 'subtasks', component: SubTask},
			]
		},
		{path: '/subTaskDetails', component: SubTaskDetails},
		{path: '/', component: Index},
		{path: '/login', component:Login},
		{path: '/signUp', component: SignUp},
		{
			path: '/requester', component: RequesterIndex,
			children: [
				{path: 'home', component: RequesterHome}
			]
		}

	],

	scrollBehavior (to, from, savedPosition) {
		if (savedPosition) {
			return savedPosition
		} else {
			return { x: 0, y: 0 }
		}
	}

})
