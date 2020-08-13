<template>
  <v-container fluid class="mt-5 mb-16">
    <v-row>
      <v-col cols="12" sm="6">
        <div class="courseMake mb-2" style="justify-content: center;">
          <v-sheet 
            elevation="5"
            max-width="400"
            width="400"
            height="58">
            <v-slide-group show-arrows>
              <v-slide-item
                v-for="(course, index) in courses"
                v-bind:key="index"
                class="place"
              >
                <img :src="course.thumbnailUrl" class="thumbnail" alt="img" style="position: relative;"
                v-on:click="doRemove(index)" />
              </v-slide-item>
            </v-slide-group>
          </v-sheet>

          <!-- <ul class="placeList">
            <li
              v-for="(course, index) in courses"
              v-bind:key="index"
              class="place"
              v-on:click="doRemove(index)"
            >
              <img :src="course.thumbnailUrl" class="thumbnail" alt="img" style="position: relative;" />
            </li>
          </ul> -->
          <button v-on:click="saveCourse" class="next">
            <v-icon style="color:#0c6212">fas fa-plus</v-icon>
          </button>
        </div>
        <div id="map" style="width:100%;height:400px;"></div>
      </v-col>
      <v-col cols="12" sm="6">
        <div id="menu_wrap" class="bg_white" style="height:700px;">
            <div class="option">
              <div>
                <form v-on:submit.prevent="searchPlaces">
                  <v-text-field
                    outlined
                    label="코스 검색"
                    id="keyword"
                    prepend-inner-icon="mdi-map-marker"
                  ></v-text-field>
                </form>
              </div>
            </div>
            <hr />
            <v-card
              class="mx-auto"
              max-width="400"
              tile
              id="placesList"
            ></v-card>
            <!-- <ul id="placesList"></ul> -->
            <div id="pagination"></div>
          </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import UserApi from "../../api/UserApi";
import FeedApi from "../../api/FeedApi";
import defaultImage from "../../assets/images/img-placeholder.png";

export default {
  components: {
    // LogoTitle,
    // Nav,
  },
  created() {
    this.$store.state.courses = [];
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
  },
  data: () => {
    return {
      map: {},
      places: null,
      markers: [],
      ps: {},
      infowindow: {},
      courses: [],
      temp: {},
      idx: 0,
      thumbnails: [
        "https://mp-seoul-image-production-s3.mangoplate.com/549779_1554251346194232.jpg?fit=around|738:738&crop=738:738;*,*&output-format=jpg&output-quality=80",
        "https://mp-seoul-image-production-s3.mangoplate.com/330223/180342_1532235487795_285080?fit=around|738:738&crop=738:738;*,*&output-format=jpg&output-quality=80",
        "https://dimg.donga.com/a/500/0/90/5/ugc/CDB/29STREET/Article/5e/b2/04/e8/5eb204e81752d2738236.jpg",
        "https://t1.daumcdn.net/cfile/tistory/244CE24556B5642E36",
        "https://lh3.googleusercontent.com/proxy/UAxK_k2eBbpKoDGXhqZTDKL1gH7iAYDybHVkHAy5Bw4Otc3dRTlUOVq8EENkfGQCAt6oPOTCrtwZSzAGa7DMl4tdbyakm2vHIO-8kPimGGU39ZwfmzayxuQNCU0_JYCczKqHZmAYQij-BR24xmcMNaAa4Xwqzkfz5ZxlxKwFTN7Hmld1mpTUQbgvfw8_dgo-ykqTrm4jPtFoMGcPwfJVpU8--h4JoxPO6mIGv8JQ1RXE",
      ],
       items: [
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          title: 'Brunch this weekend?',
          subtitle: "<span class='text--primary'>Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?",
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
          title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
          subtitle: "<span class='text--primary'>to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend.",
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
          title: 'Oui oui',
          subtitle: "<span class='text--primary'>Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?",
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
          title: 'Birthday gift',
          subtitle: "<span class='text--primary'>Trevor Hansen</span> &mdash; Have any ideas about what we should get Heidi for her birthday?",
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
          title: 'Recipe to try',
          subtitle: "<span class='text--primary'>Britta Holt</span> &mdash; We should eat this: Grate, Squash, Corn, and tomatillo Tacos.",
        },
      ],
    };
  },
  methods: {
    initMap() {
      var markers = [];

      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
          level: 3, // 지도의 확대 레벨
        };

      // 지도를 생성합니다
      var map = new kakao.maps.Map(mapContainer, mapOption);

      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places();

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };
      this.map = new kakao.maps.Map(container, options);

      (this.ps = new kakao.maps.services.Places()),
        (this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 }));
      //마커추가하려면 객체를 아래와 같이 하나 만든다.
      var marker = new kakao.maps.Marker({
        position: this.map.getCenter(),
      });
      marker.setMap(this.map);
    },
    // displayInfowindow(marker, title) {
    //   var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

    //   this.infowindow.setContent(content);
    //   this.infowindow.open(this.map, this.marker);
    // },
    addScript() {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=6eef005ce939915ab51bb458c785e9f4&autoload=false&libraries=services,clusterer,drawing";
      document.head.appendChild(script);
    },

    searchPlaces() {
      var keyword = document.getElementById("keyword").value;

      if (!keyword.replace(/^\s+|\s+$/g, "")) {
        alert("키워드를 입력해주세요!");
        return false;
      }

      // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
      this.ps.keywordSearch(keyword, this.placesSearchCB);
    },

    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        ////////////////////////////////////////////////
        console.log(data);
        this.places = data;

        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        this.displayPlaces(data);

        // 페이지 번호를 표출합니다
        this.displayPagination(pagination);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 존재하지 않습니다.");
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        alert("검색 결과 중 오류가 발생했습니다.");
        return;
      }
    },

    // 검색 결과 목록과 마커를 표출하는 함수입니다
    displayPlaces(places) {
      var listEl = document.getElementById("placesList"),
        menuEl = document.getElementById("menu_wrap"),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = "";

      // 검색 결과 목록에 추가된 항목들을 제거합니다
      this.removeAllChildNods(listEl);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
          marker = this.addMarker(placePosition, i),
          itemEl = this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다

        var temp = this.displayInfowindow;
        var temp2 = this.infowindow;
        var temp3 = this.addplace;
        (function (marker, title) {
          kakao.maps.event.addListener(marker, "mouseover", function () {
            temp(marker, title);
          });

          kakao.maps.event.addListener(marker, "mouseout", function () {
            temp2(marker);
          });

          kakao.maps.event.addListener(marker, "click", function () {
            temp3(marker, title);
          });

          itemEl.onmouseover = function () {
            temp(marker, title);
          };

          itemEl.onmouseout = function () {
            temp2.close();
          };

          itemEl.onclick = function () {
            temp3(marker, title);
          };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
      }

      // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
      listEl.appendChild(fragment);
      menuEl.scrollTop = 0;

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
    },

    // 검색결과 항목을 Element로 반환하는 함수입니다
    getListItem(index, places) {
      //console.log(places);
      //console.log(places.category_name);
      //console.log(places.road_address_name);
      //console.log(places.category_name.split('>'));

      var el = document.createElement("li"),
    //   itemStr = '<v-list-item two-line>' +
    //   '<v-list-item-content>' +
    //     '<v-list-item-title>'+(index+1)+places.place_name+'</v-list-item-title>' +
    //     '<v-list-item-subtitle>'+places.road_address_name+places.address_name+'</v-list-item-subtitle>' +
    //   '</v-list-item-content>' +
    // '</v-list-item>'; 
        itemStr =
          '<div>' +
          "   <h3>" +
          (index + 1) +
          ". " +
          places.place_name +
          "</h3>";

      if (places.road_address_name) {
        itemStr +=
          "    <span>" +
          places.road_address_name +
          "</span>" +
          '   <span class="jibun gray">' +
          places.address_name +
          "</span>";
      } else {
        itemStr += "    <span>" + places.address_name + "</span>";
      }

      // itemStr += '  <span class="tel">' + places.phone + "</span>" + "</div>";
      itemStr += "<hr class='p-2'/>";
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, idx, title) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },

    // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
    displayPagination(pagination) {
      var paginationEl = document.getElementById("pagination"),
        fragment = document.createDocumentFragment(),
        i;

      // 기존에 추가된 페이지번호를 삭제합니다
      while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
      }

      for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement("a");
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
          el.className = "on";
        } else {
          el.onclick = (function (i) {
            return function () {
              pagination.gotoPage(i);
            };
          })(i);
        }

        fragment.appendChild(el);
      }
      paginationEl.appendChild(fragment);
    },

    // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
    // 인포윈도우에 장소명을 표시합니다
    displayInfowindow(marker, title) {
      var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

      // 인포윈도우를 생성합니다
      // var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      this.infowindow.setContent(content);
      this.infowindow.open(this.map, marker);
    },

    infowindow(marker) {
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      infowindow.close(this.map, marker);
    },

    addplace(marker, title) {
      for (var i = 0; i < this.places.length; i++) {
        if (this.places[i].place_name == title) {
          console.log('여기');
          console.log(this.places[i]);
          var mainphoto = "";

          let data = {
            token : localStorage.getItem('token'),
            number: this.places[i].id, // 상점 id
          };

          var placeTemp = this.places[i];
          var thisTemp = this.temp;
          var courseTemp = this.courses;
          var idxTemp = this.idx;

          // 비동기 처리 -> 통신 보내놓고 기다렸다가 마지막에 실행됨
          FeedApi.detailCrawling(
              data,
              res => {
                  console.log("상세 정보 크롤링 완료!");
                  thisTemp = res.data;
                  console.log(placeTemp);
                  mainphoto = thisTemp.mainphotourl;
                  if(mainphoto==null) {
                    mainphoto = defaultImage;
                    console.log('언디파인드');
                  }
                  console.log(mainphoto);

                  var tmpstr = placeTemp.category_name.split(">");
                  var category_name = tmpstr[tmpstr.length - 1];
                  courseTemp.push({
                  tradeName: placeTemp.place_name,
                  latitude: placeTemp.x,
                  longitude: placeTemp.y,
                  categoryName: category_name,
                  thumbnailUrl: mainphoto, // 여기서 크롤링한 이미지 받아오기 
                  roadAddress: placeTemp.road_address_name,
                  });
                  idxTemp++;
                  console.log('코스 정보');
                  console.log(courseTemp);
              },
              error => {
                  alert(error);
                  console.log('상세 정보 크롤링 실패했습니다.');
              }
          );

          // var tmpstr = this.places[i].category_name.split(">");
          // var category_name = tmpstr[tmpstr.length - 1];
          // this.courses.push({
          //   tradeName: this.places[i].place_name,
          //   latitude: this.places[i].x,
          //   longitude: this.places[i].y,
          //   categoryName: category_name,
          //   thumbnailUrl: mainphoto, // 여기서 크롤링한 이미지 받아오기 
          //   roadAddress: this.places[i].road_address_name,
          // });
          // this.idx++;
          // console.log('코스 정보');
          // console.log(this.courses);
        }
      }
      console.log(this.courses);
    },

    // 검색결과 목록의 자식 Element를 제거하는 함수입니다
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },

    saveCourse() {
      this.$store.state.courses = this.courses;
      this.$router.push("/feed/write");
    },

    doRemove(index) {
      console.dir(this.courses)
      this.courses.splice(index, 1);
    },
  },
};
</script>

<style scoped>
#feedMake {
  width: 400px;
  margin: 0 auto;
  min-width: 400px;
  max-width: 500px;
}
.courseMake {
  display: flex;
  background: rgba(255, 255, 255, 0.7);
  z-index: 2;
}
.placeList {
  width: 300px;
  min-width: 300px;
  max-width: 500px;
  padding: 0;
  margin: 5px 0;
  margin-left: 20px;
  height: 60px;
  border: 1px solid rgba(0, 0, 0, 0.0975);
  border-radius: 3px;
  box-shadow: 1px 1px 1px 1px grey;
}
.place {
  display: inline-flex;
  align-items: center;
  margin: 4px;
  width: 50px;
  height: 50px;
}
.place > img {
  width: 100%;
  height: 100%;
  border: 1px solid rgba(0, 0, 0, 0.0975);
  border-radius: 5px;
  box-shadow: 1px 1px 1px 1px grey;
}
.next {
  margin-left: 10px;
}
.map_wrap,
.map_wrap * {
  margin: 0 auto;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 500px;
}
#map {
  width: 300px;
  height: 300px;
}
#menu_wrap {
  display: block;
  top: 0;
  left: 0;
  bottom: 0;
  width: 380px;
  margin: 0 auto;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
}
.bg_white {
  background: #fff;
}
#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}
#menu_wrap .option {
  text-align: center;
}
#menu_wrap .option p {
  margin: 10px 0;
}
#menu_wrap .option button {
  margin-left: 5px;
}
#keyword {
  width: 150px;
  height: 30px;
}
#placesList li {
  list-style: none;
}
#placesList .item {
  list-style: none;
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
  background: #fff;
}
#placesList .item span {
  display: block;
  margin-top: 4px;
}
#placesList .item h5,
#placesList .item .info {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  background: #fff;
}
#placesList .item .info {
  padding: 10px 0 10px 55px;
}
#placesList .info .gray {
  color: #8a8a8a;
}
#placesList .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
    no-repeat;
}
#placesList .info .tel {
  color: #009900;
}
#placesList .item .markerbg {
  /* float: left;
  position: absolute; */
  width: 36px;
  height: 37px;
  margin: 10px 0 0 10px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
    no-repeat;
}
#placesList .item .marker_1 {
  background-position: 0 -10px;
}
#placesList .item .marker_2 {
  background-position: 0 -56px;
}
#placesList .item .marker_3 {
  background-position: 0 -102px;
}
#placesList .item .marker_4 {
  background-position: 0 -148px;
}
#placesList .item .marker_5 {
  background-position: 0 -194px;
}
#placesList .item .marker_6 {
  background-position: 0 -240px;
}
#placesList .item .marker_7 {
  background-position: 0 -286px;
}
#placesList .item .marker_8 {
  background-position: 0 -332px;
}
#placesList .item .marker_9 {
  background-position: 0 -378px;
}
#placesList .item .marker_10 {
  background-position: 0 -423px;
}
#placesList .item .marker_11 {
  background-position: 0 -470px;
}
#placesList .item .marker_12 {
  background-position: 0 -516px;
}
#placesList .item .marker_13 {
  background-position: 0 -562px;
}
#placesList .item .marker_14 {
  background-position: 0 -608px;
}
#placesList .item .marker_15 {
  background-position: 0 -654px;
}
#pagination {
  margin: 10px auto;
  text-align: center;
}
#pagination a {
  display: inline-block;
  margin-right: 10px;
}
#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}
.thumbnail:hover {
  opacity: 0.5;
  animation: shake 0.5s;
  animation-iteration-count: infinite;
}

@keyframes shake {
  0% {
    transform: translate(1px, 1px) rotate(0deg);
  }
  10% {
    transform: translate(-1px, -2px) rotate(-1deg);
  }
  20% {
    transform: translate(-3px, 0px) rotate(1deg);
  }
  30% {
    transform: translate(3px, 2px) rotate(0deg);
  }
  40% {
    transform: translate(1px, -1px) rotate(1deg);
  }
  50% {
    transform: translate(-1px, 2px) rotate(-1deg);
  }
  60% {
    transform: translate(-3px, 1px) rotate(0deg);
  }
  70% {
    transform: translate(3px, 1px) rotate(-1deg);
  }
  80% {
    transform: translate(-1px, -1px) rotate(1deg);
  }
  90% {
    transform: translate(1px, 2px) rotate(0deg);
  }
  100% {
    transform: translate(1px, -2px) rotate(-1deg);
  }
}
</style>