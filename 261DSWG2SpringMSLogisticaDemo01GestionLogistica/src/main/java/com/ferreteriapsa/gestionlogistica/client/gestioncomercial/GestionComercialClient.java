package com.ferreteriapsa.gestionlogistica.client.gestioncomercial;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.request.*;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response.*;

@FeignClient(name="261DSWG2SpringMSLogisticaDemo01GestionComercial")
public interface GestionComercialClient {
    @GetMapping("/logistica/catalogo/linea-detalle/{id}")
    LineaProductoDTO buscarLineaProducto(@PathVariable("id") Long lineaProductoId);
}
