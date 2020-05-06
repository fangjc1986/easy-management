package com.fangjc1986.support.entity;

import com.fangjc1986.support.util.SerializableUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IdsForm extends SerializableUtil {

    @NotEmpty(message = "ids 不能为空")
    private List<Long> ids;

    /**
     * 获取字符串 ids
     * 用于 in( 1,2,3,4 ) 的场景
     *
     * @return
     */
    public String getIdsStr() {
        return StringUtils.join(ids, ",");
    }

    // 额外参数1
    private String p1;
    // 额外参数2
    private String p2;
    // 额外参数3
    private String p3;
    // 额外参数3
    private String p4;
}
