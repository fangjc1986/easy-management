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

import javax.validation.constraints.NotBlank;

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
@TableName("sys_dict")
@ApiModel(value = "Dict对象", description = "")
public class Dict extends BaseModel {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "编号不能为空")
    @TableField(fill = FieldFill.INSERT)
    private String code;

    private String name;

    private String remark;

    @TableField(exist = false)
    @ApiModelProperty(value = "明细数量")
    private Integer detailCount;


}
