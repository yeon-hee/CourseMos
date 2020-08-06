
<template>
  <div>
  <LogoTitle />
  <div class="join wrapC total">
    <div class="title">
        <a href="#/users/profile"><img src="../../assets/images/back-arrow.png" width="30px" height="30px" class="back-arrow"></a>
        <!-- <h1 class="logo">맛동산</h1> -->
    </div>
    <!-- <div class="line"></div> -->

    <div>
        <div class="page-title">내 계정 설정</div>
        <div class="page-title-en">My Settings</div>
    </div>
    <div class="space"></div>
    
    <div class="form-wrap">
      <div class="input-wrap">
        <img :src="profileThumbnail" width="50px" height="50px">
        <!-- <form name="uploadForm" method="post" enctype="multipart/form-data"> -->
          <input @change="selectImage()" type="file" ref="profileImage" name="imgFile" class="upload-input" accept="image/*">
          <button class="upload-btn" @click="updateProfileImage()" value="등록">등록</button>
        <!-- </form> -->
      </div>
      <div class="input-wrap">
        <label for="userId">아이디</label>
        <input class="account-input"
          v-model="userId" 
          v-bind:class="{error : error.userId, complete:!error.userId&&userId.length!==0}"
          v-bind:placeholder="placeUserId"
          id="userId" />
        <div class="error-text" v-if="error.userId">{{error.userId}}</div>
      </div>

      <div class="input-wrap">
        <label for="email">이메일</label>
        <input class="account-input"
          v-model="email"
          v-bind:placeholder="placeEmail"
          id="email" readonly/>
        <div class="email-text">이메일은 변경 불가합니다.</div>
      </div>

      <div class="input-wrap">
        <label for="password-change">비밀번호 변경</label>
        <a href="#/users/profile/password" class="password-change">비밀번호 변경</a>
      </div>

      <div class="input-wrap">
        <label for="self-info">한줄 자기소개</label>
        <!-- <input 
          v-model="profileComment" class="self-info-input"
          id="email" :type="text" 
          placeholder="간단한 자기소개를 입력하세요." /> -->
          <textarea class="self-info-textarea" cols="32" rows="7"
            v-bind:class="{error : error.profileComment, complete:!error.profileComment&&profileComment.length!==0}"
            v-model="profileComment"
            v-bind:placeholder="placeProfileComment"></textarea>
      </div>
    </div>


    <button 
      class="btn-bottom"
      @click="onChange"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}"
      >저장</button>
  </div>
  </div>
</template>

<script>
import UserApi from "../../api/UserApi";
import axios from 'axios';
import LogoTitle from '../LogoTitle.vue';
import * as firebase from "firebase/app";

export default {

  components: {LogoTitle},
  
  created() {
    this.component = this;
    console.log("created");

    let data = {
        token : localStorage.getItem('token')
    };
    UserApi.requestUserInfo(
      data,
      response => {
        console.dir(response);
        this.placeEmail = response.data.email;
        this.placeUserId = response.data.userId;
        this.placeProfileComment = response.data.profileComment;
        this.email = response.data.email;
        this.profileThumbnail = response.data.profilePhoto;
      },
      error => {
        alert(error);
        this.isSubmit = true;
        }
    );
  },
  watch: {
    userId: function(v) {
      this.checkUserId();
      this.finalCheck();
    },
    profileComment: function(v) {
      this.checkProfileComment();
      this.finalCheck();
    }
  },
  methods: {
    selectImage() {
      this.profileImage = this.$refs.profileImage.files[0];
      // console.dir(this.profileImage)
      this.profileThumbnail = window.URL.createObjectURL(this.profileImage);
      
    },
    updateProfileImage() {
      var fullPath = new Date().getTime() + this.profileImage.name
      console.log(fullPath)
      var storageRef = firebase.storage().ref('images/' + fullPath);
      // this.uploadTask = firestorage.ref('images/' + file.name).put(file)

      storageRef.put(this.profileImage).then( (snapshot) => {
          var imageUrl = firebase.storage().ref('images/' + fullPath).getDownloadURL();
          imageUrl.then((url) => {
            console.log(url)
            this.profilePhoto = url
            let { userId, email, profileComment, profilePhoto } = this;
            let data = {
              token : localStorage.getItem('token'),
              userId,
              email,
              profileComment,
              profilePhoto
            };

            UserApi.changeProfileImage(
              data,
              response => {
                alert("변경사항이 저장되었습니다.");
                this.$router.push("/users/profile");
              },
              error => {
                alert(error);
              }
            );
          })
      })
    },
    finalCheck(){
      if(this.userId.length == 0 && this.profileComment.length == 0){
        this.isSubmit = false;
      }
    },
    checkUserId(){
      this.error.userId = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
      this.isSubmit = true;
    },
    checkProfileComment(){
      this.error.profileComment = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
      this.isSubmit = true;
    },
    onChange() {
      if (this.isSubmit) {
        let { userId, email, profileComment } = this;
        let data = {
          token : localStorage.getItem('token'),
          userId,
          email,
          profileComment
        };
        console.log('프로필 정보 수정 들어옴.');

        UserApi.requestSettings(
          data,
          response => {
            localStorage.setItem("userId", this.userId);
            console.log(response);
            console.log('프로필 정보 변경 성공!');
            alert("변경사항이 저장되었습니다.");
            this.isSubmit = true;
            this.$router.push("/users/profile");
          },
          error => {
            alert(error);
            this.isSubmit = true;
          }
        );
        this.isSubmit = false;
      }
    }
  },
  data: () => {
    return {
      userId:"",
      email:"",
      profilePhoto : "", 
      profileComment: "",
      placeUserId: "",
      placeEmail: "",
      placeProfileComment: "",
      isLoading: false,
      error: {
        userId: false
      },
      isSubmit: false,
      termPopup: false,
      profileImage : {},
      profileThumbnail : "../../assets/images/user.png"
    };
  }
};
</script>
<style scoped>
.total{
  align-content: center;
  margin: 0 auto;
}

.title{
    padding-top: 5%;
}

.back-arrow{
    float: left;
    width: 8%;
}

.logo{
    text-align: center;
    float: left;
    width: 80%;
}

.line{
    clear: both;
    background-color: gray;
    height: 1px;
}

.page-title{
    margin-left: 20%;
    float: left;
    font-size: 20px;
    padding-top: 1%;
}

.page-title-en{
    float: left;
    font-size: 12px;
    padding-left: 10px;
    padding-top: 2%;
}

.space{
    clear: both;
    height: 30px;
}

.input-wrap{
    margin-top:3%;
}

.account-input{
    margin-left: 50px;
    width: 70%;
}

.self-info-textarea{
    padding: 5px;
    margin-left: 3%;
    border: 1.1px solid #CCCBCB;
    resize: none;
}

.email-text{
    color: red;
    font-size: 10px;
    margin-left: 25%;
}

.password-change{
    margin-left: 3%;
    text-decoration: underline;
}

.upload-input{
  margin-left: 24%;
  margin-top: -10%;
  float: left;
  width: 220px;
  height: 30px;
}
.upload-btn{
  float: right;
  margin-top: -8%;
  margin-right: 5%;
  border: 1px solid gray;
}

</style>
