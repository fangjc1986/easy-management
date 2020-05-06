import request from '@/utils/request.util';

export const pstStatisticWidgetQty = (params) => {
    return request({
        url: '/pst/statistic/widgetQty',
        method: 'get',
        params,
    })
};

export const pstPostQtyChart = (params) => {
    return request({
        url: '/pst/statistic/postQtyChart',
        method: 'get',
        params,
    })
};

export const pstCommentQtyChart = (params) => {
    return request({
        url: '/pst/statistic/commentQtyChart',
        method: 'get',
        params,
    })
};