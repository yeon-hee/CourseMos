<template>
  <div class="worldCup">
    <div
      class="title pt-3 mt-2 font-weight-bold text-center text-sm-h3 text-h4"
      style="color:red;"
    >추천 테마</div>
    <v-container fluid class="container pt-0">
      <v-row dense>
        <v-col class="menu1" cols="12" sm="6" offset-sm="3" @click="gotoFood()">
          <v-card>
            <v-img
              src="../../assets/images/foods.jpg"
              class="thumbnail white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="150px"
            >
              <v-card-title>먹거리 월드컵</v-card-title>
            </v-img>
          </v-card>
        </v-col>
        <v-col class="menu1" cols="12" sm="6" offset-sm="3" @click="gotoPlay()">
          <v-card>
            <v-img
              src="../../assets/images/board.png"
              class="thumbnail white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="150px"
            >
              <v-card-title>놀거리 월드컵</v-card-title>
            </v-img>
          </v-card>
        </v-col>
        <v-col class="menu1" cols="12" sm="6" offset-sm="3" @click="gotoAct()">
          <v-card>
            <v-img
              src="../../assets/images/draw.jpg"
              class="thumbnail white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="150px"
            >
              <v-card-title>볼거리 월드컵</v-card-title>
            </v-img>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => {
    return {};
  },
  methods: {
    gotoFood() {
      this.$router.push("/worldcup/food");
    },
    gotoPlay() {
      this.$router.push("/worldcup/play");
    },
    gotoAct() {
      this.$router.push("/worldcup/act");
    },
  },
};
</script>

<style scoped>
.foodCup {
  height: 120%;
  max-width: 500px;
  margin: 0 auto;
}
.title {
  color: rgb(239, 91, 91);
}
.thumbnail {
  border: 1px solid rgba(0, 0, 0, 0.0975);
  border-radius: 5px;
  box-shadow: 1px 1px 1px 1px grey;
}
.trophy {
  display: block;
  margin: 0 auto;
}
.thumbnail:hover {
  opacity: 0.9;
}
</style>
