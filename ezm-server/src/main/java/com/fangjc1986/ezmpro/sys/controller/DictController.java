package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.Dict;
import com.fangjc1986.ezmpro.sys.model.DictDetail;
import com.fangjc1986.ezmpro.sys.service.impl.DictDetailServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.DictServiceImpl;
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
@RequestMapping("/sys/dict")
@Api(description = "字典分类")
public class DictController extends BaseController {

    private DictServiceImpl service;
    private DictDetailServiceImpl detailService;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<Dict>> list(@RequestParam Map<String, Object> filter, EPage<Dict> page) {
        return R.data(
            service.page(
                page,
                ConditionUtil.getQW(filter, Dict.class)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<Dict> get(Long id) {
        return R.data(
            service.getById(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    @Transactional(rollbackFor = Exception.class)
    R<Dict> save(@RequestBody @Validated Dict form) {
        detailService.update(
            new UpdateWrapper<DictDetail>()
                .set("id", form.getId())
                .set("dict_code", form.getCode())
                .eq("id", form.getId())
        );
        return saveOrUpdate(service, form);
    }

    @PostMapping("/updateList")
    @ApiOperation(value = "批量保存")
    R<String> updateList(@RequestBody List<Dict> list) {
        ValidateUtil.validate(list);
        return R.status(
            service.updateBatchById(list)
        );
    }

    @PostMapping("/remove")
    @ApiOperation(value = "ids 批量删除")
    @Transactional(rollbackFor = Exception.class)
    R<String> remove(@RequestBody @Validated IdsForm idsForm) {
        detailService.remove(
            new QueryWrapper<DictDetail>().in("dict_id", idsForm.getIds())
        );
        return deleteCount(
            service.getBaseMapper().deleteBatchIds(idsForm.getIds())
        );
    }

}

