package com.fangjc1986.support.entity;


import com.fangjc1986.ezmpro.sys.model.User;
import lombok.Data;

@Data
public class Token {

    public static final Long EXPIRE_TIME = 7200L;

    private Long id;
    private String userName;
    private Long startTime;

    public Token() {
    }

    public Token(User user) {
        id = user.getId();
        userName = user.getUserName();
        startTime = System.currentTimeMillis();
    }

}
