<template>
    <div>
        <v-card
    class="mx-auto"
    max-width="500"
  >
    <v-sheet class="pa-4" style="background-color: #fab7ae;">
      <v-text-field
        v-model="search"
        label="유저 검색"
        dark
        flat
        solo-inverted
        hide-details
        clearable
        clear-icon="mdi-close-circle-outline"
        style="background-color: #fab7ae;"
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
            v-model="selection"
            @update:active="onChange"
        >
        <template v-slot:prepend="{ item }">
            <v-icon v-if="!item.children">mdi-account</v-icon> {{ selected.name }}
        </template>
      </v-treeview><br><br><br>

  </v-card>
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
        onChange(selection) {
            console.log('들어옴!!');
            console.log(selection[0]);
            var userId = null;

            for(var i=0;i<this.items.length;i++){
                if(this.items[i].id === selection[0]) {
                    userId = this.items[i].name;
                    break;
                }
            }
            console.log(userId);
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
      selected () { // 여기 눌리면 오는 곳 
          return '안녕하세요';
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