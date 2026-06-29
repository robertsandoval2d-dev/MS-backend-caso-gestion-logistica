package com.ferreteriapsa.gestioncomercial.client.gestionlogistica;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.request.*;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.*;

@FeignClient(name="261DSWG2SpringMSLogisticaDemo01GestionLogistica")
public interface GestionLogisticaClient {
    @GetMapping("/logistica/inventario/trabajador/{id}/filtro-catalogo")
    FiltroCatalogoTrabajadorDTO obtenerFiltroCatalogoParaTrabajador(@PathVariable("id") Long trabajadorId);
}
