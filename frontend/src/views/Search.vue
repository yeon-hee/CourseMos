<template>
    <div>
        <v-form>
            <v-container>
                <v-row>
                    <v-col cols="6" sm="6"
                            class="mx-auto">
                        <v-text-field
                            align="center"
                            outlined
                            type="text"
                            label="유저 검색"
                            prepend-inner-icon="mdi-account-question"
                            v-model="keyword"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row class="pa-0">
                    <v-col cols="6" sm="6"
                            class="mx-auto">
                    <div class="resultContainer">
                        <select v-show="users.length > 0" multiple @change="onChange($event)">
                            <option v-for="user in users" v-bind:key="user.userId">{{user.userId}}</option>
                        </select>
                    </div>
                    </v-col>
                </v-row>
            </v-container>
        </v-form>
                
    </div>
</template>

<script>
import LogoTitle from "./LogoTitle.vue";
import Nav from "./Nav.vue";
import UserApi from "../api/UserApi";

export default {
    components: {
        // LogoTitle,
        // Nav
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

.resultContainer > select{
    padding-left: 5px;
    width: 100%;
}

</style>