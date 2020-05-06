package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.Auth;
import com.fangjc1986.ezmpro.sys.service.impl.AuthServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.entity.R;
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
 * @author EricFang
 * @since 2020-03-19
 */

@RestController
@AllArgsConstructor
@RequestMapping("/sys/auth")
@Api(description = "菜单权限")
public class AuthController extends BaseController {

    private AuthServiceImpl service;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<Auth>> list(@RequestParam Map<String, Object> filter, EPage<Auth> page) {
        IPage<Auth> authIPage = service.page(
            page,
            ConditionUtil.getQW(filter, Auth.class)
        );
        return R.data(
            service.pushParentRow(service, authIPage)
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<Auth> get(Long id) {
        return R.data(
            service.getById(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    R<Auth> save(@RequestBody @Validated Auth form) {
        return saveOrUpdate(service, form);
    }

    @PostMapping("/updateList")
    @ApiOperation(value = "批量保存")
    R<String> updateList(@RequestBody List<Auth> list) {
        ValidateUtil.validate(list);
        return R.status(
            service.updateBatchById(list)
        );
    }

    @PostMapping("/remove")
    @ApiOperation(value = "ids 批量删除")
    R<String> remove(@RequestBody @Validated IdsForm idsForm) {
        return deleteCount(
            service.deleteChildByIds(service, idsForm)
        );
    }

    @PostMapping("/addCRUD")
    @ApiOperation(value = "一键添加CRUD接口")
    R<String> addCRUD(@RequestBody @Validated IdsForm idsForm) {
        return R.status(
            service.addCRUDByIds(idsForm)
        );
    }

}

