package com.fangjc1986.support.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fangjc1986.support.util.SerializableUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class R<T> extends SerializableUtil {

    public static final Integer CODE_SUCCESS = 200;
    public static final Integer CODE_ERROR = 100;
    public static final Integer SIGN_ERROR = 110;
    public static final Integer AUTH_ERROR = 111;

    private Integer code;
    private String message;
    private T data;

    /**
     * 返回成功数据内容
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> R<T> data(T data) {
        R<T> r = new R<>();
        r.setCode(CODE_SUCCESS);
        r.setData(data);
        r.setMessage("success");
        return r;
    }

    /**
     * 无参数返回成功
     *
     * @return
     */
    public static R<String> data() {
        return data("success");
    }

    /**
     * 根据布尔量返回成功或失败
     *
     * @param res
     * @param err
     * @return
     */
    public static R<String> status(Boolean res, String err) {
        R<String> r = new R<>();
        return r.setCode(res ? CODE_SUCCESS : CODE_ERROR)
            .setMessage(res ? "success" : err)
            .setData(res ? err : "");
    }

    /**
     * 根据布尔量返回成功或失败
     *
     * @param res
     * @return
     */
    public static R<String> status(Boolean res) {
        return status(res, "failure");
    }

    /**
     * 返回数量结果
     * 数量 = 0 则 失败 code！
     *
     * @param count  数量
     * @param format 匹配字符串 如："成功数量为：%s"
     * @return
     */
    public static R<String> count(Integer count, String format) {
        return status(count > 0, String.format(format, count));
    }

    /**
     * 返回数量结果
     * 数量 = 0 则 失败 code！
     *
     * @param count  数量
     * @return
     */
    public static R<String> count(Integer count) {
        return status(count > 0, "%s");
    }

    /**
     * 通过JSON字符串返回R对象
     *
     * @param r
     * @param <T>
     * @return
     */
    public static <T> R<T> fromJSON(String r) {
        return JSON.parseObject(r, new TypeReference<R<T>>() {
        }.getType());
    }

}
