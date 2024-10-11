package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.kafka.KafkaProducer;

@RestController
@RequestMapping("/kafka")
public class MessageController {

    private KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    // Ejemplo de llamada: 
    // http://localhost:8080/api/v1/kafka/publish?topic=notificaciones_compra&message=Compra%20verificada

    @GetMapping("/publish")
    public ResponseEntity<String> publish(
            @RequestParam("topic") String topic, 
            @RequestParam("message") String message) {

        switch (topic) {
            case "notificaciones_compra":
                kafkaProducer.sendMessageToCompra(message);
                break;
            case "notificaciones_suscripcion":
                kafkaProducer.sendMessageToSuscripcion(message);
                break;
            case "notificaciones_recordatorio":
                kafkaProducer.sendMessageToRecordatorio(message);
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid topic name: " + topic);
        }

        return ResponseEntity.ok("Message sent to topic: " + topic);
    }
}
