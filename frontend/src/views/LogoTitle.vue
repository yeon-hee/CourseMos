<template>
  <v-toolbar color="orange lighten-2">
    <!-- <v-toolbar-side-icon></v-toolbar-side-icon> -->
    <v-toolbar-title style="color:white; font-weight: bold;">Cosmos</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-menu
      v-model="menu"
      :close-on-content-click="false"
      :nudge-width="200"
      offset-x
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          text
          bottom
          color="white"
          v-bind="attrs"
          v-on="on"
        >
          <v-icon>fas fa-bell</v-icon>
          <span>alarm</span>
        </v-btn>
      </template>
      <v-card>
        <alertMenu></alertMenu>
      </v-card>
    </v-menu>

    <v-btn text @click="logout" color="white">
      <v-icon>fas fa-sign-out-alt</v-icon>
      <span>logout</span>
      <!-- <v-icon>fas fa-sign-out-alt</v-icon> -->
    </v-btn>
  </v-toolbar>
</template>

<script>
import "../components/css/logotitle.css";
import alertMenu from "./user/Alerts.vue";

export default {
  data() {
    return {
      menu : false,
      userId: localStorage.getItem("userId"),
    };
  },
  components: { 
    alertMenu
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