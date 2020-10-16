<template>
  <div class="playCup">
    <div
      class="pt-16 mb-5 font-weight-bold text-center text-sm-h4 text-h4 indigo--text text--darken-3"
    >볼거리 추천 월드컵</div>
    <img
      src="../../assets/images/trophy.jpeg"
      class="trophy"
      width="100px"
      height="100px"
      alt="trophy"
    />
    <div v-if="round==2">
      <div class="round mb-5 text-center font-weight-bold red--text text-sm-h4 text-h4">결승전!</div>
    </div>
    <div v-else-if="round==4">
      <div
        class="round mb-5 text-center font-weight-bold orange--text text--darken-3 text-sm-h4 text-h4"
      >4강</div>
    </div>
    <div v-else>
      <div class="round mb-5 text-center font-weight-bold amber--text text-sm-h4 text-h4">8강</div>
    </div>
    <v-container fluid class="container">
      <v-row dense>
        <v-col class="menu1" :cols="6" v-on:click="choose(idx)">
          <v-card>
            <v-img
              :src="menuList[idx].thumbnailUrl"
              class="thumbnail white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <v-card-title v-text="menuList[idx].name"></v-card-title>
            </v-img>
          </v-card>
        </v-col>
        <v-col class="menu2" :cols="6" v-on:click="choose(idx+1)">
          <v-card>
            <v-img
              :src="menuList[idx+1].thumbnailUrl"
              class="thumbnail white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <v-card-title v-text="menuList[idx+1].name"></v-card-title>
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
    return {
      idx: 0,
      round: 8,
      winner: null,
      winners: [],
      dialog: false,
      menuList: [
        {
          id: 1,
          name: "아쿠아리움",
          thumbnailUrl: require("../../assets/images/aqua.jpg"),
          key: "아쿠아리움",
        },
        {
          id: 2,
          name: "동물원",
          thumbnailUrl: require("../../assets/images/zoo.jpg"),
          key: "동물원",
        },
        {
          id: 3,
          name: "공연",
          thumbnailUrl: require("../../assets/images/perform.jpg"),
          key: "공연",
        },
        {
          id: 4,
          name: "영화관",
          thumbnailUrl: require("../../assets/images/movie.jpg"),
          key: "영화",
        },
        {
          id: 5,
          name: "쇼핑",
          thumbnailUrl: require("../../assets/images/shop.jpg"),
          key: "의류",
        },
        {
          id: 6,
          name: "전시회",
          thumbnailUrl: require("../../assets/images/draw.jpg"),
          key: "미술관",
        },
        {
          id: 7,
          name: "공원",
          thumbnailUrl: require("../../assets/images/park.jpg"),
          key: "공원",
        },
        {
          id: 8,
          name: "도서관",
          thumbnailUrl: require("../../assets/images/lib.jpg"),
          key: "도서관",
        },
      ],
    };
  },
  methods: {
    choose(idx) {
      if (this.round == 2) {
        this.winner = this.menuList[idx];
        this.dialog = true;
        alert("1등 볼거리는 " + this.winner.name + "입니다.");
        this.$router.push("/feed/main/" + this.winner.key);
      } else {
        this.winners.push(this.menuList[idx]);
        this.idx += 2;
        if (this.idx == this.round) {
          this.round /= 2;
          this.menuList = JSON.parse(JSON.stringify(this.winners));
          this.winners = [];
          this.idx = 0;
        }
      }
    },
  },
};
</script>

<style scoped>
.playCup {
  height: 120%;
  max-width: 500px;
  margin: 0 auto;
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
