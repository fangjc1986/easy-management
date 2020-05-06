package com.fangjc1986.ezmpro.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangjc1986.ezmpro.common.service.ImageCodeService;
import com.fangjc1986.ezmpro.sys.model.AccountLog;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.service.impl.AccountLogServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.RequestLogServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.UserServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.UserTenantServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/common/sign")
@AllArgsConstructor
@Api(description = "账户登录")
public class SignController extends BaseController {

    private UserServiceImpl userService;
    private TokenService tokenService;
    private UserTenantServiceImpl userTenantService;
    private ImageCodeService imageCodeService;
    private AccountLogServiceImpl accountLogService;
    private RequestLogServiceImpl requestLogService;

    @PostMapping("/in")
    @ApiOperation(value = "用户登录")
    R<User> in(@RequestBody @Validated({User.SignIn.class}) User form) {
        if (!imageCodeService.validateCode(form)) throw new ServiceException("验证码错误!");
        User db = userService.getOne(new QueryWrapper<>(new User(form.getUserName())));
        if (db == null) throw new ServiceException("用户不存在!");
        if (!db.getActivated().equals(1)) throw new ServiceException("用户尚未激活！");
        if (!db.getType().equals(2)) throw new ServiceException("用户不是后台用户！");
        if (!userService.validatePassword(form, db)) throw new ServiceException("用户密码错误！");
        request.setAttribute(User.LOGIN_USER, db);
        db.setToken(tokenService.createTokenString(db));
        userService.getBaseMapper().updateById(db);
        db.setTenantId(userTenantService.getDefaultTenantId(db));
        db.setPassword(null);
        db.setSalt(null);
        accountLogService.save(db, AccountLog.TYPE_LOGIN);
        requestLogService.saveByRequest();
        return R.data(db);
    }

}
