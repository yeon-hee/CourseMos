<template>
  <!-- <div
    class="card overflow-hidden box-shadow text-left shadow1"
    style="margin-top: 10px; margin-bottom: 5%;"
  >-->
  <div class="worldCup">
    <h1>하이</h1>
    <v-container fluid class="container">
      <v-card>
        <v-img
          src="../../assets/images/trophy.jpeg"
          class="thumbnail white--text align-end"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="200px"
          width="200px"
        >
          <v-card-title v-text="menuList[idx].name"></v-card-title>
        </v-img>
      </v-card>
    </v-container>
  </div>
  <!-- </div> -->
</template>

<script>
export default {};
</script>

<style scoped>
.worldCup {
  height: 120%;
  /* background-color: rgb(244, 81, 30); */
  max-width: 500px;
  margin: 0 auto;
}
</style>