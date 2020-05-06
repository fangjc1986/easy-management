package com.fangjc1986.ezmpro.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fangjc1986.ezmpro.sys.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectWhere(@Param("page") IPage<User> page, @Param(Constants.WRAPPER) Wrapper<User> queryWrapper);

    // 根据用户 ids 删除用户 过滤 root用户
    int removeByUserIds(@Param("userIds") String userIds);

}
