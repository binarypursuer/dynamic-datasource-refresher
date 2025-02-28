package com.github.pursuer.api;

import com.github.pursuer.api.core.ConfigCenterApi;
import com.github.pursuer.api.core.ConsulConfigCenterApi;
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
@RequiredArgsConstructor
@EnableConfigurationProperties(ConfigApiProperties.class)
public class ConfigCenterApiAutoConfiguration {

    private final ConfigApiProperties properties;

    @Bean
    @ConditionalOnClass(ConsulAutoConfiguration.class)
    @ConditionalOnProperty(prefix = "refresher.config", name = "type", havingValue = "CONSUL")
    public ConfigCenterApi consulConfigCenterApi() {
        return new ConsulConfigCenterApi(properties.getConsul());
    }
}
