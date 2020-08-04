<template>
  <div id="app">
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>맛동산</title>
    </head>
    <router-view></router-view>
  </div>
</template>
<script type="text/javascript" src='//dapi.kakao.com/v2/maps/sdk.js?appkey=6eef005ce939915ab51bb458c785e9f4&autoload=false&libraries=services,clusterer,drawing'></script>
<script>
import "./components/css/style.scss";
export default {
  name: "app"
};
</script>
<style scoped>
#app{
  margin: auto;
}
</style>
