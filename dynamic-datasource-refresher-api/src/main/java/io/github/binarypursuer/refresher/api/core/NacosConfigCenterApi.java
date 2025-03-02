package io.github.binarypursuer.refresher.api.core;

import cn.hutool.core.lang.Opt;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import io.github.binarypursuer.refresher.api.model.DsConfig;
import io.github.binarypursuer.refresher.api.properties.NacosProperties;
import io.github.binarypursuer.refresher.api.constant.GlobalConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * Nacos配置API
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Slf4j
public class NacosConfigCenterApi extends ConfigCenterApi {

    private final ConfigService configService;

    public NacosConfigCenterApi(NacosProperties properties) throws NacosException {
        Properties prop = new Properties();
        prop.put(GlobalConstants.SERVER_ADDR, properties.getServerAddr());
        Opt.ofBlankAble(properties.getNamespace()).ifPresent(v -> {
            prop.put(GlobalConstants.NAMESPACE, v);
        });
        prop.put(GlobalConstants.USERNAME, properties.getUsername());
        prop.put(GlobalConstants.PASSWORD, properties.getPassword());
        this.configService = NacosFactory.createConfigService(prop);
    }

    @Override
    protected String read(DsConfig.ServiceConfig serviceConfig) {
        try {
            return configService.getConfig(serviceConfig.getDataId(), serviceConfig.getGroup(), 5000);
        } catch (NacosException e) {
            log.error("读取Nacos配置失败，原因：", e);
            return null;
        }
    }

    @Override
    protected boolean write(DsConfig.ServiceConfig serviceConfig, String newConfig) {
        try {
            return configService.publishConfig(serviceConfig.getDataId(), serviceConfig.getGroup(), newConfig, serviceConfig.getType().getType());
        } catch (NacosException e) {
            log.error("修改Nacos配置失败，原因：", e);
            return false;
        }
    }
}
