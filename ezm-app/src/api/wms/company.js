import request from '@/utils/request.util';

export const companyGetList = (params) => {
    return request({
        url: '/wms/company/list',
        method: 'get',
        params,
    })
};

export const companyGet = (params) => {
    return request({
        url: '/wms/company/get',
        method: 'get',
        params,
    })
};

export const companySave = (data) => {
    return request({
        url: '/wms/company/save',
        method: 'post',
        data,
    })
};
export const companyUpdateList = (data) => {
    return request({
        url: '/wms/company/updateList',
        method: 'post',
        data,
    })
};

export const companyRemove = (data) => {
    return request({
        url: '/wms/company/remove',
        method: 'post',
        data,
    })
};