<template>
<<<<<<< HEAD
  <v-card height="200px" flat>
    <v-bottom-navigation fixed color="purple lighten-1">
      <v-btn icon color="teal" value="recent" @click="goMain">
=======
  <v-bottom-navigation fixed>
      <v-btn icon value="recent" @click="goMain">
>>>>>>> 395d561f3dced2889832e4539d0ef1b215482493
        <v-icon>fas fa-home</v-icon>
      </v-btn>
      <v-btn icon value="recent" @click="goSearch">
        <v-icon>fas fa-search</v-icon>
      </v-btn>
      <v-btn icon value="recent" @click="goFeedMake">
        <v-icon>far fa-plus-square fa-w-16</v-icon>
      </v-btn>
      <v-btn icon value="recent" @click="goAlerts">
        <v-icon>far fa-bell</v-icon>
      </v-btn>
      <v-btn icon value="recent" @click="goProfile">
        <v-icon>fas fa-user-circle</v-icon>
      </v-btn>
  </v-bottom-navigation>
</template>

<script>
export default {
    data() {
        return {
            profile_photo: require('@/assets/images/profile_default.png')
        }
    },
    methods: {
        goMain(event){
            this.$router.push('/feed/main')
        },
        goSearch() {
            this.$router.push('/search')
        },
        goFeedMake() {
            this.$router.push('/feedmake')
        },
        goAlerts() {
            this.$router.push('/alerts')
        },
        goProfile() {
            this.$router.push('/users/profile')
        }
    }
}
</script>

<style scoped>

</style>