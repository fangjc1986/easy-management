package com.fangjc1986.ezmpro.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fangjc1986.ezmpro.sys.model.Tenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    List<Tenant> selectByUserId(@Param("userId") Long userId);

}
