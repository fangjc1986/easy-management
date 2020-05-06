import {DialogUtil} from "@/utils/common.util";
import router from "@/router";

export const ResponseErrorBack = {
    /**
     * 文本错误
     * @param resp 返回内容
     * @param response  axios 的response对象
     */
    code100(resp, response) {
        if( resp.message === 'failure' ) resp.message = "操作失败！";
        DialogUtil.toast(resp.message, 'error');
    },
    /**
     * token非法
     * @param resp 返回内容
     * @param response  axios 的response对象
     */
    code110(resp, response) {
        if (router.app.$route.path === '/') return;
        router.push({path: '/'})
    },
    /**
     * 权限不足
     * @param resp 返回内容
     * @param response  axios 的response对象
     */
    code111(resp, response) {
        DialogUtil.toast("您的权限不足，请联系管理员！", 'error');
    },
    /**
     * 验证错误
     * @param resp 返回内容
     * @param response  axios 的response对象
     */
    code120(resp, response) {
        let str = [];
        Object.keys(resp.data).forEach(k => {
            str.push(resp.data[k]);
        });
        return DialogUtil.toast(str.join("</br>"), 'error');
    },
};