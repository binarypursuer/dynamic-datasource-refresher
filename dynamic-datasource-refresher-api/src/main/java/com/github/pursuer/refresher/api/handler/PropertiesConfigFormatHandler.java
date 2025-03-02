package com.github.pursuer.refresher.api.handler;

import cn.hutool.core.util.StrUtil;
import com.github.pursuer.refresher.api.model.DsConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

/**
 * properties配置文件处理
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/3/1
 */
public class PropertiesConfigFormatHandler implements ConfigFormatHandler {

    private static final String URL_KEY = "spring.datasource.dynamic.datasource.{}.url";
    private static final String USERNAME_KEY = "spring.datasource.dynamic.datasource.{}.username";
    private static final String PASSWORD_KEY = "spring.datasource.dynamic.datasource.{}.password";
    private static final String DRIVER_CLASS_NAME_KEY = "spring.datasource.dynamic.datasource.{}.driver-class-name";

    @Override
    public String process(String content, DsConfig dsConfig) {
        // 根据content生成properties配置文件
        Properties properties = new Properties();
        try {
            //加载配置文件
            properties.load(new ByteArrayInputStream(content.getBytes()));
            //配置文件新增配置
            properties.setProperty(StrUtil.format(URL_KEY, dsConfig.getId()), dsConfig.getUrl());
            properties.setProperty(StrUtil.format(USERNAME_KEY, dsConfig.getId()), dsConfig.getUsername());
            properties.setProperty(StrUtil.format(PASSWORD_KEY, dsConfig.getId()), dsConfig.getPassword());
            properties.setProperty(StrUtil.format(DRIVER_CLASS_NAME_KEY, dsConfig.getId()), dsConfig.getDriverClassName());
            //配置文件转为字符串
            StringWriter newConfig = new StringWriter();
            properties.store(newConfig, null);
            return newConfig.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}