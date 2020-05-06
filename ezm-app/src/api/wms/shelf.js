import request from '@/utils/request.util';

export const shelfGetList = (params) => {
    return request({
        url: '/wms/shelf/list',
        method: 'get',
        params,
    })
};

export const shelfGet = (params) => {
    return request({
        url: '/wms/shelf/get',
        method: 'get',
        params,
    })
};

export const shelfSave = (data) => {
    return request({
        url: '/wms/shelf/save',
        method: 'post',
        data,
    })
};
export const shelfUpdateList = (data) => {
    return request({
        url: '/wms/shelf/updateList',
        method: 'post',
        data,
    })
};

export const shelfRemove = (data) => {
    return request({
        url: '/wms/shelf/remove',
        method: 'post',
        data,
    })
};