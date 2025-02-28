package com.github.pursuer.api.properties;

import lombok.Data;

/**
 * Consul配置
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Data
public class NacosProperties {
    /**
     * Nacos地址
     */
    private String serverAddr;
    /**
     * Nacos端口
     */
    private int port;
    /**
     * Nacos命名空间
     */
    private String namespace;
}