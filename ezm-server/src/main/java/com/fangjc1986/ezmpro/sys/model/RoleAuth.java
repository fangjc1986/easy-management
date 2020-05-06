package com.fangjc1986.ezmpro.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fangjc1986.support.model.BaseModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_auth")
@ApiModel(value="RoleAuth对象", description="")
public class RoleAuth extends BaseModel {

    private static final long serialVersionUID=1L;

    public RoleAuth(){}
    public RoleAuth(Long roleId, Long authId){
        this.roleId = roleId;
        this.authId = authId;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authId;


}
