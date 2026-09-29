package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.request.CrearSuitePresidencialRequest;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.mapper.HabitacionMapper;
import com.hotel.Hotel.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    public HabitacionService(HabitacionRepository habitacionRepository, HabitacionMapper habitacionMapper) {
        this.habitacionRepository = habitacionRepository;
        this.habitacionMapper = habitacionMapper;
    }

    @Transactional(readOnly = true)
    public List<HabitacionResponse> listarTodas() {
        List<Habitacion> habitaciones = habitacionRepository.findAll();
        return habitacionMapper.toResponseList(habitaciones);
    }

    @Transactional
    public HabitacionResponse crearSuite(CrearSuitePresidencialRequest request) {
        SuitePresidencial suite = new SuitePresidencial(
                request.numero(),
                request.capacidadMaxima(),
                request.precioPorNoche(),
                request.incluyeMayordomo(),
                request.jacuzziPrivado());
        return habitacionMapper.toResponse(habitacionRepository.save(suite));
    }
}
