package com.github.pursuer.api.core;

import cn.hutool.core.lang.Opt;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.pursuer.api.properties.NacosProperties;

import java.util.Properties;

import static com.github.pursuer.api.model.DsConfig.Config;
import static com.github.pursuer.api.constant.GlobalConstants.*;

/**
 * Nacos配置API
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
public class NacosConfigCenterApi extends ConfigCenterApi {

    private final ConfigService configService;

    public NacosConfigCenterApi(NacosProperties properties) throws NacosException {
        Properties prop = new Properties();
        prop.put(SERVER_ADDR, properties.getServerAddr());
        Opt.ofBlankAble(properties.getNamespace()).ifPresent(v -> {
            prop.put(NAMESPACE, v);
        });
        prop.put(USERNAME, properties.getUsername());
        prop.put(PASSWORD, properties.getPassword());
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
