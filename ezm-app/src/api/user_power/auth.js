import request from '@/utils/request.util';

export const authGetList = (params) => {
    return request({
        url: '/sys/auth/list',
        method: 'get',
        params,
    })
};

export const authGet = (params) => {
    return request({
        url: '/sys/auth/get',
        method: 'get',
        params,
    })
};

export const authSave = (data) => {
    return request({
        url: '/sys/auth/save',
        method: 'post',
        data,
    })
};
export const authUpdateList = (data) => {
    return request({
        url: '/sys/auth/updateList',
        method: 'post',
        data,
    })
};

export const authRemove = (data) => {
    return request({
        url: '/sys/auth/remove',
        method: 'post',
        data,
    })
};

export const authAddCRUD = (data) => {
    return request({
        url: '/sys/auth/addCRUD',
        method: 'post',
        data,
    })
};