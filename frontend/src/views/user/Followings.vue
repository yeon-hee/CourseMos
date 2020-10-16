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
            </v-col>
        </v-row>
    </v-container>
  
</template>

<script>
import axios from 'axios';
import FollowApi from "../../api/FollowApi";
import ProfileApi from "../../api/ProfileApi";
import Tab from "../../components/follow/FollowTab";


export default {
    components: {
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
            },
            error => {
                alert(error);
            }
        )
        FollowApi.getFollowing(
            data,
            res => {
                this.followingList = res.data
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