<template>
    <div>
        <LogoTitle/>
            <div id="followings">
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

#followings{
    font-family: 'Noto Sans KR', sans-serif;
    max-width: 500px;
    min-height: 85vh;
    margin: 0 auto;
    padding: 0 20px;
}

hr{
    margin: 0px;
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
}

.tab-followers{
    width: 50%;
    text-align: center;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
    transition: all .2s ease-in-out;
}

.tab-followers:hover{
    background-color: #3897f0;
}

.tab-followings{
    width: 50%;
    text-align: center;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
    background-color: skyblue;
}

.following {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color:  skyblue;
}

.following-info{
    display: flex;
    align-items: center;
}

.following-img {
    border-radius: 50%;
    padding: 5px 10px;
}

.following-name{
    font-size: 18px;
}

.following-button >button{
    width: 60px;
    height: 35px;
    padding: 0 10px;
    margin-right: 10px;
    background-color: rgb(172, 170, 170);
}

.following-button >button:hover{
    color:white;
    background-color:  #3897f0;
}

.cancel{
    border:1px solid rgba(0,0,0,.0975);
    background-color: white;
    color:black;
    border-radius: 3px;
}

</style>