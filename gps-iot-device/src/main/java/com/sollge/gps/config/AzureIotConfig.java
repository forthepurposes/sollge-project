package com.sollge.gps.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Setter
@Getter
@Configuration
@RequiredArgsConstructor
public class AzureIotConfig {

    @Value("${azure.iot.hub.hostname}")
    private String hostname;

    @Value("${azure.iot.device.id}")
    private String deviceId;

    @Value("${azure.iot.sas.token}")
    private String sasToken;

    @Bean
    public @NonNull MqttClient mqttClient() {
        try {
            String brokerUri = "ssl://" + hostname + ":8883";
            MqttClient client = new MqttClient(brokerUri, deviceId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            options.setUserName(hostname + "/" + deviceId + "/?api-version=2021-04-12");
            options.setPassword(sasToken.toCharArray());
            options.setCleanSession(true);

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new SecureRandom());
            options.setSocketFactory(sslContext.getSocketFactory());

            client.connect(options);
            return client;
        } catch (NoSuchAlgorithmException | MqttException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}