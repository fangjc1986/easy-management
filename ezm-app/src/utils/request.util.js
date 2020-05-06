import axios from "axios";
import {cookiesUtil, DialogUtil, urlEncode} from "@/utils/common.util";
import {ResponseErrorBack} from "@/utils/response.error.util";
import setting from "@/setting";


axios.defaults.timeout = 120 * 1000;
axios.defaults.baseURL;


// 添加一个请求拦截器
axios.interceptors.request.use(function (config) {
    // 避免 IE 缓存统一接口地址下的数据
    config.url += "?" + new Date().getTime();
    config.baseURL = setting.serverBaseUrl;
    config.headers['token'] = cookiesUtil.get("token");
    config.headers['tenant_id'] = cookiesUtil.get("tenant_id");
    if (config.headers['serialize']) {
        config.data = urlEncode(config.data);
    }
    console.log({
        to: config.url,
        method: config.method,
        data: config.data,
        params: config.params,
        config
    });
    return config;
}, function (error) {
    return Promise.reject(error);
});

// 添加一个响应拦截器
axios.interceptors.response.use(function (response) {
    // Do something with response data
    let resp = response.data;
    console[resp.code === 200 ? 'log' : 'error']({
        from: response.config.url,
        method: response.config.method,
        response: resp,
        detail: response,
    });
    try {
        ResponseErrorBack['code' + resp.code](resp, response);
        return Promise.reject(resp);
    } catch (ignore) {
    }
    return Promise.resolve(resp.data);
}, function (error) {
    // Do something with response error
    if (typeof error !== 'object') {
        DialogUtil.toast(error, 'error');
    } else {
        DialogUtil.toast(JSON.stringify(error), 'error');
    }
    console.error(error);
    return Promise.reject(error);
});

export default axios;

