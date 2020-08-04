<template>
  <div class="nav">
      <div><a href="#/feed/main"><i class="fas fa-home"></i></a></div>
      <div><a href="#/search"><i class="fas fa-search"></i></a></div>
      <div><a href="#/alerts"><i class="far fa-bell"></i></a></div>
      <div><a href="#/users/profile"><img :src="profile_photo" class="profile-avatar"/></a></div>
      <!-- <div><a href="#/users/profile"><i class="fas fa-user-circle"></i></a></div> -->
  </div>
</template>

<script>
export default {
    data() {
        return {
            profile_photo: require('@/assets/images/profile_default.png')
        }
    }

}
</script>

<style scoped>
.nav{
    position: sticky;
    bottom: 0;
    margin: 0 auto;
    width: 100%;
    overflow: hidden;
    border-top: 1px solid lightslategray;
    background-color: white;
    max-width: 500px;
}

.nav > div > a{
    float: left;
    width: 25%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    text-decoration: none;
    color: black;
}

.nav > div > a >i{
    color: black;
}

.profile-avatar{
    margin-top: 10px;
    width: 25px;
    height: 25px;
    border-radius: 50%;
}

</style>