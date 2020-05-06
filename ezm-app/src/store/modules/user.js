import {cookiesUtil} from "@/utils/common.util";
import {accessGetMyInfo, accessSignOut} from "@/api/sys/access";
import router from "@/router";
import Enum from "@/utils/Enum";

const state = {
    loginUser: {},
};

// getters
const getters = {
};

// actions
const actions = {};

// mutations
const mutations = {
    setUser(state, user) {
        state.loginUser = user;
        cookiesUtil.set("token", user.token);
        Enum.vue.$store.commit('tenant/setTenantId', user.tenantId);
        Enum.vue.$store.dispatch('tenant/reloadTenantList');
    },
    clearUser(state) {
        state.loginUser = {};
        cookiesUtil.set("token", null);
        Enum.vue.$store.commit('tenant/clearTenant');
    },
    refreshMyInfo(state) {
        accessGetMyInfo().then(resp => {
            Enum.vue.$store.commit('user/setUser', resp);
        });
    },
    signOut(state) {
        accessSignOut().then(resp => {
            Enum.vue.$store.commit("user/clearUser");
            Enum.vue.$store.commit("route/clearRoute");
            Enum.vue.$store.dispatch("tabs/clearTabs");
            router.push({path: '/'});
        });
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}