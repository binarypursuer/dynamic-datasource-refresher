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
public class ConsulProperties {
    /**
     * Consul地址
     */
    private String address;
    /**
     * Consul端口
     */
    private int port;
    /**
     * Consul访问令牌
     */
    private String accessToken;
}