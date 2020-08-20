<template>
  <v-container fluid class="mb-15">
    <v-row>
      <v-col cols="12" sm="6" offset-sm="3">
        <v-card class="pa-5">
          <p style="color:#EF5B5B; font-size:20px; font-weight:bold" class="mb-3">피드 작성하기</p>
          <header
            data-v-4d6f63f3
            class="v-sheet theme--light v-toolbar v-toolbar--extended rounded-lg elevation-2"
            style="height: 65px;"
          >
            <div class="v-toolbar__content" style="height: 60px;">
              <v-slide-group show-arrows>
                <v-slide-item v-for="(course, index) in courses" v-bind:key="index" class="place">
                  <span>
                    <img
                      :src="course.thumbnailUrl"
                      class="thumbnail"
                      alt="img"
                      style="margin-top:4px; border-radius: 5px; width:55px; height:55px;"
                    />
                    <v-icon large color="orange" style="margin-top:5px;">mdi-chevron-right</v-icon>
                  </span>
                </v-slide-item>
              </v-slide-group>
            </div>
          </header>
          <div>
            <!-- input type=text -->

            <v-textarea
              label="Contents"
              rows="10"
              outlined
              v-model="contents"
              color="#EF5B5B"
              style="width:100%;"
              class="my-3"
            ></v-textarea>
          </div>

          <div>
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
          <div style="text-align: center;">
            <v-btn
              class="addButton my-7"
              color="#EF5B5B"
              style="color:white"
              @click="checkImage()"
            >피드 추가</v-btn>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
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
            .getDownloadURL()
            .then((url) => {
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
      this.contents = this.contents.split("\n").join("<br/>");
      let data = {
        token: this.token,
        userId: this.userId,
        contents: this.contents.split('\n').join('<br />'),
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
              };
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
.placeList {
  width: 300px;
  min-width: 300px;
  max-width: 500px;
  padding: 0;
  margin: 5px 0;
  margin-left: 20px;
  height: 60px;
  border: 1px solid rgba(0, 0, 0, 0.0975);
  border-radius: 3px;
  box-shadow: 1px 1px 1px 1px grey;
}
.place {
  display: inline-flex;
  align-items: center;
  margin: 4px;
  width: 85px;
  height: 70px;
}
.place > img {
  width: 100%;
  height: 100%;
  border: 1px solid rgba(0, 0, 0, 0.0975);
  border-radius: 5px;
  box-shadow: 1px 1px 1px 1px grey;
}
</style>
