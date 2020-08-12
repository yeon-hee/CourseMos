<template>
    <div>
        <v-card
    class="mx-auto"
    max-width="500"
  >
    <v-sheet class="pa-4 primary lighten-2">
      <v-text-field
        v-model="search"
        label="유저 검색"
        dark
        flat
        solo-inverted
        hide-details
        clearable
        clear-icon="mdi-close-circle-outline"
      ></v-text-field>
      <v-checkbox
        v-model="caseSensitive"
        dark
        hide-details
        label="Case sensitive search"
      ></v-checkbox>
    </v-sheet><br>
        <v-treeview
            :active.sync="active"
            :items="items"
            :search="search"
            :filter="filter"
            :open.sync="open"
            transition
            activatable
        >
        <template v-slot:prepend="{ item }">
            <v-icon v-if="!item.children">mdi-account</v-icon>
        </template>
        
      </v-treeview><br>

      <v-col
        class="d-flex text-center"
      >
        <v-scroll-y-transition mode="out-in">
          <div
            v-if="!selected"
            class="title grey--text text--lighten-1 font-weight-light"
            style="align-self: center;"
          >
            Select a User
          </div>
          <v-card
            v-else
            :key="selected.id"
            class="pt-6 mx-auto"
            flat
            max-width="400"
          >
          </v-card>
        </v-scroll-y-transition>
      </v-col>
  </v-card>
   


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
import 'material-design-icons-iconfont/dist/material-design-icons.css'

export default {
    components: {
        // LogoTitle,
        // Nav
    },
    methods : {
        onChange(name) {
            console.log('들어옴!!');
            console.log(name);
            // const userId = event.target.value;

            // if(userId == localStorage.getItem('userId'))
            //     this.$router.push("/users/profile");
            // else 
            //     this.$router.push("/users/profile/info/" + userId);
        },
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
        },
        selected: 'randomAvatar',
    },
    data: () => {
    return { 
        keyword : "",
        users : [],
        items: [],
        active: [],
      temp : [],
      tempList: [],
      open: [1, 2],
      search: null,
      caseSensitive: false,
    };
  },

  created() {
        let data = {
            token : localStorage.getItem('token'),
        };

        UserApi.findAllUser(
            data,
            res => {
                this.temp = res.data;
                for(var i=0;i<this.temp.length;i++){
                    this.items.push({
                        id: i+1,
                        name: this.temp[i].userId});
                }
                console.log(this.items);
            },
            error => {
                alert(error);
            }
        );
    },

  computed: {
       filter () {
        return this.caseSensitive
          ? (item, search, textKey) => item[textKey].indexOf(search) > -1
          : undefined
      },
      selected () {
          console.log('안녕!');
          return '안녕';
      },
  }

}
</script>

<style scoped>

.resultContainer > select{
    padding-left: 5px;
    width: 100%;
}

</style>