import request from '@/utils/request.util';

export const postGetList = (params) => {
    return request({
        url: '/pst/post/list',
        method: 'get',
        params,
    })
};

export const postGet = (params) => {
    return request({
        url: '/pst/post/get',
        method: 'get',
        params,
    })
};

export const postSave = (data) => {
    return request({
        url: '/pst/post/save',
        method: 'post',
        data,
    })
};
export const postUpdateList = (data) => {
    return request({
        url: '/pst/post/updateList',
        method: 'post',
        data,
    })
};

export const postRemove = (data) => {
    return request({
        url: '/pst/post/remove',
        method: 'post',
        data,
    })
};

export const postSubmit = (data) => {
    return request({
        url: '/pst/post/submit',
        method: 'post',
        data,
    })
};

export const postApprove = (data) => {
    return request({
        url: '/pst/post/approve',
        method: 'post',
        data,
    })
};

export const postBindIndustry = (data) => {
    return request({
        url: '/pst/post/bindIndustry',
        method: 'post',
        data,
    })
};

export const postExcelImport = (data) => {
    return request({
        url: '/pst/post/excelImport',
        method: 'post',
        headers: {
            'Content-Type':'multipart/form-data'
        },
        data,
    })
};
