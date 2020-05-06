package com.fangjc1986.ezmpro.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BaseValidateMapper {

    @Select("select count(*) from ${table} where ${where} ")
    Integer exist(@Param("table") String table, @Param("where") String where);

    @Select("select count(*) from ${table} where `${field}` = ${value}")
    Integer exist(@Param("table") String table, @Param("field") String field, @Param("value") String value);

}
