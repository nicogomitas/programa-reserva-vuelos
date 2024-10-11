package com.vuelos.registroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vuelos.basedomains.dto.User;
import com.vuelos.basedomains.dto.UserEvent;
import com.vuelos.registroservice.kafka.UserProducer;
import com.vuelos.registroservice.model.MyAppUser;
import com.vuelos.registroservice.model.MyAppUserRepository;
import java.util.Optional;
@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserProducer userProducer;

    @PostMapping("/req/signup")
    public MyAppUser createUser(@RequestBody MyAppUser user) {
        // Registrar el usuario en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        MyAppUser savedUser = myAppUserRepository.save(user);

        // Crear evento de Kafka para el registro
        UserEvent userEvent = new UserEvent();
        userEvent.setStatus("REGISTERED");
        userEvent.setMessage("User registered successfully");
        User kafkaUser = new User(savedUser.getId().toString(), savedUser.getUsername(), savedUser.getEmail(), null);
        userEvent.setUser(kafkaUser);

        // Enviar evento a Kafka
        userProducer.sendMessage(userEvent);

        return savedUser;
    }

 

@PostMapping("/req/login")
public String loginUser(@RequestBody MyAppUser loginUser) {
    // Buscar al usuario en la base de datos por email
    Optional<MyAppUser> existingUserOpt = myAppUserRepository.findByEmail(loginUser.getEmail());

    // Verificar si el usuario existe
    if (existingUserOpt.isPresent()) {
        MyAppUser existingUser = existingUserOpt.get();

        // Validar la contraseña
        if (passwordEncoder.matches(loginUser.getPassword(), existingUser.getPassword())) {
            // Si el usuario existe y la contraseña es correcta, enviar el evento de login a Kafka
            UserEvent userEvent = new UserEvent();
            userEvent.setStatus("LOGGED_IN");
            userEvent.setMessage("User logged in successfully");
            User kafkaUser = new User(existingUser.getId().toString(), existingUser.getUsername(), existingUser.getEmail(), null);
            userEvent.setUser(kafkaUser);

            userProducer.sendMessage(userEvent);

            return "User logged in successfully";
        } else {
            return "Invalid credentials";
        }
    } else {
        // Si el usuario no existe
        return "User not found";
    }
}

}
