<template>
  <div class="feed newsfeed">
      <logo-title />
      <!-- <h2>{{$route.params.feedNo}}</h2> -->
      <FeedItemDetail v-bind:feedNo="$route.params.feedNo"/>
    <Nav />
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItemDetail from "../../components/feed/FeedItemDetail.vue";
import Nav from "../../views/Nav.vue";
import LogoTitle from "../../views/LogoTitle.vue";

export default {
  // props: ["keyword"],
  components: { FeedItemDetail, Nav, LogoTitle },

  created() {

  }
};
</script>

