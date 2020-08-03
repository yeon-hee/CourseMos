<template>
  <div class="feed newsfeed">
    <logo-title/>
    <div class="wrapB">
      <!-- <br><h1 style="text-align : center">뉴스피드</h1> -->

      <FeedItem2 v-for="feed in feeds" v-bind:key="feed" v-bind:feed="feed"/>

      <!-- <FeedItem />
      <FeedItem />
      <FeedItem /> -->
      <Nav/>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem2 from "../../components/feed/FeedItem2.vue";
import FeedApi from "../../api/FeedApi";
import Nav from "../../views/Nav";
import LogoTitle from "../LogoTitle.vue";

export default {
  props: ["keyword"],
  data: () => {
    return {
      feeds : []
    };
  },
  components: { FeedItem2, Nav, LogoTitle },
  created() {
    let token = localStorage.getItem('token');
    let data = { token }
    FeedApi.loadFeeds(
      data,
      response => {
        this.feeds = response.data
        console.dir(this.feeds)
      },
      error => {
        alert('피드 목록 조회에 실패했습니다.');
      }
    );
  },
  methods : {
  }
};
</script>
<style scoped>

.wrapB{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
}

</style>
