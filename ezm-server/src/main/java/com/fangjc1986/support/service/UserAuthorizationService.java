package com.fangjc1986.support.service;

import com.fangjc1986.ezmpro.sys.model.Auth;
import com.fangjc1986.ezmpro.sys.model.Config;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.service.impl.AuthServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.ConfigServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.UserTenantServiceImpl;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserAuthorizationService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ConfigServiceImpl configService;
    @Autowired
    private UserTenantServiceImpl userTenantService;
    @Autowired
    private AuthServiceImpl authService;

    /**
     * 验证权限
     *
     * @param user
     * @return
     */
    public boolean validateAuth(User user) {
        if (user.getActivated().equals(0)) throw new ServiceException("用户未激活！", R.SIGN_ERROR);
        if (user.getRoot().equals(1)) return true;
        if (request.getMethod().equalsIgnoreCase("get") &&
            configService.get(Config.GET_TRUST, 1) == 1
        ) return true;
        Auth auth = authService.getBaseMapper().getUserAuthorization(user.getId(), (Long) request.getAttribute("tenant_id"), request.getRequestURI());
        if (auth == null) throw new ServiceException("权限不足，请联系管理员！", R.AUTH_ERROR);
        return true;
    }


}
