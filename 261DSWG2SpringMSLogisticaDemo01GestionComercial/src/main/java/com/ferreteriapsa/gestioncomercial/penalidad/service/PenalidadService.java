package com.ferreteriapsa.gestioncomercial.penalidad.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ferreteriapsa.gestioncomercial.penalidad.dto.request.*;
import com.ferreteriapsa.gestioncomercial.penalidad.dto.response.*;
import com.ferreteriapsa.gestioncomercial.penalidad.model.*;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.GestionLogisticaClient;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.AsignacionDTO;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.TrabajadorDTO;
import com.ferreteriapsa.gestioncomercial.compra.model.OrdenCompra;
import com.ferreteriapsa.gestioncomercial.penalidad.repository.*;
import com.ferreteriapsa.gestioncomercial.compra.repository.OrdenCompraRepository;

import jakarta.transaction.Transactional;

@Service
public class PenalidadService {
    private final PenalidadRepository penalidadRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final GestionLogisticaClient gestionLogisticaClient;

    public PenalidadService(PenalidadRepository penalidadRepository, OrdenCompraRepository ordenCompraRepository, GestionLogisticaClient gestionLogisticaClient) {
        this.penalidadRepository = penalidadRepository;
        this.ordenCompraRepository = ordenCompraRepository;
        this.gestionLogisticaClient = gestionLogisticaClient;
    }

    @Transactional
    public void registrarPenalidad(PenalidadRequest request){

        Penalidad penalidad = new Penalidad();

        OrdenCompra ordenCompra = ordenCompraRepository.getReferenceById(request.getOrdenCompraId());

        penalidad.setOrdenCompra(ordenCompra);
        penalidad.setDiasRetraso(request.getDiasRetraso());
        penalidad.setMontoPenalidad(request.getMontoPenalidad());
        penalidad.setEstadoPago("NO PAGADO");

        penalidadRepository.save(penalidad);
    }

    public List<OrdenConRetrasoResponse> listarOrdenesCompraConRetraso(Long trabajadorId){

        TrabajadorDTO trabajador = gestionLogisticaClient.buscarTrabajador(trabajadorId);

        Long tiendaId = trabajador.getAsignaciones().stream()
        .filter(AsignacionDTO::isActivo)
        .map(AsignacionDTO::getTiendaId)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El trabajador no tiene tienda activa")
        );

        List<OrdenCompra> ordenes = ordenCompraRepository.listarOrdenesCompraConRetraso(tiendaId);

        List<OrdenConRetrasoResponse> response = ordenes.stream()
                .map(orden -> {
                    OrdenConRetrasoResponse or = new OrdenConRetrasoResponse();
                    or.setOrdenCompraId(orden.getOrdenCompraId());
                    or.setProveedor(orden.getProveedor().getNombre());
                    or.setFechaEntrega(orden.getFechaEntrega());
                    or.setFechaLimite(orden.getPlazoFechaMaximo());
                    Integer diasRetraso = (int)  ChronoUnit.DAYS.between(
                            orden.getPlazoFechaMaximo().toLocalDate(),
                            orden.getFechaEntrega().toLocalDate()
                    );

                    or.setDiasRetraso(diasRetraso);
                    or.setMonto(orden.getMontoTotalCalculado());
                    or.setEstado(orden.getEstado());

                    return or;
                }).toList();

        return response;
    }

}
