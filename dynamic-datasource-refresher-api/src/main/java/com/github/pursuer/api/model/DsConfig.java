package com.github.pursuer.api.model;

import lombok.Data;

import java.util.List;

/**
 * 数据源配置对象
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Data
public class DsConfig {
    /**
     * 配置ID列表
     */
    private List<Config> configs;
    /**
     * 数据源标识ID
     */
    private String id;
    /**
     * 数据源连接地址
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 驱动类名
     */
    private String driverClassName;

    @Data
    public static class Config {
        /**
         * 应用在配置中心的配置ID，如Consul: config/{serviceName},rdc/data
         */
        private String dataId;
        /**
         * 配置组
         */
        private String group;
    }
}
