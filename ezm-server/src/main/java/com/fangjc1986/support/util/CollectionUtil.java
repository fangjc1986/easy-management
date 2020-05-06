package com.fangjc1986.support.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
    /**
     * 分割 集合
     *
     * @param collection
     * @param len
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitCollection(List<T> collection, int len) {
        List<List<T>> res = new ArrayList<>();
        List<T> group = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            int index = i % len;
            if (index == 0 && i != 0) {
                group = new ArrayList<>();
            }
            group.add(collection.get(i));
            if (index + 1 == len) {
                res.add(group);
            }
        }
        if (group.size() < len) {
            res.add(group);
        }
        return res;
    }
}
