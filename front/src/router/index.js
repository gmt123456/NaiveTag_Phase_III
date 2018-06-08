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
import RequesterNavi from '../components/requester/RequesterNavi.vue'
import RequesterProfile from '../components/requester/requesterProfile/Profile.vue'

import InsiderLogin from '../components/index/InsiderLogin.vue'
import AdminIndex from '../components/admin/AdminIndex.vue'
import AdminRequester from '../components/admin/user/AdminRequester.vue'
import AdminWorkers from '../components/admin/user/AdminWorker.vue'
import AdminAdmin from '../components/admin/user/AdminAdmin.vue'
import AdminStuff from '../components/admin/user/AdminStuff.vue'
import ActiveUser from '../components/admin/analysis/ActiveUser.vue'
import TotalUser from '../components/admin/analysis/TotalUser.vue'
import TaskAnalysis from '../components/admin/analysis/Task.vue'

import StaffNavi from '../components/staff/staffNavi.vue'
import StaffMain from '../components/staff/staffMain.vue'
import StaffAllCheck from '../components/staff/staffHome/staffAllCheck.vue'
import StaffMyCheck from '../components/staff/staffHome/staffMyCheck.vue'
import StaffAllTasks from '../components/staff/staffHome/staffAllTasks.vue'
import StaffMyTasks from '../components/staff/staffHome/staffMyTasks.vue'
import StaffFirstLevelTask from '../components/staff/staffTask/staffFirstLevelTask.vue'
import StaffOverview from '../components/staff/staffTask/staffOverview.vue'
import StaffSubTask from '../components/staff/staffTask/staffSubTasks.vue'
import StaffMyParticipation from '../components/staff/staffTask/staffMyParticipation.vue'
import StaffCheck from '../components/staff/staffCheck/staffCheckPage.vue'
import StaffTag from '../components/staff/staffTag/staffTagPage.vue'
import StaffSubTaskDetails from '../components/staff/staffTask/staffSubTasksDetails.vue'

import Introduction from '../components/index/Introduction.vue';

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
        {path: '/about', component: Introduction},

        {path: '/tag/:taskId/:subTaskId/:taskType/:picUrl', component: WorkerTag, name: 'workerTag'},
      ]
    },
    {
      path: '/staffNavi', component: StaffNavi,
      children: [
        {
          path: '/staff', component: StaffMain,
          children: [
            {path: 'allCheck', component: StaffAllCheck},
            {path: 'myCheck', component: StaffMyCheck},
            {path: 'allTasks', component: StaffAllTasks},
            {path: 'myTasks', component: StaffMyTasks},

          ]
        },
        {
          path: '/staffFirstTask', component: StaffFirstLevelTask,
          children: [
            {path: 'staffOverview', component: StaffOverview},
            {path: 'staffSubtasks', component: StaffSubTask},
            {path: 'staffMyparticipation', component: StaffMyParticipation},
          ]
        },

	      {path: '/staffSubTaskDetails/:taskId/:subTaskId/:taskType', name: 'staffSubTaskDetails', component: StaffSubTaskDetails},
        {path: '/staffCheck/:taskId/:subPartId/:taskType/:picUrl', component: StaffCheck, name: 'staffCheck'},
        {path: '/staffTag/:taskId/:subPartId/:taskType/:picUrl', component: StaffTag, name: 'staffTag'},
      ]
    },

    {path: '/', component: Index},
    {path: '/login', component: Login},
    {path: '/signUp', component: SignUp},
    {path: '/makeNaiveTagGreat', component: InsiderLogin},
    {
      path: '/requester', component: RequesterIndex,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: 'home', component: RequesterHome,
        },
        {path: 'taskDetail', name: 'taskDetail', component: TaskDetail},
        {path: 'profile', component: RequesterProfile},
        {path: 'about', component: Introduction},

      ]
    },
    {
      path: '/makeNaiveTagGreatAgain', component: AdminIndex,
      meta: {
        requireAuth: true
      },
      children: [
        {path: 'worker', component: AdminWorkers},
        {path: 'requester', component: AdminRequester},
        {path: 'admin', component: AdminAdmin},
        {path: 'stuff', component: AdminStuff},
        {path: 'activeUser', component: ActiveUser},
        {path: 'totalUser', component: TotalUser},
        {path: 'task', component: TaskAnalysis}
      ]
    },
    {path: '/aboutUs', component: Introduction},

  ],

  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {x: 0, y: 0}
    }
  }

})
