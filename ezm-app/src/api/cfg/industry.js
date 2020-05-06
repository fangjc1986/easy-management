import request from '@/utils/request.util';

export const industryGetList = (params) => {
    return request({
        url: '/cfg/industry/list',
        method: 'get',
        params,
    })
};

export const industryGet = (params) => {
    return request({
        url: '/cfg/industry/get',
        method: 'get',
        params,
    })
};

export const industrySave = (data) => {
    return request({
        url: '/cfg/industry/save',
        method: 'post',
        data,
    })
};
export const industryUpdateList = (data) => {
    return request({
        url: '/cfg/industry/updateList',
        method: 'post',
        data,
    })
};

export const industryRemove = (data) => {
    return request({
        url: '/cfg/industry/remove',
        method: 'post',
        data,
    })
};