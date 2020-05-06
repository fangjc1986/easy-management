import request from '@/utils/request.util';

export const transferHistoryGetList = (params) => {
    return request({
        url: '/wms/transfer-history/list',
        method: 'get',
        params,
    })
};

export const transferHistoryGet = (params) => {
    return request({
        url: '/wms/transfer-history/get',
        method: 'get',
        params,
    })
};

export const transferHistorySave = (data) => {
    return request({
        url: '/wms/transfer-history/save',
        method: 'post',
        data,
    })
};
export const transferHistoryUpdateList = (data) => {
    return request({
        url: '/wms/transfer-history/updateList',
        method: 'post',
        data,
    })
};

export const transferHistoryRemove = (data) => {
    return request({
        url: '/wms/transfer-history/remove',
        method: 'post',
        data,
    })
};