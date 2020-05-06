package com.fangjc1986.ezmpro.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fangjc1986.support.model.BaseModel;
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
@TableName("sys_tenant")
@ApiModel(value="Tenant对象", description="")
public class Tenant extends BaseModel {

    private static final long serialVersionUID=1L;

    private String name;

    private String code;

    private String remark;


}
