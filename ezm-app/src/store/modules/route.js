import {list2Tree, routerImport} from "@/utils/common.util";
import router from "@/router";
import {accessAuthList} from "@/api/sys/access";
import Router from 'vue-router'
import Enum from "@/utils/Enum";

const state = {
    menuTree: [],
    routeAuthList: [],
};

// getters
const getters = {};

// actions
const actions = {};

// mutations
const mutations = {

    clearRoute(state) {
        state.menuTree = [];
        state.routeAuthList = [];
        router.options.routes[0].children = [];
        router.addRoutes(router.options.routes);
        router.matcher = new Router({
            routes: router.options.routes,
        });
    },

    /**
     * 刷新路由
     * @param state
     */
    refreshRoute(state) {
        accessAuthList().then(resp => {
            state.routeAuthList = resp.concat();
            state.menuTree = list2Tree(resp.filter(x => x.type === 1).sort((a, b) => (a.rank||0) - (b.rank||0)).map(x => {
                x.meta = {};
                x.meta.title = x.name;
                x.name = x.path;
                delete x.name;
                return x;
            }), '0', {idKey: 'id'});
            let r = resp.reduce((t, x) => {
                try {
                    if (x.children && x.children.length) return t;
                    x.component = routerImport(x.path.replace(/(^\/)|(\/$)/ig, '') + '/' + x.path.split('/').pop());
                    t.push(x);
                } catch (ignore) {
                }
                return t;
            }, []);
            setTimeout(() => {
                router.options.routes[0].children = r;
                router.addRoutes(router.options.routes);
            });
        }).catch((err) => {
            Enum.vue.$router.push({path: "/"});
        });
    },

};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}