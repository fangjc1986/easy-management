import {cookiesUtil, DialogUtil} from "@/utils/common.util";
import {accessTenantList} from "@/api/sys/access";
import Enum from "@/utils/Enum";

const state = {
    tenantList: [],
    tenantId: '',
};

// getters
const getters = {};

// actions
const actions = {
    reloadTenantList({state}) {
        accessTenantList().then(resp => {
            state.tenantList = resp.map(x => {
                x.label = x.name;
                x.value = x.id;
                return x;
            });
        })
    }
};

// mutations
const mutations = {
    setTenantId(state, tid) {
        if (state.tenantId && state.tenantId !== tid && tid) {
            DialogUtil.toast("正在切换租户，并刷新页面！", 'loading');
            Enum.vue.$store.dispatch('tabs/closeByCmd', 'all');
            setTimeout(() => {
                window.location.reload();
            }, 500);
        }
        cookiesUtil.set("tenant_id", tid);
        state.tenantId = tid;
    },
    clearTenant(state) {
        cookiesUtil.set("tenant_id", null);
        state.tenantList = [];
        state.tenantId = null;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}