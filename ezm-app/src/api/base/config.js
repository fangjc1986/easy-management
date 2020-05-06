import request from '@/utils/request.util';

export const configGetList = (params) => {
    return request({
        url: '/sys/config/list',
        method: 'get',
        params,
    })
};

export const configGet = (params) => {
    return request({
        url: '/sys/config/get',
        method: 'get',
        params,
    })
};

export const configSave = (data) => {
    return request({
        url: '/sys/config/save',
        method: 'post',
        data,
    })
};
export const configUpdateList = (data) => {
    return request({
        url: '/sys/config/updateList',
        method: 'post',
        data,
    })
};

export const configRemove = (data) => {
    return request({
        url: '/sys/config/remove',
        method: 'post',
        data,
    })
};