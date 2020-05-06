package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.DictDetail;
import com.fangjc1986.ezmpro.sys.service.impl.DictDetailServiceImpl;
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
@RequestMapping("/sys/dict-detail")
@Api(description = "字典明细")
public class DictDetailController extends BaseController {

    private DictDetailServiceImpl service;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<DictDetail>> list(@RequestParam Map<String, Object> filter, EPage<DictDetail> page) {
        return R.data(
            service.page(
                page,
                ConditionUtil.getQW(filter, DictDetail.class)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<DictDetail> get(Long id) {
        return R.data(
            service.getById(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    R<DictDetail> save(@RequestBody @Validated DictDetail form) {
        return saveOrUpdate(service, form);
    }


    @PostMapping("/updateList")
    @ApiOperation(value = "批量保存")
    R<String> updateList(@RequestBody List<DictDetail> list) {
        ValidateUtil.validate(list);
        return R.status(
            service.updateBatchById(list)
        );
    }

    @PostMapping("/remove")
    @ApiOperation(value = "ids 批量删除")
    R<String> remove(@RequestBody @Validated IdsForm idsForm) {
        return deleteCount(
            service.getBaseMapper().deleteBatchIds(idsForm.getIds())
        );
    }

}

