<template>
<v-app id="app">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>코스모스</title>
  </head>
  <router-view></router-view>
</v-app>
</template>

<script>
// import "./components/css/style.scss";
// import Vue from 'vue'
// import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
// // import 'bootstrap/dist/css/bootstrap.css'
// // import 'bootstrap-vue/dist/bootstrap-vue.css'

// Vue.use(BootstrapVue)
// Vue.use(IconsPlugin)

export default {
  name: "app",
  mounted() {
    this.addScript();
  },
  methods : {
    addScript() {
      console.log('지도 add');
      const script = document.createElement('script')
      /* global kakao */
      script.onload = () => kakao.maps.load(()=>console.log("Hi"))
      script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=6eef005ce939915ab51bb458c785e9f4&autoload=false&libraries=services,clusterer,drawing'
      document.head.appendChild(script);
    },
  }
};
</script>
<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.notosanskr * { 
 font-family: 'Noto Sans KR', sans-serif;
}

#app {
  margin: auto;
}

* {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
