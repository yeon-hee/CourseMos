<template>
 <div class="feed-item">
    <div class="feed-top">
      <!-- <br>
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>{{feed.userId}}</button>
        </div>
      </div> -->
    </div>
    
    <div class="feed-car">
      <div class="map_wrap">

        <v-sheet 
    class="mx-auto" 
    elevation="11"
    height= "400px"
  >

  <v-expand-transition>
      <v-sheet
        v-if="model != null"
        color="grey lighten-4"
        height="332"
      >
          <div id="map" style="width:100%; height:331px;"></div>
     

      </v-sheet>
    </v-expand-transition>
    
    <v-slide-group
      v-model="model"
      class="pa-1"
      show-arrows
    >

    <v-slide-item v-slot:default="{ toggle }">
        <v-card
          class="ma-1"
          height="50"
          width="76"
          @click="toggle"
        >
        <div id="map" style="width:76px; height:50px;"></div>
        </v-card>
      </v-slide-item>

      <v-slide-item
        v-for="photo in photoList"
        :key="photo.id"
        v-slot:default="{ toggle }"
      >
        <v-card
          class="ma-1"
          height="50"
          width="76"
          @click="toggle"
        >
        <img :src="photo.photoUrl" height="50" width="76">
        </v-card>
      </v-slide-item>

      <v-slide-item v-slot:default="{ toggle }">
        <v-card
          class="ma-1"
          height="50"
          width="76"
          @click="toggle"
        >
        <div id="map" style="width:76px; height:50px;"></div>
        </v-card>
      </v-slide-item>
      
    </v-slide-group>
  </v-sheet>


        <!-- <div id="map" style="width:100%;height:400px;"></div> -->
      </div>

        
     
    </div>
    <div class="feed-btn" style="margin-left: 10px;">
      <div>
        <img :src="feed.mine ? redHeart: emptyHeart" width="20px" height="20px" class="like-btn" @click="clickLikeBtn(feed)">
        <span>{{feed.likeCount}}</span>
        <img src="../../assets/images/comment.png" width="20px" height="20px" class="comments-btn">
        <span>{{feed.commentCount}}</span>
        <img src="../../assets/images/share.png" width="20px" height="20px" class="share-btn">
        <!-- <img src="../../assets/images/star.png" width="20px" height="20px" class="scrap-btn"> -->
      </div>
      

      
    </div>
    <div style="height:10px;"></div>
    <div class="line"></div>
      <!-- <div style="height:15px;"></div>
      <p>{{feed.contents}}</p>
       <div class="feed-hashtag">
         <p class="hashtag">#맛집</p>
         <p class="hashtag">#파스타</p>
         <p class="hashtag">#와인</p>
      </div>
          <h5 class="feed-time">{{feed.writeDate}}</h5>
    </div>
    <div class="comment-area"> -->
    <div style="clear: both;"></div>
    <div style="height:15px;"></div><br>

     

      

 <v-timeline :dense="$vuetify.breakpoint.smAndDown">
   
    <div v-for="course in courseList" :key="course.id">
       <v-timeline-item
            color="teal lighten-3"
            small
          >
      <div data-v-19a3425a="" class="v-timeline-item theme--light" style="width: 370px; height: 45px;">
        <div style="margin-right: 102px;">
          <div  class="elevation-2 v-card v-sheet theme--light" style="border-radius:10px; width:300px; height:57px; border:1px solid #ccc;">
              <div style="float:left;">
              <img :src="course.thumbnailUrl" style="height:45px; width:45px; border-radius: 8px; margin: 5px 0px 5px 8px;">
            </div>
            <div style="float:left; margin: 10px 0px 9px 10px; line-height: 1.2em;">
                <div style="font-size:10px; color:rgb(51,102,255);">{{course.categoryName}}</div>
                <div style="font-size:13px;">{{course.tradeName}} </div>
            </div>
            <div style="float: right; margin: 12px;"> 
              <a href="javascript:;" @click="clickRoute()" style="margin-bottom: 15px;">
                <img src="../../assets/images/find_route_icon.png" width="30px" height="30px">
              </a>
            </div>
            <div style="clear: both;"></div>
          </div>



          <!-- <div data-v-19a3425a="" class="elevation-2 v-card v-sheet theme--light" style="height: 60px;">
            <div style="float:left;">
              <img :src="course.thumbnailUrl" style="height:50px; width:50px; border-radius: 8px; margin: 5px 0px 5px 8px;">
            </div>
            <div style="float:left; margin: 10px 0px 9px 10px; line-height: 1.4em;">
                <div style="font-size:12px; color:rgb(51,102,255);">{{course.categoryName}}</div>
                <div style="font-size:14px;">{{course.tradeName}} </div>
            </div>
            <div data-v-19a3425a="" class="v-card__title headline" style="height: 70px; float : right;">
              <a href="javascript:;" @click="clickRoute()">
                <img src="../../assets/images/find_route_icon.png" width="35px" height="35px">
              </a>
            </div>
            <div style="clear: both;"></div>
            </div> -->
            </div>
           
          <div class="v-timeline-item__opposite">
          </div>
      </div>
        </v-timeline-item>
    </div>
    </v-timeline><br>
    <!-- 코스 들어가는 곳-->
<!-- <div class="img" :style="{'background-image': 'url('+defaultImage+')'}" @click="onImgClick"></div> -->
      <!-- <div class="box" style="border:1px solid rgb(183,183,183); height:40px; width:40px; border-radius: 10px;"></div> -->
      <a href="javascript:;"  @click="clickComment()" style="float: right; margin-right: 20px; color: rgb(51,102,255); ">댓글 보기...</a><br><br>
      
  
  <!-- <p style="padding-bottom: 10px;">댓글
      <input class="comment-input"
             v-model="content"
             id="content"/>
      
      <button class="regist-comment"
              @click="registComment(feed)">등록</button> -->
      <!-- <div>
        <div style="margin-top:3%;" v-for="(comment, index) in comments" v-bind:key="comment.commentNo">
          <img src="../../assets/images/user.png" width="30px" height="30px" class="comment-profile"/>
          <p class="comment-writer">{{comment.writer}}</p>
          <p class="comment-content">{{comment.content}}</p>
          <button v-if="userId==comment.writer" @click="deleteComment(feed, comment, index)" class="delete-comment">삭제</button>
          <p style="clear:both;"></p>
        </div>
      </div> -->
      <Nav />
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import UserApi from "../../api/UserApi";
import AlertApi from "../../api/AlertApi";
import Map from "../../views/Map.vue";
import Nav from "../../views/Nav.vue";
import axios from "axios";

export default {
  components: { Nav },
  props : {feedNo : String},
  mounted() {
    console.log('마운트')
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.$route.params.feedNo
    };
    
    // 코스 등록 추가되면 연동하기
    //let feedNo =  this.feedNo; // 피드 넘버에 대한 코스 받아오기
    FeedApi.getCourse(
      data,
      res => {
          this.courseList = res.data;
          console.log('코스 정보 받아옴!!');
          this.category();
          this.listSize = this.courseList.length + 1;
      },
      error => {
        alert(error);
      }
    );  
  },
  data: () => {
    return { 
      map : {},
      markers : [],
      points: [],
      ps : {},
      one : {},
      two: {},
      name: {},
      listSize: {},
      x: {},
      switch1: false,
      tempList: [],
      address : {},
      infowindow : {},
      courseList: [],
      bounds : {},
      feed : {},
      photos : [],
      photoList: [],
      comments : [],
      content: "",
      defaultImage, 
      defaultProfile,
      hashtags: ['#맛집','#파스타','#와인'],
      heartChange: false,
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      myComment: false,
      userId: "",
      model: ""
    };
  }, 
  created() {
    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.$route.params.feedNo
    };
    
    FeedApi.getFeedPhoto(
      data,
      response => {
        this.photoList = response.data;
        console.log(this.photoList);
      },
      error => {
        alert('피드 이미지 조회에 실패했습니다.');
      }
    );

    // // 코스 등록 추가되면 연동하기
    // //let feedNo =  this.feedNo; // 피드 넘버에 대한 코스 받아오기
    // FeedApi.getCourse(
    //   data,
    //   res => {
    //       this.courseList = res.data;
    //       console.log('코스 정보 받아옴!!');
    //   },
    //   error => {
    //     alert(error);
    //   }
    // );  
    
    FeedApi.loadFeedDetail(
      data,
      response => {
        this.userId = response.data.userId;
        this.feed = response.data.feed;
      },
      error => {
        alert('피드 상세 조회에 실패했습니다.');
      }
    );
    FeedApi.loadFeedPhotos(
      this.$route.params.feedNo,
      response => {
        this.photos = response.data
      },
      error => {
        alert('피드 이미지 조회에 실패했습니다.');
      }
    );

  },
  methods: {
    category(){
      console.log('분류');
      for(var i=0;i<this.courseList.length;i++){
        this.ps.keywordSearch(this.courseList[i].tradeName, this.placesSearchCB);
      }
    },
    clickComment(){
      this.$router.push("/feeds/comments/" + this.$route.params.feedNo); // 여기 수정
    },
    clickShare(){
       location.href = "holapet://share?m="+this.address;
    },
    clickRoute(){
       location.href = "https://maps.google.com/?daddr="+this.address;
    },
    clickLikeBtn(feed){
      if(!feed.mine){
        feed.mine = true;
        feed.likeCount += 1;
      } else{
        feed.mine = false;
        feed.likeCount -= 1;
      }

      let feedNo =  feed.feedNo;
      let data = {
        token : localStorage.getItem('token'),
        feedNo,
        feed
      };

      FeedApi.clickLike(
          data,
          response => {
            console.log("좋아요 클릭!");
          },
          error => {
            alert(error);
          }
        );
    },

    initMap() { 
      console.log('맵 초기화 ~~');

            var container = document.getElementById('map')
            this.bounds = new kakao.maps.LatLngBounds();   
            var options = {
                center: new kakao.maps.LatLng(33.450701, 126.570667),
                level: 3
            }
            this.map = new kakao.maps.Map(container, options)
            
            this.ps = new kakao.maps.services.Places()
            this.infowindow = new kakao.maps.InfoWindow({zIndex:1})
            //마커추가하려면 객체를 아래와 같이 하나 만든다. 
            var marker = new kakao.maps.Marker({
                position: this.map.getCenter()
            })
            marker.setMap(this.map)
            //if(this.courseList.length != 0) this.category();

            // var keyword = '우마이도 건대점';
            // var url = 'https://map.naver.com/v5/search/'; // 크롤링 url로 바꾸기 
            // for(var i=0; i<keyword.length;i++){
            //   if(keyword[i] == " ") url += '%20';
            //   else url += keyword[i];
            // }
            // url += '/place/';
            // console.log(url);

            // for (var i=0; i<this.courseList.length; i++) {
            //       //this.ps.keywordSearch(this.courseList[i]., this.placesSearchCB);
            //       console.log(this.courseList[i]);
            // }


            //this.test(url);
            //this.crawling(url);
            //this.ps.keywordSearch(name, this.placesSearchCB);
            // keyword로 이미지 찾아오기

            //this.ps.keywordSearch('호야 참치초밥 본점', this.placesSearchCB);
            //this.ps.keywordSearch('이태원 맛집', this.placesSearchCB);
            //this.setReBound();
        },

        crawling(url){
          console.log('로그들어옴')
            const request = require('request-promise')
            const cheerio = require('cheerio')
            const v = require('voca')
            const log = console.log;

            request(url)
              .then(html => {
                  let ulList = [];
                  const $ = cheerio.load(html); 
                  const $bodyList = $("div.link_search a.thumb_box");

                  // $bodyList.each(function(i, elem){
                  //     ulList[i] = {
                  //         src: $(this).find('a.nathumb_boxme').attr('src')
                  //     };
                  // });

                  // const data = ulList.filter(n => n.src);
                  // console.log(data);
                  // return data;
            })
        },

        test(url) {
                var optionAxios = {
                      headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Access-Control-Allow-Origin': '*',
                        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
                        'Access-Control-Allow-Headers': 'Content-Type, Authorization, Content-Length, X-Requested-With'
                      }
                }

                axios.get('https://cors-anywhere.herokuapp.com/' + 'https://place.map.kakao.com/26849085', optionAxios)
                  .then((response) => {
                    var htmlText = response.data;
                    console.log(htmlText);
                    //this.crawling(htmlText);
                  })
        },

        addScript() {
          console.log('지도 add');
            const script = document.createElement('script')
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap)
            script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=6eef005ce939915ab51bb458c785e9f4&autoload=false&libraries=services,clusterer,drawing'
            document.head.appendChild(script);
        },

        // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
        placesSearchCB(data, status, pagination) {
            for(var k=0; k<this.courseList.length;k++){
              for(var j=0; j<data.length;j++){
                if(this.courseList[k].latitude == data[j].x && this.courseList[k].longitude == data[j].y){
                     if (status === kakao.maps.services.Status.OK) {
                        //var bounds = new kakao.maps.LatLngBounds();
                        this.displayMarker(data[j]);    
                        //bounds.extend(new kakao.maps.LatLng(data[j].y, data[j].x));
                        this.bounds.extend(new kakao.maps.LatLng(data[j].y, data[j].x));
                        this.tempList.push(data[j]);
                        if(this.tempList.length == this.courseList.length) {
                          this.map.setBounds(this.bounds); // 범위 재설정  
                        }

                      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

                          alert('검색 결과가 존재하지 않습니다.');
                          return;

                      } else if (status === kakao.maps.services.Status.ERROR) {

                          alert('검색 결과 중 오류가 발생했습니다.');
                          return;

                      }
                    }
                 }
              }
        },

        displayMarker(place) {
            var str = place.category_name.split('>');
            this.name = place.place_name;
            this.address = place.address_name;

            var marker = new kakao.maps.Marker({
                map: this.map,
                position: new kakao.maps.LatLng(place.y, place.x) 
            });
            
            
            // 마커에 클릭이벤트를 등록합니다
            var temp = this.infowindow;
            var temp_map = this.map;
            kakao.maps.event.addListener(marker, 'click', function() {
                // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
                
                temp.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
                temp.open(temp_map, marker);
            }
            );
        }
  },
};

</script>

<style scoped>
.user-name{
  margin-top: 3%;
}
.feed-top{
  margin-left: 6%;
}
.feed-card{
  clear: both;
  margin-top: 1%;
  margin-right: 5%;
  border-radius: 0;
}
.space{
  height: 50px;
}
.content{
  margin-right: 15px;
}
.hashtag{
  float: left;
}
.feed-time{
  margin-top: 10px;
  padding-right: 5%;
  float:right;
}
.line{
  clear: both;
  height: 0.5px;
  width: 100%;
  background-color: black;
  opacity: 50%;
}
.feed-btn{
  padding-top: 3%;
}
.like-btn{
  margin-right: 2px;
}
.comments-btn{
  margin-left: 10px;
  margin-right: 2px;
}
.share-btn{
  margin-left: 11px;
}
.scrap-btn{
  float: right;
  padding-right: 7%;
}
.comment-area{
  padding-left: 7%;
}
.comment-input{
  margin-left: 5%;
  height: auto;
  line-height: normal;
  padding: .3em .9em;
  width: 200px;
  border: none;
  border-radius: 20px;
  -webkit-appearance: none;
  background-color: #ECECEC;
}
.regist-comment{
  padding: 1%;
  margin-left: 3%;
  border: 1px groove gray;
  border-radius: 2px;
}
.comment-profile{
  float: left;
}
.comment-writer{
  padding-top: 1.4%;
  margin-left: 1%;
  font-size: 15px;
  font-weight: 600;
  float: left;
}
.comment-content{
  margin-left: 2%;
  padding-top: 1.5%;
  float: left;
}
.delete-comment{
  padding: 1%;
  border: 1px groove gray;
  border-radius: 2px;
  float: right;
  margin-right: 3%;
  margin-top: 2%;
}

.feed-card > div> img {
  width: 100%;
  height: 100%;
}

.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:400px;}
#menu_wrap {position:absolute;top:0;left:0;bottom:0;width:250px;margin:10px 0 30px 10px;padding:5px;overflow-y:auto;z-index: 1;font-size:12px;border-radius: 10px;}

#menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#placesList li {list-style: none;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
#placesList .item .marker_11 {background-position: 0 -470px;}
#placesList .item .marker_12 {background-position: 0 -516px;}
#placesList .item .marker_13 {background-position: 0 -562px;}
#placesList .item .marker_14 {background-position: 0 -608px;}
#placesList .item .marker_15 {background-position: 0 -654px;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}
</style>
