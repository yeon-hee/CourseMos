<template>
    <div>
        <!-- <LogoTitle/> -->
        <tab/>
        <v-list subheader>
            <v-list-item v-for="(follower, index) in followerList" :key="follower.id">
                <v-list-item-avatar>
                    <img src="@/assets/images/profile_default.png">
                </v-list-item-avatar>
                <v-list-item-content>
                    <v-list-item-title>{{follower}}</v-list-item-title>
                </v-list-item-content>
                <v-list-item-icon>
                    <v-btn icon @click="block(follower, index)"><v-icon>fas fa-ban</v-icon></v-btn>
                </v-list-item-icon>
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
    </div>
  
</template>

<script>
import LogoTitle from "../LogoTitle.vue";
import Nav from "../Nav.vue";
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
            followerList: []
        };
    },
    methods: {
        block(follower, index) {
            this.followerList.splice(index, 1);
            let data = {
                email : localStorage.getItem('email'),
                follower: follower,
                token : localStorage.getItem('token')
            };
            FollowApi.updateFollower(
                data,
                res => {
                    this.follower_count = res.data.followerCount
                },
                error => {
                    console.log(error);
                }
            );
            FollowApi.deleteFollower(
                data,
                res => {
                    console.log(res);
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