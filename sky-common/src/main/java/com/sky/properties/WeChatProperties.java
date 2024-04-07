package com.sky.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.wechat")
@Data
public class WeChatProperties {

    private String appid; //little program's appid
    private String secret; //little program's secret key
    private String mchid; //business account
    private String mchSerialNo; // business API certificate serialNumber
    private String privateKeyFilePath; //business secret key file
    private String apiV3Key; //certificate decryption key
    private String weChatPayCertFilePath; // weChat certificate
    private String notifyUrl; // return address after payment successful
    private String refundNotifyUrl; //return address after refund successful

}
