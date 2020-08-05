<template>
    <div>
        <LogoTitle/>
        <div id="feedMake">
            <div class="courseMake">
                <ul class="placeList"></ul>
            </div>
            <Map/>
        </div> 
        <Nav/>
    </div>
  
</template>

<script>
import LogoTitle from "../LogoTitle.vue";
import Nav from "../Nav.vue";
import Map from "../Map.vue";
import UserApi from "../../api/UserApi";



export default {
    components: {
        LogoTitle,
        Nav,
        Map
    },
    methods : {
    },
    data() {
        return { 
            keyword : "",
            users : []
        };
    }
}
</script>

<style scoped>
#feedMake{
    width: 400px;
    margin: 0 auto;
    min-width: 400px;
    max-width: 500px;
}
.courseMake{
    width: 350px;
    min-width: 350px;
    max-width: 500px;
    margin: 5px auto;
    height: 60px;
    border:1px solid rgba(0,0,0,.0975);
    border-radius: 3px;
}
</style>