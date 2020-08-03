<template>
  <div class='NotFound'>
      <h1>404 Page Not Found</h1>
      <p><img src="../../assets/images/NotFound.png" alt="NotFound" width="400" height="400"></p>
      <button><router-link to="/feed/main">메인으로 돌아가기</router-link></button>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.NotFound {
    text-align: center;
}
h1 {
    margin: 50px 0;
}
button {
    font-size: 20px;
}
</style>