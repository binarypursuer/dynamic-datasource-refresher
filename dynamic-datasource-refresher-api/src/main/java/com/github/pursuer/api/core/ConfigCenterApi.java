package com.github.pursuer.api.core;

import cn.hutool.setting.yaml.YamlUtil;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.pursuer.api.enums.ConfigType;
import com.github.pursuer.api.model.DsConfig;

import static com.github.pursuer.api.model.DsConfig.Config;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.pursuer.api.constant.GlobalConstants.*;

/**
 * 数据源推送
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Slf4j
public abstract class ConfigCenterApi {

    @SuppressWarnings("unchecked")
    public boolean push(DsConfig dsConfig) throws NacosException {
        //结果集
        List<Boolean> results = new ArrayList<>(dsConfig.getConfigs().size());
        //遍历服务列表
        for (Config config : dsConfig.getConfigs()) {
            //获取配置
            String configStr = read(config);
            log.info("获取到的kv为：{}", configStr);
            //获取新的配置
            String newConfig = ConfigType.getHandler(config.getFileType()).process(configStr, dsConfig);
            log.info("新的配置为：{}", newConfig);
            //写入
            boolean result = write(config, newConfig);
            results.add(result);
            //日志记录结果
            log.info("修改结果为：{}", result);
        }
        return results.stream().allMatch(Boolean::booleanValue);
    }

    /**
     * 读取配置
     *
     * @param config 配置文件描述配置
     * @return java.lang.String
     * @author Pursuer
     * @date 2025/2/28
     * @since 1.0
     **/
    protected abstract String read(Config config) throws NacosException;

    /**
     * 写入配置
     *
     * @param config    配置文件描述配置
     * @param newConfig 新配置
     * @return boolean
     * @author Pursuer
     * @date 2025/2/28
     * @since 1.0
     **/
    protected abstract boolean write(Config config, String newConfig) throws NacosException;
}
