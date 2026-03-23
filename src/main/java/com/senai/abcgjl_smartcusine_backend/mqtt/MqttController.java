package com.senai.abcgjl_smartcusine_backend.mqtt;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttPublisher publisher;

    public MqttController(MqttPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String message) throws Exception {
        publisher.publish("meu/topico", message);
        return "Mensagem enviada!";
    }
}