import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    serverName: "none",
    userName: "",
    userEmail: "",
    userImgUrl: "",
    userLocale: "ko"
  },
  getters: {
    getServer : function(state){
      return state.serverName;
    }
  },
  mutations: {
    setServerEnv: function (state, payload) {
      state.serverName = payload.data;
    },
    setUserSessionData: function (state, payload) {
      state.userName = payload.data.user_name;
      state.userEmail = payload.data.user_email;
      state.userImgUrl = payload.data.user_img_url;
      state.userLocale = payload.data.user_locale;
    },
    userSessionInvalidate: function (state, payload) {
      window.location.href="/";
    }
  },
  actions: {
    getServerEnvData: function (context) {
     axios.get('/getServerEnv').then(response => {
        context.commit('setServerEnv', response);
      });
    },
    getUserSessionData: function (context) {
     axios.get('/getUserSession').then(response => {
        context.commit('setUserSessionData', response);
      });
    },
    userSessionInvalidate: function (context) {
     axios.get('/userSessionInvalidate').then(response => {
        context.commit('userSessionInvalidate', response);
      });
    }
  }
})

export default store