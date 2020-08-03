<template>
    <div>
        <LogoTitle/>
            <div id="followers">
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
            </div>
        <Nav/>
    </div>
  
</template>

<script>
import LogoTitle from "../LogoTitle.vue";
import Nav from "../Nav.vue";
import axios from 'axios';
import FollowApi from "../../api/FollowApi";
import ProfileApi from "../../api/ProfileApi";


export default {
    components: {
        LogoTitle,
        Nav
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

#followers{
    font-family: 'Noto Sans KR', sans-serif;
    max-width: 500px;
    min-height: 85vh;
    margin: 0 auto;
    padding: 0 20px;
}

hr{
    margin-bottom: 0px;
}

.tab{
    width: 100%;
    padding-top: 10px;
    max-width: 500px;
    margin: auto;
    display: flex;
    justify-content: center;
}

.tab > div{
    font-size: 20px;
    font-weight: 500;
    padding: 10px;
}

.tab > div > a{
    text-decoration: none;
    color: black;
    padding: 20px;
}

.tab-followers{
    width: 50%;
    text-align: center;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
    background-color: skyblue;
}

.tab-followings{
    width: 50%;
    text-align: center;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
    transition: all .2s ease-in-out;
}

.tab-followings:hover{
    background-color: #3897f0;
}

.follower {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color:  skyblue;
    margin: 0px;
}

.follower-info{
    display: flex;
    align-items: center;
}

.follower-img {
    border-radius: 50%;
    padding: 5px 10px;
}

.follower-name{
    font-size: 18px;
}

.follower-button >button{
    width: 60px;
    height: 35px;
    padding: 0 10px;
    margin-right: 10px;
    background-color: rgb(172, 170, 170);
}

.follower-button >button:hover{
    color:white;
    background-color:  #3897f0;
}

.block{
    border:1px solid rgba(0,0,0,.0975);
    background-color: white;
    color:black;
    border-radius: 3px;
}


</style>