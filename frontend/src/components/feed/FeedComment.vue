<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12" sm="6" offset-sm="3">
        <img src="../../assets/images/backwards_icon_149034.png" width="20px" height="20px" style="float: left;" @click="goBack()">
        <p style="text-align: center; font-weight: bold; font-size: 17px;">댓글</p>
      </v-col>
    </v-row>
    <v-row v-for="(comment, index) in comments" v-bind:key="comment.commentNo" style="height: 44px;">
      <v-col cols="12" sm="6" offset-sm="3">
        <img src="../../assets/images/user.png" width="30px" height="30px" class="comment-profile">
          <p class="comment-writer">{{comment.writer}}</p>
          <p class="comment-content">{{comment.content}}</p>
          <v-btn small color="error"
                v-if="userId==comment.writer" @click="deleteComment(feed, comment, index)" style="float:right;">삭제</v-btn>

          <div style="clear:both;"></div>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="6" offset-sm="3" class="plane">
        <img src="../../assets/images/send_120237.png" class="send" width="43px" height="43px" style="padding: 7px; float: left; position: sticky;">
        <input style="border:none; width:70%" v-model="content" id="content"/>
        <v-btn color="primary" @click="registComment(feed)">등록</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import UserApi from "../../api/UserApi";
import AlertApi from "../../api/AlertApi";

export default {
  // props : {feedNo : String},
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
    width: 100%;
    overflow: hidden;
    border-top: 1px solid lightslategray;
    background-color: white;
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

</style>
