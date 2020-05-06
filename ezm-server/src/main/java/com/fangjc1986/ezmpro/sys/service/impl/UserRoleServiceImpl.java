package com.fangjc1986.ezmpro.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.model.UserRole;
import com.fangjc1986.ezmpro.sys.mapper.UserRoleMapper;
import com.fangjc1986.ezmpro.sys.service.IUserRoleService;
import com.fangjc1986.support.service.BaseService;
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
public class UserRoleServiceImpl extends BaseService<UserRoleMapper, UserRole> implements IUserRoleService {

    /**
     * 重置用户角色
     *
     * @param user
     * @param roleIds
     * @param tenantId
     * @return
     */
    public boolean resetUserRole(User user, Collection<Long> roleIds, Long tenantId) {
        remove(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            userRoles.add(new UserRole(user.getId(), roleId, tenantId));
        }
        return saveBatch(userRoles);
    }

    /**
     * 重置用户角色
     * @param user
     * @param userRoles
     * @return
     */
    public boolean resetUserRole(User user, Collection<UserRole> userRoles) {
        remove(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        return saveBatch(userRoles);
    }

    /**
     * 通过用户 角色列表 和 租户 id 生成 userTenant 列表
     *
     * @param user
     * @param roleIds
     * @param tenantId
     * @return
     */
    public List<UserRole> getUserRoleListByURT(User user, Collection<Long> roleIds, Long tenantId) {
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            userRoles.add(new UserRole(user.getId(), roleId, tenantId));
        }
        return userRoles;
    }

}
