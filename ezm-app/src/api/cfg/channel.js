import request from '@/utils/request.util';

export const channelGetList = (params) => {
    return request({
        url: '/cfg/channel/list',
        method: 'get',
        params,
    })
};

export const channelGet = (params) => {
    return request({
        url: '/cfg/channel/get',
        method: 'get',
        params,
    })
};

export const channelSave = (data) => {
    return request({
        url: '/cfg/channel/save',
        method: 'post',
        data,
    })
};
export const channelUpdateList = (data) => {
    return request({
        url: '/cfg/channel/updateList',
        method: 'post',
        data,
    })
};

export const channelRemove = (data) => {
    return request({
        url: '/cfg/channel/remove',
        method: 'post',
        data,
    })
};