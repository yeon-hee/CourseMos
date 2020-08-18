<template>
  <v-toolbar color="white" flat>
    <!-- <v-toolbar-side-icon></v-toolbar-side-icon> -->
    <v-toolbar-title style="color:rgb(239,91,91); font-weight: bold; font-size: 38px">코스모스</v-toolbar-title>
    <v-spacer></v-spacer>
      <v-icon size="30" @click="gotoCup" color="rgb(239,91,91)" style="margin-right:20px;">emoji_events</v-icon>

    <v-menu v-model="menu" :close-on-content-click="false" :nudge-width="200" offset-x>
      <template v-slot:activator="{ on, attrs }" >
        <v-btn text bottom color="rgb(239,91,91)" v-bind="attrs" v-on="on" style="padding: 0px; min-width:5px;">
          <v-icon size="25">fas fa-bell</v-icon>
        </v-btn>

      </template>
      <v-card>
        <alertMenu></alertMenu>
      </v-card>
    </v-menu>
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