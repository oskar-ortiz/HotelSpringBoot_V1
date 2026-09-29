package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ReservaItemResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponse toResponse(Cliente cliente);

    List<ClienteResponse> toResponseList(List<Cliente> clientes);

    @Mapping(target = "totalReservasRealizadas", expression = "java(cliente.getReservas().size())")
    @Mapping(target = "montoTotalGastado", expression = "java(cliente.getReservas().stream().mapToDouble(Reserva::getCostoTotal).sum())")
    @Mapping(target = "reservasRecientes", source = "reservas")
    ClienteResumenResponse toResumen(Cliente cliente);

    @Named("reservaToItem")
    @Mapping(target = "idReserva", source = "id")
    @Mapping(target = "numeroHabitacion", source = "habitacion.numero")
    @Mapping(target = "fechaInicio", source = "periodo.fechaInicio")
    @Mapping(target = "fechaFin", source = "periodo.fechaFin")
    @Mapping(target = "estado", expression = "java(reserva.getEstado().name())")
    ReservaItemResponse reservaToItem(Reserva reserva);

    @IterableMapping(qualifiedByName = "reservaToItem")
    List<ReservaItemResponse> reservasToItems(List<Reserva> reservas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    Cliente toEntity(CrearClienteRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombre", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    void updateClienteFromDto(ActualizarClienteRequest dto, @MappingTarget Cliente entity);

    @AfterMapping
    default void aplicarDatosActualizados(ActualizarClienteRequest dto, @MappingTarget Cliente entity) {
        if (dto != null && entity != null) {
            entity.actualizarDatos(dto.nombre(), dto.email());
        }
    }
}
