package com.github.pursuer.api.enums;

import cn.hutool.core.lang.Opt;
import com.github.pursuer.api.handler.ConfigFormatHandler;
import com.github.pursuer.api.handler.YamlConfigFormatHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 配置文件类型
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/3/1
 */
@Getter
@AllArgsConstructor
public enum ConfigType {
    /**
     * yaml配置
     */
    YAML("yaml", new YamlConfigFormatHandler()),
    /**
     * properties配置文件
     */
    PROPERTIES("properties", new YamlConfigFormatHandler());

    private final String type;
    private final ConfigFormatHandler handler;

    private static final Map<String, ConfigType> TYPE_MAP = Map.of(
            YAML.getType(), YAML,
            PROPERTIES.getType(), PROPERTIES
    );

    /**
     * 获取处理器
     *
     * @param type 文件类型
     * @return com.github.pursuer.api.handler.ConfigFormatHandler
     * @author Pursuer
     * @date 2025/3/1
     * @since 1.0
     **/
    public static ConfigFormatHandler getHandler(String type) {
        return Opt.ofNullable(TYPE_MAP.get(type).getHandler()).orElse(YAML.handler);
    }
}
