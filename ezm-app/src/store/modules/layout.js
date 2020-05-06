import {cookiesUtil} from "@/utils/common.util";

const state = {
    leftShrink: false,
    tableDefaultHeight: 500,
    tenantId: 0,
    dialogZIndex: 500,
    bodyPosition: {
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
    }
};

// getters
const getters = {};

// actions
const actions = {
    reload({state}) {
        state.leftShrink = cookiesUtil.get('layout_leftShrink') || false;
        state.tableDefaultHeight = cookiesUtil.get('layout_tableDefaultHeight') || 500;
        state.tenantId = cookiesUtil.get('layout_tenantId') || 0;
    },
    setTenantId({state}, tid) {
        state.tenantId = tid;
        cookiesUtil.set('layout_tenantId', tid);
    },
    setLeftShrink({state}, status) {
        state.leftShrink = status;
        cookiesUtil.set('layout_leftShrink', status);
    },
    setTableDefaultHeight({state}, height) {
        state.tableDefaultHeight = height;
        cookiesUtil.set('layout_tableDefaultHeight', height);
    },
    incDialogZIndex({state}) {
        state.dialogZIndex++;
    },
    setBodyPosition({state}, pos) {
        state.bodyPosition = pos;
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