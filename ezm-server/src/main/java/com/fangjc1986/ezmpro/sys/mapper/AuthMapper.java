package com.fangjc1986.ezmpro.sys.mapper;

import com.fangjc1986.ezmpro.sys.model.Auth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
public interface AuthMapper extends BaseMapper<Auth> {

    // 根据用户从数据库中查询是否有符合权限的 auth 信息
    Auth getUserAuthorization(@Param("userId") Long uid, @Param("tenantId") Long tenantId, @Param("path") String path);

    // 根据角色id查找
    List<Auth> listByRoleId(@Param("roleId") Long roleId);

    // 根据用户id和tenant_id 查找
    List<Auth> listByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

}
