<template>
  <v-container fluid class="mt-0 mb-5">
    <v-row class="py-0">
      <v-col class="py-0" cols="12" sm="6" offset-sm="3">
        <v-row>
          <v-col cols="3" style="text-align:center; align-self:center;">
            <v-avatar class="profile-avatar" size="96">
              <v-img :src="profile_photo" aspect-ratio="1"></v-img>
            </v-avatar>

            <div class="profile-name">
              <h5 class="profile-title ml-2">{{user.userId}}</h5>
            </div>
          </v-col>
          <v-col cols="9">
            <v-row class="px-3">
              <v-col cols="4" class="text-center">
                <span class="profile-number u-fat-text">게시물</span>
                <div class="articles-number">{{feedCount}}</div>
              </v-col>
              <v-col cols="4" class="text-center">
                <a @click="goFollower">
                  <span class="profile-number u-fat-text">팔로워</span>
                  <div class="followers-number">{{count.followerCount}}</div>
                </a>
              </v-col>
              <v-col cols="4" class="text-center">
                <a @click="goFollowing">
                  <span class="profile-number u-fat-text">팔로잉</span>
                  <div class="followings-number">{{count.followingCount}}</div>
                </a>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="10" class="mx-auto" style="text-align:center">
                <v-btn
                  class="deep-orange white--text"
                  @click="requestButton(follow_status);"
                >{{message}}</v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <v-row class="pa-0">
      <v-col class="profile-bio-box py-0" cols="12" sm="6" offset-sm="3">
        <div class="profile-bio">
          <p class="profile-full-bio pa-1">{{user.profileComment}}</p>
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
  <!-- <div>
        <main class="profile-container">
            <section class="profile">
                <header class="profile-header">
                    <div class="profile-avatar-container">
                        <img 
                            :src="profile_photo"
                            class="profile-avatar"
                        />
                        <div class="profile-name">
                            <h5 class="profile-title">{{user.userId}}</h5>
                        </div>
                    </div>
                    <div class="profile-info">
                        <ul class="profile-numbers">
                        
                            <li class="profile-posts">
                                    <span class="profile-number u-fat-text">게시물</span>
                                    <div class="articles-number">{{count.feedCount}}</div>
                            </li>
                        
                            <a href="/#/friends/followers">
                                <li class="profile-followers">
                                    <span class="profile-number u-fat-text">팔로워</span>
                                    <div class="followers-number">{{count.followerCount}}</div>
                                </li>
                            </a>
                            <a href="#/friends/followings">
                                <li class="profile-following">
                                    <span class="profile-number u-fat-text">팔로잉</span>
                                    <div class="followings-number">{{count.followingCount}}</div>
                                </li>
                            </a>
                        </ul>
                        <a href='javascript:void(0);' @click="requestButton(follow_status);" class="profile-button u-fat-text">{{message}}</a>
                    </div>
                </header>
                <div class="profile-bio">
                    <p class="profile-full-bio">{{user.profileComment}}</p>
                </div>
                <hr>
                <ul class="profile-pictures">
                    <li class="profile-picture" v-for="feed in feeds" :key="feed.id">
                        <a @click="onImgClick(feed.feedNo)">
                            <img
                                :src="feed.thumbnail"
                                class="profile-picture-picture"
                            />
                            <div class="profile-picture-overlay">
                                <span class="profile-picture-number">
                                    <i class="fa fa-heart"></i> {{feed.like_count}}
                                </span>
                                <span class="profile-picture-number">
                                    <i class="fa fa-comment"></i> {{feed.comment_count}}
                                </span>
                            </div>
                        </a>
                    </li>
                </ul>
            </section>
        </main>
  </div>-->
</template>

<script>
import "../../components/css/user/profile.css";
import ProfileApi from "../../api/ProfileApi";
import FeedApi from "../../api/FeedApi";
import FollowApi from "../../api/FollowApi";
import RequestApi from "../../api/RequestApi";

export default {
  components: {},
  created() {
    let data = {
      email: localStorage.getItem("email"),
      token: localStorage.getItem("token"),
      userId: this.$route.params.userId,
    };

    ProfileApi.requestUserProfile(
      data,
      (res) => {
        console.dir(res.data);
        this.user = res.data;
        data.email = this.user.email;
        if (
          res.data.profilePhoto != undefined &&
          res.data.profilePhoto.length > 10
        )
          this.profile_photo = res.data.profilePhoto;

        ProfileApi.requestUserCount(
          data,
          (res) => {
            this.count = res.data;
          },
          (error) => {
            alert(error);
          }
        );
        FeedApi.loadUserFeeds(
          data,
          (res) => {
            this.feeds = res.data;
            this.feedCount = this.feeds.length;
          },
          (error) => {
            alert(error);
          }
        );
        FollowApi.getFollower(
          data,
          (res) => {
            const follows = res.data;
            for (const follow of follows) {
              if (follow == this.$store.state.userId) {
                this.follow_status = 2;
                this.message = "팔로잉 취소";
                break;
              }
            }
          },
          (error) => {
            alert(error);
          }
        );
        RequestApi.requestList(
          data,
          (res) => {
            const requests = res.data;
            for (const request of requests) {
              if (request.follower == localStorage.getItem("userId")) {
                this.follow_status = 1;
                this.message = "팔로우 신청 중";
                break;
              }
            }
          },
          (error) => {
            alert(error);
          }
        );
      },
      (error) => {
        alert(error);
      }
    );
  },
  data() {
    return {
      profile_photo: require("@/assets/images/profile_default.png"),
      feed_default_photo: require("@/assets/images/NotFound.png"),
      user: {},
      //0은 팔로우x
      //1은 팔로우 신청 상태
      //2는 팔로우o
      follow_status: 0,
      message: "팔로우 요청",
      count: {},
      feeds: [],
      feedCount: 0,
    };
  },
  methods: {
    requestButton(v) {
      if (v == 0) {
        RequestApi.followRequest(
          {
            follower: localStorage.getItem("userId"),
            email: this.user.email,
          },
          (res) => {
            this.follow_status = 1;
            this.message = "팔로우 신청 중";
            alert("팔로우 요청됐습니다!");
          },
          (error) => {
            alert(error);
          }
        );
      } else if (v == 2) {
        //
      }
    },
    onImgClick(no) {
      this.$router.push("/feeds/" + no);
    },
    goFollower() {
      this.$router.push("/friends/followers/"+this.user.email+'/'+this.user.userId)
    },
    goFollowing() {
      this.$router.push("/friends/followings/"+this.user.email+'/'+this.user.userId)
    }
  },
};
</script>

<style scoped>
.text-center a {
  text-decoration: none;
  color: #000000;
}

</style>