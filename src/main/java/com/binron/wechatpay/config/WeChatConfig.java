package com.binron.wechatpay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 微信配置类
 */
@Configuration
@PropertySource(value="classpath:application.properties")
@Data
public class WeChatConfig {

    /**
     * 公众号appid
     */
    @Value("${wxpay.appid}")
    private String appId;

    /**
     * 公众号秘钥
     */
    @Value("${wxpay.appsecret}")
    private String appsecret;

    /**
     * 商户号id
     */
    @Value("${wxpay.mer_id}")
    private String mchId;

    /**
     * 支付key
     */
    @Value("${wxpay.key}")
    private String key;

    /**
     * 微信支付回调url
     */
    @Value("${wxpay.callback}")
    private String payCallbackUrl;


    /**
     * 统一下单url
     */
    private static final String UNIFIED_ORDER_URL = "http://api.xdclass.net:8081/pay/unifiedorder";


    public static String getUnifiedOrderUrl() {
        return UNIFIED_ORDER_URL;
    }


}
