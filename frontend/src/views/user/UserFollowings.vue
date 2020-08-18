<template>
    <v-container fluid class="mt-0 mb-5">
        <v-row class="py-0">
            <v-col class="py-0" cols="12" sm="6" offset-sm="3">
        <!-- <LogoTitle/> -->
            <v-tabs
                grow
                background-color="red lighten-2"
                dark
            >
                <v-tab :to="'/friends/followers/'+email">
                팔로워
                </v-tab>
                <v-tab :to="'/friends/followings/'+email">
                팔로잉
                </v-tab>
            </v-tabs>
            <v-list subheader>
                <v-list-item v-for="following in followingList" :key="following.id">
                    <v-list-item-avatar>
                        <v-img :src="following.profilePhoto" 
                            v-if="following.profilePhoto != undefined &&
                            following.profilePhoto.length > 10" />
                        <v-img src="@/assets/images/profile_default.png" v-else />
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title>{{following}}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
            <!-- <div id="followings">
                <section class="tab">
                    <div class="tab-followers"><a href="#/friends/followers">팔로워 <span>{{follower_count}}</span></a></div>
                    <div class="tab-followings"><a href="#">팔로잉 <span>{{following_count}}</span></a></div>
                </section>
                <hr>
                <section class="tab-list">
                    <ul class="following-list">
                        <li v-for="(following, index) in followingList" :key="following.id">
                            <div class="following">
                                <div class="following-info">
                                    <img class="following-img" src="@/assets/images/profile_default.png" width="50px" height="50px" alt="user_img">
                                    <div class="following-name">{{following}}</div>
                                </div>
                                <div class="following-button">
                                    <button class="u-fat-text cancel" @click="cancel(following, index)">취소</button>
                                </div>
                            </div>
                            <hr>
                        </li>
                        <hr>
                    </ul>
                </section>
            </div> -->
        <!-- <Nav/> -->
            </v-col>
        </v-row>
    </v-container>
  
</template>

<script>
// import LogoTitle from "../LogoTitle.vue";
// import Nav from "../Nav.vue";
import axios from 'axios';
import FollowApi from "../../api/FollowApi";
import ProfileApi from "../../api/ProfileApi";
// import Tab from "../../components/follow/FollowTab";


export default {
    components: {
        // LogoTitle,
        // Nav,
        // Tab
    },
    created() {
        this.email = this.$route.params.email;
        let data = {
            email : this.email,
            token : localStorage.getItem('token'),
            follower: localStorage.getItem('userId')
        }
        ProfileApi.requestCount(
            data,
            res => {
                this.follower_count = res.data.followerCount
                this.following_count = res.data.followingCount
                console.log(res);
            },
            error => {
                alert(error);
            }
        )
        FollowApi.getFollowing(
            data,
            res => {
                this.followingList = res.data
                console.log(res);
            },
            error => {
                alert(error);
            }
        )
    },
    data() {
        return {
            follower_count: 0,
            following_count: 0, 
            followingList: [],
            user_id: '',
            email : ""
        };
    },
    methods: {
    }

}
</script>

<style scoped>
</style>