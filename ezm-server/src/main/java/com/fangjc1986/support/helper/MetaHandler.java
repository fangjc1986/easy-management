package com.fangjc1986.support.helper;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fangjc1986.ezmpro.sys.model.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        User user = SystemHelper.loginUserOrNull();
        if (user == null) return;
        this.setFieldValByName("createUser", user.getUserName(), metaObject);
        this.setFieldValByName("updateUser", user.getUserName(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        User user = SystemHelper.loginUserOrNull();
        if (user == null) return;
        this.setFieldValByName("updateUser", user.getUserName(), metaObject);
    }
}
