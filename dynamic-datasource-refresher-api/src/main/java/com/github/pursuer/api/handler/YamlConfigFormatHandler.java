package com.github.pursuer.api.handler;

import cn.hutool.setting.yaml.YamlUtil;
import com.github.pursuer.api.model.DsConfig;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.pursuer.api.constant.GlobalConstants.*;
import static com.github.pursuer.api.constant.GlobalConstants.PASSWORD;

/**
 * Yaml配置文件处理器
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/3/1
 */
public class YamlConfigFormatHandler implements ConfigFormatHandler {

    @Override
    public String process(String content, DsConfig dsConfig) {
        //解析配置为Map集合
        Map map = YamlUtil.load(new ByteArrayInputStream(content.getBytes()), Map.class);
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
        newDatasource.put(DB_URL, dsConfig.getUrl());
        newDatasource.put(DRIVER_CLASS_NAME, dsConfig.getDriverClassName());
        newDatasource.put(USERNAME, dsConfig.getUsername());
        newDatasource.put(PASSWORD, dsConfig.getPassword());
        //替换数据库
        dynamicDatasource.put(dsConfig.getId(), newDatasource);

        //重新转换为Yaml文件
        StringWriter newConfig = new StringWriter();
        YamlUtil.dump(map, newConfig);

        return newConfig.toString();
    }
}