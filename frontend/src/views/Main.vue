<template>
    <div>
        <logo-title/>
        <router-view/>
        <Nav/>
    </div>
</template>

<script>
import Nav from "./Nav"
import LogoTitle from "./LogoTitle";
export default {
    components : {
        Nav, 
        LogoTitle
    }
}
</script>

<style scoped>

</style>