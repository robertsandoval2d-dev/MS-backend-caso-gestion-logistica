package com.ferreteriapsa.gestioncomercial.compensacion.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.GestionLogisticaClient;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.AsignacionDTO;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.TrabajadorDTO;
import com.ferreteriapsa.gestioncomercial.compensacion.dto.request.CompensacionRequest;
import com.ferreteriapsa.gestioncomercial.compensacion.dto.response.CompensacionResponse;
import com.ferreteriapsa.gestioncomercial.compensacion.model.Compensacion;
import com.ferreteriapsa.gestioncomercial.compensacion.repository.CompensacionRepository;
import com.ferreteriapsa.gestioncomercial.ventas.model.Pedido;
import com.ferreteriapsa.gestioncomercial.ventas.repository.PedidoRepository;

@Service
public class CompensacionService {
    
    private final CompensacionRepository compensacionRepository;
    private final PedidoRepository pedidoRepository;
    private final GestionLogisticaClient gestionLogisticaClient;

    public CompensacionService(CompensacionRepository compensacionRepository, 
                               PedidoRepository pedidoRepository,
                               GestionLogisticaClient gestionLogisticaClient) {
        this.compensacionRepository = compensacionRepository;
        this.pedidoRepository = pedidoRepository;
        this.gestionLogisticaClient = gestionLogisticaClient;
    }

    @Transactional
    public void registrarCompensacion(CompensacionRequest request) {

        Compensacion compensacion = new Compensacion();

        Pedido pedido = pedidoRepository.getReferenceById(request.getPedidoId());

        compensacion.setPedido(pedido);
        compensacion.setDiasRetraso(request.getDiasRetraso());
        compensacion.setMontoCompensacion(request.getMontoCompensacion());
        compensacion.setEstado("PENDIENTE");

        compensacionRepository.save(compensacion);
    }

    @SuppressWarnings("null")
    public List<CompensacionResponse> listarCompensacionesPendientes(Long trabajadorId) {
        
        TrabajadorDTO trabajador = gestionLogisticaClient.buscarTrabajador(trabajadorId);

        Long tiendaId = trabajador.getAsignaciones().stream()
        .filter(AsignacionDTO::isActivo)
        .map(AsignacionDTO::getTiendaId)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El trabajador no tiene tienda activa")
        );

        List<Compensacion> compensaciones = compensacionRepository.listarCompensacionesPendientes(tiendaId);

        return compensaciones.stream().map(c -> {
            CompensacionResponse response = new CompensacionResponse();
            response.setCompensacionId(c.getCompensacionId());
            response.setDiasRetraso(c.getDiasRetraso());
            response.setMontoCompensacion(c.getMontoCompensacion());
            
            // Obtenemos el monto total del pedido sin hacer querys adicionales gracias al JOIN FETCH
            response.setMontoTotalPedido(c.getPedido().getMontoTotal());
            
            response.setEstado(c.getEstado());
            return response;
        }).toList();
    }

    @SuppressWarnings("null")
    @Transactional
    public CompensacionResponse aprobarCompensacion(Long compensacionId) {
        Compensacion compensacion = compensacionRepository.findById(compensacionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Compensación no encontrada"
                ));

        compensacion.setEstado("APROBADO");
        compensacion = compensacionRepository.save(compensacion);

        CompensacionResponse response = new CompensacionResponse();

        response.setCompensacionId(compensacion.getCompensacionId());
        response.setDiasRetraso(compensacion.getDiasRetraso());
        response.setMontoTotalPedido(compensacion.getPedido().getMontoTotal());
        response.setMontoCompensacion(compensacion.getMontoCompensacion());
        response.setEstado(compensacion.getEstado());

        return response;
    }

    @SuppressWarnings("null")
    @Transactional
    public CompensacionResponse rechazarCompensacion(Long compensacionId) {
        Compensacion compensacion = compensacionRepository.findById(compensacionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Compensación no encontrada"
                ));

        // Cambiamos el estado a RECHAZADO
        compensacion.setEstado("RECHAZADO");
        compensacion = compensacionRepository.save(compensacion);

        CompensacionResponse response = new CompensacionResponse();

        response.setCompensacionId(compensacion.getCompensacionId());
        response.setDiasRetraso(compensacion.getDiasRetraso());
        response.setMontoTotalPedido(compensacion.getPedido().getMontoTotal());
        response.setMontoCompensacion(compensacion.getMontoCompensacion());
        response.setEstado(compensacion.getEstado());

        return response;
    }
    
}