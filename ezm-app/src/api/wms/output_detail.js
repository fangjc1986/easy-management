import request from '@/utils/request.util';

export const outputDetailGetList = (params) => {
    return request({
        url: '/wms/output-detail/list',
        method: 'get',
        params,
    })
};

export const outputDetailGet = (params) => {
    return request({
        url: '/wms/output-detail/get',
        method: 'get',
        params,
    })
};

export const outputDetailSave = (data) => {
    return request({
        url: '/wms/output-detail/save',
        method: 'post',
        data,
    })
};
export const outputDetailUpdateList = (data) => {
    return request({
        url: '/wms/output-detail/updateList',
        method: 'post',
        data,
    })
};

export const outputDetailRemove = (data) => {
    return request({
        url: '/wms/output-detail/remove',
        method: 'post',
        data,
    })
};