package com.hotel.Hotel.config;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.RangoFechas;
import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.repository.ClienteRepository;
import com.hotel.Hotel.repository.HabitacionRepository;
import com.hotel.Hotel.repository.ReservaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev")
public class DevDataInitializer implements org.springframework.boot.CommandLineRunner {

    private static final String CLIENTE_EMAIL = "ana.demo@hotel.local";
    private static final String HABITACION_ESTANDAR = "D01-101";
    private static final String SUITE_PRESIDENCIAL = "S01-501";

    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;
    private final ReservaRepository reservaRepository;

    public DevDataInitializer(
            ClienteRepository clienteRepository,
            HabitacionRepository habitacionRepository,
            ReservaRepository reservaRepository) {
        this.clienteRepository = clienteRepository;
        this.habitacionRepository = habitacionRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Cliente cliente = clienteRepository.findByEmail(CLIENTE_EMAIL)
                .orElseGet(() -> clienteRepository.save(
                        new Cliente("Ana Demo", CLIENTE_EMAIL)));

        Habitacion estandar = habitacionRepository.findByNumero(HABITACION_ESTANDAR)
                .orElseGet(() -> habitacionRepository.save(
                        new HabitacionEstandar(HABITACION_ESTANDAR, 2, 120.0, 2)));

        Habitacion suite = habitacionRepository.findByNumero(SUITE_PRESIDENCIAL)
                .orElseGet(() -> habitacionRepository.save(
                        new SuitePresidencial(SUITE_PRESIDENCIAL, 2, 350.0, true, true)));

        if (reservaRepository.findByClienteId(cliente.getId()).isEmpty()) {
            reservaRepository.saveAll(List.of(
                    new Reserva(cliente, estandar, new RangoFechas(
                            LocalDateTime.of(2030, 1, 10, 15, 0),
                            LocalDateTime.of(2030, 1, 12, 11, 0))),
                    new Reserva(cliente, suite, new RangoFechas(
                            LocalDateTime.of(2030, 2, 20, 15, 0),
                            LocalDateTime.of(2030, 2, 21, 11, 0)))));
        }

        double montoTotal = reservaRepository.findByClienteId(cliente.getId()).stream()
                .mapToDouble(Reserva::getCostoTotal)
                .sum();
        int totalReservas = reservaRepository.findByClienteId(cliente.getId()).size();

        System.out.printf(
                "[DEV-SEED] clienteId=%s, habitacionEstandarId=%s, suiteId=%s, reservas=%d, montoTotal=%.2f%n",
                cliente.getId(), estandar.getId(), suite.getId(), totalReservas, montoTotal);
    }
}
