<template>
    <div>
      크롤링 페이지<br>
      <img :src="photo">
    </div>
    
</template>


<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import axios from "axios";

export default {
  data: () => {
    return { 
      photo : {}
    };
  }, 
  created() {
    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.$route.params.feedNo,
        tradeName: '우마이도 건대점' // 상점 이름
    };

    FeedApi.crawling(
          data,
          res => {
            this.photo = res.data;
          },
          error => {
            alert(error);
        }
    );

  },
  methods: {
    
  },
};

</script>


