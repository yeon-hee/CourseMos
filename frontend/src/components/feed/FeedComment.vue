<template>
<div>
 <div class="feed-item">
    <div class="feed-card">
      <p style="padding-bottom: 10px;">댓글
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
  <Nav />
</div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import UserApi from "../../api/UserApi";
import AlertApi from "../../api/AlertApi";
import Nav from "../../views/Nav.vue";

export default {
  props : {feedNo : String},
  components: { Nav },
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
          console.log(data),
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
   
    deleteComment(feed, comment, index){
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
  width: 200px;
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
