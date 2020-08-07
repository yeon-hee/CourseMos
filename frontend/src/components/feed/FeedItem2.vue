<template>
<div>
  <v-card class="mx-auto">

    <v-list-item>
      <v-list-item-avatar color="grey">
        <img src="@/assets/images/profile_default.png"/>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="headline">{{feed.userId}}</v-list-item-title>
        <v-list-item-subtitle>{{feed.writeDate}}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-row>
      <v-col v-for="course in courses" :key="course.courseOrder" cols=3>
        <v-badge color="#1A237E" :content="Number(course.courseOrder)" overlap>
          <v-card class="mx-auto" @click="onImgClick">
            <v-img :src="course.thumbnailUrl" aspect-ratio="1" class="grey lighten-2"/>
            <v-card-subtitle class="pb-0" style="font-size:0.8em">{{cutStr(course.tradeName)}}</v-card-subtitle>
          </v-card>
        </v-badge>
      </v-col>
    </v-row>
    <v-card-actions>
      <v-btn class="ma-2" tile @click="clickLikeBtn(feed)">
        <v-img :src="feed.mine ? redHeart: emptyHeart" max-height="20px" max-width="20px" left/>
        <span>{{feed.likeCount}}</span>
      </v-btn>
      <v-btn class="ma-2" tile @click="onImgClick">
        <v-icon left>far fa-comment</v-icon>
        <span>{{feed.commentCount}}</span>
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn class="ma-2" tile>
        <v-icon>mdi-bookmark</v-icon>
      </v-btn>
    </v-card-actions>

  </v-card>
<!-- <div class="w3-col m7">
  <div class="w3-container w3-card w3-white w3-round w3-margin">
    <button class="w3-left w3-circle w3-margin-right">{{feed.userId}}</button>
    <span class="w3-right w3-opacity">{{feed.writeDate}}</span>
    <hr class="w3-clear">
    <div class="feed-card" @click="onImgClick">
        <div style="margin-top:3%;" v-for="course in courses" v-bind:key="course.courseOrder" class="content">
          <img v-bind:src="course.thumbnailUrl" width="36px" height="36px" class="comment-profile"/>
          <p class="comment-writer">{{course.courseOrder + " : "+ course.tradeName}}</p>
        </div>
    </div>
    <div class="content">
    <div class="feed-btn">
      <div>
        <img :src="feed.mine ? redHeart: emptyHeart" width="20px" height="20px"
            class="like-btn" @click="clickLikeBtn(feed)">
        <span>{{feed.likeCount}}</span>
        <img src="../../assets/images/comment.png" width="20px" height="20px"
            class="comments-btn" @click="onImgClick">
        <span>{{feed.commentCount}}</span>
        <img src="../../assets/images/share.png" width="20px" heig0t="20px" 
              class="share-btn">
        <img src="../../assets/images/star.png" width="20px" height="20px" class="scrap-btn">
      </div>
    </div>
    <div style="height:10px;"></div>
    <div class="line"></div>
      <div style="height:15px;"></div>
      <h5 class="feed-time">{{feed.writeDate}}</h5>
    </div>
  </div>
</div> -->
</div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import moment from "moment";

export default {
  props : ['feed'],
  data: () => {
    return { 
      defaultImage,
      defaultProfile,
      // hashtags: ['#맛집','#파스타','#와인'],
      heartChange: false,
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      courses : []
    };
  },
  created() {
    this.feed.writeDate = moment(this.feed.writeDate).format( 'YYYY-MM-DD HH시');
    let data = {
      token : localStorage.getItem("token"),
      feedNo : this.feed.feedNo
    }
    FeedApi.getCourse(data,
      response => {
        if(response.data.length ==0) return;
        this.courses = this.courses.concat(response.data);
      },
      error => {
        alert(error);
      }
    )
  },
  methods : {
    onImgClick() {
      // this.$router.push({name : "/feeds/" + this.feed.feedNo, query : {feedNo : this.feed.feedNo}});
      this.$router.push("/feeds/" + this.feed.feedNo);
    },
    clickLikeBtn(feed){
      if(!feed.mine){
        feed.mine = true;
        feed.likeCount += 1;
      } else{
        feed.mine = false;
        feed.likeCount -= 1;
      }

      let feedNo =  feed.feedNo;
      let data = {
        token : localStorage.getItem('token'),
        feedNo
      };

      FeedApi.clickLike(
          data,
          response => {
            console.log("좋아요 클릭");
          },
          error => {
            alert(error);
          }
        );
    },
    cutStr(str) {
      if(str.length >= 3){
          return str.substr(0,3)+"...";
      } else return str;
    }
  }
};
</script>
<style scoped>
</style>
