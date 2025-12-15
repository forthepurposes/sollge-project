package com.sollge.gps.mqtt;

import com.sollge.gps.config.AzureIotConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MqttSender {

    private final MqttClient mqttClient;
    private final String topic;

    public MqttSender(
            final @NonNull AzureIotConfig config,
            final @Value("${azure.iot.device.id}") String deviceId
    ) {
        this.mqttClient = config.mqttClient();
        this.topic = "devices/" + deviceId + "/messages/events/";
    }

    public void send(final @NonNull String payload) throws MqttException {
        var message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        message.setQos(1);

        mqttClient.publish(topic, message);
    }
}