import 'babel-polyfill'
import 'es6-promise/auto'
import Vue from 'vue'
import router from './router/router'

import BootstrapVue from "bootstrap-vue"

import App from './App.vue'

import Default from './Layout/Wrappers/baseLayout.vue';
import Pages from './Layout/Wrappers/pagesLayout.vue';

Vue.config.productionTip = false;

Vue.use(BootstrapVue);

Vue.component('default-layout', Default);
Vue.component('userpages-layout', Pages);

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
});
