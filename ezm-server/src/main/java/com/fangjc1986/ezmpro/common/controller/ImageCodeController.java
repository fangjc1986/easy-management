package com.fangjc1986.ezmpro.common.controller;

import com.fangjc1986.support.util.DrawImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/common/image-code")
@Api(description = "图片验证码")
public class ImageCodeController {

    @GetMapping(value = "/getLoginCode")
    @ApiOperation(value = "图片验证码地址")
    @ResponseBody
    public void getLoginCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DrawImageUtils d = new DrawImageUtils();
        String code = d.doGet(request, response);
    }

}
