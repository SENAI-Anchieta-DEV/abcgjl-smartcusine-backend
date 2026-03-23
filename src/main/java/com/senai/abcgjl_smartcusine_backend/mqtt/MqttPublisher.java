package com.senai.abcgjl_smartcusine_backend.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisher {

    private final MqttClient client;

    public MqttPublisher(MqttClient client) {
        this.client = client;
    }

    public void publish(String topic, String payload) throws Exception {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);

        client.publish(topic, message);
    }
}
