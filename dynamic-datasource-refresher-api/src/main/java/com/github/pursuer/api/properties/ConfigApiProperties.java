package com.github.pursuer.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 配置中心API配置
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Data
@ConfigurationProperties(prefix = "refresher")
public class ConfigApiProperties {
    /**
     * 配置中心类型
     */
    private ConfigType type;
    /**
     * Consul配置
     */
    @NestedConfigurationProperty
    private ConsulProperties consul;
    /**
     * Nacos配置
     */
    @NestedConfigurationProperty
    private NacosProperties nacos;

    public enum ConfigType {
        NACOS,
        CONSUL
    }
}