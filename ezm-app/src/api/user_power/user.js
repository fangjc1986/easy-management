import request from '@/utils/request.util';

export const userGetList = (params) => {
    return request({
        url: '/sys/user/list',
        method: 'get',
        params,
    })
};

export const userGet = (params) => {
    return request({
        url: '/sys/user/get',
        method: 'get',
        params,
    })
};

export const userSave = (data) => {
    return request({
        url: '/sys/user/save',
        method: 'post',
        data,
    })
};
export const userUpdateList = (data) => {
    return request({
        url: '/sys/user/updateList',
        method: 'post',
        data,
    })
};

export const userRemove = (data) => {
    return request({
        url: '/sys/user/remove',
        method: 'post',
        data,
    })
};

export const userUpdateURT = (data) => {
    return request({
        url: '/sys/user/updateURT',
        method: 'post',
        data,
    })
};