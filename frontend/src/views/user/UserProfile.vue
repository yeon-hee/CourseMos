<template>
    <div>
        <main class="profile-container">
            <LogoTitle/>
            <section class="profile">
                <header class="profile-header">
                    <div class="profile-avatar-container">
                        <img 
                            :src="profile_photo"
                            class="profile-avatar"
                        />
                        <div class="profile-name">
                            <h5 class="profile-title">{{user.userId}}</h5>
                        </div>
                    </div>
                    <div class="profile-info">
                        <ul class="profile-numbers">
                        
                            <li class="profile-posts">
                                    <span class="profile-number u-fat-text">게시물</span>
                                    <div class="articles-number">{{count.feedCount}}</div>
                            </li>
                        
                            <a href="/#/friends/followers">
                                <li class="profile-followers">
                                    <span class="profile-number u-fat-text">팔로워</span>
                                    <div class="followers-number">{{count.followerCount}}</div>
                                </li>
                            </a>
                            <a href="#/friends/followings">
                                <li class="profile-following">
                                    <span class="profile-number u-fat-text">팔로잉</span>
                                    <div class="followings-number">{{count.followingCount}}</div>
                                </li>
                            </a>
                        </ul>
                        <a href='javascript:void(0);' @click="requestButton(follow_status);" class="profile-button u-fat-text">{{message}}</a>
                    </div>
                </header>
                <div class="profile-bio">
                    <p class="profile-full-bio">{{user.profileComment}}</p>
                </div>
                <hr>
                <ul class="profile-pictures">
                    <li class="profile-picture" v-for="feed in feeds" :key="feed.id">
                        <a @click="onImgClick(feed.feedNo)">
                            <img
                                :src="feed.thumbnail"
                                class="profile-picture-picture"
                            />
                            <div class="profile-picture-overlay">
                                <span class="profile-picture-number">
                                    <i class="fa fa-heart"></i> {{feed.like_count}}
                                </span>
                                <span class="profile-picture-number">
                                    <i class="fa fa-comment"></i> {{feed.comment_count}}
                                </span>
                            </div>
                        </a>
                    </li>
                </ul>
            </section>
        </main>
     <Nav/>
    </div>
</template>

<script>
import LogoTitle from "../LogoTitle.vue";
import Nav from "../Nav.vue";
import "../../components/css/user/profile.css";
import ProfileApi from "../../api/ProfileApi";
import FeedApi from "../../api/FeedApi";
import FollowApi from "../../api/FollowApi";
import RequestApi from "../../api/RequestApi";


export default {
    components: {
        LogoTitle,
        Nav
    },
    created() {
        let data = {
            email : localStorage.getItem('email'),
            token : localStorage.getItem('token'),
            userId : localStorage.getItem('userId')
        }
        
        ProfileApi.requestUserProfile(
            data,
            res => {
                this.user = res.data
                data.email = this.user.email


                ProfileApi.requestUserCount(
                    data,
                    res => {
                        this.count = res.data
                    },
                    error => {
                        alert(error);
                    }
                )
                FeedApi.loadUserFeeds(
                    data,
                    res => {
                        this.feeds = res.data
                    },
                    error => {
                        alert(error);
                    }
                )
                FollowApi.getFollower(
                    data,
                    res => {
                        const follows = res.data
                        for(const follow of follows) {
                            if(follow == this.$store.state.userId) {
                                this.follow_status = 2
                                this.message = "팔로잉 취소"
                                break
                            }
                        }
                    },
                    error => {
                        alert(error);
                    }
                )
                RequestApi.requestList(
                    data,
                    res => {
                        const requests = res.data
                        for(const request of requests) {
                            if(request.follower == localStorage.getItem('userId')) {
                                this.follow_status = 1
                                this.message = "팔로우 신청 중"
                                break
                            }
                        }
                    },
                    error => {
                        alert(error);
                    }
                )




            },
            error => {
                alert(error);
            }
        )
    },
    data() {
        return {
            profile_photo: require('@/assets/images/profile_default.png'),
            feed_default_photo: require('@/assets/images/NotFound.png'),
            user: {},
            //0은 팔로우x
            //1은 팔로우 신청 상태
            //2는 팔로우o
            follow_status : 0,
            message : "팔로우 요청",
            count : {},
            feeds: [
                
            ]
        };
    },
    methods: {
        requestButton(v) {
            if(v == 0) {
                RequestApi.followRequest(
                    {
                        follower : localStorage.getItem('userId'),
                        email : this.user.email
                    },
                    res => {
                        this.follow_status = 1
                        this.message = "팔로우 신청 중"
                        alert("팔로우 요청됐습니다!")
                    },
                    error => {
                        alert(error);
                    }
                )
            }
            else if(v == 2) {
                //
            }
        },
        onImgClick(no) {
        this.$router.push("/feeds/" + no);
        }
    }

}
</script>

<style scoped>

</style>