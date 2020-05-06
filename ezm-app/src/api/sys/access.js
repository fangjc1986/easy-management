import request from '@/utils/request.util';


export const accessSignOut = (data) => {
    return request({
        url: '/access/account/signOut',
        method: 'post',
        data,
    })
};

export const accessGetMyInfo = (params) => {
    return request({
        url: '/access/account/getMyInfo',
        method: 'get',
        params,
    })
};

export const accessAuthList = (params) => {
    return request({
        url: '/access/account/authList',
        method: 'get',
        params,
    })
};

export const accessChangePass = (data) => {
    return request({
        url: '/access/account/password',
        method: 'post',
        data,
    })
};

export const accessTenantList = (params) => {
    return request({
        url: '/access/account/tenantList',
        method: 'get',
        params,
    })
};
