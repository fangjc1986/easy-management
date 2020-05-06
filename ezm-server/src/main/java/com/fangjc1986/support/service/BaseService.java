package com.fangjc1986.support.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fangjc1986.ezmpro.common.mapper.BaseValidateMapper;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.model.BaseModel;
import com.fangjc1986.support.model.BasePidModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements IService<T> {

    @Autowired
    public HttpServletRequest request;
    @Autowired
    public BaseValidateMapper baseValidateMapper;

    @Override
    @DS("slave")
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    /**
     * 是否存在
     *
     * @param table
     * @param field
     * @param value
     * @return
     */
    public boolean isExist(String table, String field, String value) {
        return baseValidateMapper.exist(table, field, value) != 0;
    }

    /**
     * 检查是否存在
     *
     * @param field
     * @param value
     * @return
     */
    public boolean isExist(String field, String value) {
        return count(
            new QueryWrapper<T>().eq(field, value)
        ) != 0;
    }

    /**
     * 检查是否存在
     *
     * @return
     */
    public boolean isExist(QueryWrapper<T> qw){
        return count( qw ) != 0 ;
    }

    /**
     * 提交表单是否为新增
     *
     * @param model
     * @param <S>
     * @return
     */
    public <S extends BaseModel> boolean isSave(S model) {
        return true;
    }

    /**
     * 追加 pid 父条目
     * 防止在树状展示条目中只筛选到了子条目导致没有父条目而无法展示准确树状结构
     *
     * @param service
     * @param iPage
     * @return
     */
    public <B extends BaseMapper<P>, P extends BasePidModel> IPage<P> pushParentRow(BaseService<B, P> service, IPage<P> iPage) {
        List<P> list = iPage.getRecords();
        Set<Long> pids = new HashSet<>();
        int maxLoop = 100;
        while (maxLoop-- > 0) {
            for (P row : list) {
                pids.add(row.getPid());
            }
            Iterator<Long> it = pids.iterator();
            while (it.hasNext()) {
                Long pid = it.next();
                for (P row : iPage.getRecords()) {
                    if (row.getId().equals(pid)) {
                        it.remove();
                    }
                }
            }
            if (pids.size() == 0) {
                break;
            }
            list = service.list(
                new QueryWrapper<P>().in("id", pids)
            );
            if (list.size() == 0) {
                break;
            }
            iPage.getRecords().addAll(list);
            iPage.setTotal(iPage.getTotal() + list.size());
        }
        return iPage;
    }


    public static interface PushParentRowCondition<T> {
        boolean needParent(T object);
    }

    public <B extends BaseMapper<P>, P extends BasePidModel> List<P> pushParentRowCondition(BaseService<B, P> service, List<P> list, PushParentRowCondition<P> condition) {
        List<P> result = new ArrayList<>(list);
        Set<Long> pids = new HashSet<>();
        int maxLoop = 100;
        while (maxLoop-- > 0) {
            for (P row : list) {
                if (condition.needParent(row)) pids.add(row.getPid());
            }
            Iterator<Long> it = pids.iterator();
            while (it.hasNext()) {
                Long pid = it.next();
                for (P row : result) {
                    if (row.getId().equals(pid)) {
                        it.remove();
                    }
                }
            }
            if (pids.size() == 0) {
                break;
            }
            list = service.list(
                new QueryWrapper<P>().in("id", pids)
            );
            if (list.size() == 0) {
                break;
            }
            result.addAll(list);
        }
        return result;
    }

    /**
     * 追加 pid 父条目
     * 防止在树状展示条目中只筛选到了子条目导致没有父条目而无法展示准确树状结构
     *
     * @param service
     * @return
     */
    public <B extends BaseMapper<P>, P extends BasePidModel> List<P> pushParentRow(BaseService<B, P> service, List<P> list) {
        return pushParentRowCondition(service, list, new PushParentRowCondition<P>() {
            @Override
            public boolean needParent(P object) {
                return true;
            }
        });
    }


    /**
     * 跟根据 ids 删除
     * 并且删除旗下的子列
     *
     * @return
     */
    public <B extends BaseMapper<P>, P extends BasePidModel> int deleteChildByIds(BaseService<B, P> service, IdsForm idsForm) {
        int all = 0;
        List<Long> ids = idsForm.getIds();
        int maxLoop = 100;
        while (maxLoop-- > 0) {
            if (ids.size() == 0) break;
            int delCount = service.getBaseMapper().deleteBatchIds(ids);
            if (delCount == 0) break;
            all += delCount;
            List<P> childes = service.list(
                new QueryWrapper<P>().in("pid", ids)
            );
            ids = new ArrayList<>();
            for (P row : childes) {
                ids.add(row.getId());
            }
        }
        return all;
    }

}