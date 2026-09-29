package com.hotel.Hotel.dto.response;

import java.util.UUID;

public sealed interface HabitacionResponse
        permits HabitacionEstandarResponse, SuitePresidencialResponse {

    UUID id();

    String tipo();

    String numero();

    double precioPorNoche();

    int capacidadMaxima();
}
