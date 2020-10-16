<template>
  <v-container fluid class="mb-14">
    <v-row>
      <v-col cols="12" sm="6">
        <template>
          <div data-v-4d6f63f3 class="v-card v-sheet theme--light" style="padding: 0px;">
            <header
              data-v-4d6f63f3
              class="v-sheet theme--light v-toolbar v-toolbar--extended"
              style="height: 65px;"
            >
              <div class="v-toolbar__content" style="height: 60px;">
                <v-slide-group show-arrows>
                  <v-slide-item v-for="(course, index) in courses" v-bind:key="index" class="place">
                    <span>
                      <img
                        :src="course.thumbnailUrl"
                        class="thumbnail"
                        alt="img"
                        style="margin-top:4px; border-radius: 5px; width:55px; height:55px;"
                        v-on:click="doRemove(index)"
                      />
                      <v-icon large color="orange" style="margin-top:5px;">mdi-chevron-right</v-icon>
                    </span>
                  </v-slide-item>
                </v-slide-group>
              </div>
              <button
                v-on:click="saveCourse"
                style="background-color:rgb(239,91,91);"
                data-v-4d6f63f3
                type="button"
                class="v-btn v-btn--absolute v-btn--bottom v-btn--contained v-btn--fab v-btn--right v-btn--round theme--dark v-size--small"
              >
                <span class="v-btn__content">
                  <i
                    data-v-4d6f63f3
                    aria-hidden="true"
                    class="v-icon notranslate mdi mdi-arrow-right-bold theme--dark"
                  ></i>
                </span>
              </button>
            </header>
          </div>
          <div id="map" style="width:100%;height:400px; margin:30px 0 15px 0;"></div>
        </template>
      </v-col>
      <v-col cols="12" sm="6">
        <v-card height="100%" min-height="300px">
          <v-toolbar color="#EF5B5B" dark>
            <form v-on:submit.prevent="searchPlaces">
              <input
                type="text"
                placeholder="검색할 코스를 입력하세요"
                style="outline:none; width: 100%; color:white;"
                id="keyword"
              />
            </form>
            <v-spacer></v-spacer>

            <v-btn icon>
              <v-icon v-on:click="searchPlaces">mdi-magnify</v-icon>
            </v-btn>
          </v-toolbar>
          <div id="menu_wrap" class="bg_white">
            <div id="placesList"></div>
            <ul id="placesList"></ul>
            <div id="pagination"></div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import UserApi from "../../api/UserApi";
import FeedApi from "../../api/FeedApi";
import defaultImage from "../../assets/images/img-placeholder.png";
import PlaceImage from "../../assets/images/detail1.png";
import CallImage from "../../assets/images/detail4.png";

export default {
  components: {
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
      model: null,
      overlay: {},
      places: null,
      markers: [],
      ps: {},
      courses: [],
      temp: {},
      idx: 0,
      index: 0,
      thumbnails: [
        "https://mp-seoul-image-production-s3.mangoplate.com/549779_1554251346194232.jpg?fit=around|738:738&crop=738:738;*,*&output-format=jpg&output-quality=80",
        "https://mp-seoul-image-production-s3.mangoplate.com/330223/180342_1532235487795_285080?fit=around|738:738&crop=738:738;*,*&output-format=jpg&output-quality=80",
        "https://dimg.donga.com/a/500/0/90/5/ugc/CDB/29STREET/Article/5e/b2/04/e8/5eb204e81752d2738236.jpg",
        "https://t1.daumcdn.net/cfile/tistory/244CE24556B5642E36",
        "https://lh3.googleusercontent.com/proxy/UAxK_k2eBbpKoDGXhqZTDKL1gH7iAYDybHVkHAy5Bw4Otc3dRTlUOVq8EENkfGQCAt6oPOTCrtwZSzAGa7DMl4tdbyakm2vHIO-8kPimGGU39ZwfmzayxuQNCU0_JYCczKqHZmAYQij-BR24xmcMNaAa4Xwqzkfz5ZxlxKwFTN7Hmld1mpTUQbgvfw8_dgo-ykqTrm4jPtFoMGcPwfJVpU8--h4JoxPO6mIGv8JQ1RXE",
      ],
      dataList: [],
    };
  },
  methods: {
    initMap() {
      var markers = [];

      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.5012860931305, 127.039604663862), // 지도의 중심좌표
          level: 4, // 지도의 확대 레벨
        };

      // 지도를 생성합니다
      var map = new kakao.maps.Map(mapContainer, mapOption);

      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places();

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(37.5012860931305, 127.039604663862),
        level: 4,
      };
      this.map = new kakao.maps.Map(container, options);
      this.map.setZoomable(false);

      (this.ps = new kakao.maps.services.Places()),
        (this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 }));
    },
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
      this.$store.state.keywordSave = keyword;

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
        
        //기존 오버레이 제거
        if (this.overlay.Da != undefined) {
          this.overlay.setMap(null);
        }
        this.places = data;

        for (var i = 0; i < data.length; i++) {
          this.dataList.push({
            name: data[i].place_name,
            address: data[i].address_name,
            id: data[i].id,
          });
        }

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
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        var marker = this.addMarker(placePosition, i);
        var itemEl = this.nextfunc(i, places[i]);

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
            //temp(marker, title);
          });

          kakao.maps.event.addListener(marker, "mouseout", function () {
            //temp2(marker);
          });

          kakao.maps.event.addListener(marker, "click", function () {
            // 마커 클릭시 띄우는 인포윈도우
            temp3(marker, title);
          });

          itemEl.onmouseover = function () {
            //temp(marker, title);
          };

          itemEl.onmouseout = function () {
            // 리스트
            temp2.close();
          };

          itemEl.onclick = function () {
            temp3(marker, title);
          };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
        //fragment.appendChild(this.dataList); // 여기
      }

      // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
      listEl.appendChild(fragment);
      menuEl.scrollTop = 0;

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
      //this.map.setZoomable(false);
    },

    // 검색결과 항목을 Element로 반환하는 함수입니다
    getListItem(index, places) {
      let data = {
        token: localStorage.getItem("token"),
        number: places.id, // 상점 id
      };

      var thisTemp = this.temp;
      var courseTemp = this.courses;
      var idxTemp = this.idx;
      var photo = "";
      var mainphoto = "";

      FeedApi.detailCrawling(
        data,
        (res) => {
          photo = res.data;
          mainphoto = photo.mainphotourl;
          if (mainphoto == null) {
            mainphoto = defaultImage;
          }
          this.nextfunc(index, places, mainphoto);
        },
        (error) => {
          alert(error);
        }
      );
    },

    nextfunc(index, places) {
      var el = document.createElement("div"),
        itemStr =
          '<div style="line-height: 1.8em; padding: 10px 7px 7px 25px;">' +
          '   <h3 style=" display: inline; color:red;">' +
          (index + 1) +
          " </h3>" +
          '   <h3 style="display: inline; color:orange;">' +
          places.place_name +
          "</h3><br>";

      if (places.road_address_name) {
        itemStr +=
          "<img src=" +
          PlaceImage +
          ' style= "width:7px; height=7px; -webkit-filter: opacity(.5) drop-shadow(0 0 0 gray); filter: opacity(.4) drop-shadow(0 0 0 gray); ">' +
          '   <span style="font-size: 13px;">' +
          " " +
          places.address_name +
          "</span><br>";
      } else {
        itemStr +=
          "<img src=" +
          PlaceImage +
          ' style= "width:7px; height=7px; -webkit-filter: opacity(.5) drop-shadow(0 0 0 gray); filter: opacity(.4) drop-shadow(0 0 0 gray); ">' +
          '<span style="font-size: 13px;">' +
          " " +
          places.address_name +
          "</span><br>";
      }

      if (places.phone) {
        itemStr +=
          "<img src=" + CallImage + ' style= "width:9px; height=9px; ">';
      }
      itemStr +=
        '<span style="font-size: 13px;">' +
        " " +
        places.phone +
        "</span></div>";
      itemStr +=
        '<div style=" height: 0.5px; width: 100%; background-color: black; opacity: 20%;"/>';
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
        el.innerHTML = i + " ";
        el.style = "text-decoration:none; font-size:14px; margin-right:7px";

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
      // 리스트 클릭 했을 경우
      var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

      // 인포윈도우를 생성합니다
      this.infowindow.setContent(content);
      this.infowindow.open(this.map, marker);
    },

    infowindow(marker) {
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      infowindow.close(this.map, marker);
    },

    addplace(marker, title) {
      // 코스 리스트에 추가하기
      for (var i = 0; i < this.places.length; i++) {
        if (this.places[i].place_name == title) {
          var mainphoto = "";

          let data = {
            token: localStorage.getItem("token"),
            number: this.places[i].id, // 상점 id
          };

          var placeTemp = this.places[i];
          var thisTemp = this.temp;
          var courseTemp = this.courses;
          var idxTemp = this.idx;
          var tempoverlay = this.overlay;
          var routetemp = this.$router;

          // 비동기 처리 -> 통신 보내놓고 기다렸다가 마지막에 실행됨신 보내놓고 기다렸다가 마지막에 실행됨
          FeedApi.detailCrawling(
            data,
            (res) => {
              thisTemp = res.data;
              mainphoto = thisTemp.mainphotourl;
              if (mainphoto == null) {
                mainphoto = defaultImage;
              }

              var tmpstr = placeTemp.category_name.split(">");
              var category_name = tmpstr[tmpstr.length - 1];

              var content =
                '<div class="wrap">' +
                '    <div class="info">' +
                '        <div style="padding: 5px 0 5px 10px;height: 30px;background:#fcd3ce;border-bottom: 1px solid #ddd;font-size: 15px;font-weight: bold;">' +
                placeTemp.place_name +
                '            <div class="close" id="button">' +
                " </div>" +
                "        </div>" +
                '        <div class="body">' +
                "                <img src=" +
                mainphoto +
                ' class="img" width="73" height="70" style="border-radius: 5px;">' +
                '            <div class="desc">' +
                '                <div class="ellipsis">' +
                category_name +
                "</div>" +
                '                <div class="jibun ellipsis">' +
                placeTemp.address_name +
                "</div>" +
                '                <div><div id="detail" target="_blank" class="link">상세보기 </div>' +
                '                <div id="add" target="_blank" class="link2">코스추가</div></div>' +
                "            </div>" +
                "        </div>" +
                "    </div>" +
                "</div>";

              this.map.setCenter(marker.getPosition());

              if (this.overlay.Da != undefined) {
                this.overlay.setMap(null);
              }
              tempoverlay = new kakao.maps.CustomOverlay({
                content: content,
                map: this.map,
                position: marker.getPosition(),
              });

              this.overlay = tempoverlay;

              tempoverlay.setMap(this.map);

              var butt = document.getElementById("button");
              butt.onclick = function () {
                tempoverlay.setMap(null);
              };
              var addbtn = document.getElementById("add"); // 코스에 추가하기
              addbtn.onclick = () => {
                var newCourseInfo = {
                  tradeName: placeTemp.place_name,
                  latitude: placeTemp.x,
                  longitude: placeTemp.y,
                  categoryName: category_name,
                  thumbnailUrl: mainphoto, // 여기서 크롤링한 이미지 받아오기
                  roadAddress: placeTemp.road_address_name,
                };
                if (this.idx > 0) {
                  if (
                    this.courses[this.idx - 1].latitude != placeTemp.x ||
                    this.courses[this.idx - 1].longitude != placeTemp.y
                  ) {
                    this.courses.push(newCourseInfo);
                    this.idx++;
                  }
                } else {
                  this.courses.push(newCourseInfo);
                  this.idx++;
                }
              };

              var id = placeTemp.id;
              var det = document.getElementById("detail"); // 상세보기로 이동
              det.onclick = function () {
                if (mainphoto == defaultImage)
                  alert("등록된 상세정보가 없습니다.");
                else {
                  window.open("/trade/" + id, "_blank", "width=400,height=667");
                }
              };

            },
            (error) => {
              alert(error);
            }
          );
        }
      }
    },

    closeOverlay() {
      this.overlay.setMap(null);
    },

    // 검색결과 목록의 자식 Element를 제거하는 함수입니다
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },

    saveCourse() {
      if (this.courses.length < 2) {
        alert("두 개 이상의 코스를 추가해 주세요.");
      } else {
        this.$store.state.courses = this.courses;
        this.$router.push("/feed/write");
      }
    },

    doRemove(index) {
      this.courses.splice(index, 1);
      this.idx--;
    },
  },
};
</script>

<style scoped>
.detail1 {
  -webkit-filter: opacity(0.5) drop-shadow(0 0 0 gray);
  filter: opacity(0.2) drop-shadow(0 0 0 gray);
}
input::placeholder {
  color: white;
}
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
  width: 85px;
  height: 70px;
}
.place > img {
  width: 100%;
  height: 100%;
  border: 1px solid rgba(0, 0, 0, 0.0975);
  border-radius: 5px;
  box-shadow: 1px 1px 1px 1px grey;
}
.line {
  clear: both;
  height: 0.5px;
  width: 100%;
  background-color: black;
  opacity: 50%;
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
  width: 100%;
  /* margin: 0 auto; */
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

 <style>
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 114px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 102px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 5px 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 14px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 6px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
  background: white;
}
.info .desc {
  position: relative;
  margin: 10px 0 0 75px;
  height: 70px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 12px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 60px;
  height: 60px;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
  text-decoration: none;
  display: inline;
}
.info .link2 {
  color: red;
  text-decoration: none;
  display: inline;
}
</style>