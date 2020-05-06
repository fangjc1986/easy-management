package com.fangjc1986.ezmpro.sys.mapper;

import com.fangjc1986.ezmpro.sys.model.UserRole;
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
public interface UserRoleMapper extends BaseMapper<UserRole> {

    // 根据用户 id 列表删除 1,2,3,4,5
    int removeByUserIds(@Param("userIds") String userIds);

    // 根据角色 id 列表删除 1,2,3,4,5
    int removeByRoleIds(@Param("roleIds") String roleIds);

}
