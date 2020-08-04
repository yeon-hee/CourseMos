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
      <div v-if="feed.thumbnail=='NULL'">
        <div class="img" :style="{'background-image': 'url('+defaultImage+')'}"></div>
      </div>
      <div v-else>
        <img :src="feed.thumbnail" alt="img">
      </div>
    </div>
    <div class="content">
    <div class="feed-btn">
      <div>
        <img :src="feed.mine ? redHeart: emptyHeart" width="20px" height="20px" class="like-btn" @click="clickLikeBtn(feed)">
        <span>{{feed.likeCount}}</span>
        <img src="../../assets/images/comment.png" width="20px" height="20px" class="comments-btn">
        <span>{{feed.commentCount}}</span>
        <img src="../../assets/images/share.png" width="20px" heig0t="20px" class="share-btn">
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
    <div class="comment-area">
    <div class="line"></div>
    <div style="height:15px;"></div>
      <p style="padding-bottom: 10px;">댓글</p>
      <input class="comment-input"
             v-model="content"
             id="content"/>
      
      <button class="regist-comment"
              @click="registComment(feed)">등록</button>
      <div>
        <div style="margin-top:3%;" v-for="(comment, index) in comments" v-bind:key="comment.commentNo">
          <img src="../../assets/images/user.png" width="30px" height="30px" class="comment-profile"/>
          <p class="comment-writer">{{comment.writer}}</p>
          <p class="comment-content">{{comment.content}}</p>
          <button v-if="userId==comment.writer" @click="deleteComment(feed, comment, index)" class="delete-comment">삭제</button>
          <p style="clear:both;"></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import UserApi from "../../api/UserApi";
import AlertApi from "../../api/AlertApi";

export default {
  props : {feedNo : String},
  data: () => {
    return { 
      feed : {},
      photos : [],
      comments : [],
      content: "",
      defaultImage, 
      defaultProfile,
      hashtags: ['#맛집','#파스타','#와인'],
      heartChange: false,
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      myComment: false,
      userId: ""
    };
  }, 
  created() {
    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.feedNo
    };
    console.log("feedNo : " +this.feedNo);
    FeedApi.loadFeedDetail(
      data,
      response => {
        this.userId = response.data.userId;
        this.feed = response.data.feed;
      },
      error => {
        alert('피드 상세 조회에 실패했습니다.');
      }
    );
    FeedApi.loadFeedPhotos(
      this.feedNo,
      response => {
        this.photos = response.data
        // console.dir(this.photos)
      },
      error => {
        alert('피드 이미지 조회에 실패했습니다.');
      }
    );
    FeedApi.loadFeedComments(
      this.feedNo,
      response => {
        this.comments = response.data
        console.dir(this.comments)
      },
      error => {
        alert('피드 댓글 조회에 실패했습니다.');
      }
    );
  },
  methods: {
    registComment(feed){
        let { feedNo, content } = this;
        let data = {
          token : localStorage.getItem('token'),
          feedNo,
          content
        };
        console.log('댓글 등록 들어옴.');
        feed.commentCount += 1;

        FeedApi.registerComment(
          data,
          response => {
            console.log(response);
            console.log('댓글 등록 성공');
            alert("댓글이 등록되었습니다.");
            this.comments.push(response.data);

          },
          error => {
            alert(error);
          }
        );

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
        feedNo,
        feed
      };

      FeedApi.clickLike(
          data,
          response => {
            console.log("좋아요 클릭!");
          },
          error => {
            alert(error);
          }
        );

      //  AlertApi.requestLike(
      //     data,
      //     response => {
      //       console.log("좋아요 알림 보내기!");
      //     },
      //     error => {
      //       alert(error);
      //     }
      //   );
    },
    deleteComment(feed, comment, index){
      //this.comments.splice(index, 1);
      let feedNo =  this.feedNo;
      let commentNo = comment.commentNo;
      let data = {
        token : localStorage.getItem('token'),
        feedNo,
        commentNo
      };

      FeedApi.deleteComment(
        data,
        response => {
          alert("댓글이 삭제되었습니다.");
          this.comments.splice(index, 1);
          // location.reload();
          // this.$router.push("/feed/main");
        },
        error => {
          alert(error);
        }
      );



    }
  },
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
  clear: both;
  margin-top: 1%;
  margin-right: 7%;
  border-radius: 0;
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
.comment-area{
  padding-left: 7%;
}
.comment-input{
  margin-left: 5%;
  height: auto;
  line-height: normal;
  padding: .3em .9em;
  width: 80%;
  border: none;
  border-radius: 20px;
  -webkit-appearance: none;
  background-color: #ECECEC;
}
.regist-comment{
  padding: 1%;
  margin-left: 3%;
  border: 1px groove gray;
  border-radius: 2px;
}
.comment-profile{
  float: left;
}
.comment-writer{
  padding-top: 1.4%;
  margin-left: 1%;
  font-size: 15px;
  font-weight: 600;
  float: left;
}
.comment-content{
  margin-left: 2%;
  padding-top: 1.5%;
  float: left;
}
.delete-comment{
  padding: 1%;
  border: 1px groove gray;
  border-radius: 2px;
  float: right;
  margin-right: 3%;
  margin-top: 2%;
}

.feed-card > div> img {
  width: 100%;
  height: 100%;
}
</style>
