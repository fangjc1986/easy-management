export const validateInteger = (rule, value, cb) => {
    if (/^[\-\+]{0,1}\d+$/.test(value)) {
        return cb();
    }
    cb(new Error("只能为是整数格式"));
};

export const validateNumber = (rule, value, cb) => {
    if (!rule.isFloat && !/^[\-\+]{0,1}\d+$/.test(value)) {
        return cb(new Error("必须为整数"))
    }
    if (value === "") return cb();
    try {
        value = value * 1;
    } catch (e) {
        return cb(new Error("必须为数字"))
    }
    if (rule.min && value < rule.min) {
        return cb(new Error(`不能小于 ${rule.min}`))
    }
    if (rule.max && value > rule.max) {
        return cb(new Error(`不能大于 ${rule.max}`))
    }
    cb();
};

export const validateRequired = (rule, value, cb) => {
    value = (value + '').trim();
    if (!value) {
        return cb(new Error("不能为空"))
    }
    cb();
};

export const vsCard = (idCard) => {
    //15位或者18位身份证号码的正则表达式
    let regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

    //如果通过该验证，说明身份证格式正确，但准确性还需计算
    if (regIdCard.test(idCard)) {
        if (idCard.length === 18) {
            let idCardWi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]; //将前17位加权因子保存在数组里
            let idCardY = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2]; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
            let idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (let i = 0; i < 17; i++) {
                idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
            }

            let idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            let idCardLast = idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod === 2) {
                if (idCardLast === "X" || idCardLast === "x") {
                    return true;
                } else {
                    return false;
                }
            } else {
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast === idCardY[idCardMod]) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    } else {
        return false;
    }
};

export const commonValidate = {
    required: {validator: validateRequired, required: true, trigger: 'change'},
    number: {validator: validateNumber, isFloat: true},
    integer: {validator: validateInteger},
    email: {
        pattern: /[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?/,
        message: '邮箱格式错误'
    },
    money: {pattern: /^[0-9]+(.[0-9]{2})?$/, message: "最多2位小数"},
    mobile: {pattern: /^1[3456789]\d{9}$/, message: '手机号格式错误'},
    userName: {pattern: /^[a-zA-Z][\da-zA-Z]*$/, message: '字母打头的字母数字组合'},
    idCard: {
        validator: (rule, value, cb) => {
            if( !value ) return cb();
            if( /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value) ){
                return cb();
            }
            if(/^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/.test(value)){
                return cb();
            }
            return cb( new Error("身份证错误！"));
        }
    }

};