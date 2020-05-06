import request from '@/utils/request.util';

export const inventoryGetList = (params) => {
    return request({
        url: '/wms/inventory/list',
        method: 'get',
        params,
    })
};

export const inventoryGet = (params) => {
    return request({
        url: '/wms/inventory/get',
        method: 'get',
        params,
    })
};

export const inventorySave = (data) => {
    return request({
        url: '/wms/inventory/save',
        method: 'post',
        data,
    })
};
export const inventoryUpdateList = (data) => {
    return request({
        url: '/wms/inventory/updateList',
        method: 'post',
        data,
    })
};

export const inventoryRemove = (data) => {
    return request({
        url: '/wms/inventory/remove',
        method: 'post',
        data,
    })
};