<template>
<div>
  <v-card>

    <v-list-item>
      <v-list-item-avatar color="grey">
        <img :src="profileImage"/>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title>{{feed.userId}}</v-list-item-title>
        <v-list-item-subtitle>
          <v-icon small>fas fa-users</v-icon>
          {{followerCount}}
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-spacer></v-spacer>
      <span>
        {{parseDate}}
      </span>
    </v-list-item>
    <v-divider></v-divider>

    <v-container>
    <v-timeline dense>
      <v-timeline-item
        v-for="course in courses"
        :key="course.courseOrder"
      >
        <template v-slot:icon>
          <v-avatar>
            <img :src="course.thumbnailUrl">
          </v-avatar>
        </template>
        <template v-slot:opposite>
          <span>{{course.categoryName}}</span>
        </template>
        <v-row justify="space-between" @click="onImgClick">
          <v-col cols="7">{{course.tradeName}}</v-col>
          <v-col class="text-right text-caption" cols="5" style="color:#f09894">{{course.categoryName}}</v-col>
        </v-row>
      </v-timeline-item>
      
    </v-timeline>
          <v-chip-group
            column
            active-class="primary--text"
          >
            <v-chip v-for="tag in feed.tags" :key="tag">
              {{ tag }}
            </v-chip>
          </v-chip-group>
    <!-- <span>{{feed.tags}}</span> -->
    </v-container>
    <!-- <v-container>
      <v-card-text class="text--primary">
        <div>{{feed.contents}}</div>
      </v-card-text>
      <v-row>
        <v-col v-for="course in courses" :key="course.courseOrder" cols=3>
          <v-badge color="indigo" :content="Number(course.courseOrder)" overlap>
            <v-card @click="onImgClick">
              <v-img :src="course.thumbnailUrl" height="4em" width="4em" aspect-ratio="1" class="grey lighten-2"/>
              <div style="padding-top:2px; font-size:0.8em">{{cutStr(course.tradeName)}}</div>
            </v-card>
          </v-badge>

          <span>{{course.content}}</span>
        </v-col>
      </v-row>
    </v-container> -->
    
    
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn icon @click="clickLikeBtn(feed)">
        <v-img :src="feed.mine ? redHeart: emptyHeart" max-height="20px" max-width="20px" left/>
      </v-btn>
      <span>{{feed.likeCount}}</span>
      <v-btn icon @click="clickComment">
        <v-icon left small>far fa-comment</v-icon>
      </v-btn>
      <span>{{feed.commentCount}}</span>
      <v-spacer></v-spacer>
      <v-btn icon>
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
      followerCount : 0,
      defaultImage,
      profileImage : require('../../assets/images/profile_default.png'),
      defaultProfile,
      // hashtags: ['#맛집','#파스타','#와인'],
      heartChange: false,
      emptyHeart: require('../../assets/images/empty-heart.png'),
      redHeart: require('../../assets/images/red-heart.png'),
      courses : [],
      region : "",
      parseDate : {},
      tags : []
    };
  },
  created() {
    this.parseDate = moment(this.feed.writeDate).format( 'MM.DD H:mm');
    console.dir(this.feed);
    //태그파싱
    if(this.feed.tags != " " && this.feed.tags != "") {
      var tags = this.feed.tags.split(" ");
    }
    this.feed.tags = []
    for(let i in tags) {
      if(tags[i] == "") continue;
      this.feed.tags.push(tags[i]);
    }
    //
    let data = {
      token : localStorage.getItem("token"),
      feedNo : this.feed.feedNo,
      userId : this.feed.userId
    }
    ProfileApi.requestUserProfile(data,
      response => {
        // console.dir(response.data)
        if(response.data.profilePhoto != undefined && response.data.profilePhoto.length > 10) {
            this.profileImage = response.data.profilePhoto
        }
        let data = {
          token : localStorage.getItem('token'),
          email : response.data.email
        }

        ProfileApi.requestUserCount(data,
          response=> {
            this.followerCount = response.data.followerCount;
          },
          error=> {
            alert(error);
          }
        )
      },
      error => {
        alert(error);
      }
    )
    FeedApi.getCourse(data,
      response => {
        if(response.data.length ==0) return;
        this.courses = this.courses.concat(response.data);
        this.getRegionStr(this.courses)
      },
      error => {
        alert(error);
      }
    )
  },
  methods : {
    onImgClick() {
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
    clickComment() {
      this.$router.push("/feeds/comments/" + this.feed.feedNo);
    },
    cutStr(str) {
      if(str.length > 3){
          return str.substr(0,3)+"..";
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
      }

      const regionArray = [... set]
      for(var i = 0 ; i < regionArray.length; i++) {
        // console.log(regionArray[i])
        if(regionArray.length - 1 != i) {
          this.region += (regionArray[i] + ", ")
        } else {
          this.region += (regionArray[i])
        }
      }
      
    },
  }
};
</script>
<style scoped>
</style>
