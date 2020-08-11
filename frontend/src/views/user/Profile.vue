<template>
    <v-container fluid>
        <v-row>
            <v-col cols="12" sm="6" offset-sm="3">
                <v-row>
                    <v-col cols="3">
                        <v-avatar size="48">
                            <v-img :src="profile_photo" aspect-ratio="1"></v-img>
                        </v-avatar>
                        
                        <div class="profile-name">
                            <h5 class="profile-title">{{user_id}}</h5>
                        </div>
                    </v-col>
                    <v-col cols="9">
                        <v-row>
                            <v-col cols="4" class="text-center">
                                <span class="profile-number u-fat-text">게시물</span>
                                <div class="articles-number">{{feed_count}}</div>
                            </v-col>
                            <v-col cols="4" class="text-center">
                                <span class="profile-number u-fat-text">팔로워</span>
                                <div class="followers-number">{{follower_count}}</div>
                            </v-col>
                            <v-col cols="4" class="text-center">
                                <span class="profile-number u-fat-text">팔로잉</span>
                                <div class="followings-number">{{following_count}}</div>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="10" class="mx-auto">
                                
                                <a href="#/users/profile/setting" class="profile-button u-fat-text">프로필 수정</a>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>
            </v-col>
        </v-row>

        <v-row class="pa-0">
            <v-col cols="12" sm="6" offset-sm="3">
                <div class="profile-bio">
                    <p class="profile-full-bio">{{profile_comment}}</p>
                </div>
            </v-col>
        </v-row>

        <v-row class="pa-0">
            <v-col cols="12" sm="6" offset-sm="3">
                <v-card>
                    <v-container fluid>
                        <v-row>
                            <v-col
                            v-for="feed in feeds" :key="feed.id"
                            class="d-flex child-flex"
                            cols="4"
                            >
                                <template>
                                <v-hover>
                                <template v-slot:default="{ hover }">
                                <v-card flat tile class="d-flex">
                                    <v-img
                                    @click="onImgClick(feed.feedNo)"
                                    :src="feed.thumbnail"
                                    aspect-ratio="1"
                                    class="grey lighten-2"
                                    >
                                    <template v-slot:placeholder>
                                        <v-row
                                        class="fill-height ma-0"
                                        align="center"
                                        justify="center"
                                        >
                                        <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                        </v-row>
                                    </template>
                                    </v-img>
                                    <v-fade-transition>
                                        <v-overlay
                                            @click="onImgClick(feed.feedNo)"
                                            v-if="hover"
                                            absolute
                                            color="#036358"
                                        >
                                            <v-row justify="space-around" class="mb-2">
                                                <span class="group pa-2">
                                                    <v-icon>fas fa-heart</v-icon> {{feed.like_count}}
                                                    <v-icon>fas fa-comment-dots</v-icon> {{feed.comment_count}}
                                                </span>
                                            </v-row>
                                            <!-- <span>
                                                <v-icon>favorite</v-icon> {{feed.like_count}}
                                            </span>
                                            <span>
                                                <v-icon>comment</v-icon> {{feed.comment_count}}
                                            </span> -->
                                        </v-overlay>
                                    </v-fade-transition>
                                </v-card>
                                </template>
                                </v-hover>
                                </template>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card>
            </v-col>
        </v-row>

        <main class="profile-container">
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
    </v-container>
</template>

<script>
import "../../components/css/user/profile.css";
import ProfileApi from "../../api/ProfileApi";
import FeedApi from "../../api/FeedApi";


export default {
    components: {
        // LogoTitle,
        // Nav
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
                // this.profile_photo = res.data.profilePhoto
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

</style>