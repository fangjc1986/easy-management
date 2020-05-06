package com.fangjc1986.support.util;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fangjc1986.support.model.BaseMongoModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class MongoPageable<T extends BaseMongoModel> extends SerializableUtil implements Pageable {

    public EPage<T> page;

    public MongoPageable(EPage<T> page) {
        this.page = page;
    }

    @Override
    public int getPageNumber() {
        return (int) page.getCurrent();
    }

    @Override
    public int getPageSize() {
        return (int) page.getSize();
    }

    @Override
    public long getOffset() {
        return page.getSize() * (page.getCurrent() - 1);
    }

    @Override
    public Sort getSort() {
        if (page.getOrders().size() > 0) {
            OrderItem item = page.getOrders().get(0);
            Sort.Direction direction = item.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
            return new Sort(direction, item.getColumn());
        }
        return new Sort(Sort.Direction.ASC, "id");
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
