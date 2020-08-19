<template>
    <v-card>
        <v-virtual-scroll
        :items="requestList"
        item-height="85"
        height="200"
        >
        <template v-slot="{ item , index}" >
                <v-list :key="item.id" style="margin: 0 0 0 12px;" >
                        <div style="float: left;" class="text">
                        {{item.follower}}님이 팔로우를 요청하였습니다. 
                        </div>
                        <div style="float: right; margin:6px 12px 0 0;">
                        <v-btn @click="allow(item, index)" style="height:33px; margin-right:5px; border-radius: 30px; background-color:rgb(239,91,91);">수락</v-btn>
                        <v-btn @click="reject(item, index)" style="height:33px; border-radius: 30px; background-color:rgb(223,223,223);">거절</v-btn>
                        </div>
                        <div style="clear:both;"/>
                </v-list>
            <v-divider style="margin: 0 10px 0 10px;"></v-divider> 
        </template>
        </v-virtual-scroll>
    </v-card>
  
</template>

<script>
import "../../components/css/user/requests.css";
import axios from 'axios';
import RequestApi from "../../api/RequestApi";

export default {
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
            requestList: [],
        };
    }

}
</script>

<style scoped>

hr{
    margin: 0;
}
.line{
  clear: both;
  height: 0.5px;
  width: 100%;
  background-color: gray;
  opacity: 30%;
}
.request-button{
    padding: 5px;
    height: 40px;
    display: flex;
    margin-right: 0;
}
.text{
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 298px;
  height: 23px;
}
.tab-requests{
    background-color: bisque;
    opacity: 80%;
}

</style>