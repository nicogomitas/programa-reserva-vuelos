package com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // @Value("${spring.kafka.topic.name}")
    // private String javaguides;

    // @Value("${spring.kafka.topic-json.name}")
    // private String topicJsonName;

    // Función que recibe el nombre del topic como parámetro
    public NewTopic crearTopic(String nombreTopic) {
        return TopicBuilder.name(nombreTopic)
                .build();
    }

    @Bean
    public NewTopic topicNotificacionesCompra() {
        return crearTopic("notificaciones_compra");
    }

    @Bean
    public NewTopic topicNotificacionesSuscripcion() {
        return crearTopic("notificaciones_suscripcion");
    }

    @Bean
    public NewTopic topicNotificacionesRecordatorio() {
        return crearTopic("notificaciones_recordatorio");
    }
    // @Bean
    // public NewTopic javaguidesJsonTopic(){
    //     return TopicBuilder.name(topicJsonName)
    //             .build();
    // }
}