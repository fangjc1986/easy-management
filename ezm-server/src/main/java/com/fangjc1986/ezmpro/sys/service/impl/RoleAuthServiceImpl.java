package com.fangjc1986.ezmpro.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangjc1986.ezmpro.sys.model.Role;
import com.fangjc1986.ezmpro.sys.model.RoleAuth;
import com.fangjc1986.ezmpro.sys.mapper.RoleAuthMapper;
import com.fangjc1986.ezmpro.sys.service.IRoleAuthService;
import com.fangjc1986.support.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class RoleAuthServiceImpl extends BaseService<RoleAuthMapper, RoleAuth> implements IRoleAuthService {

    /**
     * 更新角色权限
     * @param role
     */
    public boolean updateAuth(Role role) {
        remove(
            new QueryWrapper<RoleAuth>().eq("role_id", role.getId())
        );
        List<RoleAuth> roleAuths = new ArrayList<>();
        for (Long authId : role.getAuthIds()) {
            roleAuths.add(new RoleAuth(role.getId(), authId));
        }
        return saveBatch(roleAuths);
    }


}
