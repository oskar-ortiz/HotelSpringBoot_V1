package com.hotel.Hotel.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class HotelDomainUnitTests {

    @Test
    void clienteDebeCrearseConDatosValidos() {
        Cliente cliente = new Cliente("Ana García", "ana@hotel.com");

        assertNotNull(cliente.getId());
        assertEquals("Ana García", cliente.getNombre());
        assertEquals("ana@hotel.com", cliente.getEmail());
        assertTrue(cliente.isActivo());
        assertEquals(0, cliente.getPenalizaciones());
        assertTrue(cliente.puedeRealizarReservas());
    }

    @Test
    void registrarPenalizacionTresVecesDebeDesactivarCliente() {
        Cliente cliente = new Cliente("Luis Pérez", "luis@hotel.com");

        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        assertEquals(3, cliente.getPenalizaciones());
        assertFalse(cliente.isActivo());
        assertFalse(cliente.puedeRealizarReservas());
    }

    @Test
    void habitacionEstandarDebeRechazarPrecioNegativo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new HabitacionEstandar("101", 2, -50.0, 2));

        assertEquals("Precio no puede ser negativo", exception.getMessage());
    }

    @Test
    void reservaDebeConfirmarseYMarcarHabitacionComoOcupada() {
        Cliente cliente = new Cliente("Maria López", "maria@hotel.com");
        Habitacion habitacion = new HabitacionEstandar("202", 2, 120.0, 2);
        LocalDateTime inicio = LocalDateTime.of(2025, 6, 10, 14, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 6, 12, 14, 0);
        Reserva reserva = new Reserva(cliente, habitacion, new RangoFechas(inicio, fin));

        reserva.confirmar();

        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());
        assertEquals(240.0, reserva.getCostoTotal());
    }

    @Test
    void rangoFechasDebeRechazarFechaFinAnterior() {
        LocalDateTime inicio = LocalDateTime.of(2025, 7, 20, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 7, 19, 10, 0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RangoFechas(inicio, fin));

        assertEquals("La fecha fin no puede ser anterior a la de inicio", exception.getMessage());
    }
}
