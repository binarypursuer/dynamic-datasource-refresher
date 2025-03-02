package com.github.pursuer.refresher.listener.listener;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.github.pursuer.refresher.listener.event.DatasourceRefreshCompletedEvent;
import com.github.pursuer.refresher.listener.properties.RefreshableDynamicDataSourceProperties;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 配置刷新监听器
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Order(0)
@Configuration
@ConditionalOnClass(DynamicDataSourceAutoConfiguration.class)
@EnableConfigurationProperties(RefreshableDynamicDataSourceProperties.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class, DynamicDataSourceAutoConfiguration.class})
public class ConfigRefreshListener implements ApplicationListener<RefreshScopeRefreshedEvent>, ApplicationContextAware {

    private final RefreshableDynamicDataSourceProperties properties;
    private final DataSource dataSource;
    private final DefaultDataSourceCreator creator;
    private ApplicationContext context;

    public ConfigRefreshListener(RefreshableDynamicDataSourceProperties properties, DataSource dataSource, DefaultDataSourceCreator creator) {
        this.properties = properties;
        this.dataSource = dataSource;
        this.creator = creator;
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        //获取最新的数据源
        Map<String, DataSourceProperty> datasource = properties.getDatasource();
        //获取原来的数据源
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        //获取当前配置中心最新的数据源
        Set<String> keys = datasource.keySet();
        //删除应用中配置中心没有的数据源
        ds.getDataSources().entrySet().removeIf(next -> !keys.contains(next.getKey()));
        //获取当前应用的数据源
        Set<String> dataSourceKeys = ds.getDataSources().keySet();
        //新加的数据源集合
        Map<String, DataSourceProperty> newDatasource = new LinkedHashMap<>();
        //添加新的数据源
        datasource.forEach((key, value) -> {
            if (!dataSourceKeys.contains(key)) {
                newDatasource.put(key, value);
                ds.addDataSource(key, creator.createDataSource(value));
            }
        });
        //发布数据源刷新完成事件
        context.publishEvent(new DatasourceRefreshCompletedEvent(event.getSource(), newDatasource));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
