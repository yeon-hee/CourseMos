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
                            <h5 class="profile-title">{{user_id}}</h5>
                        </div>
                    </div>
                    <div class="profile-info">
                        <ul class="profile-numbers">
                        
                            <li class="profile-posts">
                                    <span class="profile-number u-fat-text">게시물</span>
                                    <div class="articles-number">{{feed_count}}</div>
                            </li>
                        
                            <a href="/#/friends/followers">
                                <li class="profile-followers">
                                    <span class="profile-number u-fat-text">팔로워</span>
                                    <div class="followers-number">{{follower_count}}</div>
                                </li>
                            </a>
                            <a href="#/friends/followings">
                                <li class="profile-following">
                                    <span class="profile-number u-fat-text">팔로잉</span>
                                    <div class="followings-number">{{following_count}}</div>
                                </li>
                            </a>
                        </ul>
                        <a href="#/users/profile/setting" class="profile-button u-fat-text">프로필 수정</a>
                    </div>
                </header>
                <div class="profile-bio">
                    <p class="profile-full-bio">{{profile_comment}}</p>
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
        ProfileApi.requestCount(
            data,
            res => {
                this.follower_count = res.data.followerCount
                this.following_count = res.data.followingCount
                this.feed_count = res.data.feedCount
                console.dir(res);
            },
            error => {
                alert(error);
            }
        )
        ProfileApi.requestProfile(
            data,
            res => {
                this.user_id = res.data.userId
                this.profile_comment = res.data.profileComment
            },
            error => {
                alert(error);
            }
        )
        FeedApi.loadUserFeeds(
                    data,
                    res => {
                        this.feeds = res.data
                        console.dir(this.feeds)
                    },
                    error => {
                        alert(error);
                    }
        )
        // ProfileApi.requestFeed(
        //     data,
        //     res => {
        //         // this.feedList.photo_url = res.data.thumbnail
        //         console.log("들어옴");
        //         console.log(res);
        //     },
        //     error => {
        //         alert(error);
        //     }
        // )
    },
    data() {
        return {
            profile_photo: require('@/assets/images/profile_default.png'),
            user_id: '',
            profile_comment: '',
            feed_count: 0,
            follower_count: 0,
            following_count: 0, 
            feeds: [
                    
            ]
        };
    },
    methods : {
        onImgClick(no) {
            this.$router.push("/feeds/" + no);
            console.dir()
        }
    }

}
</script>

<style scoped>

.profile-container{
    min-width: 400px;
    min-height: 650px;
}

</style>