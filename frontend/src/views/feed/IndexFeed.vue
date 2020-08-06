<template>
  <div>
    <logo-title/>
    <div class="container">
      <search-bar
          inputValue="text"
          placeholder="검색어를 입력해주세요."
          label="검색"
          @search="searchFeeds"
      />
      <FeedItem2 v-for="feed in feeds" v-bind:key="feed" v-bind:feed="feed"/>
      
      <button @click="initPage" v-if="isSearch && page==0">Load more</button>
      <infinite-loading v-else slot="append" @infinite="infiniteHandler" force-use-infinite-wrapper=".el-table__body-wrapper">
      </infinite-loading>
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
import InfiniteLoading from "vue-infinite-loading";
import SearchBar from "../../components/feed/SearchBar";

export default {
  props: ["keyword"],
  data: () => {
    return {
      page : 0,
      feeds : [],
      text : "",
      error: {
        email: "",
        text: ""
      },
      isSearch : false,
      search : ""
    };
  },
  components: { SearchBar, FeedItem2, Nav, LogoTitle, InfiniteLoading },
  created() {
  },
  methods : {
    initPage() {
      let data = {
          token : localStorage.getItem("token"),
          search : this.search,
          page : this.page
      };
      console.dir(data);
      FeedApi.searchFeeds(
          data,
          response => {
            console.dir(response);
            if (response.data.length) {
              this.page += 1;
              this.feeds = this.feeds.concat(response.data);
            }else {
              alert("검색 결과가 없습니다.");
            }
          },
          error => {
            alert(error);
          }
        )
    },
    infiniteHandler($state) {
      if(!this.isSearch) {
        let data = {
          token : localStorage.getItem("token"),
          page : this.page
        }
        FeedApi.loadFeeds(
          data,
          response => {
            if (response.data.content.length) {
              this.page += 1;
              this.feeds = this.feeds.concat(response.data.content);
              $state.loaded();
            } else {
              $state.complete();
            }
          },
          error => {
            alert(error);
          }
        )
      } else {
        let data = {
          token : localStorage.getItem("token"),
          search : this.search,
          page : this.page
        };
        FeedApi.searchFeeds(
          data,
          response => {
            if (response.data.length) {
              this.page += 1;
              this.feeds = this.feeds.concat(response.data);
              $state.loaded();
            } else {
              $state.complete();
            }
          },
          error => {
            alert(error);
          }
        )

      }
    },
    searchFeeds(search) {
      this.isSearch = true;
      this.search = search;
      this.feeds = [];
      this.page = 0;
      this.$refs.InfiniteLoading.stateChanger.reset();
    }
  }
};
</script>
<style scoped>

</style>
