<template>
  <v-container fluid class="mt-0 mb-5">
    <v-row class="py-0">
      <v-col class="py-0" cols="12" sm="6" offset-sm="3">
        <v-row>
          <v-col cols="4" style="text-align:center; align-content:center;">
            <v-avatar class="profile-avatar" size="96">
              <v-img :src="profile_photo" aspect-ratio="1"></v-img>
            </v-avatar>

            <div class="profile-name">
              <h5 class="profile-title ml-2">{{user_id}}</h5>
            </div>
          </v-col>
          <v-col cols="8">
            <v-row class="px-3">
              <v-col cols="4" class="text-center">
                <span class="profile-number u-fat-text">게시물</span>
                <div class="number">{{feed_count}}</div>
              </v-col>
              <v-col cols="4" class="text-center">
                <a @click="goFollower">
                  <span class="profile-number u-fat-text">팔로워</span>
                  <div class="number bold">{{follower_count}}</div>
                </a>
              </v-col>
              <v-col cols="4" class="text-center">
                <a @click="goFollowing">
                  <span class="profile-number u-fat-text">팔로잉</span>
                  <div class="number bold">{{following_count}}</div>
                </a>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="10" class="btns d-flex mx-auto" style="text-align:center">
                <v-btn
                  class="white--text px-5"
                  @click="goSetting"
                  style="background-color:rgb(239,91,91);"
                >프로필 수정</v-btn>
                <v-btn
                  class="white--text px-5 ml-3"
                  @click="logout"
                  style="background-color:rgb(239,91,91);"
                >
                  <div>로그아웃</div>
                </v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <v-row class="pa-0">
      <v-col class="profile-bio-box py-0" cols="12" sm="6" offset-sm="3">
        <div class="profile-bio">
          <p class="profile-full-bio pa-1">{{profile_comment}}</p>
        </div>
      </v-col>
    </v-row>

    <v-row class="feeds pa-0">
      <v-col cols="12" md="6" offset-md="3">
        <v-card>
          <v-container fluid>
            <v-row>
              <v-col v-for="feed in feeds" :key="feed.id" class="d-flex child-flex" cols="4" md="3">
                <template>
                  <!-- <v-hover>
                    <template v-slot:default="{ hover }"> -->
                      <v-card flat tile class="d-flex">
                        <v-img
                          v-if="feed.thumbnail != undefined &&
                            feed.thumbnail.length > 10"
                          @click="onImgClick(feed.feedNo)"
                          :src="feed.thumbnail"
                          aspect-ratio="1"
                          class="grey lighten-2"
                        >
                          <!-- <template v-slot:placeholder>
                            <v-row class="fill-height ma-0" align="center" justify="center">
                              <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                            </v-row>
                          </template> -->
                        </v-img>
                        <v-img 
                          v-else 
                          src="../../assets/images/image-not-found.jpg" 
                          aspect-ratio="1"
                          class="grey lighten-2"
                          @click="onImgClick(feed.feedNo)"
                        />
                        
                        <!-- <v-fade-transition>
                          <v-overlay
                            @click="onImgClick(feed.feedNo)"
                            v-if="hover"
                            absolute
                            color="#036358"
                          >
                            <v-row justify="space-around" class="mb-2">
                              <span class="group pa-2">
                                <v-icon>fas fa-heart</v-icon>
                                {{feed.like_count}}
                                <v-icon>fas fa-comment-dots</v-icon>
                                {{feed.comment_count}}
                              </span>
                            </v-row>
                          </v-overlay>
                        </v-fade-transition> -->
                      </v-card>
                    <!-- </template>
                  </v-hover> -->
                </template>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import ProfileApi from "../../api/ProfileApi";
import FeedApi from "../../api/FeedApi";

export default {
  components: {
    // LogoTitle,
    // Nav
  },
  created() {
    let data = {
      email: localStorage.getItem("email"),
      token: localStorage.getItem("token"),
      userId: localStorage.getItem("userId"),
    };
    ProfileApi.requestCount(
      data,
      (res) => {
        this.follower_count = res.data.followerCount;
        this.following_count = res.data.followingCount;
        // this.feed_count = res.data.feedCount;
        console.dir(res);
      },
      (error) => {
        alert(error);
      }
    );
    ProfileApi.requestProfile(
      data,
      (res) => {
        this.user_id = res.data.userId;
        this.profile_comment = res.data.profileComment;
        if (
          res.data.profilePhoto != undefined &&
          res.data.profilePhoto.length > 10
        )
          this.profile_photo = res.data.profilePhoto;
      },
      (error) => {
        alert(error);
      }
    );
    FeedApi.loadUserFeeds(
      data,
      (res) => {
        console.dir(res.data);
        this.feeds = res.data;
        this.feed_count = this.feeds.length;
      },
      (error) => {
        alert(error);
      }
    );
  },
  data() {
    return {
      profile_photo: require("@/assets/images/profile_default.png"),
      user_id: "",
      profile_comment: "",
      feed_count: 0,
      follower_count: 0,
      following_count: 0,
      feeds: [],
    };
  },
  methods: {
    onImgClick(no) {
      this.$router.push("/feeds/" + no);
      console.dir();
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      localStorage.removeItem("email");
      alert("로그아웃 되었습니다.");
      this.$router.push("/");
    },
    goFollower() {
      this.$router.push("/friends/followers")
    },
    goFollowing() {
      this.$router.push("/friends/followings")
    },
    goSetting() {
      this.$router.push("/users/profile/setting")
    }
  },
};
</script>

<style scoped>
.text-center a {
  text-decoration: none;
  color: #000000;
}
.profile-avatar {
  border: 1px solid #f89077;
}
.btns {
  justify-content: space-around;
}
.btn {
  width: 50%;
}
.profile-bio {
  background-color: #EF9A9A;
  border-radius: 5px;
}
.profile-number {
  opacity: 0.7;
  font-size: 15px;
}
.number {
  font-size: 20px;
  font-weight: bold;
}
</style>