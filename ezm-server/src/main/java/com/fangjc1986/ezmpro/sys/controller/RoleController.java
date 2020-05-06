package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.Auth;
import com.fangjc1986.ezmpro.sys.model.Role;
import com.fangjc1986.ezmpro.sys.service.impl.AuthServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.RoleAuthServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.RoleServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.util.ConditionUtil;
import com.fangjc1986.support.util.EPage;
import com.fangjc1986.support.util.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/sys/role")
@Api(description = "角色管理")
public class RoleController extends BaseController {

    private RoleServiceImpl service;
    private AuthServiceImpl authService;
    private RoleAuthServiceImpl roleAuthService;


    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<Role>> list(@RequestParam Map<String, Object> filter, EPage<Role> page) {
        return R.data(
            service.getBaseMapper().selectWhere(
                page,
                ConditionUtil.getQW(filter, Role.class)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<Role> get(Long id) {
        return R.data(
            service.getById(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    R<Role> save(@RequestBody @Validated Role form) {
        return saveOrUpdate(service, form);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "ids 批量删除")
    R<String> remove(@RequestBody @Validated IdsForm idsForm) {
        return deleteCount(
            service.getBaseMapper().deleteBatchIds(idsForm.getIds())
        );
    }

    @PostMapping("/updateList")
    @ApiOperation(value = "批量保存")
    R<String> updateList(@RequestBody List<Role> list) {
        ValidateUtil.validate(list);
        return R.status(
            service.updateBatchById(list)
        );
    }

    @GetMapping("/authList")
    @ApiOperation(value = "角色的权限列表")
    R<List<Auth>> authList(Long id) {
        return R.data(
            authService.getBaseMapper().listByRoleId(id)
        );
    }

    @PostMapping("/bindAuths")
    @ApiOperation("绑定权限")
    @Transactional(rollbackFor = Exception.class)
    R<String> bindAuths(@RequestBody @Validated Role role) {
        return R.status(
            roleAuthService.updateAuth(role)
        );
    }

}

