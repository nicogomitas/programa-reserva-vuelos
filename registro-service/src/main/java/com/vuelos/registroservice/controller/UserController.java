package com.vuelos.registroservice.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuelos.basedomains.dto.User;
import com.vuelos.basedomains.dto.UserEvent;
import com.vuelos.registroservice.kafka.UserProducer;
@RestController
@RequestMapping("/api/v1")
public class UserController {
    
    private UserProducer userProducer;

    public UserController(UserProducer userProducer) {
        this.userProducer = userProducer;
    }

    @PostMapping("/users/register")
    public String registerUser(@RequestBody User user) {
        user.setId(UUID.randomUUID().toString());

        UserEvent userEvent = new UserEvent();
        userEvent.setStatus("REGISTERED");
        userEvent.setMessage("User registered successfully");
        userEvent.setUser(user);

        // Enviar el evento a Kafka
        userProducer.sendMessage(userEvent);

        return "User registered successfully";
    }

    @PostMapping("/users/login")
    public String loginUser(@RequestBody User user) {
        // Aquí deberías validar si el usuario y la contraseña son correctos.

        UserEvent userEvent = new UserEvent();
        userEvent.setStatus("LOGGED_IN");
        userEvent.setMessage("User logged in successfully");
        userEvent.setUser(user);

        // Enviar el evento de login a Kafka
        userProducer.sendMessage(userEvent);

        return "User logged in successfully";
    }
}
