package com.github.pursuer.api;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.alibaba.nacos.api.exception.NacosException;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.github.pursuer.api.core.ConfigCenterApi;
import com.github.pursuer.api.core.ConsulConfigCenterApi;
import com.github.pursuer.api.core.NacosConfigCenterApi;
import com.github.pursuer.api.properties.ConfigApiProperties;
import com.github.pursuer.api.properties.ConsulProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.consul.ConsulAutoConfiguration;
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
@ConditionalOnClass(DynamicDataSourceAutoConfiguration.class)
@EnableConfigurationProperties(ConfigApiProperties.class)
public class ConfigCenterApiAutoConfiguration {

    private final ConfigApiProperties properties;

    public ConfigCenterApiAutoConfiguration(ConfigApiProperties properties) {
        this.properties = properties;
    }

//    @Bean
//    @ConditionalOnClass(ConsulAutoConfiguration.class)
//    @ConditionalOnProperty(prefix = "refresher", name = "type", havingValue = "consul")
//    public ConfigCenterApi consulConfigCenterApi() {
//        return new ConsulConfigCenterApi(properties.getConsul());
//    }

    @Bean
    @ConditionalOnClass(NacosConfigAutoConfiguration.class)
    @ConditionalOnProperty(prefix = "refresher", name = "type", havingValue = "NACOS")
    public ConfigCenterApi nacosConfigCenterApi() throws NacosException {
        return new NacosConfigCenterApi(properties.getNacos());
    }
}
