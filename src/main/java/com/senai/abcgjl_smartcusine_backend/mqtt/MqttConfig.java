package com.senai.abcgjl_smartcusine_backend.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Bean
    public MqttClient mqttClient() throws Exception {
        String broker = "tcp://localhost:1883";
        String clientId = "springboot-client";

        MqttClient client = new MqttClient(broker, clientId);
        client.connect();

        return client;
    }
}
