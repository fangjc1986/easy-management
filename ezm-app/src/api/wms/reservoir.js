import request from '@/utils/request.util';

export const reservoirGetList = (params) => {
    return request({
        url: '/wms/reservoir/list',
        method: 'get',
        params,
    })
};

export const reservoirGet = (params) => {
    return request({
        url: '/wms/reservoir/get',
        method: 'get',
        params,
    })
};

export const reservoirSave = (data) => {
    return request({
        url: '/wms/reservoir/save',
        method: 'post',
        data,
    })
};
export const reservoirUpdateList = (data) => {
    return request({
        url: '/wms/reservoir/updateList',
        method: 'post',
        data,
    })
};

export const reservoirRemove = (data) => {
    return request({
        url: '/wms/reservoir/remove',
        method: 'post',
        data,
    })
};