import request from '@/utils/request.util';

export const transferDetailGetList = (params) => {
    return request({
        url: '/wms/transfer-detail/list',
        method: 'get',
        params,
    })
};

export const transferDetailGet = (params) => {
    return request({
        url: '/wms/transfer-detail/get',
        method: 'get',
        params,
    })
};

export const transferDetailSave = (data) => {
    return request({
        url: '/wms/transfer-detail/save',
        method: 'post',
        data,
    })
};
export const transferDetailUpdateList = (data) => {
    return request({
        url: '/wms/transfer-detail/updateList',
        method: 'post',
        data,
    })
};

export const transferDetailRemove = (data) => {
    return request({
        url: '/wms/transfer-detail/remove',
        method: 'post',
        data,
    })
};