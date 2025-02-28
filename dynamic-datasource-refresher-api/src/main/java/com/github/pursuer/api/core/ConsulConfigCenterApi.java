package com.github.pursuer.api.core;

import cn.hutool.core.codec.Base64;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.github.pursuer.api.properties.ConsulProperties;
import org.springframework.stereotype.Component;

/**
 * Consul配置API
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Component
public class ConsulConfigCenterApi extends ConfigCenterApi {

    private final ConsulProperties properties;
    private final ConsulClient client;

    public ConsulConfigCenterApi(ConsulProperties properties) {
        this.properties = properties;
        this.client = new ConsulClient(properties.getAddress(), properties.getPort());
    }

    @Override
    protected String read(String dataId) {
        Response<GetValue> kvValue = client.getKVValue(dataId, properties.getAccessToken());
        return Base64.decodeStr(kvValue.getValue().getValue());
    }

    @Override
    protected boolean write(String dataId, String newConfig) {
        Response<Boolean> result = client.setKVValue(dataId, newConfig, properties.getAccessToken(), null);
        return result.getValue();
    }
}
