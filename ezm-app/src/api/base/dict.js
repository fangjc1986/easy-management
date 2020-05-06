import request from '@/utils/request.util';

export const dictGetList = (params) => {
    return request({
        url: '/sys/dict/list',
        method: 'get',
        params,
    })
};

export const dictGet = (params) => {
    return request({
        url: '/sys/dict/get',
        method: 'get',
        params,
    })
};

export const dictSave = (data) => {
    return request({
        url: '/sys/dict/save',
        method: 'post',
        data,
    })
};
export const dictUpdateList = (data) => {
    return request({
        url: '/sys/dict/updateList',
        method: 'post',
        data,
    })
};

export const dictRemove = (data) => {
    return request({
        url: '/sys/dict/remove',
        method: 'post',
        data,
    })
};