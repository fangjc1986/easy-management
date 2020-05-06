package com.fangjc1986.ezmpro.sys.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fangjc1986.support.model.BaseModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@TableName("sys_dict_detail")
@ApiModel(value = "DictDetail对象", description = "")
public class DictDetail extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT)
    private String code;

    private String name;

    @ApiModelProperty(value = "字典分类 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dictId;

    @ApiModelProperty(value = "字典分类 CODE")
    private String dictCode;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String color;

    private String remark;

    private Integer rank;

}
