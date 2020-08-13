<template>
  <v-toolbar color="#f89077" flat>
    <!-- <v-toolbar-side-icon></v-toolbar-side-icon> -->
    <v-toolbar-title style="color:white; font-weight: bold; font-size: 38px">Cosmos</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn text @click="gotoCup" color="white">
      <v-icon large>emoji_events</v-icon>
    </v-btn>

    <v-menu v-model="menu" :close-on-content-click="false" :nudge-width="200" offset-x>
      <template v-slot:activator="{ on, attrs }">
        <v-btn text bottom color="white" v-bind="attrs" v-on="on">
          <v-icon>fas fa-bell</v-icon>
          <!-- <span>alarm</span> -->
        </v-btn>
      </template>
      <v-card>
        <alertMenu></alertMenu>
      </v-card>
    </v-menu>

    <v-btn text @click="logout" color="white">
      <v-icon>fas fa-sign-out-alt</v-icon>
      <!-- <span>logout</span> -->
      <!-- <v-icon>fas fa-sign-out-alt</v-icon> -->
    </v-btn>
  </v-toolbar>
</template>

<script>
import "../components/css/logotitle.css";
import alertMenu from "../components/alert/AlertTab.vue";

export default {
  data() {
    return {
      menu: false,
      userId: localStorage.getItem("userId"),
    };
  },
  components: {
    alertMenu,
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
    },
    gotoCup() {
      this.$router.push("/worldcup");
    },
  },
};
</script>