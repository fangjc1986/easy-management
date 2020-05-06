import request from '@/utils/request.util';

export const requestLogGetList = (params) => {
    return request({
        url: '/sys/request-log/list',
        method: 'get',
        params,
    })
};

export const requestLogGet = (params) => {
    return request({
        url: '/sys/request-log/get',
        method: 'get',
        params,
    })
};

export const requestLogSave = (data) => {
    return request({
        url: '/sys/request-log/save',
        method: 'post',
        data,
    })
};
export const requestLogUpdateList = (data) => {
    return request({
        url: '/sys/request-log/updateList',
        method: 'post',
        data,
    })
};

export const requestLogRemove = (data) => {
    return request({
        url: '/sys/request-log/remove',
        method: 'post',
        data,
    })
};