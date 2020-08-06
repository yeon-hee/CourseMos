<template>
  <div>
    <div class="logo-title">
      <a href="#/feed/main">
        <img src="../assets/images/logo.jpg" width="30px" height="30px" alt="logo" class="logo" />
      </a>
      <span class="title u-fat-text">맛동산</span>
      <div class="logout-button">
        <!-- <button @click="goToWrite">addFeed</button> -->
        <button @click="logout">Logout</button>
      </div>
      <!-- <b-button v-b-toggle.sidebar-right><b-icon icon="text-right" aria-hidden="true"></b-icon></b-button>
      <b-sidebar id="sidebar-right" :title="userId" right shadow>
        <div class="px-3 py-2">
          <b-button size="sm" class="mb-2">
            <b-icon icon="gear-fill" aria-hidden="true"></b-icon> Settings
          </b-button>
          <b-button variant="outline-info" class="mb-2">
            <b-icon icon="power" aria-hidden="true"></b-icon> Logout
          </b-button>
        </div>
      </b-sidebar>-->
    </div>
    <hr />
  </div>
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
    },
  },
};
</script>

<style scoped>
.logo-title {
  max-width: 500px;
}

.logout-button {
  margin-left: auto;
}
</style>