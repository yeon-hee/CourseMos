import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";
import actions from "./actions";
import mutations from "./mutations";

Vue.use(Vuex);

const state = {
  token: {},
  isUser: false,
  email: {},
  userId: {},
  courses: {},
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
});
