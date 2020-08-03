<template>
  <div id="app">
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>맛동산</title>
    </head>
    <router-view></router-view>
  </div>
</template>
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
