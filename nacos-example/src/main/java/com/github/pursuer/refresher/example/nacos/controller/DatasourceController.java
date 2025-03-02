package com.github.pursuer.refresher.example.nacos.controller;

import com.github.pursuer.refresher.api.core.ConfigCenterApi;
import com.github.pursuer.refresher.api.enums.ConfigType;
import com.github.pursuer.refresher.api.model.DsConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源API
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/3/2
 */
@RestController
@RequestMapping("/datasource")
public class DatasourceController {

    @Resource
    private ConfigCenterApi configCenterApi;

    @RequestMapping("/push")
    public boolean addDatasource() {
        DsConfig dsConfig = new DsConfig();
        List<DsConfig.ServiceConfig> configs = new ArrayList<>();
        DsConfig.ServiceConfig config = new DsConfig.ServiceConfig();
        config.setDataId("nacos-example");
        config.setGroup("DEFAULT_GROUP");
        config.setType(ConfigType.YAML);
        configs.add(config);
        dsConfig.setServiceConfigs(configs);
        dsConfig.setId("slave");
        dsConfig.setUrl("jdbc:mysql://127.0.0.1:3306/monitor_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
        dsConfig.setUsername("root");
        dsConfig.setPassword("root");
        dsConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return configCenterApi.publish(dsConfig) == configs.size();
    }
}