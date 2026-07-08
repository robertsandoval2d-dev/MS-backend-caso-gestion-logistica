package com.ferreteriapsa.gestioncomercial.ventas.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.GestionLogisticaClient;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.AsignacionDTO;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.TrabajadorDTO;
import com.ferreteriapsa.gestioncomercial.ventas.dto.response.PedidoRetrasadoResponse;
import com.ferreteriapsa.gestioncomercial.ventas.model.Pedido;
import com.ferreteriapsa.gestioncomercial.ventas.repository.PedidoRepository;

@Service
public class VentasService {
    private final PedidoRepository pedidoRepository;
    private final GestionLogisticaClient gestionLogisticaClient;

    public VentasService(PedidoRepository pedidoRepository, GestionLogisticaClient gestionLogisticaClient){
        this.pedidoRepository = pedidoRepository;
        this.gestionLogisticaClient = gestionLogisticaClient;
    }

    public List<PedidoRetrasadoResponse> listarPedidosRetrasados(Long trabajadorId) {
        
        TrabajadorDTO trabajador = gestionLogisticaClient.buscarTrabajador(trabajadorId);

        Long tiendaId = trabajador.getAsignaciones().stream()
        .filter(AsignacionDTO::isActivo)
        .map(AsignacionDTO::getTiendaId)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El trabajador no tiene tienda activa")
        );

        List<Pedido> pedidos = pedidoRepository.listarPedidosEntregadosTarde(tiendaId);

        List<PedidoRetrasadoResponse> response = pedidos.stream()
                .map(pedido -> {
                    PedidoRetrasadoResponse pr = new PedidoRetrasadoResponse();
                    
                    // Datos del cliente
                    pr.setClienteId(pedido.getCliente().getClienteId());
                    pr.setNombreCliente(pedido.getCliente().getNombre());
                    
                    // Datos del pedido
                    pr.setPedidoId(pedido.getPedidoId());
                    pr.setFechaMaximaEntrega(pedido.getFechaEntregaMaxima());
                    pr.setFechaEntrega(pedido.getFechaEntrega());
                    pr.setMontoTotalPedido(pedido.getMontoTotal());
                    Integer diasRetraso = (int) ChronoUnit.DAYS.between(
                        pedido.getFechaEntregaMaxima().toLocalDate(),
                        pedido.getFechaEntrega().toLocalDate()
                    );
                    pr.setDiasRetraso(diasRetraso);
                    pr.setEstado(pedido.getEstado());

                    return pr;
                }).toList();

        return response;
    }
    
}