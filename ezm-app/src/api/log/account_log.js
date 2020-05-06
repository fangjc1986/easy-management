import request from '@/utils/request.util';

export const accountLogGetList = (params) => {
    return request({
        url: '/sys/account-log/list',
        method: 'get',
        params,
    })
};

export const accountLogGet = (params) => {
    return request({
        url: '/sys/account-log/get',
        method: 'get',
        params,
    })
};

export const accountLogSave = (data) => {
    return request({
        url: '/sys/account-log/save',
        method: 'post',
        data,
    })
};
export const accountLogUpdateList = (data) => {
    return request({
        url: '/sys/account-log/updateList',
        method: 'post',
        data,
    })
};

export const accountLogRemove = (data) => {
    return request({
        url: '/sys/account-log/remove',
        method: 'post',
        data,
    })
};