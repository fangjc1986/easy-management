package com.fangjc1986.ezmpro.common.service;

import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.support.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ImageCodeService {

    @Autowired
    private HttpServletRequest request;

    /**
     * 检查验证码是否正确
     *
     * @param signUser
     * @return
     */
    public boolean validateCode(User signUser) {
        String authCode = authCode();
        clearCode();
        return signUser.getAuthCode().equalsIgnoreCase(authCode);
    }

    /**
     * 获取当前session下的 验证码
     *
     * @return
     */
    public String authCode() {
        if (request.getSession().getAttribute("imageCode") == null) {
            throw new ServiceException("验证码不存在！");
        }
        return (String) request.getSession().getAttribute("imageCode");
    }

    /**
     * 清掉当前session中的 图片验证码
     */
    public void clearCode() {
        request.getSession().removeAttribute("imageCode");
    }
}
