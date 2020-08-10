<template>
  <v-toolbar>
    <!-- <v-toolbar-side-icon></v-toolbar-side-icon> -->
    <v-toolbar-title>코스모스</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn text @click="logout">
      <span>logout</span>
      <!-- <v-icon>fas fa-sign-out-alt</v-icon> -->
    </v-btn>
  </v-toolbar>
</template>

<script>
import "../components/css/logotitle.css";

export default {
  data() {
    return {
      userId: localStorage.getItem("userId"),
    };
  },
  methods: {
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      localStorage.removeItem("email");
      alert("로그아웃 되었습니다.");
      this.$router.push("/");
    },
    goToWrite() {
      this.$router.push("/feed/write");
    }
  }
}
</script>