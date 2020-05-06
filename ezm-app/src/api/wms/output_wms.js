import request from '@/utils/request.util';

export const outputWmsGetList = (params) => {
    return request({
        url: '/wms/output/list',
        method: 'get',
        params,
    })
};

export const outputWmsGet = (params) => {
    return request({
        url: '/wms/output/get',
        method: 'get',
        params,
    })
};

export const outputWmsSave = (data) => {
    return request({
        url: '/wms/output/save',
        method: 'post',
        data,
    })
};
export const outputWmsUpdateList = (data) => {
    return request({
        url: '/wms/output/updateList',
        method: 'post',
        data,
    })
};

export const outputWmsRemove = (data) => {
    return request({
        url: '/wms/output/remove',
        method: 'post',
        data,
    })
};


export const outputWmsSubmit = (data) => {
    return request({
        url: '/wms/output/submit',
        method: 'post',
        data,
    })
};

export const outputWmsApprove = (data) => {
    return request({
        url: '/wms/output/approve',
        method: 'post',
        data,
    })
};

export const outputWmsFinish = (data) => {
    return request({
        url: '/wms/output/finish',
        method: 'post',
        data,
    })
};