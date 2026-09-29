package com.hotel.Hotel.dto.response;

import java.util.UUID;

public record SuitePresidencialResponse(
        UUID id,
        String tipo,
        String numero,
        double precioPorNoche,
        int capacidadMaxima,
        boolean incluyeMayordomo,
        boolean jacuzziPrivado) implements HabitacionResponse {
}
