<template>
  <v-bottom-navigation
    height="48px"
    fixed
    :value="activeBtn"
    color="deep-orange darken-1"
    style="z-index:100;"
    background-color="rgb(239,91,91)"
  >
    <div class="nav">
      <v-btn style="width:100%;height:100%;" icon value="main" @click="goMain">
        <v-icon size="25" color="white">fas fa-home</v-icon>
      </v-btn>
      <v-btn style="width:100%;height:100%;" icon value="search" @click="goSearch">
        <v-icon size="20" color="white">fas fa-search</v-icon>
      </v-btn>
      <v-btn style="width:100%;height:100%;" icon value="feedmake" @click="goFeedMake">
        <v-icon size="28" color="white">post_add</v-icon>
      </v-btn>
      <v-btn style="width:100%;height:100%;" icon value="profile" @click="goProfile">
        <v-avatar class="profile-avatar" size="25">
          <v-img :src="profile_photo" aspect-ratio="1"></v-img>
        </v-avatar>
      </v-btn>
    </div>
  </v-bottom-navigation>
</template>

<script>
import ProfileApi from "../api/ProfileApi";

export default {
  created() {
    let data = {
      email: localStorage.getItem("email"),
      token: localStorage.getItem("token"),
      userId: localStorage.getItem("userId"),
    };
    ProfileApi.requestProfile(
      data,
      (res) => {
        if (
          res.data.profilePhoto != undefined &&
          res.data.profilePhoto.length > 10
        )
          this.profile_photo = res.data.profilePhoto;
      },
      (error) => {}
    );
  },
  data() {
    return {
      profile_photo: require("@/assets/images/profile_default.png"),
      activeBtn: 1,
    };
  },
  methods: {
    goMain(event) {
      this.$router.push("/feed/main");
    },
    goSearch() {
      this.$router.push("/search");
    },
    goFeedMake() {
      this.$router.push("/feedmake");
    },
    goProfile() {
      this.$router.push("/users/profile");
    },
  },
};
</script>

<style scoped>
.nav {
  min-width: 400px;
  max-width: 500px;
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
</style>