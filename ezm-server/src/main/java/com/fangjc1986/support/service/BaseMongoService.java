package com.fangjc1986.support.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.support.model.BaseMongoModel;
import com.fangjc1986.support.util.CollectionUtil;
import com.fangjc1986.support.util.EPage;
import com.fangjc1986.support.util.MongoPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;


@Service
public abstract class BaseMongoService<T extends BaseMongoModel> {

    @Autowired
    public MongoTemplate mongoTemplate;
    @Autowired
    public HttpServletRequest request;


    public static int MAX_BATCH_ROW = 1000;

    public Class<T> tClass;

    public BaseMongoService() {
        tClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 保存文档
     *
     * @param doc
     */
    public void save(T doc) {
        //doc.setCreateTime(LocalDateTime.now());
        //doc.setCreateUser(SystemHelper.loginUserName());
        mongoTemplate.insert(doc);
    }

    public void saveBatch(List<T> list) {
        List<List<T>> lists = CollectionUtil.splitCollection(list, MAX_BATCH_ROW);
        for (List<T> l : lists) {
            mongoTemplate.insertAll(l);
        }
    }

    /**
     * 通过id 更新
     *
     * @param doc
     */
    public void updateById(T doc) {
        Query query = new Query(Criteria.where("_id").is(doc.getId()));
        Update update = new Update();
        //update.set("update_time", LocalDateTime.now().format(DateTimeFormatter.ofPattern(BaseModel.DATE_TIME)));
        //update.set("update_user", SystemHelper.loginUserName());
        setUpdateFields(update, doc);
        mongoTemplate.updateFirst(query, update, tClass);
    }

    /**
     * 修改
     *
     * @param query
     * @param update
     */
    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, tClass);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<T> all() {
        return mongoTemplate.findAll(tClass);
    }


    /**
     * 查询byid
     *
     * @param id
     * @return
     */
    public T getById(String id) {
        return mongoTemplate.findById(id, tClass);
    }

    /**
     * 查询一条
     *
     * @param query
     * @return
     */
    public T getOne(Query query) {
        return mongoTemplate.findOne(query, tClass);
    }


    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    public IPage<T> page(EPage<T> page, Query query) {
        long count = mongoTemplate.count(query, tClass);
        page.setTotal(count);
        MongoPageable<T> pageable = new MongoPageable<>(page);
        page.setRecords(mongoTemplate.find(query.with(pageable), tClass));
        return page;
    }


    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(Long id) {
        mongoTemplate.remove(
            new Query(Criteria.where("_id").is(id))
        );
    }

    /**
     * 根据id 批量删除
     *
     * @param ids
     */
    public void deleteBatchByIds(Collection<String> ids) {
        mongoTemplate.remove(
            new Query(new Criteria().and("_id").in(ids))
        );
    }

    /**
     * 根据条件删除
     *
     * @param query
     */
    public void delete(Query query) {
        mongoTemplate.remove(query, tClass);
    }

    abstract public void setUpdateFields(Update update, T doc);
}
