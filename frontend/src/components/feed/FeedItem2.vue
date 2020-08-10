<template>
<div>
  <v-card>

    <v-list-item>
      <v-list-item-avatar color="grey">
        <img src="@/assets/images/profile_default.png"/>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title>{{feed.userId}}</v-list-item-title>
        <v-list-item-subtitle>{{region}}</v-list-item-subtitle>
      </v-list-item-content>
      <v-spacer></v-spacer>
      <v-list-item-content>
        <v-list-item-subtitle v-text="feed.writeDate"></v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-row>
      <v-col v-for="course in courses" :key="course.courseOrder" cols=3>
        <v-badge color="indigo" :content="Number(course.courseOrder)" overlap>
          <v-card @click="onImgClick">
            <v-img :src="course.thumbnailUrl" height="70px" aspect-ratio="1" class="grey lighten-2"/>
            <v-card-subtitle class="pb-0" style="font-size:0.8em">{{cutStr(course.tradeName)}}</v-card-subtitle>
          </v-card>
        </v-badge>

        <span>{{course.content}}</span>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn text @click="clickLikeBtn(feed)">
        <v-img :src="feed.mine ? redHeart: emptyHeart" max-height="20px" max-width="20px" left/>
        <span>{{feed.likeCount}}</span>
      </v-btn>
      <v-btn text @click="onImgClick">
        <v-icon left>far fa-comment</v-icon>
        <span>{{feed.commentCount}}</span>
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn text>
        <v-icon>mdi-bookmark</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
  <br>
</div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import FeedApi from "../../api/FeedApi";
import moment from "moment";
import ProfileApi from '../../api/ProfileApi';

export default {
  props : ['feed'],
  data: () => {
    return { 
      followCount : 0,
      defaultImage,
      defaultProfile,
      // hashtags: ['#맛집','#파스타','#와인'],
      heartChange: false,
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      courses : [],
      region : ""
    };
  },
  created() {
    this.feed.writeDate = moment(this.feed.writeDate).format( 'MM월DD일 HH시');
    let data = {
      token : localStorage.getItem("token"),
      feedNo : this.feed.feedNo,
      userId : this.feed.userId
    }
    ProfileApi.requestUserProfile(data,
      response => {
        this.followCount = response.data.userId
      },
      error => {
        alert(error);
      }
    )
    FeedApi.getCourse(data,
      response => {
        if(response.data.length ==0) return;
        this.courses = this.courses.concat(response.data);
        // console.log(this.courses)
        this.getRegionStr(this.courses)
      },
      error => {
        alert(error);
      }
    )
  },
  methods : {
    onImgClick() {
      // this.$router.push({name : "/feeds/" + this.feed.feedNo, query : {feedNo : this.feed.feedNo}});
      this.$router.push("/feeds/" + this.feed.feedNo);
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
        feedNo
      };

      FeedApi.clickLike(
          data,
          response => {
            console.log("좋아요 클릭");
          },
          error => {
            alert(error);
          }
        );
    },
    cutStr(str) {
      if(str.length >= 3){
          return str.substr(0,3)+"...";
      } else return str;
    },
    getRegionStr(courses) {
      var set = new Set()
      for(var course of courses) {
        try{
          const roadAddressArray = course.roadAddress.split(' ');
          const roadAddress = roadAddressArray[0].concat(" ", roadAddressArray[1]);
          set.add(roadAddress);
        }
        catch(e) {
          console.log(e);
        }
        //const roadAddressArray = course.roadAddress.split(' ')
        //const roadAddress = roadAddressArray[0].concat(" ", roadAddressArray[1])
        //set.add(roadAddress)
      }

      const regionArray = [... set]
      // console.dir(regionArray)
      for(var i = 0 ; i < regionArray.length; i++) {
        console.log(regionArray[i])
        if(regionArray.length - 1 != i) {
          this.region += (regionArray[i] + ", ")
        } else {
          this.region += (regionArray[i])
        }
      }
      
        // console.log(this.region)
    }
  }
};
</script>
<style scoped>
</style>
