// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import echarts from 'echarts';
import jQuery from 'jquery';
import WorkerRank from './components/worker/workerHome/workerRank.vue'

Vue.prototype.$echarts = echarts;
Vue.use(ElementUI);
Vue.config.productionTip = false

window.$ = jQuery;
window.jQuery = jQuery;

new Vue({
  el: '#app',
  router,
  render: h => h(App)

})
