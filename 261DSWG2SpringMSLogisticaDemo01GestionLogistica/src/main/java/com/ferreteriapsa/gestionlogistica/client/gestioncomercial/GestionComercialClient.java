package com.ferreteriapsa.gestionlogistica.client.gestioncomercial;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
// import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.request.*;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name="261DSWG2SpringMSLogisticaDemo01GestionComercial")
public interface GestionComercialClient {
    @GetMapping("/logistica/catalogo/linea-detalle/{id}")
    LineaProductoDTO buscarLineaProducto(@PathVariable("id") Long lineaProductoId);

    @GetMapping("/logistica/catalogo/productos-ids-linea")
    List<Long> obtenerProductosIdsPorLineaProducto(@RequestParam("lineaProductoId") Long lineaProductoId);

    @PostMapping("/logistica/catalogo/productos-detalles-linea")
    List<ProductoDetalleDTO> obtenerDetallesProductosPorIds(@RequestBody List<Long> ids);

    @GetMapping("/logistica/compras/ordenes-compra/{id}")
    OrdenCompraDTO obtenerOrdenCompraPorId(@PathVariable("id") Long ordenCompraId);

    @PutMapping("/logistica/compras/ordenes-compra/{id}/recepcion")
    void recepcionarOrdenCompra(
        @PathVariable("id") Long id, 
        @RequestParam("nuevoEstado") String nuevoEstado, 
        @RequestParam("fechaEntrega") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaEntrega
    );
}
