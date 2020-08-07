<template>
    <div>
        <!-- <LogoTitle/> -->
        <tab></tab>
        <v-list subheader>
            <v-subheader>
                <v-btn text @click="rejectAll"><span>모두 거절</span></v-btn>
                <v-btn text @click="allowAll"><span>모두 승인</span></v-btn>
            </v-subheader>
            <v-list-item v-for="(request, index) in requestList" :key="request.id">
                <v-list-item-avatar>
                    <img src="@/assets/images/profile_default.png">
                </v-list-item-avatar>
                <v-list-item-content>
                    {{request.follower}}님이 팔로우를 요청하였습니다.
                </v-list-item-content>
                <v-list-item-icon>
                    <v-btn icon @click="reject(request, index)"><v-icon>fas fa-times</v-icon></v-btn>
                </v-list-item-icon>
                <v-list-item-icon>
                    <v-btn icon @click="allow(request, index)"><v-icon>fas fa-check</v-icon></v-btn>
                </v-list-item-icon>
            </v-list-item>
        </v-list>
        <!-- <div class="requests">
            <section class="tab-list">
                <ul class="request-list">
                    <li v-for="(request, index) in requestList" :key="request.id">
                        <div class="request">
                            <div class="request-info">
                                <img src="@/assets/images/profile_default.png" width="50px" height="50px" alt="user_img">
                                <div>{{request.follower}}님이 팔로우를 요청하였습니다.</div>
                            </div>
                            <div class="request-button">
                                <button class="u-fat-text deny" @click="reject(request,index)">거절</button>
                                <button class="u-fat-text approve" @click="allow(request,index)">승인</button>
                            </div>
                        </div>
                        <hr>
                    </li>
                    <hr>
                </ul>
            </section>
        </div> -->
        <!-- <Nav/> -->
    </div>
  
</template>

<script>
// import LogoTitle from "../LogoTitle.vue";
// import Nav from "../Nav.vue";
import "../../components/css/user/requests.css";
import axios from 'axios';
import RequestApi from "../../api/RequestApi";
import Tab from "../../components/alert/AlertTab";

export default {
    components: {
        // LogoTitle,
        // Nav,
        Tab
    },
    created() {
        let data = {
            email : localStorage.getItem('email'),
        }
        RequestApi.requestList(
            data,
            res => {
                this.requestList = res.data;
                console.dir(res);
            },
            error => {
                alert(error);
            }
        )
    },
    methods: {
        reject(request, index) {
            this.requestList.splice(index,1);
            let data = {
                req : request
            }
            RequestApi.requestReject( // 팔로우 거절할 경우
                data,
                res => {
                    console.log('Delete!');
                },
                error => {
                    console.log(error);
                }
            );
        },
        allow(request, index) {
            this.requestList.splice(index,1);
            let data = {
            req : request,
            token : localStorage.getItem('token'),
        }
            RequestApi.requestAllow( // 팔로우 승인할 경우
                data,
                res => {
                    console.log('Allow!');
                },
                error => {
                    console.log(error);
                }
            );
        },
        allowAll() { 
            this.requestList = []; // 전체 지우기 
            let data = {
            token : localStorage.getItem('token'),
        }
            RequestApi.requestAllowAll( // 팔로우 모두 승인
                data,
                res => {
                    console.log('Allow All!');
                },
                error => {
                    console.log(error);
                }
            );
        },
        rejectAll() { 
            this.requestList = []; // 전체 지우기 
            let data = {
            token : localStorage.getItem('token'),
        }
            RequestApi.requestRejectAll( // 팔로우 모두 거절
                data,
                res => {
                    console.log('Reject All!');
                },
                error => {
                    console.log(error);
                }
            );
        }
    },
    data() {
        return {
            requestList: []
        };
    }

}
</script>

<style scoped>

hr{
    margin: 0;
}

.request-button{
    padding: 5px;
    height: 40px;
    display: flex;
    margin-right: 0;
}

.tab-requests{
    background-color: bisque;
    opacity: 80%;
}

</style>