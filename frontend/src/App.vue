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

export default {
  name: "app",
  mounted() {
    this.addScript();
  },
  methods : {
    addScript() {
      const script = document.createElement('script')
      /* global kakao */
      script.onload = () => kakao.maps.load()
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
