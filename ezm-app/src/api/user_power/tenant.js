import request from '@/utils/request.util';

export const tenantGetList = (params) => {
    return request({
        url: '/sys/tenant/list',
        method: 'get',
        params,
    })
};

export const tenantGet = (params) => {
    return request({
        url: '/sys/tenant/get',
        method: 'get',
        params,
    })
};

export const tenantSave = (data) => {
    return request({
        url: '/sys/tenant/save',
        method: 'post',
        data,
    })
};
export const tenantUpdateList = (data) => {
    return request({
        url: '/sys/tenant/updateList',
        method: 'post',
        data,
    })
};

export const tenantRemove = (data) => {
    return request({
        url: '/sys/tenant/remove',
        method: 'post',
        data,
    })
};