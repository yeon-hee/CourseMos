<template>
    <v-tabs dark background-color="primary" grow>
        <v-tab to="/alerts">
            <span>알림</span>
         </v-tab>

        <v-tab to="/requests">
            <span>요청</span>
        </v-tab>
    </v-tabs>
</template>

<script>
    export default {
        methods : {
            goRequest() {
                this.$router.push('/requests');
            },
            goAlert() {
                this.$router.push('/alerts');
            }
        }
    }
</script>