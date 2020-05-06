package com.fangjc1986.support.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BaseController {

    public static final String SAVE_ERROR = "保存失败!";
    public static final String UPDATE_ERROR = "修改失败!";
    public static final String DELETE_ERROR = "删除失败!";
    public static final String UPDATE_FORMAT = "成功修改 %s 条记录!";
    public static final String DELETE_FORMAT = "成功删除 %s 条记录！";
    public static final String SAVE_FORMAT = "成功新增 %s 条记录！";


    @Autowired
    public HttpServletRequest request;


    /**
     * 检查提交表单是否是新增
     *
     * @param form
     * @param <T>
     * @return
     */
    public <T extends BaseModel> boolean isSave(T form) {
        return form.getId() == null;
    }

    /**
     * 保存和更新
     *
     * @param service
     * @param model
     * @param <T>
     * @param <S>
     * @return
     */
    public <T extends BaseModel, S extends IService<T>> R<T> saveOrUpdate(S service, T model) {
        boolean res = service.saveOrUpdate(model);
        return saveStatus(res, model);
    }

    /**
     * 成功返回 model
     * 失败返回 错误信息
     *
     * @param res
     * @param model
     * @param <T>
     * @return
     */
    public <T extends BaseModel> R<T> saveStatus(boolean res, T model) {
        if (res) {
            return R.data(model);
        }
        throw new ServiceException("保存失败");
    }

    /**
     * 删除记录数返回
     *
     * @param count
     * @return
     */
    public R<String> deleteCount(int count) {
        if (count == 0) throw new ServiceException(DELETE_ERROR);
        return R.count(count, DELETE_FORMAT);
    }

    /**
     * 成功更新数量
     *
     * @param count
     * @return
     */
    public R<String> updateCount(int count) {
        if (count == 0) throw new ServiceException(UPDATE_ERROR);
        return R.count(count, UPDATE_FORMAT);
    }

    /**
     * 成功新增数量
     *
     * @param count
     * @return
     */
    public R<String> saveCount(int count) {
        if (count == 0) throw new ServiceException(SAVE_ERROR);
        return R.count(count, SAVE_FORMAT);
    }
}
