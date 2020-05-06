package com.fangjc1986.ezmpro.sys.service.impl;

import com.fangjc1986.ezmpro.sys.mapper.ConfigMapper;
import com.fangjc1986.ezmpro.sys.model.Config;
import com.fangjc1986.ezmpro.sys.service.IConfigService;
import com.fangjc1986.support.service.BaseService;
import com.fangjc1986.support.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@Service
public class ConfigServiceImpl extends BaseService<ConfigMapper, Config> implements IConfigService {

    // 缓存 config 参数
    public static Map<String, String> params = new HashMap<>();
    // Config 版本 key
    private static String VERSION_KEY = "SYS_CONFIG_VERSION";
    // Config 版本
    private static String VERSION = "INIT";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置是否需要刷新换成
     */
    public String updateVersion() {
        String ver = UUID.randomUUID().toString();
        redisUtil.set(VERSION_KEY, ver );
        return ver ;
    }

    /**
     * 刷新换成列表
     */
    synchronized public void refreshConfigList() {
        String redisVersion = redisUtil.get(VERSION_KEY);
        if (redisVersion != null && redisVersion.equals(VERSION)) return;
        VERSION = redisVersion;
        params = new HashMap<>();
        List<Config> configs = list();
        for (Config config : configs) {
            params.put(config.getCode(), config.getValue());
        }
        if (redisVersion == null) {
            VERSION = updateVersion();
        }
    }

    /**
     * 获取对应参数值
     *
     * @param code
     * @return
     */
    synchronized public String get(String code) {
        refreshConfigList();
        return params.get(code);
    }

    /**
     * 获取对应参数值 数字类型
     * 不存在则返回 默认参数 def
     *
     * @param code
     * @param def
     * @return
     */
    synchronized public int get(String code, int def) {
        String value = get(code);
        if (value == null) return def;
        try{
            return Integer.parseInt(value);
        }catch (Exception e){
            return def;
        }
    }


    /**
     * 获取对应参数值 数字类型
     * 不存在则返回 默认参数 def
     *
     * @param code
     * @param def
     * @return
     */
    synchronized public long get(String code, long def) {
        String value = get(code);
        if (value == null) return def;
        try{
            return Long.parseLong(value);
        }catch (Exception e){
            return def;
        }
    }

    /**
     * 获取对应参数值 文本类型
     * 不存在则返回 默认参数 def
     *
     * @param code
     * @param def
     * @return
     */
    synchronized public String get(String code, String def) {
        String value = get(code);
        if (value == null) return def;
        return value;
    }


}
