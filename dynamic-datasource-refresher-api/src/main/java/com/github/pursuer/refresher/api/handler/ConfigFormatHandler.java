package com.github.pursuer.refresher.api.handler;

import com.github.pursuer.refresher.api.model.DsConfig;

/**
 * 配置格式处理器
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/3/1
 */
public interface ConfigFormatHandler {
    /**
     * 接收配置内容，返回处理后的新配置内容
     *
     * @param content  配置内容
     * @param dsConfig 数据库配置
     * @return java.lang.String
     * @author Pursuer
     * @date 2025/3/1
     * @since 1.0
     **/
    String process(String content, DsConfig dsConfig);
}
