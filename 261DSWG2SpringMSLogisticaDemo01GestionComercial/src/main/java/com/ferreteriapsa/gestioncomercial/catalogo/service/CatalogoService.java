package com.ferreteriapsa.gestioncomercial.catalogo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ferreteriapsa.gestioncomercial.catalogo.dto.response.*;
import com.ferreteriapsa.gestioncomercial.catalogo.model.LineaProducto;
import com.ferreteriapsa.gestioncomercial.catalogo.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
public class CatalogoService implements CatalogoInterface{

    // private final ProductoRepository productoRepository;

    // public CatalogoService(ProductoRepository productoRepository){
    //     this.productoRepository = productoRepository;
    // }

    // public List<CatalogoResponse> obtenerCatalogo(Long trabajadorId) {

    //     List<CatalogoProjection> filas =
    //             productoRepository.obtenerCatalogoPorTrabajador(trabajadorId);

    //     Map<Long, CatalogoResponse> mapa = new LinkedHashMap<>();

    //     for (CatalogoProjection row : filas) {

    //         mapa.putIfAbsent(
    //             row.getProductoId(),
    //             new CatalogoResponse(
    //                 row.getProductoId(),
    //                 row.getProductoNombre(),
    //                 new ArrayList<>()
    //             )
    //         );

    //         if (row.getProveedorId() != null) {
    //             mapa.get(row.getProductoId())
    //                 .getProveedores()
    //                 .add(new ProveedorDTO(
    //                         row.getProveedorId(),
    //                         row.getProveedorNombre()
    //                 ));
    //         }
    //     }

    //     return new ArrayList<>(mapa.values());
    // }

    private final LineaProductoRepository lineaProductoRepository;

    public CatalogoService(LineaProductoRepository lineaProductoRepository){
        this.lineaProductoRepository = lineaProductoRepository;
    }

    public LineaProducto buscarLineaProducto(Long lineaProductoId){
        return lineaProductoRepository.findById(lineaProductoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada"));
    }

}
