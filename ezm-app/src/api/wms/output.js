import request from '@/utils/request.util';

export const outputGetList = (params) => {
    return request({
        url: '/wms/output/list',
        method: 'get',
        params,
    })
};

export const outputGet = (params) => {
    return request({
        url: '/wms/output/get',
        method: 'get',
        params,
    })
};

export const outputSave = (data) => {
    return request({
        url: '/wms/output/save',
        method: 'post',
        data,
    })
};
export const outputUpdateList = (data) => {
    return request({
        url: '/wms/output/updateList',
        method: 'post',
        data,
    })
};

export const outputRemove = (data) => {
    return request({
        url: '/wms/output/remove',
        method: 'post',
        data,
    })
};