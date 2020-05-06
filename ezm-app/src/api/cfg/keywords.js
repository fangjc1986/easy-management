import request from '@/utils/request.util';

export const keywordsGetList = (params) => {
    return request({
        url: '/cfg/keywords/list',
        method: 'get',
        params,
    })
};

export const keywordsGet = (params) => {
    return request({
        url: '/cfg/keywords/get',
        method: 'get',
        params,
    })
};

export const keywordsSave = (data) => {
    return request({
        url: '/cfg/keywords/save',
        method: 'post',
        data,
    })
};
export const keywordsUpdateList = (data) => {
    return request({
        url: '/cfg/keywords/updateList',
        method: 'post',
        data,
    })
};

export const keywordsRemove = (data) => {
    return request({
        url: '/cfg/keywords/remove',
        method: 'post',
        data,
    })
};