package com.fangjc1986.ezmpro.sys.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fangjc1986.support.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;
    // 登录用户 request.attr key
    public static final String LOGIN_USER = "LOGIN_USER";

    // 登录表单验证
    public interface SignIn {}

    public interface ChangePass {}

    public User() {}

    public User(String userName) {
        setUserName(userName);
    }

    @ApiModelProperty(value = "用户名（登录用）")
    @NotBlank(message = "用户名不能为空", groups = {SignIn.class})
    @TableField(fill = FieldFill.INSERT)
    private String userName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码（加密后的）")
    @NotBlank(message = "密码不能为空", groups = {SignIn.class, ChangePass.class})
    private String password;

    @ApiModelProperty(value = "密码盐值")
    private String salt;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别：1：女，2：男；")
    private Integer gender;

    @ApiModelProperty(value = "用户类型：1：普通用户；2：后台用户；10：root 账号")
    private Integer type;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "座机电话")
    private String phone;

    @ApiModelProperty(value = "移动电话")
    private String mobile;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @JsonFormat(pattern = DATE_ONLY)
    @ApiModelProperty(value = "生日", example = DATE_ONLY_EXAMPLE)
    private LocalDate birthday;

    @ApiModelProperty(value = "token 登录凭证")
    private String token;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "是否激活")
    private Integer activated;

    @ApiModelProperty(value = "是否为root用户")
    private Integer root;

    @TableField(exist = false)
    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空", groups = {SignIn.class})
    private String authCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空", groups = {ChangePass.class})
    private String newPassword;

    /**
     * 参数格式
     * {
     * tenantId : [roleId, roleId ]
     * }
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "租户角色")
    private Map<Long, List<Long>> tenantRoles;

    @TableField(exist = false)
    @ApiModelProperty(value = "租户角色字符串 id")
    private Map<String, List<String>> tenantRolesStr;


}
