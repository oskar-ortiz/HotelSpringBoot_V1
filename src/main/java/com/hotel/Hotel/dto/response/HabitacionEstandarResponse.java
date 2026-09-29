package com.hotel.Hotel.dto.response;

import java.util.UUID;

public record HabitacionEstandarResponse(
        UUID id,
        String tipo,
        String numero,
        double precioPorNoche,
        int capacidadMaxima,
        int camasIndividuales) implements HabitacionResponse {
}
