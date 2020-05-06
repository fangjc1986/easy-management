package com.fangjc1986.support.util;

//"records": [],
//"total": 0,
//"size": 100,
//"current": 1,
//"orders": [],
//"searchCount": true,
//"pages": 0

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangjc1986.support.model.BaseModel;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class ConditionUtil<T extends BaseModel> {

    // 过滤字段 Map
    private Map<String, Object> filter;

    public ConditionUtil(Map<String, Object> filter) {
        this.filter = filter;
    }


    /**
     * 快捷方法获取筛选条件
     *
     * @param filter
     * @param clz
     * @param <T>
     * @return
     */
    public static <T extends BaseModel> QueryWrapper<T> getQW(Map<String, Object> filter, Class<T> clz) {
        return new ConditionUtil<T>(filter).getQueryWrapper();
    }

    /**
     * 获取查询条件
     *
     * @return
     */
    private QueryWrapper<T> getQueryWrapper() {
        clearPageField(filter);
        QueryWrapper<T> qw = new QueryWrapper<>();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String field = entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (value.isEmpty()) continue;
            addFieldCondition(qw, field, value);
        }
        return qw;
    }

    /**
     * 添加条件
     * 根据字段后缀名称进行条件追加
     *
     * @param qw
     * @param field
     * @param value
     */
    private void addFieldCondition(QueryWrapper<T> qw, String field, String value) {
        if (value.isEmpty()) return;
        field = replaceSuffix(field, "_isdate");
        field = replaceSuffix(field, "_istime");
        String orField = "_or";
        if (field.contains(orField)) {
            qw.or();
            field = field.replace(orField, "");
        }
        if (field.endsWith("_like")) {
            qw.like(replaceSuffix(field, "_like"), value);
        } else if (field.endsWith("_likeLeft")) {
            qw.likeLeft(replaceSuffix(field, "_likeLeft"), value);
        } else if (field.endsWith("_likeRight")) {
            qw.likeRight(replaceSuffix(field, "_likeRight"), value);
        } else if (field.endsWith("_null")) {
            qw.isNull(replaceSuffix(field, "_null"));
        } else if (field.endsWith("_notNull")) {
            qw.isNotNull(replaceSuffix(field, "_notNull"));
        } else if (field.endsWith("_gt")) {
            qw.gt(replaceSuffix(field, "_gt"), value);
        } else if (field.endsWith("_between")) {
            List<String> list = Arrays.asList(value.split(","));
            qw.between(replaceSuffix(field, "_between"), list.get(0), list.get(1));
        } else if (field.endsWith("_lt")) {
            qw.lt(replaceSuffix(field, "_lt"), value);
        } else if (field.endsWith("_ge")) {
            qw.ge(replaceSuffix(field, "_ge"), value);
        } else if (field.endsWith("_le")) {
            qw.le(replaceSuffix(field, "_le"), value);
        } else if (field.endsWith("_in")) {
            qw.in(replaceSuffix(field, "_in"), Arrays.asList(value.split(",")));
        } else if (field.endsWith("_notIn")) {
            qw.notIn(replaceSuffix(field, "_notIn"), Arrays.asList(value.split(",")));
        } else {
            qw.eq(replaceSuffix(field, ""), value);
        }
    }

    /**
     * 替换 字段尾部 _*(_null, _gt 等)
     * 并把驼峰式改为下划线形式
     *
     * @param field
     * @param key
     * @return
     */
    private static String replaceSuffix(String field, String key) {
        String f = field.replace(key, "");
        String[] s = f.split(".");
        if (s.length > 1) {
            return s[0] + "`" + StringUtil.humpToLineBetter(s[1]) + "`";
        }
        return StringUtil.humpToLineBetter(f);
    }


    /**
     * 删除分页和排序相关的字段
     */
    public static void clearPageField(Map<String, Object> filter) {
        filter.remove("desc");
        filter.remove("descs");
        filter.remove("asc");
        filter.remove("ascs");
        filter.remove("total");
        filter.remove("current");
        filter.remove("size");
        filter.remove("orders");
        filter.remove("searchCount");
    }

}
