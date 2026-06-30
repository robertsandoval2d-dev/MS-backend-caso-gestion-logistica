package com.ferreteriapsa.gestionlogistica.client.gestioncomercial;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.request.*;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response.*;

import java.util.List;

@FeignClient(name="261DSWG2SpringMSLogisticaDemo01GestionComercial")
public interface GestionComercialClient {
    @GetMapping("/logistica/catalogo/linea-detalle/{id}")
    LineaProductoDTO buscarLineaProducto(@PathVariable("id") Long lineaProductoId);

    @GetMapping("/logistica/catalogo/productos-ids-linea")
    List<Long> obtenerProductosIdsPorLineaProducto(@RequestParam("lineaProductoId") Long lineaProductoId);

    @PostMapping("/logistica/catalogo/productos-detalles-linea")
    List<ProductoDetalleDTO> obtenerDetallesProductosPorIds(@RequestBody List<Long> ids);
}
