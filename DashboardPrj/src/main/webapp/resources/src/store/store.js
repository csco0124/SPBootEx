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
    },
    getUserEmail : function(state){
      return state.userEmail;
    }
  },
  mutations: {
    setServerEnv: function (state, payload) {
      state.serverName = payload.data;
    },
    setUserSessionData: function (state, data) {
      state.userName = data.user_name;
      state.userEmail = data.user_email;
      state.userImgUrl = data.user_img_url;
      state.userLocale = data.user_locale;
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
    	//동기식 처리를 위해 사용
    	$.ajax({ 
    		data: {}, 
    		url: '/getUserSession', 
    		dataType: 'json', 
    		async: false, 
    		success: function(response) { 
    			context.commit('setUserSessionData', response); 
    		} 
    	});
     /*axios.get('/getUserSession').then(response => {
        context.commit('setUserSessionData', response);
      });*/
    },
    userSessionInvalidate: function (context) {
     axios.get('/userSessionInvalidate').then(response => {
        context.commit('userSessionInvalidate', response);
      });
    }
  }
})

export default store
