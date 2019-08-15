package com.jianghu.winter.config.remote;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * GatewayRequestInterceptor
 *
 * @author daniel.hu
 */
@Slf4j
@Component
public class GatewayRequestInterceptor implements RequestInterceptor {

    @Setter
    @Value(RemoteConstant.GATEWAY_URL)
    private String gatewayUrl;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (StringUtils.startsWith(gatewayUrl, "http://gateway")) {
            requestTemplate.header(RemoteConstant.CLIENT_ID, RemoteConstant.CLIENT_ID_VAL);
            requestTemplate.header(RemoteConstant.CLIENT_SECRET, RemoteConstant.CLIENT_SECRET_VAL);
        }
    }

}
