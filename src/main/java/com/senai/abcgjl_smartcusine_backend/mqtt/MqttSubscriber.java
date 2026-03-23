package com.senai.abcgjl_smartcusine_backend.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MqttSubscriber {

    private final MqttClient client;

    public MqttSubscriber(MqttClient client) {
        this.client = client;
    }

    @PostConstruct
    public void subscribe() throws Exception {
        client.subscribe("meu/topico", (topic, msg) -> {
            System.out.println("Mensagem recebida: " + new String(msg.getPayload()));
        });
    }
}
