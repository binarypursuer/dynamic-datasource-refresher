package com.github.pursuer.refresher.listener.event;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据源刷新完成事件
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Getter
public class DatasourceRefreshCompletedEvent extends ApplicationEvent {
    /**
     * 新增的数据源
     */
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();

    public DatasourceRefreshCompletedEvent(Object source, Map<String, DataSourceProperty> datasource) {
        super(source);
        this.datasource = datasource;
    }
}
