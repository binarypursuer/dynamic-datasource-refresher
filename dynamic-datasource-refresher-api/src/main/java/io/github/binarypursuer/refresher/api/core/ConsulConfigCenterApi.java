package io.github.binarypursuer.refresher.api.core;

import cn.hutool.core.codec.Base64;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import io.github.binarypursuer.refresher.api.properties.ConsulProperties;
import io.github.binarypursuer.refresher.api.model.DsConfig;

/**
 * Consul配置API
 *
 * @author binarypursuer
 * @version 1.0
 * @date 2025/2/28
 */
public class ConsulConfigCenterApi extends ConfigCenterApi {

    private final ConsulProperties properties;
    private final ConsulClient client;

    public ConsulConfigCenterApi(ConsulProperties properties) {
        this.properties = properties;
        this.client = new ConsulClient(properties.getHost(), properties.getPort());
    }

    @Override
    protected String read(DsConfig.ServiceConfig serviceConfig) {
        Response<GetValue> kvValue = client.getKVValue(serviceConfig.getDataId(), properties.getAccessToken());
        return Base64.decodeStr(kvValue.getValue().getValue());
    }

    @Override
    protected boolean write(DsConfig.ServiceConfig serviceConfig, String newConfig) {
        Response<Boolean> result = client.setKVValue(serviceConfig.getDataId(), newConfig, properties.getAccessToken(), null);
        return result.getValue();
    }
}
