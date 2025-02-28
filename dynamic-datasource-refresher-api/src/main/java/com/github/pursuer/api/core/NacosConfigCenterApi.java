package com.github.pursuer.api.core;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.pursuer.api.model.DsConfig;
import com.github.pursuer.api.properties.NacosProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static com.github.pursuer.api.model.DsConfig.Config;

/**
 * Nacos配置API
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Component
public class NacosConfigCenterApi extends ConfigCenterApi {

    private final ConfigService configService;

    public NacosConfigCenterApi(NacosProperties properties) throws NacosException {
        Properties prop = new Properties();
        prop.put("serverAddr", properties.getServerAddr());
        prop.put("port", properties.getPort());
        prop.put("namespace", properties.getNamespace());
        this.configService = NacosFactory.createConfigService(prop);
    }

    @Override
    protected String read(Config config) throws NacosException {
        return this.configService.getConfig(config.getDataId(), config.getGroup(), 5000);
    }

    @Override
    protected boolean write(Config config, String newConfig) throws NacosException {
        return configService.publishConfig(config.getDataId(), config.getGroup(), newConfig);
    }
}
