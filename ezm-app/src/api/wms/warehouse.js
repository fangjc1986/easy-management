import request from '@/utils/request.util';

export const warehouseGetList = (params) => {
    return request({
        url: '/wms/warehouse/list',
        method: 'get',
        params,
    })
};

export const warehouseGet = (params) => {
    return request({
        url: '/wms/warehouse/get',
        method: 'get',
        params,
    })
};

export const warehouseSave = (data) => {
    return request({
        url: '/wms/warehouse/save',
        method: 'post',
        data,
    })
};
export const warehouseUpdateList = (data) => {
    return request({
        url: '/wms/warehouse/updateList',
        method: 'post',
        data,
    })
};

export const warehouseRemove = (data) => {
    return request({
        url: '/wms/warehouse/remove',
        method: 'post',
        data,
    })
};