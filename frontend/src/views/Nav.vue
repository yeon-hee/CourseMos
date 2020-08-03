<template>
  <div class="nav">
      <div><a href="#/feed/main"><i class="fas fa-home"></i></a></div>
      <div><a href="#/search"><i class="fas fa-search"></i></a></div>
      <div><a href="#/alerts"><i class="far fa-bell"></i></a></div>
      <div><a href="#/users/profile"><i class="fas fa-user-circle"></i></a></div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.nav{
    position: sticky;
    left: 0;
    bottom: 0;
    width: 100%;
    overflow: hidden;
    border-top: 1px solid lightslategray;
    background-color: white;
    max-width: 500px;
    margin: auto;
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

</style>