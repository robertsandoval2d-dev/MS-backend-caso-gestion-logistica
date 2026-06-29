package com.ferreteriapsa.gestioncomercial.catalogo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ferreteriapsa.gestioncomercial.catalogo.dto.response.*;
import com.ferreteriapsa.gestioncomercial.catalogo.model.*;
import com.ferreteriapsa.gestioncomercial.catalogo.repository.*;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.GestionLogisticaClient;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.FiltroCatalogoTrabajadorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
public class CatalogoService implements CatalogoInterface{

    private final LineaProductoRepository lineaProductoRepository;
    private final ProductoRepository productoRepository;
    private final GestionLogisticaClient gestionLogisticaClient;

    public CatalogoService(LineaProductoRepository lineaProductoRepository, ProductoRepository productoRepository,
        GestionLogisticaClient gestionLogisticaClient){
        this.lineaProductoRepository = lineaProductoRepository;
        this.productoRepository = productoRepository;
        this.gestionLogisticaClient = gestionLogisticaClient;
    }

    public List<CatalogoResponse> obtenerCatalogo(Long trabajadorId) {

        FiltroCatalogoTrabajadorDTO filtros = gestionLogisticaClient.obtenerFiltroCatalogoParaTrabajador(trabajadorId);

        List<CatalogoProjection> filas =
                productoRepository.obtenerCatalogoFiltrado(filtros.getLineaProductoId(), filtros.getProductosIds());

        Map<Long, CatalogoResponse> mapa = new LinkedHashMap<>();

        for (CatalogoProjection row : filas) {

            mapa.putIfAbsent(
                row.getProductoId(),
                new CatalogoResponse(
                    row.getProductoId(),
                    row.getProductoNombre(),
                    new ArrayList<>()
                )
            );

            if (row.getProveedorId() != null) {
                mapa.get(row.getProductoId())
                    .getProveedores()
                    .add(new ProveedorDTO(
                            row.getProveedorId(),
                            row.getProveedorNombre()
                    ));
            }
        }

        return new ArrayList<>(mapa.values());
    }

    public LineaProducto buscarLineaProducto(Long lineaProductoId){
        return lineaProductoRepository.findById(lineaProductoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada"));
    }

}
