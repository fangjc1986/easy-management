package com.fangjc1986.ezmpro.sys.model;

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
 * @since 2020-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_upload")
@ApiModel(value="Upload对象", description="")
public class Upload extends BaseModel {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "是否为图片")
    private Integer image;

    private String url;

    private String suffix;

    @ApiModelProperty(value = "业务编码： 1：CMS系统附件")
    private Integer bizCode;

    @ApiModelProperty(value = "原始名称")
    private String orgName;


}
