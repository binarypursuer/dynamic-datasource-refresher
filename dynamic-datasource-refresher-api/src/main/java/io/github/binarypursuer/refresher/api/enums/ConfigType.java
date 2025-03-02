package io.github.binarypursuer.refresher.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 配置文件类型
 *
 * @author binarypursuer
 * @version 1.0
 * @date 2025/3/1
 */
@Getter
@AllArgsConstructor
public enum ConfigType {
    /**
     * yaml配置
     */
    YAML("yaml"),
    /**
     * properties配置文件
     */
    PROPERTIES("properties");

    private final String type;
}
