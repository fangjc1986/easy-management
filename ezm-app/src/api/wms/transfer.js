import request from '@/utils/request.util';

export const transferGetList = (params) => {
    return request({
        url: '/wms/transfer/list',
        method: 'get',
        params,
    })
};

export const transferGet = (params) => {
    return request({
        url: '/wms/transfer/get',
        method: 'get',
        params,
    })
};

export const transferSave = (data) => {
    return request({
        url: '/wms/transfer/save',
        method: 'post',
        data,
    })
};
export const transferUpdateList = (data) => {
    return request({
        url: '/wms/transfer/updateList',
        method: 'post',
        data,
    })
};

export const transferRemove = (data) => {
    return request({
        url: '/wms/transfer/remove',
        method: 'post',
        data,
    })
};

export const transferSubmit = (data) => {
    return request({
        url: '/wms/transfer/submit',
        method: 'post',
        data,
    })
};

export const transferApprove = (data) => {
    return request({
        url: '/wms/transfer/approve',
        method: 'post',
        data,
    })
};

export const transferFinish = (data) => {
    return request({
        url: '/wms/transfer/finish',
        method: 'post',
        data,
    })
};