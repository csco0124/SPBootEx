<template>
  <div id="app">
    <component :is="layout">
        <transition name="fade" mode="out-in">
            <router-view></router-view>
        </transition>
    </component>
  </div>
</template>

<script>
import I18n from './i18n/i18n'
import axios from 'axios'

const default_layout = "default";

export default {
  name: 'app',
  i18n: I18n,
  data () {
    return {
      msg: ''
    }
  },
  methods: {

  },
  computed: {
    layout() {
      return (this.$route.meta.layout || default_layout) + '-layout';
    }
  },
  created: function () {
      this.$store.dispatch('getServerEnvData');
      this.$store.dispatch('getUserSessionData');
  },
  components: {

  }
}
</script>

<style lang="scss">
@import "assets/base.scss";
</style>
