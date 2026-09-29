package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.response.HabitacionEstandarResponse;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.dto.response.SuitePresidencialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    default HabitacionResponse toResponse(Habitacion habitacion) {
        if (habitacion instanceof HabitacionEstandar estandar) {
            return toEstandarResponse(estandar);
        }
        if (habitacion instanceof SuitePresidencial suite) {
            return toSuiteResponse(suite);
        }
        throw new IllegalArgumentException("Tipo de habitacion no soportado: "
                + habitacion.getClass().getName());
    }

    default List<HabitacionResponse> toResponseList(List<Habitacion> habitaciones) {
        return habitaciones.stream().map(this::toResponse).toList();
    }

    @Mapping(target = "tipo", constant = "ESTANDAR")
    HabitacionEstandarResponse toEstandarResponse(HabitacionEstandar habitacion);

    @Mapping(target = "tipo", constant = "SUITE")
    SuitePresidencialResponse toSuiteResponse(SuitePresidencial habitacion);
}
