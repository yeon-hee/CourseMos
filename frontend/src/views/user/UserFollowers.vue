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
            <v-list-item v-for="follower in followerList" :key="follower.id">
                <v-list-item-avatar>
                    <img src="@/assets/images/profile_default.png">
                </v-list-item-avatar>
                <v-list-item-content>
                    <v-list-item-title>{{follower}}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
            <!-- <div id="followers">
                <section class="tab">
                    <div class="tab-followers"><a href="#">팔로워 <span>{{follower_count}}</span></a></div>
                    <div class="tab-followings"><a href="#/friends/followings">팔로잉 <span>{{following_count}}</span></a></div>
                </section>
                <hr>
                <section class="tab-list">
                    <ul class="follower-list">
                        <li v-for="(follower, index) in followerList" :key="follower.id">
                            <div class="follower" >
                                <div class="follower-info">
                                    <img class="follower-img" src="@/assets/images/profile_default.png" width="50px" height="50px" alt="user_img">
                                    <div class="follower-name">{{follower}}</div>
                                </div>
                                <div class="follower-button">
                                    <button class="u-fat-text block" @click="block(follower, index)">차단</button>
                                </div>
                            </div>
                            <hr>
                        </li>
                    </ul>
                </section>
            </div> -->
        <!-- <Nav/> -->
            </v-col>
        </v-row>
    </v-container>
  
</template>

<script>
import LogoTitle from "../LogoTitle.vue";
import Nav from "../Nav.vue";
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
            token : localStorage.getItem('token')
        }
        FollowApi.getFollower(
            data,
            res => {
                this.followerList = res.data
                console.log(res);
            },
            error => {
                alert(error);
            }
        )
        ProfileApi.requestCount(
            data,
            res => {
                this.follower_count = res.data.followerCount
                this.following_count = res.data.followingCount
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
            followerList: [],
            email : ""
        };
    },
    methods: {
    }

}
</script>

<style scoped>
</style>