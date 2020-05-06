package com.fangjc1986.support.helper;

import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.util.BeanUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Data
public class SystemHelper {

    @Autowired
    private HttpServletRequest request;

    /**
     * 快速获取当前登录用户
     *
     * @return
     */
    public static User loginUser() {
        HttpServletRequest request = BeanUtil.getBean(SystemHelper.class).getRequest();
        if (request.getAttribute(User.LOGIN_USER) == null) throw new ServiceException("用户未登录！", R.SIGN_ERROR);
        return (User) request.getAttribute(User.LOGIN_USER);
    }

    /**
     * 用户不存在则返回空
     * @return
     */
    public static User loginUserOrNull() {
        HttpServletRequest request = BeanUtil.getBean(SystemHelper.class).getRequest();
        if (request.getAttribute(User.LOGIN_USER) == null) return null;
        return (User) request.getAttribute(User.LOGIN_USER);
    }

    /**
     * 快速获取当前登录用户名
     *
     * @return
     */
    public static String loginUserName() {
        return loginUser().getUserName();
    }

    /**
     * 快速获取登录用户id
     *
     * @return
     */
    public static Long loginUserId() {
        return loginUser().getId();
    }

    /**
     * 快捷获取 tenantId
     *
     * @return
     */
    public static Long tenantId() {
        HttpServletRequest request = BeanUtil.getBean(SystemHelper.class).getRequest();
        if (request.getAttribute("tenant_id") == null) throw new ServiceException("租户号不存在！", R.SIGN_ERROR);
        return (Long) request.getAttribute("tenant_id");
    }

}
