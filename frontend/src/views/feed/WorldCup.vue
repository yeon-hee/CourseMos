<template>
  <div class="worldCup">
    <div class="title pt-3 ml-2 font-weight-bold text-left text-sm-h3 text-h4">추천 테마</div>
    <v-container fluid class="container pt-0">
      <v-row dense>
        <v-col class="menu1" :cols="12" v-on:click="choose(idx)">
          <v-card>
            <v-img
              src="../../assets/images/foods.jpg"
              class="thumbnail white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="150px"
            >
              <v-card-title>음식 월드컵</v-card-title>
            </v-img>
          </v-card>
        </v-col>
        <v-col class="menu1" :cols="12" v-on:click="choose(idx)">
          <v-card>
            <v-img
              src="../../assets/images/foods.jpg"
              class="thumbnail white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="150px"
            >
              <v-card-title>놀거리 월드컵</v-card-title>
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
          thumbnailUrl:
            "https://img.piku.co.kr/w/uploads/aPqCxg/84b10cbdf0ec13b33ad2ce81420c7b96.jpg",
        },
        {
          id: 2,
          name: "소고기",
          thumbnailUrl:
            "https://img.piku.co.kr/w/uploads/aPqCxg/03c4a79455ec531adf0cd04513272aa8.jpg",
        },
        {
          id: 3,
          name: "치킨",
          thumbnailUrl:
            "https://img.piku.co.kr/w/uploads/aPqCxg/5272db391e7bbea26a87c765cd2fe017.jpg",
        },
        {
          id: 4,
          name: "피자",
          thumbnailUrl:
            "https://img.piku.co.kr/w/uploads/aPqCxg/8bf97919e5e9898f5128047cdf520788.jpg",
        },
        {
          id: 5,
          name: "짜장면",
          thumbnailUrl:
            "https://img.piku.co.kr/w/uploads/aPqCxg/eda37ece53fe020e4f78e66049a055d6.jpg",
        },
        {
          id: 6,
          name: "짬뽕",
          thumbnailUrl:
            "https://img.piku.co.kr/w/uploads/aPqCxg/97864c4aa544198bec6ef110a9f33e80.jpg",
        },
        {
          id: 7,
          name: "돈까스",
          thumbnailUrl:
            "https://d20aeo683mqd6t.cloudfront.net/ko/articles/title_images/000/026/550/medium/IMG_2236s.jpg?2019",
        },
        {
          id: 8,
          name: "떡볶이",
          thumbnailUrl:
            "https://dimg.donga.com/a/500/0/90/5/ugc/CDB/29STREET/Article/5e/b2/04/e8/5eb204e81752d2738236.jpg",
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
        alert("1등 음식은 " + this.winner.name);
        console.log(this.winner);
        this.$router.push("/feed/main/" + this.winner.name);
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
