package io.github.binarypursuer.refresher.api.handler;

import io.github.binarypursuer.refresher.api.enums.ConfigType;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置文件格式处理器缓存
 *
 * @author binarypursuer
 * @version 1.0
 * @date 2025/3/2
 */
public class ConfigFormatHandlerContext {
    /**
     * 缓存
     */
    private static final Map<String, ConfigFormatHandler> HANDLERS;

    static {
        HANDLERS = new HashMap<>();
        HANDLERS.put(ConfigType.YAML.getType(), new YamlConfigFormatHandler());
        HANDLERS.put(ConfigType.PROPERTIES.getType(), new PropertiesConfigFormatHandler());
    }

    /**
     * 注册自定义处理器
     *
     * @param type    类型
     * @param handler 处理器
     * @author binarypursuer
     * @date 2025/3/2
     * @since 1.0
     **/
    public static void registerHandler(String type, ConfigFormatHandler handler) {
        HANDLERS.put(type, handler);
    }

    /**
     * 获取处理器
     *
     * @param configType 配置文件类型
     * @return io.github.binarypursuer.api.handler.ConfigFormatHandler
     * @author binarypursuer
     * @date 2025/3/2
     * @since 1.0
     **/
    public static ConfigFormatHandler getHandler(ConfigType configType) {
        return HANDLERS.getOrDefault(configType.getType(), HANDLERS.get(ConfigType.YAML.getType()));
    }
}