package com.github.pursuer.refresher.api.core;

import cn.hutool.core.lang.Opt;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.pursuer.refresher.api.handler.ConfigFormatHandlerContext;
import com.github.pursuer.refresher.api.model.DsConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据源推送
 *
 * @author Pursuer
 * @version 1.0
 * @date 2025/2/28
 */
@Slf4j
public abstract class ConfigCenterApi {
    /**
     * 添加数据源
     *
     * @param dsConfig 数据源配置
     * @return int     成功数量
     * @author Pursuer
     * @date 2025/3/2
     * @since 1.0
     **/
    public int publish(DsConfig dsConfig) {
        //结果集
        AtomicInteger resultCount = new AtomicInteger(0);
        //遍历服务列表
        for (DsConfig.ServiceConfig serviceConfig : dsConfig.getServiceConfigs()) {
            //获取配置并处理
            Opt.ofNullable(read(serviceConfig)).ifPresentOrElse(configStr -> {
                log.info("{}获取到的kv为：{}", serviceConfig.getDataId(), configStr);
                //获取新的配置
                String newConfig = ConfigFormatHandlerContext.getHandler(serviceConfig.getType()).process(configStr, dsConfig);
                log.info("{}新的配置为：{}", serviceConfig.getDataId(), newConfig);
                //写入
                boolean result = write(serviceConfig, newConfig);
                Opt.of(result).ifPresent(t -> resultCount.incrementAndGet());
                //日志记录结果
                log.info("{}修改结果为：{}", serviceConfig.getDataId(), result);
            }, () -> {
                log.error("未获取到配置：{}", serviceConfig.getDataId());
            });
        }
        return resultCount.get();
    }

    /**
     * 读取配置
     *
     * @param serviceConfig 配置文件描述配置
     * @return java.lang.String
     * @author Pursuer
     * @date 2025/2/28
     * @since 1.0
     **/
    protected abstract String read(DsConfig.ServiceConfig serviceConfig);

    /**
     * 写入配置
     *
     * @param serviceConfig 配置文件描述配置
     * @param newConfig     新配置
     * @return boolean
     * @author Pursuer
     * @date 2025/2/28
     * @since 1.0
     **/
    protected abstract boolean write(DsConfig.ServiceConfig serviceConfig, String newConfig);
}
