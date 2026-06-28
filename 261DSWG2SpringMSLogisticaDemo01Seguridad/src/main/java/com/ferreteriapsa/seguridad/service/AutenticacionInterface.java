package com.ferreteriapsa.seguridad.service;

import com.ferreteriapsa.seguridad.model.Usuario;

public interface AutenticacionInterface {
    Usuario registrarUsuario(String username, String password, String userRol);

    void desactivarCuentaPorTrabajador(Long usuarioId);
}
