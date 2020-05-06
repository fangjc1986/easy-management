import request from '@/utils/request.util';

export const dictDetailGetList = (params) => {
    return request({
        url: '/sys/dict-detail/list',
        method: 'get',
        params,
    })
};

export const dictDetailGet = (params) => {
    return request({
        url: '/sys/dict-detail/get',
        method: 'get',
        params,
    })
};

export const dictDetailSave = (data) => {
    return request({
        url: '/sys/dict-detail/save',
        method: 'post',
        data,
    })
};
export const dictDetailUpdateList = (data) => {
    return request({
        url: '/sys/dict-detail/updateList',
        method: 'post',
        data,
    })
};

export const dictDetailRemove = (data) => {
    return request({
        url: '/sys/dict-detail/remove',
        method: 'post',
        data,
    })
};


export const getDictToOptionsByCode = (code, needCodePrefix = true) => {
    return dictDetailGetList({
        size: 999,
        dictCode: code,
    }).then(resp => {
        return resp.records.map(x => {
            x.label = (needCodePrefix ? (x.code + ' - ') : '') + x.name;
            x.value = x.code;
            return x;
        });
    })
};