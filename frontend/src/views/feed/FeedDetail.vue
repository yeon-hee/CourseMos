<template>
  <div class="feed newsfeed">
      <!-- <h2>{{$route.params.feedNo}}</h2> -->
      <FeedItemDetail v-bind:feedNo="$route.params.feedNo"/>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItemDetail from "../../components/feed/FeedItemDetail.vue";

export default {
  // props: ["keyword"],
  components: { FeedItemDetail },

  created() {

  }
};
</script>

