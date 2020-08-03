<template>
    <div>
    <LogoTitle/>
        <div id="alerts">
            <section class="tab">
                <a class="active-tab" href="#"><div class="tab-alerts">알림</div></a>
                <a href="#/requests"><div class="tab-requests">요청</div></a>
            </section>
            <div class="all-button">
                <button class="all-close" @click="closeAll()">모두닫기</button>
            </div>
            <div class="tab-list">
                <ul class="alert-list">
                    <hr>
                    <li v-for="(alert, index) in alertList" :key="alert.id">
                        <div class="alert">
                            <div class="alert-info">
                                <img src="@/assets/images/profile_default.png" width="50px" height="50px" alt="user_img">
                                <div>{{alert.message}}</div> 
                            </div>
                            <button class="close-button" @click="close(alert, index)"><i class="fas fa-times"></i></button>
                        </div>
                        <hr>
                    </li>
                </ul>
            </div>
        </div>
    <Nav/>
    </div>
  
</template>

<script>
import LogoTitle from "../LogoTitle.vue";
import Nav from "../Nav.vue";
import "../../components/css/user/alerts.css";
import axios from 'axios';
import AlertApi from "../../api/AlertApi";


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
        AlertApi.requestAlert(
            data,
            res => {
                this.alertList = res.data;
                console.dir(res);
            },
            error => {
                alert('알림을 불러올 수 없습니다.')
            }
        )
    },
    data() {
        return {
            alertList: []
        };
    },
    methods: {
        close(alert, index) {
            let data = {
                alert : alert
            }
            AlertApi.requestClose(
                data,
                res => {
                    this.alertList.splice(index,1);
                    console.log('Close!');
                },
                error => {
                    console.log(error);
                }
            );
        },
        closeAll() {
            this.alertList = [];
            let data = {
                token: localStorage.getItem('token')
            }
            AlertApi.requestCloseAll(
                data,
                res => {
                    console.log('Close All!!!');
                },
                error => {
                    console.log(error);
                }
            );
        }
    }
}

</script>

<style scoped>

hr{
    margin: 0;
}

.tab-alerts{
    background-color: bisque;
    opacity: 80%;
}

</style>