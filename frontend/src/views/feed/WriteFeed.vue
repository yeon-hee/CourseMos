<template>
  <div class="wrap components-page">
    <div class="wrapB">
      <h1>피드 작성하기</h1>

      <h2>내용</h2>
      <div class="wrap">
        <!-- input type=text -->

        <textarea cols="50" rows="5" v-model="contents" style="border-style: solid;"></textarea>
      </div>

      <h2>사진 추가하기</h2>

      <div class="wrap">
        <input
          @change="selectImage()"
          type="file"
          ref="feedImage"
          name="imgFile"
          class="upload-input"
          accept="image/*"
        />
        <ul class="profile-pictures">
          <li class="profile-pictures" v-for="thumbnail in imageThumbnails" :key="thumbnail">
            <img :src="thumbnail" class="profile-picture-picture" width="40px" height="40px" />
          </li>
        </ul>
      </div>
      <div class="wrap">
        <v-btn class="addButton" large color="primary" @click="checkImage()">피드 추가</v-btn>
        <!-- <input class="addButton" type="button" value="피드 추가" @click="checkImage()" /> -->
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/components.scss";
import "../../components/css/user/profile.css";
import FeedApi from "../../api/FeedApi";
import CourseApi from "../../api/CourseApi";
import * as firebase from "firebase/app";

export default {
  components: {},
  data: () => {
    return {
      images: [],
      imageThumbnails: [],
      uploadedImageUrl: [],
      token: localStorage.getItem("token"),
      userId: localStorage.getItem("userId"),
      contents: "",
      courses: [],
    };
  },
  created() {
    this.courses = this.$store.state.courses;
    console.log(this.courses);
  },
  methods: {
    selectImage() {
      const profileImage = this.$refs.feedImage.files[0];
      File;
      console.dir(profileImage);
      this.images.push(profileImage);

      var binaryData = [];
      binaryData.push(profileImage);

      const profileThumbnail = window.URL.createObjectURL(
        new Blob(binaryData, { type: "application/zip" })
      );
      this.imageThumbnails.push(profileThumbnail);
    },
    checkImage() {
      if (this.images.length > 0) this.uploadImages();
      else this.uploadFeed();
    },
    uploadImages() {
      for (var i = 0; i < this.images.length; i++) {
        var fullPath = new Date().getTime() + this.images[i].name;
        var storageRef = firebase.storage().ref("images/" + fullPath);

        storageRef.put(this.images[i]).then((snapshot) => {
          var imageUrl = firebase
            .storage()
            .ref(snapshot.metadata.fullPath)
            .getDownloadURL().then((url) => {
              this.uploadedImageUrl.push(url);

              if (this.uploadedImageUrl.length == this.images.length) {
                alert("이미지 업로드 완료");
                this.uploadFeed();
              }
            });
        });
      }
    },
    uploadFeed() {
      let data = {
        token: this.token,
        userId: this.userId,
        contents: this.contents,
        thumbnail: null,
      };

      if (this.images.length > 0) {
        data.thumbnail = this.uploadedImageUrl[0];
      }
      FeedApi.writeFeed(
        data,
        (response) => {
          console.dir(response);
          
          if (this.images.length > 0) {
            for (const url of this.uploadedImageUrl) {
              const photoData = {
                photoUrl: url,
                feedNo: response.data.feedNo,
              }
              FeedApi.uploadFeedImage(
                photoData,
                (response) => {
                  console.log(url);
                },
                (error) => {
                  alert(error);
                }
              );
            }
          }

          for (var idx = 0; idx < this.courses.length; idx++) {
            let courseData = {
              feedNo: response.data.feedNo,
              courseOrder: idx + 1,
              tradeName: this.courses[idx].tradeName,
              latitude: this.courses[idx].latitude,
              longitude: this.courses[idx].longitude,
              thumbnailUrl: this.courses[idx].thumbnailUrl,
              categoryName: this.courses[idx].categoryName,
              roadAddress: this.courses[idx].roadAddress,
              token: localStorage.getItem("token"),
            };

            console.dir(courseData);
            CourseApi.uploadFeedCourses(
              courseData,
              (response) => {
                console.log(response);
              },
              (error) => {
                alert(error);
              }
            );
          }
          this.$router.push("/feed/main");
        },
        (error) => {
          alert(error);
        }
      );
    },
  },
};
</script>

<style scoped>
.addButton {
  border: black;
  float: right;
}
</style>;
