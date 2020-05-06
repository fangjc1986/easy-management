package com.fangjc1986.support.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: IPUtils <br/>
 * Description: IP查询工具 <br/>
 * Date: 2016年4月28日 上午11:23:53 <br/>
 * <br/>
 *
 * @author Administrator(邮箱)
 * <p>
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息<br/>
 */

public class IPUtils {
    /**
     * getRemoteIP:获取远程请求客户端的外网IP <br/>
     *
     * @param request 请求实体对象
     * @return ip 外网ip<br/>
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
