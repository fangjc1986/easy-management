package com.fangjc1986.ezmpro.sys.mapper;

import com.fangjc1986.ezmpro.sys.model.UserTenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
public interface UserTenantMapper extends BaseMapper<UserTenant> {

    // 根据用户 ids 删除
    int removeByUserIds(@Param("userIds") String userIds);

    // 根据组合 ids 删除
    int removeByTenantIds(@Param("tenantIds") String tenantIds);

}
