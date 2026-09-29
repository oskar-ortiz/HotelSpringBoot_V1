package com.hotel.Hotel.controller;

import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.service.ClienteService;
import com.hotel.Hotel.mapper.ClienteMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@RequestBody CrearClienteRequest request,
            UriComponentsBuilder uriBuilder) {
        ClienteResponse response = clienteService.crear(request);
        URI uri = uriBuilder.path("/api/clientes/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @GetMapping("/{id}/resumen")
    public ResponseEntity<ClienteResumenResponse> resumen(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.obtenerResumen(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizarParcial(
            @PathVariable UUID id,
            @RequestBody ActualizarClienteRequest request) {
        return ResponseEntity.ok(clienteMapper.toResponse(
                clienteService.actualizarParcial(id, request)));
    }
}
