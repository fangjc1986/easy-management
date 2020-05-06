import {mockResponseIntercept} from "@/utils/common.util";

mockResponseIntercept("/sys/auth", [
    {
        id: '1',
        path: '/system/index',
        name: '首页',
        icon: 'icon-folder-open',
        pid: '0',
    },
    {
        id: '2',
        path: '/sys',
        name: '基础配置',
        icon: 'icon-folder-open',
        pid: '0'
    },
    {
        id: '3',
        path: '/sys/config',
        name: '参数配置',
        icon: 'icon-folder-open',
        pid: '2'
    },
    {
        id: '4',
        path: '/sys/dict',
        name: '字典管理',
        icon: 'icon-folder-open',
        pid: '2',
    }
], 1, false);