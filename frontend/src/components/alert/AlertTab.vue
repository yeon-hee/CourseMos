<template>
    <v-card min-height="200" width="350">
    <v-tabs dark background-color="#fab7ae" grow v-model="tab">
        <v-tab key="1">
            <span>알림</span>
         </v-tab>

        <v-tab key="2">
            <span>요청</span>
        </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab" >
      <v-tab-item key="1">
        <alerts></alerts>
      </v-tab-item>
      <v-tab-item key="2">
        <requests></requests>
      </v-tab-item>
    </v-tabs-items>
    </v-card>
</template>

<script>
import alerts from "../../views/user/Alerts.vue";
import requests from "../../views/user/Requests.vue";
    export default {
        components: { 
            alerts,
            requests
        },
        data () {
            return {
                tab: null,
            }
        },
    }
</script>