package com.fangjc1986.support.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BasePidModel extends BaseModel {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "父id")
    private Long pid;
}
