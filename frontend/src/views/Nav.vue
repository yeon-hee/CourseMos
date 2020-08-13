<template>

  <v-bottom-navigation  fixed :value="activeBtn" color="deep-orange darken-1" style="z-index:100;" background-color="#f89077">
      <v-btn icon value="main" @click="goMain">
        <v-icon color="white">fas fa-home</v-icon>
      </v-btn>
      <v-btn icon value="search" @click="goSearch">
        <v-icon color="white">fas fa-search</v-icon>
      </v-btn>
      <v-btn icon value="feedmake" @click="goFeedMake">
        <v-icon medium color="white">post_add</v-icon>
      </v-btn>
      <v-btn icon value="profile" @click="goProfile">
        <v-icon color="white">fas fa-user-circle</v-icon>
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
        goProfile() {
            this.$router.push('/users/profile')
        }
    }
}
</script>

<style scoped>

</style>