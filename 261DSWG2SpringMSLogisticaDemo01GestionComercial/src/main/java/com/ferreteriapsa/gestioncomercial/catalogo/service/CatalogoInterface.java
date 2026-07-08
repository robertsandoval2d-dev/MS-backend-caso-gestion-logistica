package com.ferreteriapsa.gestioncomercial.catalogo.service;

import com.ferreteriapsa.gestioncomercial.catalogo.dto.response.ProductoClientDTO;
import com.ferreteriapsa.gestioncomercial.catalogo.dto.response.ProductoDetalleDTO;
import com.ferreteriapsa.gestioncomercial.catalogo.dto.response.ProductoInfoVentasDTO;
import com.ferreteriapsa.gestioncomercial.catalogo.model.LineaProducto;

import java.time.LocalDateTime;
import java.util.List;

public interface CatalogoInterface {
    LineaProducto buscarLineaProducto(Long lineaProductoId);
    List<Long> obtenerProductosIdsPorLineaProducto(Long lineaProductoId);
    List<ProductoDetalleDTO> obtenerDetallesProductosPorIds(List<Long> ids);
    List<ProductoClientDTO> obtenerProductosPorIds(List<Long> productosIds);
    List<ProductoInfoVentasDTO> contarVentasPorProductos(List<Long> productoIds, List<Long> tiendaIds, LocalDateTime fechaLimite);
}
