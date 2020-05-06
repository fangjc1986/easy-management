package com.fangjc1986.ezmpro.sys.service.impl;

import com.fangjc1986.ezmpro.sys.model.AccountLog;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.support.service.BaseMongoService;
import com.fangjc1986.support.util.IPUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号登录登出日志 服务实现类
 * </p>
 *
 * @author EricFang
 * @since 2020-04-10
 */
@Service
public class AccountLogServiceImpl extends BaseMongoService<AccountLog> {

    @Override
    public void setUpdateFields(Update update, AccountLog doc) {

    }

    /**
     * 根据用户和类型保存
     *
     * @param user
     * @param type
     */
    public void save(User user, Integer type) {
        AccountLog accountLog = new AccountLog();
        accountLog.setUserId(user.getId());
        accountLog.setUserName(user.getUserName());
        accountLog.setType(type);
        accountLog.setIp(IPUtils.getRemoteIP(request));
        accountLog.setSuccess(1);
        save(accountLog);
    }

}
