<template>
  <div id="app">
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>코스모스</title>
    </head>
    <router-view></router-view>
  </div>
</template>

<script>
import "./components/css/style.scss";
// import Vue from 'vue'
// import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
// // import 'bootstrap/dist/css/bootstrap.css'
// // import 'bootstrap-vue/dist/bootstrap-vue.css'

// Vue.use(BootstrapVue)
// Vue.use(IconsPlugin)

export default {
  name: "app"
};
</script>
<style scoped>
#app{
  margin: auto;
}
</style>
