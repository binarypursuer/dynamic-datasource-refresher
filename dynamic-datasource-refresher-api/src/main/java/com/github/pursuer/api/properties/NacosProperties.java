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
     * Nacos命名空间  public默认不填
     */
    private String namespace;
    /**
     * Nacos用户名
     */
    private String username;
    /**
     * Nacos密码
     */
    private String password;
}