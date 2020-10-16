<template>
  <div class="foodCup">
    <div
      class="pt-16 mb-5 font-weight-bold text-center text-sm-h4 text-h4 indigo--text text--darken-3"
    >먹거리 추천 월드컵</div>
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
          name: "삼겹살",
          thumbnailUrl: require("../../assets/images/pork.jpg"),
          key: "삼겹살",
        },
        {
          id: 2,
          name: "족발",
          thumbnailUrl: require("../../assets/images/foot.jpg"),
          key: "족발",
        },
        {
          id: 3,
          name: "치킨",
          thumbnailUrl: require("../../assets/images/chicken.jpg"),
          key: "치킨",
        },
        {
          id: 4,
          name: "피자",
          thumbnailUrl: require("../../assets/images/pizza.jpg"),
          key: "피자",
        },
        {
          id: 5,
          name: "짜장면",
          thumbnailUrl: require("../../assets/images/zza.jpg"),
          key: "중화요리",
        },
        {
          id: 6,
          name: "초밥",
          thumbnailUrl: require("../../assets/images/sushi.jpg"),
          key: "초밥",
        },
        {
          id: 7,
          name: "파스타",
          thumbnailUrl: require("../../assets/images/pasta.jpg"),
          key: "이탈리안",
        },
        {
          id: 8,
          name: "떡볶이",
          thumbnailUrl: require("../../assets/images/dduk.jpg"),
          key: "분식",
        },
      ],
    };
  },
  //       created(){
  //           shuffle(this.menuList) {
  //           var j, x, i;
  //           for (i = menuList.length; i; i -= 1) {
  //               j = Math.floor(Math.random() * i);
  //               x = menuList[i - 1];
  //               menuList[i - 1] = menuList[j];
  //               menuList[j] = x;
  //           }
  //       }
  //   },
  methods: {
    choose(idx) {
      if (this.round == 2) {
        this.winner = this.menuList[idx];
        this.dialog = true;
        alert("1등 먹거리는 " + this.winner.name + "입니다.");
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
.foodCup {
  height: 120%;
  /* background-color: rgb(244, 81, 30); */
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
