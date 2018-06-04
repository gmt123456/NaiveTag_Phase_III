import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/index/Login.vue'
import SignUp from '../components/index/SignUp.vue'
import WorkerNavi from '../components/worker/workerNavi.vue'
import WorkerHome from '../components/worker/workerUser/workerHome.vue'
import WorkerMain from '../components/worker/workerMain.vue'
import WorkerUnfinish from '../components/worker/workerUser/workerUnfinish.vue'
import WorkerFinish from '../components/worker/workerUser/workerFinish.vue'
import WorkerRank from '../components/worker/workerUser/workerRank.vue'
import WorkerTag from '../components/worker/workerTag/workerTagPage.vue'
import SubTask from '../components/worker/workerTask/subTasks.vue'
import SubTaskDetails from '../components/worker/workerTask/subTasksDetails.vue'
import FirstLevelTask from '../components/worker/workerTask/firstLevelTask.vue'
import Overview from '../components/worker/workerTask/overview.vue'
import MyParticipation from '../components/worker/workerTask/myParticipation.vue'
import TaskHall from '../components/worker/workerUser/taskHall.vue'
import Index from '../components/index/Index.vue'
import RequesterIndex from '../components/requester/RequesterIndex.vue'
import RequesterHome from '../components/requester/Home.vue'
import TaskDetail from '../components/requester/TaskDetail.vue'
import RequesterProfile from '../components/requester/requesterProfile/Profile.vue'

import ServiceCheck from '../components/stuff/stuffCheck/stuffCheckPage'

Vue.use(Router);

export default new Router({
  mode: 'history',

  routes: [
    {
      path: '/workerNavi', component: WorkerNavi,
      children: [
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
            {path: 'myparticipation', component: MyParticipation},
          ]
        },
        {path: '/subTaskDetails/:taskId/:subTaskId/:taskType', name: 'subTaskDetails', component: SubTaskDetails},
        {path: '/taskHall', component: TaskHall},

        {path: '/tag/:taskId/:subTaskId/:taskType/:picUrl', component: WorkerTag, name: 'workerTag'},
      ]
    },
    {path: '/service', component: ServiceCheck},

    {path: '/', component: Index},
    {path: '/login', component: Login},
    {path: '/signUp', component: SignUp},
    {
      path: '/requester', component: RequesterIndex,
      children: [
        {path: 'home', component: RequesterHome},
        {path: 'taskDetail', name: 'taskDetail', component: TaskDetail},
        {path:'profile',component:RequesterProfile}
      ]
    }

  ],

  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {x: 0, y: 0}
    }
  }

})
