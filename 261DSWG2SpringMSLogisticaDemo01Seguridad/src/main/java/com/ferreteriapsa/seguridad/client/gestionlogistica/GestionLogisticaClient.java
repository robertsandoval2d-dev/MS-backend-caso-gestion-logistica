package com.ferreteriapsa.seguridad.client.gestionlogistica;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.ferreteriapsa.seguridad.client.gestionlogistica.dto.request.*;
import com.ferreteriapsa.seguridad.client.gestionlogistica.dto.response.*;

@FeignClient(name="261DSWG2SpringMSLogisticaDemo01GestionLogistica")
public interface GestionLogisticaClient {
    @GetMapping("/logistica/trabajadores/{id}")
    TrabajadorDTO buscarTrabajador(@PathVariable ("id") Long usuarioId);
}
