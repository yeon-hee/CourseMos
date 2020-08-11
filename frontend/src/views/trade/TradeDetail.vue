<template>
    <div class="wrap components-page">

        <div id="content" class="content">
            <div class="ct_box_area">
                <div class="biz_name_area">
                    <strong class="name">우마이도 건대점</strong>
                    <span class="category">일본식라면</span>
                    <div class="info"><div class="info_inner">
                  <div>돈코츠 라멘이 맛있는 건대 맛집</div>
                    </div>
                </div>
            </div>
        </div>
    </div>




        <div class="wrapB">
           
            <p style="font-size: 21px;">{{trade.trade_name}}</p>
            <p style="text-align : center;">{{trade.trade_description}}</p>

            <div class="wrap">
                <b-icon icon="telephone-fill"></b-icon>
                <p>{{trade.telephone}}</p>
                <b-icon icon="geo-alt"></b-icon>
                <p>{{trade.address}}</p>
            </div>

            <div class="wrap">
                <ul>
                    <li v-for="item in menu" :key="item">
                        <p>{{item.name}}</p>
                        <p>{{item.price}}</p>
                    </li>
                </ul>
            </div>

            <h2>블로그 리뷰</h2>
            <div class="wrap">
                <div v-for="blog in blogs" :key="blog">
                    <a>{{blog.title}}</a>
                    <p>{{blog.contents}}</p>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import FeedApi from "../../api/FeedApi";

export default {
 
  data : () => {
      return {
            trade : {
                trade_name : "전주전집",
                trade_description : "사당동 전집으로 유명하고 사당 막걸리",
                telephone : "02-581-1419",
                address : "서울 동작구 동작대로 7길 19",
                menu : [],
                blogs : []
          }
      }
  },
   created() {

        let data = {
            token : localStorage.getItem('token'),
            tradeName: '우마이도 건대점' // 상점 이름
        };

        FeedApi.detailCrawling(
            data,
            res => {
                console.log("상세 정보 크롤링 완료!");
                console.log(res.data);
            },
            error => {
                alert(error);
            }
        );
    },
  
};
</script>

<style scoped>

h2 {
    color : black;
}
</style>
