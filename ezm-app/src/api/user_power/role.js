import request from '@/utils/request.util';

export const roleGetList = (params) => {
    return request({
        url: '/sys/role/list',
        method: 'get',
        params,
    })
};

export const roleGet = (params) => {
    return request({
        url: '/sys/role/get',
        method: 'get',
        params,
    })
};

export const roleSave = (data) => {
    return request({
        url: '/sys/role/save',
        method: 'post',
        data,
    })
};
export const roleUpdateList = (data) => {
    return request({
        url: '/sys/role/updateList',
        method: 'post',
        data,
    })
};

export const roleRemove = (data) => {
    return request({
        url: '/sys/role/remove',
        method: 'post',
        data,
    })
};

export const roleBindAuths = (data) => {
    return request({
        url: '/sys/role/bindAuths',
        method: 'post',
        data,
    })
};

export const roleAuthList = (params) => {
    return request({
        url: '/sys/role/authList',
        method: 'get',
        params,
    })
};