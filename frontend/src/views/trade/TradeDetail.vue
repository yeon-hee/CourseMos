<template>
    <div class="wrap components-page">

        <div class="wrapB">
            <div class="wrap">
                <b-carousel
                    id="carousel-no-animation"
                    style="text-shadow: 0px 0px 2px #000"
                    no-animation
                    indicators
                    img-width="1024"
                    img-height="480"
                >
                    <b-carousel-slide
                    caption="First slide"
                    img-src="https://picsum.photos/1024/480/?image=10"
                    ></b-carousel-slide>
                    <b-carousel-slide
                    caption="Second Slide"
                    img-src="https://picsum.photos/1024/480/?image=12"
                    ></b-carousel-slide>
                    <b-carousel-slide
                    caption="Third Slide"
                    img-src="https://picsum.photos/1024/480/?image=22"
                    ></b-carousel-slide>
                    <b-carousel-slide
                    caption="Fourth Slide"
                    img-src="https://picsum.photos/1024/480/?image=23"
                    ></b-carousel-slide>
                </b-carousel>
            </div>
            <div class="wrap">
                <h1>{{trade.trade_name}}</h1>
                <p style="text-align : center;">{{trade.trade_description}}</p>
            </div>

            <div class="wrap">
                <b-icon icon="telephone-fill"></b-icon>
                <p>{{trade.telephone}}</p>
                <b-icon icon="geo-alt"></b-icon>
                <p>{{trade.address}}</p>
            </div>

            <h2>블로그 리뷰</h2>
            <div class="wrap">
            </div>

        </div>
    </div>
</template>

<script>
import "../../assets/css/components.scss";

export default {
  components: {
  },
  data : () => {
      return {
            trade : {
                trade_name : "전주전집",
                trade_description : "사당동 전집으로 유명하고 사당 막걸리",
                telephone : "02-581-1419",
                address : "서울 동작구 동작대로 7길 19",
                menu : [],
                blogs : {}
          }
      }
  }
};
</script>

<style scoped>
</style>