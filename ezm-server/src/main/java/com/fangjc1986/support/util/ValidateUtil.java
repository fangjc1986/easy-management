package com.fangjc1986.support.util;

import com.fangjc1986.support.exception.ServiceException;

import javax.validation.*;
import java.util.List;
import java.util.Set;

public class ValidateUtil {

    private static Validator createValidator() {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        Validator validator = factory.getValidator();
        factory.close();
        return validator;
    }

    /**
     * Controller层验证List类型的参数
     *
     * @return
     */
    public static <T> void validate(List<T> list) {
        Validator validator = createValidator();
        for (T dto : list) {
            Set<ConstraintViolation<T>> violations = validator.validate(dto);
            if (violations.size() != 0) {
                for (ConstraintViolation<T> violation : violations) {
                    throw new ServiceException(violation.getMessage());
                }
            }
        }
    }
}
