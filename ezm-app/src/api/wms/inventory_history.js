import request from '@/utils/request.util';

export const inventoryHistoryGetList = (params) => {
    return request({
        url: '/wms/inventory-history/list',
        method: 'get',
        params,
    })
};

export const inventoryHistoryGet = (params) => {
    return request({
        url: '/wms/inventory-history/get',
        method: 'get',
        params,
    })
};

export const inventoryHistorySave = (data) => {
    return request({
        url: '/wms/inventory-history/save',
        method: 'post',
        data,
    })
};
export const inventoryHistoryUpdateList = (data) => {
    return request({
        url: '/wms/inventory-history/updateList',
        method: 'post',
        data,
    })
};

export const inventoryHistoryRemove = (data) => {
    return request({
        url: '/wms/inventory-history/remove',
        method: 'post',
        data,
    })
};