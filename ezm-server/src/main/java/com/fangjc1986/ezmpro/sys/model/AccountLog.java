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
 * 账号登录登出日志
 * </p>
 *
 * @author EricFang
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Document(collection = "sys_account_log")
@ApiModel(value = "AccountLog对象", description = "账号登录登出日志")
public class AccountLog extends BaseMongoModel {

    private static final long serialVersionUID = 1L;
    public static final Integer TYPE_LOGIN = 1;
    public static final Integer TYPE_LOGOUT = 2;

    @ApiModelProperty(value = "用户id")
    @Field("user_id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    @Field("user_name")
    private String userName;

    @ApiModelProperty(value = "类型：1：登录；2：登出")
    private Integer type;

    @ApiModelProperty(value = "是否成功")
    private Integer success;

    @ApiModelProperty(value = "登录ip")
    private String ip;


}
