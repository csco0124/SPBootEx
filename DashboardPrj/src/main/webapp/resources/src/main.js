import 'babel-polyfill'
import 'es6-promise/auto'
import Vue from 'vue'
import router from './router/router'

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import BootstrapVue from "bootstrap-vue"

import App from './App.vue'

import VueCookies from 'vue-cookies'

import Default from './Layout/Wrappers/baseLayout.vue';
import Pages from './Layout/Wrappers/pagesLayout.vue';

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(VueCookies);
/*
예제
$cookies.set('vue_test', 'vue_test_value', '30s');  //30초동안 쿠키 저장
$cookies.get('vue_test')  //쿠키 가져오
*/
Vue.component('default-layout', Default);
Vue.component('userpages-layout', Pages);

router.beforeEach( async(to, from, next) => {

  alert(to + ":" + from + ":" + next);
  next();
  //next({ name: 'login-boxed' });
})

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
});
