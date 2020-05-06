
const getIndex = (path, state) => {
    return state.showTabs.findIndex(x => x.path === path);
};

const getIndexAndRemove = (path, state) => {
    let index = getIndex(path, state);
    if (index >= 0) {
        state.showTabs.splice(index, 1);
    }
    return index;
};

const state = {
    showTabs: [],
    activeTabsName: "/system/index",
};

// getters
const getters = {};


// actions
const actions = {
    closeTabs({state}, path) {
        let index = getIndexAndRemove(path, state);
        if (index < 0) return;
        if (index < state.showTabs.length) {
            state.activeTabsName = state.showTabs[index].path;
        } else if (state.showTabs.length <= 0) {
            state.activeTabsName = "/system/index";
        } else {
            state.activeTabsName = state.showTabs[state.showTabs.length - 1].path;
        }
    },
    clearTabs({state}){
        state.showTabs = [];
    },
    setActiveTabsName({state,dispatch}, path) {
        state.activeTabsName = path;
    },
    pushTo({dispatch, state}, route) {
        state.activeTabsName = route.path;
        if (route.path === '/system/index') return;
        if (state.showTabs.find(x => x.path === route.path)) return;
        state.showTabs.push(route);
    },
    closeByCmd({dispatch, state}, cmd) {
        switch (cmd) {
            case "now" :
                dispatch('closeTabs', state.activeTabsName);
                break;
            case "all" :
                state.showTabs = [];
                dispatch('setActiveTabsName', '/system/index');
                break;
            case 'other' :
                state.showTabs = state.showTabs.filter(x => x.path === state.activeTabsName);
                break;
            case 'right' :
                if (state.activeTabsName === '/system/index') {
                    return dispatch('closeByCmd', 'all');
                }
                let i1 = getIndex(state.activeTabsName, state);
                state.showTabs = state.showTabs.filter((x, i) => i <= i1);
                break;
            case 'left' :
                if (state.activeTabsName === '/system/index') {
                    return;
                }
                let i2 = getIndex(state.activeTabsName, state);
                state.showTabs = state.showTabs.filter((x, i) => i >= i2);
                break;
            default :
                break;

        }
    }
};

// mutations
const mutations = {};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}