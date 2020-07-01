<template>
  <div id="app">
    <component :is="layout">
        <transition name="fade" mode="out-in">
            <router-view v-on:make-toast="makeToast"></router-view>
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
  	makeToast(variant = null) {
	    this.$bvToast.toast('Toast body content', {
	      title: "Variant ${variant || 'default'}",
	      variant: variant,
	      solid: true
	    })
	}
  },
  computed: {
    layout() {
      return (this.$route.meta.layout || default_layout) + '-layout';
    }
  },
  created: function () {
    console.log('1111');
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
