<template>
    <div>
        <LogoTitle/>
        <div id="feedMake">
            <div class="courseMake">
                <ul class="placeList">
                    <li v-for="(place, index) in places" v-bind:key="index" class="place" v-on:click="doRemove(index)">
                        <img :src="place.img" alt="" style="position: relative;">
                    </li>
                </ul>
                <button class="next">글작성하기</button>
            </div>
            <Map/>
        </div> 
        <Nav/>
    </div>
  
</template>

<script>
import LogoTitle from "../LogoTitle.vue";
import Nav from "../Nav.vue";
import Map from "../Map.vue";
import UserApi from "../../api/UserApi";



export default {
    components: {
        LogoTitle,
        Nav,
        Map
    },
    methods : {
        doRemove(index){
            this.places.splice(index, 1)
            console.log(this.places)
        }
    },
    data() {
        return { 
            // keyword : "",
            // users : []
            places:[
                {img:'https://d20aeo683mqd6t.cloudfront.net/ko/articles/title_images/000/026/550/medium/IMG_2236s.jpg?2019'},
                {img:'https://dimg.donga.com/a/500/0/90/5/ugc/CDB/29STREET/Article/5e/b2/04/e8/5eb204e81752d2738236.jpg'},
                {img:'https://2.bp.blogspot.com/-F3_mfQGh8JI/XIxFMDO8YuI/AAAAAAAAKPo/IaE7QjW-h8wCh2G62AZoFRYeJDdEMBvggCLcBGAs/s1600/1552639462050.jpg'},
                {img:'https://mp-seoul-image-production-s3.mangoplate.com/549779_1554251346194232.jpg?fit=around|738:738&crop=738:738;*,*&output-format=jpg&output-quality=80'},
                {img:'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQFagW0xUymk3gAxMStM3JFf8UfHF-CEPeu_w&usqp=CAU'},
            ]
        };
    }
}
</script>

<style scoped>
#feedMake{
    width: 400px;
    margin: 0 auto;
    min-width: 400px;
    max-width: 500px;
}
.courseMake{
    display: flex;
}
.placeList{
    width: 290px;
    min-width: 290px;
    max-width: 500px;
    margin: 5px 0;
    margin-left: 20px;
    height: 60px;
    border:1px solid rgba(0,0,0,.0975);
    border-radius: 3px;
}
.place{
    display: inline-flex;
    align-items: center;
    margin: 4px;
    width: 50px;
    height: 50px;
}
.place>img{
    width: 100%;
    height: 100%;
}
.next{
    margin-left: 10px;
}
</style>