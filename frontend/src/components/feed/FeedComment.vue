<template>
<div> 
    <div style="padding: 15px; height: 20px;">
        <img src="../../assets/images/backwards_icon_149034.png" width="20px" height="20px" style="float: left;" @click="goBack()">
        <div style="text-align: center; font-weight: bold; font-size: 17px;">댓글</div>
    </div>

      <div style="padding: 20px;">
      <!-- <p style="padding-bottom: 10px;">댓글
      <input class="comment-input"
             v-model="content"
             id="content"/>
      
      <button class="regist-comment"
              @click="registComment(feed)">등록</button> -->
              <br>
      <div>
        <div v-for="(comment, index) in comments" v-bind:key="comment.commentNo" style="height: 44px;">
          <img src="../../assets/images/user.png" width="30px" height="30px" class="comment-profile">
          <p class="comment-writer">{{comment.writer}}</p>
          <p class="comment-content">{{comment.content}}</p>
          <button v-if="userId==comment.writer" @click="deleteComment(feed, comment, index)" class="delete-comment">삭제</button>
          <p style="clear:both;"></p>
        </div>
      </div> 
    </div>

    <div class="plane">
      <img src="../../assets/images/send_120237.png" class="send" width="43px" height="43px" style="padding: 7px; float: left; position: sticky;">
      <input style="border:none; width:70%" v-model="content" id="content"/>
      <button class="regist-comment" @click="registComment(feed)">등록</button>
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
      feedNo: {},
      feed : {},
      photos : [],
      comments : [],
      content: "",
      defaultImage, 
      defaultProfile,
      myComment: false,
      userId: ""
    };
  }, 
  created() {
    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.$route.params.feedNo
    };

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

    FeedApi.loadFeedComments(
      data,
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
    goBack(){
        this.$router.push("/feeds/back/" + this.$route.params.feedNo);
    },
    registComment(feed){
        let { feedNo, content } = this;
        let data = {
          token : localStorage.getItem('token'),
          feedNo : this.$route.params.feedNo,
          content
        };
        console.log('댓글 등록 들어옴.');
        feed.commentCount += 1;

        FeedApi.registerComment(
          data,
          response => {
            alert("댓글이 등록되었습니다.");
            this.comments.push(response.data);
            this.content = []; // 작성된 댓글 지워주기
          },
          error => {
            alert(error);
          }
        );
    },
   
    deleteComment(feed, comment, index){
      let feedNo =  this.feedNo;
      let commentNo = comment.commentNo;
      let data = {
        token : localStorage.getItem('token'),
        feedNo : this.$route.params.feedNo,
        commentNo
      };

      FeedApi.deleteComment(
        data,
        response => {
          alert("댓글이 삭제되었습니다.");
          this.comments.splice(index, 1);
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
.send{
  -webkit-filter: opacity(.10) drop-shadow(0 0 0 gray);
  filter: opacity(.3) drop-shadow(0 0 0 gray);
}
.plane{
    position: absolute;
    bottom: 57px;
    width: 380px;
    overflow: hidden;
    border-top: 1px solid lightslategray;
    background-color: white;
    max-width: 500px;
}
.user-name{
  margin-top: 3%;
}
.feed-top{
  margin-left: 6%;
}
.feed-card{
  clear: both;
  margin-top: 1%;
  margin-right: 5%;
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
  width: 97%;
  background-color: gray;
}
.line2{
  clear: both;
  height: 1px;
  width: 97%;
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
  margin-left: 4%;
  height: auto;
  line-height: normal;
  padding: .3em .9em;
  width: 200px;
  border: none;
  border-radius: 20px;
  -webkit-appearance: none;
  background-color: #ECECEC;
}
.regist-comment{
  padding: 1%;
  margin-top: 5px;
  margin-left: 3%;
  border: 1px groove gray;
  border-radius: 2px;
}
.comment-profile{
  float: left;
}
.comment-writer{
  padding-top: 1.4%;
  margin-left: 2%;
  font-size: 15px;
  font-weight: 600;
  float: left;
}
.comment-content{
  margin-left: 2%;
  padding-top: 1.5%;
  font-size: 15px;
  float: left;
}
.delete-comment{
  padding: 1%;
  border: 1px groove gray;
  border-radius: 2px;
  float: right;
  margin-right: 3%;
}
.line{
  clear: both;
  height: 1px;
  width: 100%;
  background-color: gray;
}
.feed-card > div> img {
  width: 100%;
  height: 100%;
}

</style>
