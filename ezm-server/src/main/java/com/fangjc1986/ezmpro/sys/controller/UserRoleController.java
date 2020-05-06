package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.UserRole;
import com.fangjc1986.ezmpro.sys.service.IUserRoleService;
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
 * @since 2020-03-18
 */

@RestController
@AllArgsConstructor
@RequestMapping("/sys/user-role")
@Api(description = "用户角色关联")
public class UserRoleController extends BaseController {
    private IUserRoleService service;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<UserRole>> list(@RequestParam Map<String, Object> filter, EPage<UserRole> page) {
        return R.data(
            service.page(
                page.setDefaultDescId(),
                ConditionUtil.getQW(filter, UserRole.class)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<UserRole> get(Long id) {
        return R.data(
            service.getById(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    R<UserRole> save(@RequestBody @Validated UserRole form) {
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

