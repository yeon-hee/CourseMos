<template>
 <div class="feed-item">
    <div class="feed-top">
      <br>
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>{{feed.userId}}</button>
        </div>
      </div>
    </div>
    <div class="space"></div>
    
    <div class="feed-card">
      <div class="map_wrap">
        <div id="map" style="width:500px;height:400px;"></div>
            <hr>
    </div>
    </div>
    <div class="content">
    <div class="feed-btn">
      <div>
        <img :src="feed.mine ? redHeart: emptyHeart" width="20px" height="20px" class="like-btn" @click="clickLikeBtn(feed)">
        <span>{{feed.likeCount}}</span>
        <img src="../../assets/images/comment.png" width="20px" height="20px" class="comments-btn">
        <span>{{feed.commentCount}}</span>
        <img src="../../assets/images/share.png" width="20px" heig0t="20px" class="share-btn">
        <img src="../../assets/images/star.png" width="20px" height="20px" class="scrap-btn">
      </div>
    </div>
    <div style="height:10px;"></div>
    <div class="line"></div>
      <div style="height:15px;"></div>
      <p>{{feed.contents}}</p>
       <div class="feed-hashtag">
         <p class="hashtag">#맛집</p>
         <p class="hashtag">#파스타</p>
         <p class="hashtag">#와인</p>
        <!-- <p v-for="hashtag in hashtags">{{hashtag}}</p> -->
      </div>
          <h5 class="feed-time">{{feed.writeDate}}</h5>
    </div>
    <div class="comment-area">
    <div class="line"></div>
    <div style="height:15px;"></div>
      <p style="padding-bottom: 10px;">댓글</p>
      <input class="comment-input"
             v-model="content"
             id="content"/>
      
      <button class="regist-comment"
              @click="registComment(feed)">등록</button>
      <div>
        <div style="margin-top:3%;" v-for="(comment, index) in comments" v-bind:key="comment.commentNo">
          <img src="../../assets/images/user.png" width="30px" height="30px" class="comment-profile"/>
          <p class="comment-writer">{{comment.writer}}</p>
          <p class="comment-content">{{comment.content}}</p>
          <button v-if="userId==comment.writer" @click="deleteComment(feed, comment, index)" class="delete-comment">삭제</button>
          <p style="clear:both;"></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import UserApi from "../../api/UserApi";
import AlertApi from "../../api/AlertApi";
import Map from "../../views/Map.vue";

export default {
  props : {feedNo : String},
  mounted() {
        window.kakao && window.kakao.maps ? this.initMap() : this.addScript();

  },
  data: () => {
    return { 
      map : {},
      markers : [],
      ps : {},
      infowindow : {},
      feed : {},
      photos : [],
      comments : [],
      content: "",
      defaultImage, 
      defaultProfile,
      hashtags: ['#맛집','#파스타','#와인'],
      heartChange: false,
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      myComment: false,
      userId: ""
    };
  }, 
  created() {
    let data = {
        token : localStorage.getItem('token'),
        feedNo : this.feedNo
    };
    console.log("feedNo : " +this.feedNo);
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
      this.feedNo,
      response => {
        this.photos = response.data
        // console.dir(this.photos)
      },
      error => {
        alert('피드 이미지 조회에 실패했습니다.');
      }
    );
    FeedApi.loadFeedComments(
      this.feedNo,
      response => {
        this.comments = response.data
        console.dir(this.comments)
      },
      error => {
        alert('피드 댓글 조회에 실패했습니다.');
      }
    );
  },
  methods: {
    registComment(feed){
        let { feedNo, content } = this;
        let data = {
          token : localStorage.getItem('token'),
          feedNo,
          content
        };
        console.log('댓글 등록 들어옴.');
        feed.commentCount += 1;

        FeedApi.registerComment(
          data,
          response => {
            console.log(response);
            console.log('댓글 등록 성공');
            alert("댓글이 등록되었습니다.");
            this.comments.push(response.data);

          },
          error => {
            alert(error);
          }
        );

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
      console.dir(feed);
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

      //  AlertApi.requestLike(
      //     data,
      //     response => {
      //       console.log("좋아요 알림 보내기!");
      //     },
      //     error => {
      //       alert(error);
      //     }
      //   );
    },
    deleteComment(feed, comment, index){
      //this.comments.splice(index, 1);
      let feedNo =  this.feedNo;
      let commentNo = comment.commentNo;
      let data = {
        token : localStorage.getItem('token'),
        feedNo,
        commentNo
      };

      FeedApi.deleteComment(
        data,
        response => {
          alert("댓글이 삭제되었습니다.");
          this.comments.splice(index, 1);
          // location.reload();
          // this.$router.push("/feed/main");
        },
        error => {
          alert(error);
        }
      );
    },

    initMap() { 
            var container = document.getElementById('map')
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

             // 코스에 대한 list 보여줄 부분 
            //this.ps.keywordSearch('호야 참치초밥 본점', this.placesSearchCB);
            this.ps.keywordSearch('우마이도 건대점', this.placesSearchCB);
            this.ps.keywordSearch('호야 참치초밥 본점', this.placesSearchCB);

            let feedNo =  this.feedNo; // 피드 넘버에 대한 코스 받아오기
            let data = {
              token : localStorage.getItem('token'),
              feedNo
            };

            FeedApi.getCourse(
              data,
              response => {
                console.log('코스 정보 받아옴!');
                console.dir(response);
              },
              error => {
                alert(error);
              }
            );  
        },

        addScript() {
            const script = document.createElement('script')
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap)
            script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=6eef005ce939915ab51bb458c785e9f4&autoload=false&libraries=services,clusterer,drawing'
            document.head.appendChild(script);
        },

        // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
        placesSearchCB(data, status, pagination) {

            if (status === kakao.maps.services.Status.OK) {

                var bounds = new kakao.maps.LatLngBounds();
                for (var i=0; i<data.length; i++) {
                  this.displayMarker(data[i]);    
                  bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
                }       

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
                this.map.setBounds(bounds);

            } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

                alert('검색 결과가 존재하지 않습니다.');
                return;

            } else if (status === kakao.maps.services.Status.ERROR) {

                alert('검색 결과 중 오류가 발생했습니다.');
                return;

            }
        },

        displayMarker(place) {

          // 마커를 생성하고 지도에 표시합니다
            var marker = new kakao.maps.Marker({
                map: this.map,
                position: new kakao.maps.LatLng(place.y, place.x) 
            });
            
            // 마커에 클릭이벤트를 등록합니다
              kakao.maps.event.addListener(marker, 'click', function() {
                // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
                this.infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
                this.infowindow.open(this.map, marker);
            });
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
  height: 1px;
  width: 94%;
  background-color: gray;
}
.feed-btn{
  padding-top: 3%;
}
.like-btn{
  margin-right: 10px;
}
.comments-btn{
  margin-left: 10px;
}
.share-btn{
  margin-left: 20px;
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
  width: 80%;
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
