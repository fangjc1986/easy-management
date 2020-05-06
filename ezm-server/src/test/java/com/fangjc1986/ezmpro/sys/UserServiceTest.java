package com.fangjc1986.ezmpro.sys;

import com.fangjc1986.ezmpro.support.BaseTest;
import com.fangjc1986.ezmpro.sys.service.impl.UserServiceImpl;
import com.fangjc1986.support.service.TokenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceTest extends BaseTest {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void createToken(){

        System.out.println( userService.list());

        //User user = userService.getById(1L);
        //System.out.println(user);
        //String tokenString = tokenService.createTokenString(user);
        //System.out.println(tokenString);
    }


}
