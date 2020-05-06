import request from '@/utils/request.util';

export const industryUserGetList = (params) => {
    return request({
        url: '/cfg/industry-user/list',
        method: 'get',
        params,
    })
};

export const industryUserGet = (params) => {
    return request({
        url: '/cfg/industry-user/get',
        method: 'get',
        params,
    })
};

export const industryUserSave = (data) => {
    return request({
        url: '/cfg/industry-user/save',
        method: 'post',
        data,
    })
};
export const industryUserUpdateList = (data) => {
    return request({
        url: '/cfg/industry-user/updateList',
        method: 'post',
        data,
    })
};

export const industryUserRemove = (data) => {
    return request({
        url: '/cfg/industry-user/remove',
        method: 'post',
        data,
    })
};