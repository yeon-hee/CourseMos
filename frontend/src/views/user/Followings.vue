<template>
    <v-container fluid class="mt-0 mb-5">
        <v-row class="py-0">
            <v-col class="py-0" cols="12" sm="6" offset-sm="3">
        <!-- <LogoTitle/> -->
            <tab/>
            <v-list subheader>
                <v-list-item v-for="(following, index) in followingList" :key="following.id">
                    <v-list-item-avatar>
                        <v-img :src="following.profilePhoto" 
                            v-if="following.profilePhoto != undefined &&
                            following.profilePhoto.length > 10" />
                        <v-img src="@/assets/images/profile_default.png" v-else />
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title>{{following}}</v-list-item-title>
                    </v-list-item-content>
                    <v-list-item-icon>
                        <v-btn icon @click="cancel(following, index)"><v-icon>fas fa-window-close</v-icon></v-btn>
                    </v-list-item-icon>
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
import Tab from "../../components/follow/FollowTab";


export default {
    components: {
        // LogoTitle,
        // Nav,
        Tab
    },
    created() {
        let data = {
            email : localStorage.getItem('email'),
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
            user_id: ''
        };
    },
    methods: {
        cancel(following, index) {
            this.followingList.splice(index, 1);
            let data = {
                userId: following,
                follower: localStorage.getItem('userId'),
                token : localStorage.getItem('token'),
            };
            FollowApi.updateFollowing(
                data,
                res => {
                    this.following_count = res.data.followingCount
                },
                error => {
                    console.log(error);
                }
            );
            FollowApi.deleteFollowing(
                data,
                res => {
                    console.log(data)
                    console.log(res)
                    console.log('Delete!');
                },
                error => {
                    console.log(error);
                }
            );
        },
    }

}
</script>

<style scoped>
</style>