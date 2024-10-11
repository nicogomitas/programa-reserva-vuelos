package com.vuelos.registroservice.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class MyAppUserService implements UserDetailsService{
    
    @Autowired
    private MyAppUserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         // Si necesitas buscar por email, debes modificar este método para buscar por email
        // o crear otro método específico para esa funcionalidad.
        Optional<MyAppUser> user = repository.findByEmail(email); // Cambia 'username' a 'email' si buscas por email
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .build();    
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
    
    
}