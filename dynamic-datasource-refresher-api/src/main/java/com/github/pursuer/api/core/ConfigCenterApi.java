package com.github.pursuer.api.core;

import cn.hutool.setting.yaml.YamlUtil;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.pursuer.api.model.DsConfig;

import static com.github.pursuer.api.model.DsConfig.Config;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源推送
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Slf4j
public abstract class ConfigCenterApi {

    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DRIVER_CLASS_NAME = "driver-class-name";

    @SuppressWarnings("unchecked")
    public boolean push(DsConfig dsConfig) {
        //结果集
        List<Boolean> results = new ArrayList<>(dsConfig.getConfigs().size());
        //遍历服务列表
        for (Config config : dsConfig.getConfigs()) {
            //获取配置
            String configStr = read(config);
            log.info("获取到的kv为：{}", configStr);
            //解析配置为Map集合
            Map map = YamlUtil.load(new ByteArrayInputStream(configStr.getBytes()), Map.class);
            //获取Spring配置
            Map<Object, Object> spring = (LinkedHashMap<Object, Object>) map.get("spring");
            //获取数据源
            Map<Object, Object> datasource = (LinkedHashMap<Object, Object>) spring.get("datasource");
            //获取动态数据源配置
            Map<Object, Object> dynamic = (LinkedHashMap<Object, Object>) datasource.get("dynamic");
            //获取动态数据源
            Map<Object, Object> dynamicDatasource = (LinkedHashMap<Object, Object>) dynamic.get("datasource");
            //拷贝默认数据源配置至新数据源
            Map<Object, Object> newDatasource = new LinkedHashMap<>();
            newDatasource.put(URL, dsConfig.getUrl());
            newDatasource.put(DRIVER_CLASS_NAME, dsConfig.getDriverClassName());
            newDatasource.put(USERNAME, dsConfig.getUsername());
            newDatasource.put(PASSWORD, dsConfig.getPassword());
            //替换数据库
            dynamicDatasource.put(dsConfig.getId(), newDatasource);

            //重新转换为Yaml文件
            StringWriter newConfig = new StringWriter();
            YamlUtil.dump(map, newConfig);
            log.info("新的配置为：{}", newConfig);
            //写入
            boolean result = write(config, newConfig.toString());
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
