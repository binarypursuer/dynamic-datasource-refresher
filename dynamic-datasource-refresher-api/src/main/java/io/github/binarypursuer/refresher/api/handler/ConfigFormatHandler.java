package io.github.binarypursuer.refresher.api.handler;

import io.github.binarypursuer.refresher.api.model.DsConfig;

/**
 * 配置格式处理器
 *
 * @author binarypursuer
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
     * @author binarypursuer
     * @date 2025/3/1
     * @since 1.0
     **/
    String process(String content, DsConfig dsConfig);
}
