package com.fangjc1986.support.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fangjc1986.support.util.SerializableUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseModel extends SerializableUtil {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_EXAMPLE = "2000-01-01 12:00:00";
    public static final String DATE_ONLY = "yyyy-MM-dd";
    public static final String DATE_ONLY_EXAMPLE = "2000-01-01";


    public interface SaveForm {}

    @Min(value = 1, message = "id 不能小于0")
    @TableId(type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonFormat(pattern = DATE_TIME)
    @ApiModelProperty(value = "创建时间", example = DATE_TIME_EXAMPLE)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    @JsonFormat(pattern = DATE_TIME)
    @ApiModelProperty(value = "修改时间", example = DATE_TIME_EXAMPLE)
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;


    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;

}
