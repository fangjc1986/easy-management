import request from '@/utils/request.util';

export const wmsOutputGetList = (params) => {
    return request({
        url: '/wms/output/list',
        method: 'get',
        params,
    })
};

export const wmsOutputGet = (params) => {
    return request({
        url: '/wms/output/get',
        method: 'get',
        params,
    })
};

export const wmsOutputSave = (data) => {
    return request({
        url: '/wms/output/save',
        method: 'post',
        data,
    })
};
export const wmsOutputUpdateList = (data) => {
    return request({
        url: '/wms/output/updateList',
        method: 'post',
        data,
    })
};

export const wmsOutputRemove = (data) => {
    return request({
        url: '/wms/output/remove',
        method: 'post',
        data,
    })
};