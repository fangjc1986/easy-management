package com.fangjc1986.support.util;

import org.springframework.util.DigestUtils;

public class Md5Util {
    /**
     * 生成md5
     *
     * @param str 被加密字符串
     * @return String
     */
    private static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String getMD5(String... str) {
        StringBuilder sb = new StringBuilder("");
        for (String s : str) {
            sb.append(s);
        }
        return getMD5(sb.toString());
    }
}
