package com.fangjc1986.ezmpro.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangjc1986.ezmpro.sys.mapper.UserTenantMapper;
import com.fangjc1986.ezmpro.sys.model.Tenant;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.model.UserTenant;
import com.fangjc1986.ezmpro.sys.service.IUserTenantService;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@Service
public class UserTenantServiceImpl extends BaseService<UserTenantMapper, UserTenant> implements IUserTenantService {

    @Autowired
    private TenantServiceImpl tenantService;

    /**
     * 获取默认 租户 id
     * 没有则获取第一条
     *
     * @param user
     * @return
     */
    public Long getDefaultTenantId(User user) {
        if (user.getRoot().equals(1)) return getRootsDefaultTenantId(user);
        UserTenant userTenant = getOne(
            new QueryWrapper<UserTenant>()
                .eq("user_id", user.getId())
                .eq("def", 1)
                .last("limit 1")
        );

        if (userTenant == null) {
            userTenant = getOne(
                new QueryWrapper<UserTenant>()
                    .eq("user_id", user.getId())
                    .last("limit 1")
            );
        }
        if (userTenant == null) throw new ServiceException("租户不存在！", R.AUTH_ERROR);
        return userTenant.getTenantId();
    }

    /**
     * 获取默认 租户 id
     * 没有则获取第一条
     *
     * @param user
     * @return
     */
    public Long getRootsDefaultTenantId(User user) {
        Tenant tenant = tenantService.getOne(
            new QueryWrapper<Tenant>().last("limit 1")
        );
        if (tenant == null) throw new ServiceException("请在数据库增加一条租户信息！", R.AUTH_ERROR);
        return tenant.getId();
    }

    /**
     * 重置用户租户关系
     *
     * @param user
     * @param tenantIds
     * @return
     */
    public boolean resetUserTenant(User user, Collection<Long> tenantIds) {
        remove(new QueryWrapper<UserTenant>().eq("user_id", user.getId()));
        List<UserTenant> userTenants = new ArrayList<>();
        for (Long tId : tenantIds) {
            userTenants.add(new UserTenant(user.getId(), tId));
        }
        return saveBatch(userTenants);
    }

}
