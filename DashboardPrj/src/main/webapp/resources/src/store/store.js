import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    serverName: "none"
  },
  getters: {
    getServer : function(state){
      return state.serverName;
    }
  },
  mutations: {
    setServerEnv: function (state, payload) {
      state.serverName = payload.data;
    }
  },
  actions: {
    getServerEnvData: function (context) {
     axios.get('/getServerEnv.json').then(response => {
        console.log(response.data);
        context.commit('setServerEnv', response);
      });
    }
  }
})

export default store