import request from '@/utils/request.util';

export const postCatGetList = (params) => {
    return request({
        url: '/pst/post-cat/list',
        method: 'get',
        params,
    })
};

export const postCatGet = (params) => {
    return request({
        url: '/pst/post-cat/get',
        method: 'get',
        params,
    })
};

export const postCatSave = (data) => {
    return request({
        url: '/pst/post-cat/save',
        method: 'post',
        data,
    })
};
export const postCatUpdateList = (data) => {
    return request({
        url: '/pst/post-cat/updateList',
        method: 'post',
        data,
    })
};

export const postCatRemove = (data) => {
    return request({
        url: '/pst/post-cat/remove',
        method: 'post',
        data,
    })
};