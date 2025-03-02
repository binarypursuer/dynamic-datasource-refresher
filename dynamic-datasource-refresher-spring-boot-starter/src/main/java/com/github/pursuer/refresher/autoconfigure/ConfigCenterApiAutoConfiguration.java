package com.github.pursuer.refresher.autoconfigure;

import com.alibaba.nacos.api.exception.NacosException;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.github.pursuer.refresher.api.core.ConfigCenterApi;
import com.github.pursuer.refresher.api.core.ConsulConfigCenterApi;
import com.github.pursuer.refresher.api.core.NacosConfigCenterApi;
import com.github.pursuer.refresher.api.properties.ConfigApiProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置中心API自动配置
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Configuration
@ConditionalOnClass(name = "com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration")
@EnableConfigurationProperties(ConfigApiProperties.class)
public class ConfigCenterApiAutoConfiguration {

    private final ConfigApiProperties properties;

    public ConfigCenterApiAutoConfiguration(ConfigApiProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.cloud.consul.config.ConsulConfigAutoConfiguration")
    @ConditionalOnProperty(prefix = "refresher", name = "type", havingValue = "CONSUL")
    public ConfigCenterApi consulConfigCenterApi() {
        return new ConsulConfigCenterApi(properties.getConsul());
    }

    @Bean
    @ConditionalOnClass(name = "com.alibaba.cloud.nacos.NacosConfigAutoConfiguration")
    @ConditionalOnProperty(prefix = "refresher", name = "type", havingValue = "NACOS")
    public ConfigCenterApi nacosConfigCenterApi() throws NacosException {
        return new NacosConfigCenterApi(properties.getNacos());
    }
}