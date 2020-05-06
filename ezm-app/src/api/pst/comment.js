import request from '@/utils/request.util';

export const commentGetList = (params) => {
    return request({
        url: '/pst/comment/list',
        method: 'get',
        params,
    })
};

export const commentGet = (params) => {
    return request({
        url: '/pst/comment/get',
        method: 'get',
        params,
    })
};

export const commentSave = (data) => {
    return request({
        url: '/pst/comment/save',
        method: 'post',
        data,
    })
};
export const commentUpdateList = (data) => {
    return request({
        url: '/pst/comment/updateList',
        method: 'post',
        data,
    })
};

export const commentRemove = (data) => {
    return request({
        url: '/pst/comment/remove',
        method: 'post',
        data,
    })
};

export const commentSubmit = (data) => {
    return request({
        url: '/pst/comment/submit',
        method: 'post',
        data,
    })
};

export const commentApprove = (data) => {
    return request({
        url: '/pst/comment/approve',
        method: 'post',
        data,
    })
};

export const commentBindIndustry = (data) => {
    return request({
        url: '/pst/comment/bindIndustry',
        method: 'post',
        data,
    })
};

export const commentBindIndustryUser = (data) => {
    return request({
        url: '/pst/comment/bindIndustryUser',
        method: 'post',
        data,
    })
};

export const commentExcelImport = (data) => {
    return request({
        url: '/pst/comment/excelImport',
        method: 'post',
        headers: {
            'Content-Type':'multipart/form-data'
        },
        data,
    })
};
