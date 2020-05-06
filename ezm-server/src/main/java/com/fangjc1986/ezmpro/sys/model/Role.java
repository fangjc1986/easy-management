package com.fangjc1986.ezmpro.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fangjc1986.support.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "")
public class Role extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String name;

    private Long pid;

    private String remark;

    @TableField(exist = false)
    @ApiModelProperty(value = "权限数量")
    private Integer authCount;

    @TableField(exist = false)
    @ApiModelProperty(value = "权限 ids 列表")
    private List<Long> authIds;


}
