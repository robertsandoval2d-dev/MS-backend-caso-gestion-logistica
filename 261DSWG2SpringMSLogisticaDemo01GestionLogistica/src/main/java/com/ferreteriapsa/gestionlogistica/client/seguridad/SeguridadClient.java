package com.ferreteriapsa.gestionlogistica.client.seguridad;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ferreteriapsa.gestionlogistica.client.seguridad.dto.request.*;
import com.ferreteriapsa.gestionlogistica.client.seguridad.dto.response.*;

@FeignClient(name="261DSWG2SpringMSLogisticaDemo01Seguridad")
public interface SeguridadClient {
    @PostMapping("/logistica/auth/usuarios")
    UsuarioResponse registrarNuevoUsuario(@RequestBody UsuarioRegistroRequest request);

    @DeleteMapping("/logistica/auth/usuarios/desactivar/{id}")
    void desactivarCuentaUsuario(@PathVariable("id") Long usuarioId);
    
    @GetMapping("/logistica/auth/usuarios")
    List<UsuarioResponse> listarUsuarios();

    @GetMapping("/logistica/auth/usuarios/rol/{id}")
    Map<String, String> obtenerRolUsuario(@PathVariable("id") Long usuarioId);
}
