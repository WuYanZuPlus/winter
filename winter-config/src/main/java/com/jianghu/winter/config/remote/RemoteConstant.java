package com.jianghu.winter.config.remote;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * RemoteConstant
 *
 * @author daniel.hu
 * @date 2019-08-03
 */
@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoteConstant {
    public static final String GATEWAY_URL = "${winter.gateway.url:}";

    public static final String CLIENT_ID = "client-id";
    public static final String CLIENT_ID_VAL = "client-id-value";

    public static final String CLIENT_SECRET = "client-secret";
    public static final String CLIENT_SECRET_VAL = "client-secret-value";

}
