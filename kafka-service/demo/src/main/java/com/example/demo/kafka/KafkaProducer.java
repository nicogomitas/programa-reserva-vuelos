package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Value("${spring.kafka.topic.notificaciones_compra}")
    private String topicCompra;

    @Value("${spring.kafka.topic.notificaciones_suscripcion}")
    private String topicSuscripcion;

    @Value("${spring.kafka.topic.notificaciones_recordatorio}")
    private String topicRecordatorio;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Método modificado para enviar mensajes a diferentes topics
    public void sendMessageToCompra(String message){
        LOGGER.info(String.format("Sending message: '%s' to topic: '%s'", message, topicCompra));
        kafkaTemplate.send(topicCompra, message);
    }

    public void sendMessageToSuscripcion(String message){
        LOGGER.info(String.format("Sending message: '%s' to topic: '%s'", message, topicSuscripcion));
        kafkaTemplate.send(topicSuscripcion, message);
    }

    public void sendMessageToRecordatorio(String message){
        LOGGER.info(String.format("Sending message: '%s' to topic: '%s'", message, topicRecordatorio));
        kafkaTemplate.send(topicRecordatorio, message);
    }
}
