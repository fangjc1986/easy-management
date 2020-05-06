package com.fangjc1986.support.validator;

import com.fangjc1986.support.validator.impl.ExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exist {
    String message() default "存在";

    String table();

    String field();

    boolean isExist() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}