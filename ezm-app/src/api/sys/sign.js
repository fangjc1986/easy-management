import request from '@/utils/request.util';


export const signInRequest = (data) => {
    return request({
        url: '/common/sign/in',
        method: 'post',
        data,
    })
};