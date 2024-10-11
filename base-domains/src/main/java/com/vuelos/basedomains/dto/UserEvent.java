package com.vuelos.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    private String message;  // Mensaje descriptivo del evento
    private String status;       // ID del evento
    private User user;       // Objeto User asociado a este evento
}

