package com.fangjc1986.support.service;

import com.alibaba.fastjson.JSONObject;
import com.fangjc1986.ezmpro.sys.model.Config;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.service.impl.ConfigServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.UserServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.UserTenantServiceImpl;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.entity.Token;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ConfigServiceImpl configService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserTenantServiceImpl userTenantService;

    /**
     * 根据用户生成 token 字符串
     *
     * @param user
     * @return
     */
    public String createTokenString(User user) {
        Token token = new Token(user);
        return AESUtil.encrypt(JSONObject.toJSONString(token));
    }

    /**
     * 将 token 字符串转换为 token
     *
     * @param tokenString
     * @return
     */
    public Token stringToToken(String tokenString) {
        try {
            String tokenJson = AESUtil.decrypt(tokenString);
            return JSONObject.parseObject(tokenJson, Token.class);
        } catch (Exception e) {
            throw new ServiceException("token 解密失败！", R.SIGN_ERROR);
        }

    }

    /**
     * 将 token 字符串解密后查到用户并返回
     * 验证 token 是否过期
     *
     * @param tokenString
     * @return
     */
    public User stringToUser(String tokenString) {
        Token token = stringToToken(tokenString);
        long expireTime = configService.get(Config.TOKEN_EXPIRE, Token.EXPIRE_TIME) * 1000;
        if (token.getStartTime() + expireTime < System.currentTimeMillis()) {
            throw new ServiceException("token 已过期！", R.SIGN_ERROR);
        }
        User user = userService.getById(token.getId());
        if (user == null) throw new ServiceException("用户不存在！", R.SIGN_ERROR);
        if (!user.getActivated().equals(1)) throw new ServiceException("用户尚未激活！", R.SIGN_ERROR);
        if (configService.get(Config.SSO, 0) == 1 && !user.getToken().equals(tokenString)) {
            throw new ServiceException("异地登录！", R.SIGN_ERROR);
        }
        Long tenantId = null;
        if (request.getHeader("tenant_id") != null) {
            tenantId = Long.valueOf(request.getHeader("tenant_id"));
        } else {
            tenantId = userTenantService.getDefaultTenantId(user);
        }
        request.setAttribute("tenant_id", tenantId);
        request.setAttribute(User.LOGIN_USER, user);
        return user;
    }

}
