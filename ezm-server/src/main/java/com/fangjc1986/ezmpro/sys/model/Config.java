package com.fangjc1986.ezmpro.sys.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fangjc1986.support.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("sys_config")
@ApiModel(value = "Config对象", description = "")
public class Config extends BaseModel {

    private static final long serialVersionUID = 1L;

    // 单点登录
    public static final String SSO = "SSO";
    // 过期时间(s)
    public static final String TOKEN_EXPIRE = "TOKEN_EXPIRE";
    // 信任GET 请求（GET 请求不验证权限）
    public static final String GET_TRUST = "GET_TRUST";


    @ApiModelProperty(value = "编码（作为查询 key）")
    @TableField(fill = FieldFill.INSERT)
    private String code;

    @ApiModelProperty(value = "参数值")
    private String value;

    @ApiModelProperty(value = "类型：1：文本框输入，2：波尔量开关")
    private String type;

    @ApiModelProperty(value = "备注说明")
    private String remark;

}
