import request from '@/utils/request.util';

export const receiptGetList = (params) => {
    return request({
        url: '/wms/receipt/list',
        method: 'get',
        params,
    })
};

export const receiptGet = (params) => {
    return request({
        url: '/wms/receipt/get',
        method: 'get',
        params,
    })
};

export const receiptSave = (data) => {
    return request({
        url: '/wms/receipt/save',
        method: 'post',
        data,
    })
};
export const receiptUpdateList = (data) => {
    return request({
        url: '/wms/receipt/updateList',
        method: 'post',
        data,
    })
};

export const receiptRemove = (data) => {
    return request({
        url: '/wms/receipt/remove',
        method: 'post',
        data,
    })
};

export const receiptSubmit = (data) => {
    return request({
        url: '/wms/receipt/submit',
        method: 'post',
        data,
    })
};

export const receiptApprove = (data) => {
    return request({
        url: '/wms/receipt/approve',
        method: 'post',
        data,
    })
};

export const receiptFinish = (data) => {
    return request({
        url: '/wms/receipt/finish',
        method: 'post',
        data,
    })
};