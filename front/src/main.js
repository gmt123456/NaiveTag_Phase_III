// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import echarts from 'echarts';
import jQuery from 'jquery';


Vue.prototype.$echarts = echarts;
Vue.use(ElementUI);
Vue.config.productionTip = false

window.$ = jQuery;
window.jQuery = jQuery;


window.onunload = function () {
  if (event.clientX > document.body.clientWidth && event.clientY < 0 || event.altKey) {
    localStorage.clear();
  }
};

router.beforeEach((to, from, next) => {
  // console.log('before each');
  // console .log(to.path);
  // console.log(from.path);
  if (to.matched.some(record => record.meta.requireAuth)) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
    if (!localStorage.token) {
      if (to.path.includes('makeNaiveTag'))
        next({
          path: 'makeNaiveTagGreat'
        });
      else
        next({
          path: '/',
        });
    } else {
      next();
    }
  } else {
    next() // 确保一定要调用 next()
  }
});

new Vue({
  el: '#app',
  router,
  render: h => h(App),

})
