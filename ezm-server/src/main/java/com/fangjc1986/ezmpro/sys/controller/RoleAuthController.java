package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.RoleAuth;
import com.fangjc1986.ezmpro.sys.service.impl.RoleAuthServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.util.ConditionUtil;
import com.fangjc1986.support.util.EPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author EricFang
 * @since 2020-03-19
 */

@RestController
@AllArgsConstructor
@RequestMapping("/sys/role-auth")
@Api(description = "角色权限关联")
public class RoleAuthController extends BaseController {

    private RoleAuthServiceImpl service;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<RoleAuth>> list(@RequestParam Map<String, Object> filter, EPage<RoleAuth> page) {
        return R.data(
            service.page(
                page,
                ConditionUtil.getQW(filter, RoleAuth.class)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<RoleAuth> get(Long id) {
        return R.data(
            service.getById(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    R<RoleAuth> save(@RequestBody @Validated RoleAuth form) {
        return saveOrUpdate(service, form);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "ids 批量删除")
    R<String> remove(@RequestBody @Validated IdsForm idsForm) {
        return deleteCount(
            service.getBaseMapper().deleteBatchIds(idsForm.getIds())
        );
    }


}

