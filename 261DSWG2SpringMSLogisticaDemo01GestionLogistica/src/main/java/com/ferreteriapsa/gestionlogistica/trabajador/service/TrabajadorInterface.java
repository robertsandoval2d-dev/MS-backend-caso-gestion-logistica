package com.ferreteriapsa.gestionlogistica.trabajador.service;

import com.ferreteriapsa.gestionlogistica.trabajador.dto.response.TrabajadorDTO;

public interface TrabajadorInterface {
    TrabajadorDTO buscarTrabajador(Long usuarioId);
}
