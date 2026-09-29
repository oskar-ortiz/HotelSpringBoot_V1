package com.hotel.Hotel.controller;

import com.hotel.Hotel.dto.request.CrearSuitePresidencialRequest;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.service.HabitacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public ResponseEntity<List<HabitacionResponse>> listar() {
        return ResponseEntity.ok(habitacionService.listarTodas());
    }

    @PostMapping("/suites")
    public ResponseEntity<HabitacionResponse> crearSuite(
            @RequestBody CrearSuitePresidencialRequest request,
            UriComponentsBuilder uriBuilder) {
        HabitacionResponse response = habitacionService.crearSuite(request);
        URI location = uriBuilder.path("/api/habitaciones/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }
}
