<template>

  <v-bottom-navigation  fixed :value="activeBtn" color="deep-orange darken-1">
      <v-btn icon value="main" @click="goMain">
        <v-icon>fas fa-home</v-icon>
      </v-btn>
      <v-btn icon value="search" @click="goSearch">
        <v-icon>fas fa-search</v-icon>
      </v-btn>
      <v-btn icon value="feedmake" @click="goFeedMake">
        <v-icon>far fa-plus-square fa-w-16</v-icon>
      </v-btn>
      <v-btn icon value="alerts" @click="goAlerts">
        <v-icon>far fa-bell</v-icon>
      </v-btn>
      <v-btn icon value="profile" @click="goProfile">
        <v-icon>fas fa-user-circle</v-icon>
      </v-btn>
  </v-bottom-navigation>
</template>

<script>
export default {
    data() {
        return {
            profile_photo: require('@/assets/images/profile_default.png'),
            activeBtn: 1,
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