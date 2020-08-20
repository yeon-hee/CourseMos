<template>
<v-card>
  <v-container fluid style="margin-bottom:50px; padding:0">
    <v-row>
      <v-col cols="12" sm="6" style="padding: 0 12px 0 12px;">
        <v-sheet 
          class="mx-auto" 
          elevation="3"
          height= "400px"
        >

          <v-expand-transition>
            <v-sheet
              v-if="model != null"
              color="grey lighten-4"
              height="332"
            >
              <div id="map" style="width:100%; height:331px;" v-show="!imageUrl"></div>
              <v-img :src="imageUrl" v-show="imageUrl" width="100%" height="331px"/>     
            </v-sheet>
          </v-expand-transition>
    
          <v-slide-group
            v-model="model"
            class="pa-1"
            show-arrows
          >

            <v-slide-item v-slot:default="{ active }">
              <v-card
                class="ma-1"
                height="50"
                width="76"
                @click="clickThumbnail(null)"
              >
                <v-scale-transition>
                  <v-icon
                    v-if="active"
                    color="white"
                    size="48"
                    v-text="'mdi-close-circle-outline'"
                  ></v-icon>
                </v-scale-transition>
                <v-img src="../../assets/images/map_thumbnail.png" width="76" height="50"/>
              </v-card>
            </v-slide-item>

            <v-slide-item
              v-for="photo in photoList"
              :key="photo.photoNo"
              v-slot:default="{ active }"
            >
              <v-card
                class="ma-1"
                height="50"
                width="76"
                @click="clickThumbnail(photo.photoUrl)"
              >
                <v-scale-transition>
                  <v-icon
                    v-if="active"
                    color="white"
                    size="48"
                    v-text="'mdi-close-circle-outline'"
                  ></v-icon>
                </v-scale-transition>
                <img :src="photo.photoUrl" height="50" width="76">
              </v-card>
            </v-slide-item>
          </v-slide-group>
        </v-sheet>
      </v-col>

      <v-col cols="12" sm="6" style="align-self: center; padding: 0 12px 12px 12px;">
        <v-row>
      <v-col cols="12" sm="6" offset-sm="3" style="padding: 10px 10px 10px 10px;">
      
         <div style="padding: 12px 12px 15px 12px;">
         <div style="display: inline; font-weight:bold;">{{feed.userId}} </div>
         <div style="display: inline; font-size: 14px; margin:0 0 0 3px;"> {{feed.contents}}</div>
        </div>

        <div style="padding: 5px 0 0 12px;" >
          <div v-for="tag in feed.tags" :key="tag" :ripple="false" style="color:rgb(43,73,102); display:inline; font-size:15px;">
            #{{ tag }}
          </div>
        </div>

      </v-col>
    </v-row>

      <div class="line"></div>
        <div>
           <v-btn icon @click="clickLikeBtn(feed)" style="margin-left:12px; width:20px; margin-top: 5px;">
            <v-img :src="feed.mine ? redHeart: emptyHeart" max-height="20px" max-width="20px" left></v-img>
          </v-btn>
          <span style="margin-left:3px; margin-top: 15px; vertical-align:sub;">{{feed.likeCount}}</span>
          <v-btn icon @click="clickComment()" style="margin-left:10px; width:20px; margin-top: 5px;">
            <img src="../../assets/images/comment.png" width="20px" height="20px"/>
          </v-btn>
           <span style="margin-left:3px; margin-top: 10px; vertical-align:sub;">{{feed.commentCount}}</span>
          <v-btn icon style="margin-left:1px; margin-top: 5px;">
            <img src="../../assets/images/share.png" width="18px" height="18px"/>
          </v-btn>
        </div><br><br>
        <!-- <div class="v-timeline v-timeline--align-top v-timeline--dense theme--light" style="left:-25px;">
          <div class="v-timeline-item v-timeline-item--fill-dot theme--light" 
            v-for="(course, i) in courseList" :key="i" style="padding-bottom:13px; width: 108%">
            <div class="v-timeline-item__body" style="margin-right:20px;">
              <v-card
                color='white'
                dark
                style="width:100%;"
              >
                <v-card-text class="white text--primary" style="padding:0px;">
                  <img :src="course.thumbnailUrl" style="float:left; height:45px; width:45px; border-radius: 8px; margin: 6px 0 6px 8px;">
                  <div style="float:left; margin: 10px 0px 9px 10px; line-height: 1.3em;">
                    <div style="font-size:11px; color:#8a8a8a;">{{course.categoryName}}</div>
                    <div style="font-size:14px;">{{course.tradeName}} </div>
                  </div>
                  <div style="float: right; margin: 14px 13px 0 0;"> 
                    <a :href="'https://maps.google.com/?daddr='+course.roadAddress" target="_sub" style="margin-bottom: 10px;">
                      <img src="../../assets/images/find_route_icon.png" width="30px" height="30px">
                    </a>
                  </div>
                  <div style="clear:both;"/>
                </v-card-text>
              </v-card>
            </div>
            <div class="v-timeline-item__divide" style="margin-right:16px; z-index:1;">
              <div class="v-timeline-item__dot v-timeline-item__dot--small">
                <div class="v-timeline-item__inner-dot red lighten-2">
                  <p style="color:white;align-self: start;">{{i+1}}</p>
                </div>
              </div>
            </div>
          </div>
        </div><br> -->
       <v-timeline :dense="$vuetify.breakpoint.smAndDown" style="left:-15px; padding-top: 10px;">
         <div data-v-19a3425a="" class="v-timeline-item v-timeline-item--before theme--light" style="width: 103%; padding-bottom:10px;" v-for="(course,index) in courseList" :key="course.courseOrder">
           <div class="v-timeline-item__body" style="margin-right:21px;">
              <v-card >
                <div>
                    <img :src="course.thumbnailUrl" style="float:left; height:45px; width:45px; border-radius: 8px; margin: 7px 0 6px 9px;">
                      <div style="float:left; margin: 10px 0px 9px 8px; line-height: 1.2em;">
                        <div style="font-size:10px; color:#8a8a8a;" @click="findTradeInfo(course)">{{course.categoryName}}</div>
                        <div style="font-size:13px;" @click="findTradeInfo(course)">{{course.tradeName}} </div>
                      </div>
                      <div style="float: right; margin: 14px 13px 0 0;"> 
                        <a :href="'https://maps.google.com/?daddr='+course.roadAddress" target="_sub" style="margin-bottom: 10px;">
                        <img src="../../assets/images/find_route_icon.png" width="30px" height="30px">
                        </a>
                    </div>
                      <div style="clear:both;"/>
                </div>
              </v-card>
              </div>
              <div class="v-timeline-item__divider" style="min-width: 15px; margin-right:15px; ">
                <div class="v-timeline-item__dot v-timeline-item__dot--small">
                  <div class="v-timeline-item__inner-dot red lighten-1"></div>
                  <div v-if="index < courseList.length-1">
                    <img src="../../assets/images/detail5.png" style="padding-top:2px; margin: 13px 0 0 5px; -webkit-filter: opacity(.5) drop-shadow(0 0 0 red);
                       filter: opacity(.5) drop-shadow(0 0 0 red);" width="14px" height="16px">
                  </div>
                </div>
                </div>
              </div>
        </v-timeline><br>
<!-- 
        <v-timeline dense>
          <v-timeline-item
                    v-for="course in courseList" :key="course.courseOrder"
                    color="rgb(239,91,91)"
                    small>
            <div style="height: 45px; width:100%; float:left; padding-right:30px;">
              <div>
                <div class="elevation-1 v-card v-sheet theme--light"
                    style="border-radius:10px; width:100%; height:57px; border:1px solid #ccc;"
                    @click="findTradeInfo(course)">
                    <div style="float:left;">
                    <img :src="course.thumbnailUrl" style="height:45px; width:45px; border-radius: 8px; margin: 5px 0px 5px 8px;">
                  </div>
                  <div style="float:left; margin: 10px 0px 9px 10px; line-height: 1.2em;">
                      <div style="font-size:10px; color:rgb(51,102,255);">{{course.categoryName}}</div>
                      <div style="font-size:13px;">{{course.tradeName}} </div>
                  </div>
                  <div style="float: right; margin: 12px;"> 
                      <a :href="'https://maps.google.com/?daddr='+course.roadAddress" target="_sub" style="margin-bottom: 15px;">
                      <img src="../../assets/images/find_route_icon.png" width="30px" height="30px">
                    </a>
                  </div>
                  <div style="clear: both;"></div>
                </div>
              </div>
            </div>
          </v-timeline-item>
        </v-timeline> -->
      </v-col>
    </v-row>
  </v-container>
 </v-card>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import UserApi from "../../api/UserApi";
import AlertApi from "../../api/AlertApi";
import ProfileApi from "../../api/ProfileApi";
import Map from "../../views/Map.vue";
import axios from "axios";

export default {
  props : {feedNo : String},
  mounted() {

    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.$route.params.feedNo
    };
    FeedApi.getCourse(
      data,
      res => {
          this.courseList = res.data;
          window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
      },
      error => {
        alert(error);
        console.dir('코스 정보 받아오는거 실패했습니다.');
      }
    );  
    
  },
  data: () => {
    return { 
      heart_icon: "far fa-heart",
      map : {},
      markers : [],
      ps : {},
      infowindow : {},
      courseList: [],
      bounds : {},
      feed : {},
      photoList: [],
      defaultImage, 
      defaultProfile,
      profileImage : require('../../assets/images/profile_default.png'),
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      userId: "",
      model: "",
      imageUrl : "",
      tags : []
    };
  }, 
  created() {
    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.$route.params.feedNo,
    };
    FeedApi.getFeedPhoto(
      data,
      response => {
        this.photoList = response.data;
      }, // 상단에 보여줄 사진 
      error => {
        alert('피드 이미지 조회에 실패했습니다.');
      }
    );

    FeedApi.loadFeedDetail(
      data,
      response => {
        this.userId = response.data.userId;
        this.feed = response.data.feed;
        console.log(this.feed)

         //태그파싱
        if(this.feed.tags != " " && this.feed.tags != "") {
          var tags = this.feed.tags.split(" ");
        }
        this.feed.tags = []
        for(let i in tags) {
          if(tags[i] == "") continue;
          this.feed.tags.push(tags[i]);
        }
        console.log('태그')
        console.log(tags);

        ProfileApi.requestUserProfile(
        data = {
          userId : this.userId
        },
        response => {
          if(response.data.profilePhoto != undefined && response.data.profilePhoto.length > 10) {
              this.profileImage = response.data.profilePhoto
          }
          let data = {
            token : localStorage.getItem('token'),
            email : response.data.email
          }
        },
        error => {
          alert(error);
      }
    )
      },
      error => {
        alert('피드 상세 조회에 실패했습니다.');
      }
    );

  },
  methods: {
    addScript() {
      const script = document.createElement('script')
            /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap)
      script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=6eef005ce939915ab51bb458c785e9f4&autoload=false&libraries=services,clusterer,drawing'
      document.head.appendChild(script);
    },
    initMap() { 
      var container = document.getElementById('map') 
      var options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
      }
      this.map = new kakao.maps.Map(container, options)

      this.bounds = new kakao.maps.LatLngBounds()    
      this.ps = new kakao.maps.services.Places()
      this.infowindow = new kakao.maps.InfoWindow({zIndex:1})
      this.map.setZoomable(false);

      this.setMapForCoursesView()
    },
    setMapForCoursesView() {
      this.setMapBounds()
      this.addPolyLines()
      this.displayMarkers()
    },
    setMapBounds(){
        for(const course of this.courseList) {
          this.bounds.extend(new kakao.maps.LatLng(course.longitude, course.latitude));
        }
        this.map.setBounds(this.bounds); // 범위 재설정  
    },
    addPolyLines() {
      var linePath = []
      for(const course of this.courseList){
        linePath.push(new kakao.maps.LatLng(course.longitude, course.latitude))
      }
      
      var polyline = new kakao.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표배열 입니다
        strokeWeight: 2, // 선의 두께 입니다
        strokeColor: 'rgb(239,91,91)', // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid' // 선의 스타일입니다
      });
            // 지도에 선을 표시합니다 
      polyline.setMap(this.map);
    },
    displayMarkers() {
      this.courseList.forEach((course, idx) => {
        const markerContent = '<div class = "label"' +
                              ' style = "height:25px; width:25px; border-radius:50%; border: rgb(239,91,91) solid 1px;' + 
                              'background-color: white; color: rgb(239,91,91); text-align: center;"' +
                              '><span>' +
                              (idx + 1)
                              '</span></div>'

        var position = new kakao.maps.LatLng(course.longitude, course.latitude);
        // 커스텀 오버레이를 생성합니다
        const customOverlay = new kakao.maps.CustomOverlay({
            position: position,
            content: markerContent   
        });

        customOverlay.setMap(this.map);
      })
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
        this.heart_icon = "fas fa-heart"
      } else{
        feed.mine = false;
        feed.likeCount -= 1;
        this.heart_icon = "far fa-heart"
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
            console.dir('조아요 클릭 시 오류입니다');
          }
        );
    },
    clickThumbnail(img) {
      this.imageUrl = img;
    },
    placesSearchCB(data, status, pagination) {
      for(var k=0; k<this.courseList.length;k++){
        for(var j=0; j<data.length;j++){
          if(this.courseList[k].latitude == data[j].x && this.courseList[k].longitude == data[j].y){
            if (status === kakao.maps.services.Status.OK) {
               console.log(data[j]);
              this.crawlingTradePage(data[j].id)
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
    findTradeInfo(course) {
      this.ps.keywordSearch(course.tradeName, this.placesSearchCB);
    },
    crawlingTradePage(id) {
      var feedNo = this.$route.params.feedNo;
      this.$router.push("/trade/" + id + "/" + feedNo);
    }
    

  },
};

</script>

<style scoped>
.line{
  clear: both;
  height: 0.5px;
  width: 100%;
  background-color: gray;
  opacity: 30%;
}
</style>