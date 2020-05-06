package com.fangjc1986.ezmpro.sys.service.impl;

import com.fangjc1986.ezmpro.sys.model.RequestLog;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.support.helper.RequestWrapper;
import com.fangjc1986.support.helper.SystemHelper;
import com.fangjc1986.support.service.BaseMongoService;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author EricFang
 * @since 2020-04-10
 */
@Service
public class RequestLogServiceImpl extends BaseMongoService<RequestLog> {

    public static final List<String> FILTER_API = new ArrayList<>();

    static {
        FILTER_API.add("/access/account/password");
        FILTER_API.add("/common/sign/in");
    }


    @Override
    public void setUpdateFields(Update update, RequestLog doc) {
        update.set("response", doc.getResponse());
        update.set("success", doc.getSuccess());
    }

    /**
     * 将请求参数写入日志
     */
    public void saveByRequest() {
        if (!request.getMethod().equalsIgnoreCase("post")) return;
        if (request.getAttribute("REQUEST_LOG") != null) return;
        User user = SystemHelper.loginUser();
        try {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            String body = clearSecurityInfo(requestWrapper.getBody());
            RequestLog requestLog = new RequestLog();
            requestLog.setUserId(user.getId());
            requestLog.setUserName(user.getUserName());
            requestLog.setApi(request.getRequestURI());
            requestLog.setParams(body);
            save(requestLog);
            request.setAttribute("REQUEST_LOG", requestLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将返回参数更新到日志
     *
     * @param response
     */
    public void updateByRequest(String response, Integer success) {
        if (!request.getMethod().equalsIgnoreCase("post")) return;
        if (request.getAttribute("REQUEST_LOG") == null) return;
        RequestLog requestLog = (RequestLog) request.getAttribute("REQUEST_LOG");
        requestLog.setResponse(response);
        requestLog.setSuccess(success);
        updateById(requestLog);
    }

    public String clearSecurityInfo(String body) {
        if (FILTER_API.contains(request.getRequestURI().toLowerCase())) return "*****";
        if (request.getRequestURI().toLowerCase().equals("/sys/user/save")) {
            String regex = "\"password\":\".*?\",\"rePassword\":\".*?\"";
            return body.replaceAll(regex, "");
        }
        return body;
    }

}
