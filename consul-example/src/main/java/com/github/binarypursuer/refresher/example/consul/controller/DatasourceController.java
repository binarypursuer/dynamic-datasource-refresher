package com.github.binarypursuer.refresher.example.consul.controller;

import io.github.binarypursuer.refresher.api.core.ConfigCenterApi;
import io.github.binarypursuer.refresher.api.enums.ConfigType;
import io.github.binarypursuer.refresher.api.model.DsConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源API
 *
 * @author binarypursuer
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
        config.setDataId("config/consul-example/data");
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