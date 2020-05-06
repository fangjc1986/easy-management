import {routerImport} from "@/utils/common.util";

let route = {
    routes: [
        {
            path: '/back',
            redirect: '/index',
            component: routerImport('system/layout/layout'),
            children:[]
        },
        {
            path: '/',
            component: routerImport('system/login/login'),
            meta: {
                title: '用户登录'
            }
        },
        {
            path: '*',
            component: routerImport('system/loading/loading'),
            meta: {
                title: "初始化"
            }
        },
    ]
};

export default route;