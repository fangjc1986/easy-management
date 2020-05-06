package com.fangjc1986.ezmpro.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fangjc1986.support.model.BasePidModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_auth")
@ApiModel(value = "Auth对象", description = "菜单权限表")
public class Auth extends BasePidModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型：1：页面；2：API；")
    private Integer type;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer rank;

}
