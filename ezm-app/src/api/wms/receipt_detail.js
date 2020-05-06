import request from '@/utils/request.util';

export const receiptDetailGetList = (params) => {
    return request({
        url: '/wms/receipt-detail/list',
        method: 'get',
        params,
    })
};

export const receiptDetailGet = (params) => {
    return request({
        url: '/wms/receipt-detail/get',
        method: 'get',
        params,
    })
};

export const receiptDetailSave = (data) => {
    return request({
        url: '/wms/receipt-detail/save',
        method: 'post',
        data,
    })
};
export const receiptDetailUpdateList = (data) => {
    return request({
        url: '/wms/receipt-detail/updateList',
        method: 'post',
        data,
    })
};

export const receiptDetailRemove = (data) => {
    return request({
        url: '/wms/receipt-detail/remove',
        method: 'post',
        data,
    })
};

export const receiptImportExcel = (data) => {
    return request({
        url: '/wms/receipt-detail/importExcel',
        method: 'post',
        data,
    })
};
