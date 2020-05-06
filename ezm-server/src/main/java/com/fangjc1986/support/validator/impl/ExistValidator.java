package com.fangjc1986.support.validator.impl;


import com.fangjc1986.ezmpro.common.mapper.BaseValidateMapper;
import com.fangjc1986.support.util.BeanUtil;
import com.fangjc1986.support.validator.Exist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistValidator implements ConstraintValidator<Exist, Object> {

    private Exist exist;

    @Override
    public void initialize(Exist exist) {
        this.exist = exist;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String where = exist.field() + " = '" + o.toString() + "'";
        String table = exist.table();
        BaseValidateMapper baseValidateMapper = BeanUtil.getBean(BaseValidateMapper.class);
        Integer num = baseValidateMapper.exist(table, where);
        return exist.isExist() ? num > 0 : num < 1;
    }

}