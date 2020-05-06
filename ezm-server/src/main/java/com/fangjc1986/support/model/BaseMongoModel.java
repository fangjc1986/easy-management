package com.fangjc1986.support.model;

import com.fangjc1986.support.util.SerializableUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseMongoModel extends SerializableUtil {
    @Id
    private String id;



    @JsonFormat(pattern = BaseModel.DATE_TIME)
    @ApiModelProperty(value = "创建时间", example = BaseModel.DATE_TIME_EXAMPLE)
    @CreatedDate
    @Field("create_time")
    private LocalDateTime createTime;

    @JsonFormat(pattern = BaseModel.DATE_TIME)
    @ApiModelProperty(value = "修改时间", example = BaseModel.DATE_TIME_EXAMPLE)
    @LastModifiedDate
    @Field("update_time")
    private LocalDateTime updateTime;

    @CreatedBy
    @Field("update_user")
    private String updateUser;

    @LastModifiedBy
    @Field("create_user")
    private String createUser;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;
}
