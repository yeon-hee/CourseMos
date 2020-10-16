<template>
    <!-- <div> -->
        <!-- <LogoTitle/> -->
        <!-- <tab/> -->
        <!-- <v-list subheader>
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
        </v-list> -->
    <v-card>
        <!-- <v-card-title style="padding:0; justify-content: flex-end;">
            <v-btn
                color="error"
                text
                @click="closeAll">clear</v-btn>
        </v-card-title> -->
        <v-virtual-scroll
        :items="alertList"
        :item-height="30"
        height="200"
        >
        <v-btn text @click="closeAll"><span>모두 닫기</span></v-btn>

        <template v-slot="{ item , index}">
            <v-list :key="item.alertNo" style="margin: 0 0 0 12px;" >
            <div style="float: left;" class="text">
                {{ item.message }}
            </div>
            <div style="float: right; margin:6px 12px 0 0;">
                <v-btn text @click="close(item, index)" style="height:33px; margin-right:5px; border-radius: 30px; background-color:rgb(239,91,91);">닫기</v-btn>
                </div>
            <div style="clear:both;"/>
            </v-list>
            <v-divider style="margin: 0 10px 0 10px;"></v-divider> 
        </template>
        </v-virtual-scroll>
    </v-card>
    <!-- </div> -->
  
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