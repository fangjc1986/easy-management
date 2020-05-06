import request from '@/utils/request.util';

export const goodsGetList = (params) => {
    return request({
        url: '/wms/goods/list',
        method: 'get',
        params,
    })
};

export const goodsGet = (params) => {
    return request({
        url: '/wms/goods/get',
        method: 'get',
        params,
    })
};

export const goodsSave = (data) => {
    return request({
        url: '/wms/goods/save',
        method: 'post',
        data,
    })
};
export const goodsUpdateList = (data) => {
    return request({
        url: '/wms/goods/updateList',
        method: 'post',
        data,
    })
};

export const goodsRemove = (data) => {
    return request({
        url: '/wms/goods/remove',
        method: 'post',
        data,
    })
};