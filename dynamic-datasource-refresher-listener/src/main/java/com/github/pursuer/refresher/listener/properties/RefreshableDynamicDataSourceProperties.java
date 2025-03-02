package com.github.pursuer.refresher.listener.properties;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.pursuer.refresher.listener.properties.RefreshableDynamicDataSourceProperties.PREFIX;

/**
 * 可刷新动态数据源配置
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = PREFIX)
public class RefreshableDynamicDataSourceProperties implements DisposableBean {
    public static final String PREFIX = "spring.datasource.dynamic";
    /**
     * 每一个数据源
     */
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();

    @Override
    public void destroy() throws Exception {
        // 重置Map
        datasource = new LinkedHashMap<>();
    }
}
