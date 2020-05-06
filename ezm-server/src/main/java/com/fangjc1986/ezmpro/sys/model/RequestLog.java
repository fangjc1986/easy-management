package com.fangjc1986.ezmpro.sys.model;

import com.fangjc1986.support.model.BaseMongoModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 *
 * </p>
 *
 * @author EricFang
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Document(collection = "sys_request_log")
@ApiModel(value = "RequestLog对象", description = "")
public class RequestLog extends BaseMongoModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @Field("user_name")
    private String userName;

    @ApiModelProperty(value = "用户id")
    @Field("user_id")
    private Long userId;

    @ApiModelProperty(value = "访问API")
    private String api;

    @ApiModelProperty(value = "参数JSON")
    private String params;

    @ApiModelProperty(value = "是否成功")
    private Integer success;

    private String response;

}
