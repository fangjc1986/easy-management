package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.service.impl.UserServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.util.ConditionUtil;
import com.fangjc1986.support.util.EPage;
import com.fangjc1986.support.util.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/sys/user")
@AllArgsConstructor
@Api(description = "用户管理")
public class UserController extends BaseController {

    private UserServiceImpl service;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<User>> list(@RequestParam Map<String, Object> filter, EPage<User> page) {
        return R.data(
            service.getBaseMapper().selectWhere(
                page.setDefaultDescId(),
                ConditionUtil.getQW(filter, User.class).eq("root", 0)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<User> get(Long id) {
        return R.data(
            service.getDetail(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    R<User> save(@RequestBody @Validated User form) {
        if (isSave(form)) {
            if (service.getOne(new QueryWrapper<>(new User(form.getUserName()))) != null) {
                throw new ServiceException("用户名已存在！");
            }
            service.save(form);
        } else service.update(form);
        form.setPassword(null);
        form.setSalt(null);
        form.setToken(null);
        return R.data(form);
    }

    @PostMapping("/updateURT")
    @ApiOperation(value = "更新用户角色租户关系")
    R<String> updateURT(@RequestBody @Validated User form) {
        User dbUser = service.getById(form.getId());
        if (dbUser == null) throw new ServiceException("用户不存在");
        return R.status(
            service.updateURT(form)
        );
    }

    @PostMapping("/updateList")
    @ApiOperation(value = "批量保存")
    R<String> updateList(@RequestBody List<User> list) {
        ValidateUtil.validate(list);
        return R.status(
            service.updateBatchById(list)
        );
    }

    @PostMapping("/remove")
    @ApiOperation(value = "ids 批量删除")
    R<String> remove(@RequestBody @Validated IdsForm idsForm) {
        return deleteCount(
            service.removeByIds(idsForm)
        );
    }

    @PostMapping("/password")
    @ApiOperation(value = "修改用户密码")
    R<String> password(@RequestBody @Validated User form) {
        User db = service.getById(form.getId());
        if (!db.getRoot().equals(0)) throw new ServiceException("ROOT 用户无法修改！");
        return R.status(service.updatePassword(form));
    }


}

