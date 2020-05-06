package com.fangjc1986.ezmpro.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangjc1986.ezmpro.sys.mapper.UserMapper;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.model.UserRole;
import com.fangjc1986.ezmpro.sys.model.UserTenant;
import com.fangjc1986.ezmpro.sys.service.IUserService;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.service.BaseService;
import com.fangjc1986.support.util.CommonUtil;
import com.fangjc1986.support.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@Service
public class UserServiceImpl extends BaseService<UserMapper, User> implements IUserService {

    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private UserTenantServiceImpl userTenantService;

    /**
     * 重新加密用户上传的明文密码
     *
     * @param user
     */
    public void resetPasswordSalt(User user) {
        user.setSalt(CommonUtil.getStringRandom(6));
        user.setPassword(Md5Util.getMD5(user.getPassword(), user.getSalt()));
    }

    /**
     * 验证提交表单的密码是否正确
     *
     * @param form
     * @param db
     * @return
     */
    public boolean validatePassword(User form, User db) {
        return Md5Util.getMD5(form.getPassword(), db.getSalt()).equals(db.getPassword());
    }

    /**
     * 新增用户
     * 对密码进行加密
     *
     * @param user
     * @return
     */
    public boolean save(User user) {
        resetPasswordSalt(user);
        return super.save(user);
    }

    /**
     * 更新用户信息
     * 常规信息（剔除密码 token 等）
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(User user) {
        if (user.getPassword() != null) {
            resetPasswordSalt(user);
        }
        user.setToken(null);
        return super.updateById(user);
    }

    /**
     * 更新用户权限
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateURT(User user) {
        List<UserRole> userRoles = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : user.getTenantRoles().entrySet()) {
            userRoles.addAll(userRoleService.getUserRoleListByURT(user, entry.getValue(), entry.getKey()));
        }
        userRoleService.resetUserRole(user, userRoles);
        userTenantService.resetUserTenant(user, user.getTenantRoles().keySet());
        return true;
    }

    /**
     * 更新密码
     *
     * @param user
     * @return
     */
    public boolean updatePassword(User user) {
        User passUser = new User();
        passUser.setId(user.getId());
        passUser.setPassword(user.getPassword());
        resetPasswordSalt(passUser);
        return super.updateById(passUser);
    }

    /**
     * 删除用户
     * 同时删除 角色、租户关联
     *
     * @param idsForm
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(IdsForm idsForm) {
        userRoleService.getBaseMapper().removeByUserIds(idsForm.getIdsStr());
        userTenantService.getBaseMapper().removeByTenantIds(idsForm.getIdsStr());
        return baseMapper.removeByUserIds(idsForm.getIdsStr());
    }

    /**
     * 根据 id 获取用户详情
     *
     * @param id
     * @return
     */
    public User getDetail(Long id) {
        User user = getById(id);
        List<UserRole> userRoles = userRoleService.list(
            new QueryWrapper<UserRole>().eq("user_id", id)
        );
        List<UserTenant> userTenants = userTenantService.list(
            new QueryWrapper<UserTenant>().eq("user_id", id)
        );
        Map<Long, List<Long>> lists = new HashMap<>();
        Map<String, List<String>> listsStr = new HashMap<>();
        for (UserTenant ut : userTenants) {
            List<Long> ls = lists.computeIfAbsent(ut.getTenantId(), k -> new ArrayList<>());
            List<String> lsStr = listsStr.computeIfAbsent(ut.getTenantId() + "", k -> new ArrayList<>());
            for (UserRole ur : userRoles) {
                if (ur.getTenantId().equals(ut.getTenantId())) {
                    ls.add(ur.getRoleId());
                    lsStr.add(ur.getRoleId() + "");
                }
            }
        }
        user.setTenantRoles(lists);
        user.setTenantRolesStr(listsStr);
        return user;
    }


}
