import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'
import * as firebase from "firebase/app";
import "firebase/storage";
import vuetify from '@/plugins/vuetify';

var firebaseConfig = {
    apiKey: "AIzaSyC6lONSAuIKnw_a2Spz80_b_I9XHSM4xOE",
    authDomain: "matdongsan-65bb0.firebaseapp.com",
    databaseURL: "https://matdongsan-65bb0.firebaseio.com",
    projectId: "matdongsan-65bb0",
    storageBucket: "matdongsan-65bb0.appspot.com",
    messagingSenderId: "166370368871",
    appId: "1:166370368871:web:3c63cf94703cf98ca563c2",
    measurementId: "G-LJGZBQMPCY"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);


Vue.config.productionTip = false

Vue.use(VueRouter)

const router = new VueRouter({
    routes,
});


new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app');