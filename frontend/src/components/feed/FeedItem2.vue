<template>
  <div class="feed-item">
    <div class="feed-top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>{{feed.userId}}</button>
        </div>
      </div>
    </div>
    <div class="space"></div>
    <div class="feed-card">
      <!-- <div class="img" :style="{'background-image': 'url('+defaultImage+')'}" @click="onImgClick"></div> -->
      <img :src="feed.thumbnail" alt="img" @click="onImgClick">
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
      <p>{{feed.contents}}</p>
       <div class="feed-hashtag">
         <p class="hashtag">#맛집</p>
         <p class="hashtag">#파스타</p>
         <p class="hashtag">#와인</p>
        <!-- <p v-for="hashtag in hashtags">{{hashtag}}</p> -->
      </div>
          <h5 class="feed-time">{{feed.writeDate}}</h5>
    </div>
       <!-- <p class="date">9시간 전</p> -->
    <!---->

    <!---->
    <!---->
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";

export default {
  props : ['feed'],
  data: () => {
    return { 
      // feed: this.feed,
      defaultImage,
      defaultProfile,
      hashtags: ['#맛집','#파스타','#와인'],
      heartChange: false,
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png')
    };
  },

  methods : {
    onImgClick() {
      // this.$router.push({name : "/feeds/" + this.feed.feedNo, query : {feedNo : this.feed.feedNo}});
      this.$router.push("/feeds/" + this.feed.feedNo);
      // console.log(this.feed.feedNo);
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
      console.dir(feed);
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
    }
  }
};
</script>
<style scoped>
.user-name{
  margin-top: 3%;
}
.feed-top{
  margin-left: 6%;
}

.feed-card{
  display: flex;
  justify-content: center;
  margin-right: 25px;
  padding: 0;
}

.space{
  height: 50px;
}
.content{
  margin-right: 15px;
}
.hashtag{
  float: left;
}
.feed-time{
  margin-top: 10px;
  padding-right: 5%;
  float:right;
}
.line{
  clear: both;
  height: 1px;
  width: 94%;
  background-color: gray;
}
.feed-btn{
  padding-top: 3%;
}
.like-btn{
  margin-right: 10px;
}
.comments-btn{
  margin-left: 10px;
}
.share-btn{
  margin-left: 20px;
}
.scrap-btn{
  float: right;
  padding-right: 7%;
}

.feed-card > img {
  width: 100%;
  height: 100%;
}
</style>
