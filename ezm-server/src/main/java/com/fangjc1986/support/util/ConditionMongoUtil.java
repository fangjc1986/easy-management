package com.fangjc1986.support.util;

//"records": [],
//"total": 0,
//"size": 100,
//"current": 1,
//"orders": [],
//"searchCount": true,
//"pages": 0

import com.fangjc1986.support.model.BaseMongoModel;
import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class ConditionMongoUtil<T extends BaseMongoModel> {

    // 过滤字段 Map
    private Map<String, Object> filter;

    public ConditionMongoUtil(Map<String, Object> filter) {
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
    public static <T extends BaseMongoModel> Query getMQ(Map<String, Object> filter, Class<T> clz) {
        return new ConditionMongoUtil<T>(filter).getQuery();
    }

    /**
     * 获取查询条件
     *
     * @return
     */
    private Query getQuery() {
        ConditionUtil.clearPageField(filter);
        Query query = new Query();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String field = entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (StringUtil.isEmpty(value)) continue;
            addFieldCondition(query, field, value);
        }
        return query;
    }


    /**
     * 判断特殊参数
     * @param query
     * @param field
     * @param value
     */
    private void addFieldCondition(Query query, String field, String value) {
        List<String> values = Arrays.asList(value.split(","));
        List<Object> valuesUse = new ArrayList<>();
        String type = "string";
        if (field.contains("_isdate")) {
            type = "isdate";
            field = replaceSuffix(field, "_isdate");
        } else if (field.contains("_istime")) {
            type = "istime";
            field = replaceSuffix(field, "_istime");
        }
        for (int i = 0; i < values.size(); i++) {
            if (type.equals("isdate")) {
                LocalDate date = LocalDate.parse(values.get(i), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                valuesUse.add(i, date);
            } else if (type.equals("istime")) {
                LocalDateTime date = LocalDateTime.parse(values.get(i), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                valuesUse.add(i, date);
            } else {
                valuesUse.add(i, values.get(i));
            }
        }
        addFieldCondition(query, field, valuesUse);
    }

    /**
     * 添加条件
     * 根据字段后缀名称进行条件追加
     *
     * @param query
     * @param field
     */
    private void addFieldCondition(Query query, String field, List<Object> values) {
        Object value = values.get(0);
        String orField = "_or";
        if (field.contains(orField)) {
            field = field.replace(orField, "");
        }
        if (field.endsWith("_like")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_like")).regex((String) value)
            );
        } else if (field.endsWith("_likeLeft")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_likeLeft")).regex("^" + value)
            );
        } else if (field.endsWith("_likeRight")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_likeRight")).regex(value + "$")
            );
        } else if (field.endsWith("_null")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_null")).is(null)
            );
        } else if (field.endsWith("_notNull")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_notNull")).ne(null)
            );
        } else if (field.endsWith("_gt")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_gt")).gt(value)
            );
        } else if (field.endsWith("_between")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_between")).gte(values.get(0)).lte(values.get(1))
            );
        } else if (field.endsWith("_lt")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_lt")).lt(value)
            );
        } else if (field.endsWith("_ge")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_ge")).gte(value)
            );
        } else if (field.endsWith("_le")) {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "_le")).lte(value)
            );
        } else if (field.endsWith("_in")) {
            query.addCriteria(
                new Criteria().and(replaceSuffix(field, "_in")).in(values)
            );
        } else if (field.endsWith("_notIn")) {
            query.addCriteria(
                new Criteria().and(replaceSuffix(field, "_notIn")).nin(values)
            );
        } else {
            query.addCriteria(
                Criteria.where(replaceSuffix(field, "")).is(value)
            );
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


}
