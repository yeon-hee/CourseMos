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
                <v-tab :to="'/friends/followers/'+email+'/'+userId">
                팔로워
                </v-tab>
                <v-tab :to="'/friends/followings/'+email+'/'+userId">
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
            </v-col>
        </v-row>
    </v-container>
  
</template>

<script>
import axios from 'axios';
import FollowApi from "../../api/FollowApi";
import ProfileApi from "../../api/ProfileApi";


export default {
    components: {
    },
    created() {
        this.email = this.$route.params.email;
        this.userId = this.$route.params.userId;
        let data = {
            follower : this.userId,
            token : localStorage.getItem('token'),
        }

        FollowApi.getFollowing(
            data,
            res => {
                this.followingList = res.data
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
            followingList: [],
            userId: '',
            email : ""
        };
    },
    methods: {
    }

}
</script>

<style scoped>
</style>