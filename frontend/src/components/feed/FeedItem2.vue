<template>
<div>
  <v-card>

    <v-list-item>
      <v-list-item-avatar style="border: 2px solid #f89077;">
        <img :src="profileImage"/>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title><a style="color:black;"  class="text-decoration-none"
                            href="javascript:void(0);" @click="onClickUserId(feed.userId)">{{feed.userId}}</a></v-list-item-title>
        <v-list-item-subtitle>
          <v-icon x-small>fas fa-users</v-icon>
          {{followerCount}}
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-spacer></v-spacer>
      <span style="font-size:12px;">
        {{parseDate}}
      </span>
    </v-list-item>
    <v-divider></v-divider>
    <br>
<div data-v-13777b40="" class="v-timeline v-timeline--align-top v-timeline--dense theme--light"  v-if="courses.length != 0" @click="onImgClick">
  <div data-v-13777b40="" class="v-timeline-item v-timeline-item--fill-dot theme--light" v-for="(course, i) in courses" :key="i" style="padding-bottom:13px;">
    <div class="v-timeline-item__body" style="margin-right:20px;">
      <v-card
        color='white'
        dark
        style="width:95%;"
      >
        <v-card-title style="font-size:15px; color:black; height:30px; padding:0 0 0 8px; color:orange;">{{course.tradeName}}</v-card-title>
        <v-card-text class="white text--primary" style="padding:0px;">
          <img :src="course.thumbnailUrl" style="float:left; height:45px; width:45px; border-radius: 8px; margin: 8px 0 8px 8px;">
                <div style="float:left; margin: 13px 0px 9px 10px; line-height: 1.2em;">
                  <div style="font-size:10px; color:#8a8a8a;">{{course.categoryName}}</div>
                  <div style="font-size:13px;">{{course.roadAddress}} </div>
                </div>
                <div style="clear:both;"/>
        </v-card-text>
      </v-card>
          </div>
          <div class="v-timeline-item__divide" style="margin-right:16px; z-index:1;">
            <div class="v-timeline-item__dot v-timeline-item__dot--small">
              <div class="v-timeline-item__inner-dot red lighten-2">
                <p style="color:white;align-self: start;">{{i+1}}</p>

                <!-- <i aria-hidden="true" class="v-icon notranslate mdi mdi-star theme--dark" style="font-size: 16px;"></i> -->
              </div>
            </div>
          </div>
        </div>
    </div>
    <v-container >
    <!-- <v-timeline dense v-if="courses.length != 0">
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
          <v-col class="text-right text-caption" cols="5" style="color:#f09894;">{{course.categoryName}}</v-col>
        </v-row>
      </v-timeline-item>
      
    </v-timeline> -->
            <!-- <v v-for="tag in feed.tags" :key="tag" :ripple="false" style="color:rgb(43,73,102);">
              #{{ tag }}
            </v> -->
    </v-container>
    
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn icon @click="clickLikeBtn(feed)" style="margin-left:2px; width:20px;">
        <v-img :src="feed.mine ? redHeart: emptyHeart" max-height="20px" max-width="20px" left />
      </v-btn>
      <span style="margin-left:3px;">{{feed.likeCount}}</span>
      <v-btn icon @click="clickComment" style="margin-left:7px; width:20px;">
        <img src="../../assets/images/comment.png" width="20px" height="20px"/>
      </v-btn>
      <span style="margin-left:2px;">{{feed.commentCount}}</span>
      <v-spacer></v-spacer>
    </v-card-actions>
     <v-spacer v-if="feed.tags != null" style="padding: 0 8px 8px 11px;">
       <div style="margin-bottom: 3px; font-size:14px;">
         <v-icon small color="#64DD17">
           mdi-map-marker-radius-outline
         </v-icon>
        <!-- <img src="../../assets/images/detail1.png" style="height:12px; width:12px; -webkit-filter: opacity(.5) drop-shadow(0 0 0 gray); filter: opacity(.4) drop-shadow(0 0 0 gray);"> -->
          <span style="font-size:12px;"> {{region}} </span>
       </div>
        <div v-for="tag in feed.tags" :key="tag" :ripple="false" style="color:#0091EA; display:inline; font-size:15px;">
          #{{ tag }}
        </div>
    </v-spacer>
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
    this.parseDate = moment(this.feed.writeDate).format( 'YYYY-MM-DD H:mm');
    // console.dir(this.feed);
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
        this.getRegionStr(this.courses);
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
          const roadAddress = roadAddressArray[1];
          // console.log(roadAddress)
          set.add(roadAddress);
          // console.log(set);
        }
        catch(e) {
          console.log(e);
        }
      }

      const regionArray = [... set]
      for(var i = 0 ; i < regionArray.length; i++) {
        // console.log(regionArray[i])
        if(regionArray.length - 1 != i) {
          this.region += (regionArray[i] + " -> ")
        } else {
          this.region += (regionArray[i])
        }
      }
      
    },
    onClickUserId(userId) {
      if(userId == localStorage.getItem('userId'))
        this.$router.push("/users/profile");
      else 
        this.$router.push("/users/profile/info/" + userId);
    }
  }
};
</script>
<style scoped>
</style>
