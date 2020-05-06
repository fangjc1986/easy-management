package com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.model.RequestLog;
import com.fangjc1986.ezmpro.sys.service.impl.RequestLogServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.util.ConditionMongoUtil;
import com.fangjc1986.support.util.EPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author EricFang
 * @since 2020-04-10
 */

@RestController
@AllArgsConstructor
@Api(description = "RequestLogController")
@RequestMapping("/sys/request-log")
public class RequestLogController extends BaseController {

    private RequestLogServiceImpl service;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<RequestLog>> list(@RequestParam Map<String, Object> filter, EPage<RequestLog> page) {
        return R.data(
            service.page(
                page.setDefaultDescCreateTime(),
                ConditionMongoUtil.getMQ(filter, RequestLog.class)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<RequestLog> get(String id) {
        return R.data(
            service.getById(id)
        );
    }

}

