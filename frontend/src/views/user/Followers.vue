<template>
    <v-container fluid class="mt-0 mb-5">
        <v-row class="py-0">
            <v-col class="py-0" cols="12" sm="6" offset-sm="3">
                <tab/>
                <v-list subheader>
                    <v-list-item v-for="(follower, index) in followerList" :key="follower.id">
                        <v-list-item-avatar>
                            <v-img :src="follower.profilePhoto" 
                                v-if="follower.profilePhoto != undefined &&
                                follower.profilePhoto.length > 10" />
                            <v-img src="@/assets/images/profile_default.png" v-else />
                        </v-list-item-avatar>
                        <v-list-item-content>
                            <v-list-item-title>{{follower}}</v-list-item-title>
                        </v-list-item-content>
                        <v-list-item-icon>
                            <v-btn icon @click="block(follower, index)"><v-icon>fas fa-ban</v-icon></v-btn>
                        </v-list-item-icon>
                    </v-list-item>
                </v-list>
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
import Tab from "../../components/follow/FollowTab";


export default {
    components: {
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