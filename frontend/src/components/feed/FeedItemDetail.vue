<template>
  <v-container fluid style="margin-bottom:50px;">
    <v-row>
      <v-col cols="12" sm="6" offset-sm="3">
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
    </v-row>

    <v-row>
      <v-col cols="12" sm="6" offset-sm="3" style="padding-right:20px;">
        <template>
          <v-btn icon :color="feed.mine ? 'rgb(239,91,91)': emptyHeart" @click="clickLikeBtn(feed)" style="margin-right:10px">
            <!-- <v-icon>{{heart_icon}}</v-icon>  {{feed.likeCount}} -->
            <v-img left :src="feed.mine ? redHeart: emptyHeart" width="22px" height="22px"></v-img> {{feed.likeCount}}
          </v-btn>
          <v-btn icon @click="clickComment()">
            <v-icon size="22">far fa-comment</v-icon> {{feed.commentCount}}
          </v-btn>
          <v-btn icon>
            <v-icon size="22">fas fa-share-alt</v-icon>
          </v-btn>
          <!-- <img :src="feed.mine ? redHeart: emptyHeart" width="20px" height="20px" class="like-btn" @click="clickLikeBtn(feed)">
          <span>{{feed.likeCount}}</span>
          <img src="../../assets/images/comment.png" width="20px" height="20px" class="comments-btn">
          <span>{{feed.commentCount}}</span>
          <img src="../../assets/images/share.png" width="20px" height="20px" class="share-btn">
          <a href="javascript:;"  @click="clickComment()"
            style="float: right; color: rgb(51,102,255); "
             class="text-decoration-none">댓글 보기...</a> -->
        </template>
      </v-col>
    </v-row>
    <v-divider style="margin-bottom:20px; margin-top:20px;"></v-divider>

    <v-row>
      <v-col cols="12" sm="6" offset-sm="3">
        <v-timeline dense>
          
          <v-timeline-item
                    v-for="course in courseList" :key="course.courseOrder"
                    color="rgb(239,91,91)"
                    
                    small>
            <div style="height: 45px; width:100%; float:left;">
              <div>
                <div  class="elevation-1 v-card v-sheet theme--light" style="border-radius:10px; width:100%; height:57px; border:1px solid #ccc;">
                    <div style="float:left;">
                    <img :src="course.thumbnailUrl" style="height:45px; width:45px; border-radius: 8px; margin: 5px 0px 5px 8px;">
                  </div>
                  <div style="float:left; margin: 10px 0px 9px 10px; line-height: 1.2em;">
                      <div style="font-size:10px; color:rgb(51,102,255);">{{course.categoryName}}</div>
                      <div style="font-size:13px;">{{course.tradeName}} </div>
                  </div>
                  <div style="float: right; margin: 12px;"> 
                    <!-- <a href="javascript:;" @click="clickRoute()" style="margin-bottom: 15px;"> -->
                      <a :href="'https://maps.google.com/?daddr='+course.roadAddress" target="_sub" style="margin-bottom: 15px;">
                      <img src="../../assets/images/find_route_icon.png" width="30px" height="30px">
                    </a>
                  </div>
                  <div style="clear: both;"></div>
                </div>
                  </div>
                
                <div class="v-timeline-item__opposite">
                </div>
            </div>
          </v-timeline-item>
        </v-timeline>
      </v-col>
    </v-row>
 
  </v-container>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import UserApi from "../../api/UserApi";
import AlertApi from "../../api/AlertApi";
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
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      userId: "",
      model: "",
      imageUrl : ""
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
    

        

        // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
        // placesSearchCB(data, status, pagination) {
        //     for(var k=0; k<this.courseList.length;k++){
        //       for(var j=0; j<data.length;j++){
        //         if(this.courseList[k].latitude == data[j].x && this.courseList[k].longitude == data[j].y){
        //              if (status === kakao.maps.services.Status.OK) {
        //                 this.displayMarker(data[j]);    
        //                 this.bounds.extend(new kakao.maps.LatLng(data[j].y, data[j].x));
        //                 this.tempList.push(data[j]);
        //                 if(this.tempList.length == this.courseList.length) {
        //                   this.map.setBounds(this.bounds); // 범위 재설정  
        //                 }

        //               } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

        //                   alert('검색 결과가 존재하지 않습니다.');
        //                   return;

        //               } else if (status === kakao.maps.services.Status.ERROR) {

        //                   alert('검색 결과 중 오류가 발생했습니다.');
        //                   return;

        //               }
        //             }
        //          }
        //       }
        // },

//         displayMarker(place) {
//           let data = {
//             token : localStorage.getItem('token'),
//             number: place.id, // 상점 id
//           };

//           FeedApi.detailCrawling(
//               data,
//               res => {

//                   console.log(place.id+" 상세 정보 크롤링 완료!");
//                   this.temp = res.data;
//                   console.log(this.temp);

//                   this.mainPhotoList.push({
//                     name: this.temp.placenamefull,
//                     url: this.temp.mainphotourl});
//                   //console.log(this.mainPhotoList);
//               },
//               error => {
//                   // alert(error);
//                   console.log('상세 정보 크롤링 실패했습니다.');
//               }
//           );

//             var str = place.category_name.split('>');
//             this.name = place.place_name;
//             this.address = place.address_name;

//             // var marker = new kakao.maps.Marker({
//             //     map: this.map,
//             //     position: new kakao.maps.LatLng(place.y, place.x) 
//             // });
//             // console.dir(place)
//             var customOverlay = new kakao.maps.CustomOverlay({
//               position: new kakao.maps.LatLng(place.y, place.x), 
//               content: '<v-avatar color="indigo"><v-icon dark>mdi-account-circle</v-icon></v-avatar>'
//             });

// // 커스텀 오버레이를 지도에 표시합니다
//             customOverlay.setMap(this.map);
            
//             // 마커에 클릭이벤트를 등록합니다
//             // var temp = this.infowindow;
//             // var temp_map = this.map;
//             // kakao.maps.event.addListener(marker, 'click', function() {
//             //     // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
                
//             //     temp.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
//             //     temp.open(temp_map, marker);
//             // }
//             // );
//         }
  },
};

</script>

<style scoped>

/* .feed-btn{
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
} */
</style>
