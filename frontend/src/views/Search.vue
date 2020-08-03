<template>
    <div>
        <LogoTitle/>
        <div id="search">
            <div class="searchBar">
                <input type="text" placeholder="유저검색" v-model="keyword">
                <button>검색</button>
            </div>
            <div class="resultContainer">
                <select v-show="users.length > 0" multiple @change="onChange($event)">
                    <option v-for="user in users" v-bind:key="user">{{user.userId}}</option>
                </select>
            </div>
        </div>
        <Nav/>
    </div>
</template>

<script>
import LogoTitle from "./LogoTitle.vue";
import Nav from "./Nav.vue";
import UserApi from "../api/UserApi";

export default {
    components: {
        LogoTitle,
        Nav
    },
    methods : {
        onChange(event) {
            const userId = event.target.value;

            if(userId == localStorage.getItem('userId'))
                this.$router.push("/users/profile");
            else 
                this.$router.push("/users/profile/info/" + userId);
        }
    },
    watch : {
        keyword: function(v) {
            if(v.length > 0) {
                UserApi.findUserBykeyword(
                    v,
                    response => {
                        this.users = response.data
                    },
                    error => {
                        alert('유저 목록 조회에 실패했습니다.');
                    }
                );
            } else {
                this.users = []
            }
        }
    },
    data: () => {
    return { 
        keyword : "",
        users : []
    };
  },

}
</script>

<style scoped>

#search{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    min-height: 85vh;
    z-index: 1;
}

/* #search::after{
    position: absolute;
    top:0;
    left:0;
    content:"";
    width:100%;
    height: 100%;
    opacity: 0.5;
    z-index: -1;
    background-image: url(../assets/images/foods.png);
    background-repeat: no-repeat;
    background-size: cover;
} */


.resultContainer > select > option{
    padding-left: 5px;
    width: 250px;
}

.searchBar{
    display: flex;
    align-items: center;
    height: 40px;
    width: 250px;
    border: 1px solid #1b5ac2;
    background: white;

}

input{
    font-size: 16px;
    width: 75%;
    padding-left: 10px;
    border: 0px;
    outline: none;
}

button{
    width: 25%;
    height: 100%;
    border: 0px;
    background: #1b5ac2;
    outline: none;
    float: right;
    color: white;
}

</style>