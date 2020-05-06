package com.fangjc1986.ezmpro.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fangjc1986.ezmpro.sys.model.Role;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> selectWhere(@Param("page") IPage<Role> page, @Param(Constants.WRAPPER) Wrapper<Role> queryWrapper);

}
