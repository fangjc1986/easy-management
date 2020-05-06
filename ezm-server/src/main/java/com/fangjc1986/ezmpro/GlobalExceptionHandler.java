package com.fangjc1986.ezmpro;

import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<Map<String, String>> myExceptionErrorHandler(MethodArgumentNotValidException ex) throws Exception {
        Map<String, String> rMap = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            rMap.put(error.getField(), error.getDefaultMessage());
        }
        ex.printStackTrace();
        return R.data(rMap).setCode(120);
    }


    @ExceptionHandler(value = ServiceException.class)
    public R<String> authException(ServiceException ex) throws Exception {
        ex.printStackTrace();
        return new R<String>().setMessage(ex.getMessage()).setCode(ex.getCode());
    }

    @ExceptionHandler(value = MyBatisSystemException.class)
    public R<String> authException(MyBatisSystemException ex) {
        ex.printStackTrace();
        return new R<String>().setMessage(ex.getMessage()).setCode(100);
    }


}