package com.fangjc1986.support.util;

import lombok.Data;

import java.util.List;

@Data
public class ListFinder<T> {

    private List<T> list;

    public ListFinder(List<T> list) {
        this.list = list;
    }

    public interface Comparator<T> {
        boolean compare(T entity);
    }

    public T find(Comparator<T> comparator) {
        for (T entity : list) {
            if (comparator.compare(entity)) {
                return entity;
            }
        }
        return null;
    }
}
