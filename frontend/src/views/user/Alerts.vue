<template>
    <div>
        <!-- <LogoTitle/> -->
        <!-- <tab/> -->
        <v-list subheader>
            <v-subheader><v-btn text @click="closeAll"><span>모두 닫기</span></v-btn></v-subheader>
            <v-list-item v-for="(alert, index) in alertList" :key="alert.id">
                <v-list-item-avatar>
                    <img src="@/assets/images/profile_default.png">
                </v-list-item-avatar>
                <v-list-item-content>
                    <v-list-item-title v-html="alert.message"></v-list-item-title>
                </v-list-item-content>
                <v-list-item-icon>
                    <v-btn icon @click="close(alert, index)"><v-icon>fas fa-times</v-icon></v-btn>
                </v-list-item-icon>
            </v-list-item>
        </v-list>
    </div>
  
</template>

<script>
import "../../components/css/user/alerts.css";
import axios from 'axios';
import AlertApi from "../../api/AlertApi";
// import Tab from "../../components/alert/AlertTab";


export default {
    components: {
        // Tab
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
        },

    }
}

</script>

<style scoped>

</style>