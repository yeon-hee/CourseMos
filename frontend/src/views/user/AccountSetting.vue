
<template>
  <v-container fluid class="mt-2 mb-16">
    <v-row>
      <v-col cols="12" sm="6" offset-sm="3">
        <v-card class="pa-5">
          <!-- title  -->
          <v-row>
            <v-col cols="8">
              <h1>내 계정 설정</h1>
              <p>My Settings</p>
            </v-col>
          </v-row>

          <!-- profile image  -->
          <v-card color='deep-orange lighten-5'>
            <v-row>
              <v-col cols="5" style="text-align:center">
                <v-avatar size="96">
                  <img :src="profileThumbnail" />
                </v-avatar>
              </v-col>
              <v-col cols="6" style="text-align:center;">
                <v-file-input
                  @change="selectImage"
                  accept="image/*"
                  label="image select"
                  ref="profileImageFile"
                  name="imgFile"
                  prepend-icon="mdi-camera"
                ></v-file-input>
                <v-btn
                  dark color="#EF5B5B"
                  @click="updateProfileImage()"
                  value="등록"
                >등록</v-btn>
              </v-col>
            </v-row>
          </v-card>
          <br>
          <!-- user info  -->
          <template style="margin-top:50px;">
                <v-text-field
                  color="red"
                  label="아이디"
                  v-model="userId"
                  v-bind:class="{error : error.userId, complete:!error.userId&&userId.length!==0}"
                  v-bind:placeholder="placeUserId"
                  id="userId"
                ></v-text-field>
                <v-text-field
                  color="red"
                  label="이메일"
                  readonly
                  hint="이메일은 변경 불가합니다."
                  v-model="email"
                  v-bind:placeholder="placeEmail"
                  id="email"
                ></v-text-field>

            <v-row no-gutters style="margin-top:20px;">
              <v-col cols="12">
                <v-textarea
                  color="red"
                  name="input-7-1"
                  filled
                  label="한줄 자기소개"
                  auto-grow
                  cols="32"
                  rows="7"
                  v-bind:class="{error : error.profileComment, complete:!error.profileComment&&profileComment.length!==0}"
                  v-model="profileComment"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col cols="12" style="text-align:center;">
                <v-btn
                  color="#EF5B5B"
                  @click="onChange"
                  :disabled="!isSubmit"
                  :class="{disabled : !isSubmit}"
                  class="white--text"
                  block
                  depressed
                >저장</v-btn>
              </v-col>
            </v-row>
            <v-btn depressed dark block color="#EF5B5B" @click="goChangePassword">
              비밀번호 변경
            </v-btn>
          </template>

        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import UserApi from "../../api/UserApi";
import axios from "axios";
import * as firebase from "firebase/app";

export default {
  created() {
    this.component = this;
    console.log("created");

    let data = {
      token: localStorage.getItem("token"),
    };
    UserApi.requestUserInfo(
      data,
      (response) => {
        console.dir(response);
        this.placeEmail = response.data.email;
        // this.placeUserId = response.data.userId;
        this.userId = response.data.userId;
        if(response.data.profileComment != undefined)
          this.profileComment = response.data.profileComment;
        this.email = response.data.email;
        if(response.data.profilePhoto != undefined && response.data.profilePhoto.length > 10)
          this.profileThumbnail = response.data.profilePhoto;
      },
      (error) => {
        alert(error);
        this.isSubmit = true;
      }
    );
  },
  watch: {
    userId: function (v) {
      this.checkUserId();
      this.finalCheck();
    },
    profileComment: function (v) {
      this.checkProfileComment();
      this.finalCheck();
    },
  },
  methods: {
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      localStorage.removeItem("email");
      alert("로그아웃 되었습니다.");
      this.$router.push("/");
    },
    selectImage(e) {
      this.profileImage = e;
      this.profileThumbnail = window.URL.createObjectURL(this.profileImage);
    },
    updateProfileImage() {
      var fullPath = new Date().getTime() + this.profileImage.name;
      var storageRef = firebase.storage().ref("images/" + fullPath);

      storageRef.put(this.profileImage).then((snapshot) => {
        var imageUrl = firebase
          .storage()
          .ref("images/" + fullPath)
          .getDownloadURL();
        imageUrl.then((url) => {
          console.log(url);
          this.profilePhoto = url;
          let { userId, email, profileComment, profilePhoto } = this;
          let data = {
            token: localStorage.getItem("token"),
            userId,
            email,
            profileComment,
            profilePhoto,
          };

          UserApi.changeProfileImage(
            data,
            (response) => {
              alert("변경사항이 저장되었습니다.");
              this.$router.push("/users/profile");
            },
            (error) => {
              alert(error);
            }
          );
        });
      });
    },
    finalCheck() {
      if (this.userId.length == 0 && this.profileComment.length == 0) {
        this.isSubmit = false;
      }
    },
    checkUserId() {
      this.error.userId = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
      this.isSubmit = true;
    },
    checkProfileComment() {
      this.error.profileComment = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
      this.isSubmit = true;
    },
    onChange() {
      if (this.isSubmit) {
        let { userId, email, profileComment } = this;
        let data = {
          token: localStorage.getItem("token"),
          userId,
          email,
          profileComment,
        };
        console.log("프로필 정보 수정 들어옴.");

        UserApi.requestSettings(
          data,
          (response) => {
            localStorage.setItem("userId", this.userId);
            console.log(response);
            console.log("프로필 정보 변경 성공!");
            alert("변경사항이 저장되었습니다.");
            this.isSubmit = true;
            this.$router.push("/users/profile");
          },
          (error) => {
            alert(error);
            this.isSubmit = true;
          }
        );
        this.isSubmit = false;
      }
    },
    goChangePassword() {
      this.$router.push('/users/profile/password');
    }
  },
  data: () => {
    return {
      userId: "",
      email: "",
      profilePhoto: "",
      profileComment: "",
      placeUserId: "",
      placeEmail: "",
      // placeProfileComment: "",
      isLoading: false,
      error: {
        userId: false,
      },
      isSubmit: false,
      termPopup: false,
      profileImage: {},
      profileThumbnail: require("@/assets/images/profile_default.png"),
    };
  },
};
</script>
<style scoped>
</style>
