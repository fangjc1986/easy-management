package com.fangjc1986.ezmpro.access.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fangjc1986.ezmpro.sys.model.AccountLog;
import com.fangjc1986.ezmpro.sys.model.Auth;
import com.fangjc1986.ezmpro.sys.model.Tenant;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.service.impl.AccountLogServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.AuthServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.TenantServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.UserServiceImpl;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.helper.SystemHelper;
import com.fangjc1986.support.service.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/access/account")
@Api(description = "我的账户")
public class AccountController extends BaseController {

    private UserServiceImpl userService;
    private AuthServiceImpl authService;
    private TenantServiceImpl tenantService;
    private AccountLogServiceImpl accountLogService;

    @PostMapping(value = "/signOut")
    @ApiOperation(value = "用户登出")
    @Transactional(rollbackFor = Exception.class)
    R<String> signOut() {
        User user = SystemHelper.loginUser();
        userService.update(
            new UpdateWrapper<User>()
                .set("token", "SIGN OUT")
                .eq("id", user.getId())
        );
        accountLogService.save(user, AccountLog.TYPE_LOGOUT);
        return R.data();
    }

    @GetMapping(value = "/getMyInfo")
    @ApiOperation(value = "获取我的个人信息")
    R<User> getMyInfo() {
        User user = SystemHelper.loginUser();
        user.setPassword(null);
        user.setSalt(null);
        user.setTenantId(SystemHelper.tenantId());
        return R.data(user);
    }


    @PostMapping("/password")
    @ApiOperation(value = "修改密码")
    R<String> password(@RequestBody @Validated({User.ChangePass.class}) User form) {
        if (!form.getId().equals(SystemHelper.loginUserId())) {
            throw new ServiceException("非法！");
        }
        if (!userService.validatePassword(form, SystemHelper.loginUser())) {
            throw new ServiceException("原密码不正确！");
        }
        form.setPassword(form.getNewPassword());
        return R.status(userService.updatePassword(form));
    }

    @GetMapping("/authList")
    @ApiOperation(value = "获取我的权限可以查看的页面")
    R<List<Auth>> authList() {
        User user = SystemHelper.loginUser();
        if (user.getRoot().equals(1)) {
            return R.data(authService.list());
        }
        return R.data(
            userService.pushParentRowCondition(
                authService,
                authService.getBaseMapper().listByUserIdAndTenantId(
                    user.getId(), SystemHelper.tenantId()
                ),
                new BaseService.PushParentRowCondition<Auth>() {
                    @Override
                    public boolean needParent(Auth object) {
                        return object.getType().equals(1);
                    }
                }
            )
        );
    }

    @GetMapping("/tenantList")
    @ApiOperation(value = "获取我的租户列表")
    R<List<Tenant>> tenantList() {
        if (SystemHelper.loginUser().getRoot().equals(1)) {
            return R.data(
                tenantService.list()
            );
        }
        return R.data(
            tenantService.getBaseMapper().selectByUserId(
                SystemHelper.loginUserId()
            )
        );
    }
}
