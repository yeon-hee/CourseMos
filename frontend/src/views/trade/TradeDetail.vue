<template>
    <div style="height: 100%;">
         <img :src="temp.mainphotourl" style="height: 250px; width: 100%;"><br>
         <div style="text-align: center; margin-top: 8px;">
             <img src="../../assets/images/backwards_icon_149034.png" width="20px" height="20px" style="float: left; margin-left:7px;" @click="goBack()">
            <p style="display: inline; font-weight: bold; font-size: 20px;">{{temp.placenamefull}} </p>
            <p style="display: inline; color:#8a8a8a; font-size: 14px;">{{temp.catename}}</p>
         </div>

        <div style="margin: 25px; line-height: 2.0em;">
            <img src="../../assets/images/detail1.png" width="15px" height="15px" class="detail1">
             <p style="display: inline; margin-left: 2px;">
                 {{temp.address.region.newaddrfullname}} {{temp.address.newaddr.newaddrfull}}</p> <br>
            <img src="../../assets/images/detail2.png" style="padding-top: 2px;" width="15px" height="16px" class="detail1">
            <p style="display: inline; margin-left: 6px;">영업시간 : 
            {{temp.openHour.periodList[0].timeList[0].dayOfWeek}} 
            {{temp.openHour.periodList[0].timeList[0].timeSE}} </p><br>
            <div v-if="temp.phonenum != null">
                <img src="../../assets/images/detail4.png" style="margin-left: 1.7px;" width="15px" height="15px" class="detail1">
            &nbsp;<p style="display: inline; margin-left: 2px;">{{temp.phonenum}}</p>
            </div>
        </div>
        <div class="line"></div>

        <div style="margin: 25px; line-height: 1.2em;">
            <h4 style="display: inline; float: left;">메뉴</h4> 
            <div style="display: inline; float: right; color:#8a8a8a;">
                <p style="display: inline; font-size: 12px;">업데이트 </p>
                &nbsp;<p style="display: inline; font-size: 10px;">{{menu.timeexp}}</p>
            </div>
            <br><br>
            <div v-for="menus in menu.bottomList" :key="menus.id " style="line-height: 0.5em;">
                <div style="float: left; color:rgb(51,102,255);">{{menus.menu}}</div>
                <div class="hr-sect">
                <div style="float: right; color:rgb(255,81,81);">{{menus.price}}</div></div>
                <br style="clear:both;">
            </div><br><br><br>

            <h4 style="display: inline;">블로그 </h4>
            <p style="display: inline; color:rgb(40,180,0);">{{blog.blogrvwcnt}}</p><br><br>
            <div v-for="blogs in blog.list" :key="blogs.id " style="line-height: 0.5em;">
                <div></div>
            </div>

        <v-card max-width="400" class="mx-auto">
        <v-col v-for="(blogs, i) in blog.list" :key="i" cols="12">
          <v-card>
              <a :href="blogs.outlink" style="text-decoration:none;">
                  <div style="padding:16px 12px 0px 12px; font-size: 15px; color:rgb(20,10,160); font-weight: bold;">
                      {{blogs.title}}
                  </div>
              </a>
              <div class="d-flex flex-no-wrap justify-space-between"> 
                <v-card-subtitle v-text="blogs.contents" class="text" style="padding-left: 12px; color: black;"></v-card-subtitle>
                    <v-avatar class="ma-3" size="78" tile>
                    <v-img :src="blogs.photoList[0].orgurl"></v-img>
                    </v-avatar>
              </div>
              <div style="padding:0px 12px 16px 12px; font-size: 13px; color: gray;">
                  {{blogs.blogname}} | 
                  <p style="display: inline;">{{blogs.date}}</p>
              </div>
          </v-card>
        </v-col>
        <div style="text-align: center; padding-bottom: 11px;">
            <a :href="url" style="text-decoration:none; color: black; font-size: 14px;">블로그 더보기 </a>
            <img src="../../assets/images/detail5.png" style="padding-top: 4px;" width="14px" height="16px">
        </div>
        </v-card><br>
        </div>

        <div class="line"></div>
        <div style="margin: 25px; line-height: 1.2em;">
            <h4 style="display: inline;">방문자 리뷰 </h4><p style="display: inline; color:rgb(40,180,0);">{{review.allComntcnt}}</p><br><br>
            <div v-for="(reviews, i) in review.list.slice(0, 5)" :key="i">
                <div>
                    <p style="float: left; margin-right:13px;"><img :src="reviews.profile" style="height: 45px; width: 45px;"></p>
                    <div style="font-size: 13px; " class="text2">{{reviews.contents}}</div>
                    <p style="display: inline; font-size: 11px; color:#8a8a8a;">{{reviews.point}}점 | {{reviews.username}} | {{reviews.date}}</p><br>
                    <div style="clear: both;"></div>
                    <div class="line"></div><br>
                </div>
            </div>
            <div style="text-align: center;">
                <a :href="url" style="text-decoration:none; color: black; font-size: 14px;">리뷰 더보기 </a>
                <img src="../../assets/images/detail5.png" style="padding-top: 4px;" width="14px" height="16px">
            </div>
        </div>

    </div>
</template>

<script>

import FeedApi from "../../api/FeedApi";

export default {
  data : () => {
      return {
        temp: {},
        menu: {},
        blog: {},
        url: {},
        review: {}
      }
  },
   created() {
        this.url = 'https://place.map.kakao.com/' + '1165755646'; // 상점 id

        let data = {
            token : localStorage.getItem('token'),
            number: this.$route.params.id, // 상점 id
        };

        FeedApi.detailCrawling(
            data,
            res => {
                console.log("상세 정보 크롤링 완료!");
                console.log(res.data);
                this.temp = res.data;
            },
            error => {
                alert(error);
            }
        );

        FeedApi.detailMenu(
            data,
            res => {
                console.log("메뉴 크롤링 완료!");
                console.log(res.data);
                this.menu = res.data;
            },
            error => {
                alert(error);
            }
        );

        FeedApi.detailBlog(
            data,
            res => {
                console.log("블로그 크롤링 완료!");
                console.log(res.data);
                this.blog = res.data;
            },
            error => {
                alert(error);
            }
        );

        FeedApi.detailComment(
            data,
            res => {
                console.log("리뷰 크롤링 완료!");
                console.log(res.data);
                this.review = res.data;
            },
            error => {
                alert(error);
            }
        );
    },

    methods: {
        goBack(){
            var feedNo = this.$route.params.feedNo;
            if(feedNo != null) this.$router.push("/feeds/back/" + feedNo);
            else this.$router.push("/feedmake");
        },
    }
  
};
</script>

<style scoped>

h2 {
    color : black;
}

.detail1{
    -webkit-filter: opacity(.5) drop-shadow(0 0 0 gray);
    filter: opacity(.2) drop-shadow(0 0 0 gray);
}
.line{
  clear: both;
  height: 0.5px;
  width: 100%;
  background-color: gray;
  opacity: 30%;
}
.hr-sect {
	display: flex;
	flex-basis: 100%;
	align-items: center;
	color: rgba(0, 0, 0, 0.35);
	font-size: 16px;
	margin: 8px 0px;
}
.hr-sect::before {
	content: "";
	flex-grow: 1;
    border-style: dashed;
    border-width: thin;
    opacity: 30%;
	height: 0.1px;
	margin: 0px 16px;
}
.text{
  height: 80px;
  white-space: normal; 
  text-align: left; 
  word-wrap: break-word; 
  display: -webkit-box; 
  -webkit-line-clamp: 3; 
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.text2{
  display: -webkit-box;
  -webkit-line-clamp: 2; 
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
