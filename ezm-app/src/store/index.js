import Vue from 'vue'
import Vuex from 'vuex'
import tabs from "@/store/modules/tabs";
import route from "@/store/modules/route";
import user from "@/store/modules/user";
import layout from "@/store/modules/layout"
import tenant from "@/store/modules/tenant"


Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        route,
        tabs,
        user,
        layout,
        tenant,
    },
});

export default store;