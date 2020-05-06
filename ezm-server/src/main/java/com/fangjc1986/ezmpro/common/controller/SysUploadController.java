package com.fangjc1986.ezmpro.common.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangjc1986.ezmpro.sys.model.Upload;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.service.impl.UploadServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.service.TokenService;
import com.fangjc1986.support.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author EricFang
 * @since 2020-02-14
 */
@RestController
@RequestMapping("/common/upload")
@AllArgsConstructor
public class SysUploadController extends BaseController {

    UploadServiceImpl uploadService;
    TokenService authTokenService;

    @PostMapping(value = "/froala")
    @ApiOperation(value = "froala插件上传")
    Map<String, String> froala(@RequestParam("file") MultipartFile file, @RequestParam("token") String token, Upload sysUpload) {
        User user = authTokenService.stringToUser(token);
        String url = uploadService.upload2AliOSS(file);
        sysUpload.setOrgName(file.getOriginalFilename());
        sysUpload.setSuffix(StringUtil.getSuffix(sysUpload.getOrgName()));
        sysUpload.setUrl(url);
        sysUpload.setCreateUser(user.getUserName());
        sysUpload.setCreateTime(LocalDateTime.now());
        uploadService.save(sysUpload);
        Map<String, String> res = new HashMap<>();
        res.put("link", url);
        return res;
    }

    @Data
    static class FroalaImage {
        String url;
        String thumb;
        String tag;

        public FroalaImage(Upload upload) {
            url = upload.getUrl();
            thumb = upload.getUrl();
            tag = upload.getOrgName();
        }
    }

    @GetMapping(value = "/froalaImageList")
    @ApiOperation(value = "Froala图片上传插件中的图片列表")
    List<Upload> froalaImageList(@RequestParam("token") String token) {
        User user = authTokenService.stringToUser(token);
        return uploadService.list(
            new QueryWrapper<Upload>()
                .eq("create_user", user.getUserName())
                .eq("image", 1)
                .last("limit 100")
        );
    }

    @PostMapping(value = "/froalaImageDelete")
    @ApiOperation(value = "Froala 图片删除")
    String froalaImageDelete(@RequestParam("src") String src, @RequestParam("token") String token) {
        User user = authTokenService.stringToUser(token);
        uploadService.remove(
            new QueryWrapper<Upload>().eq("url", src).eq("create_user", user.getUserName())
        );
        return "success";
    }

}

