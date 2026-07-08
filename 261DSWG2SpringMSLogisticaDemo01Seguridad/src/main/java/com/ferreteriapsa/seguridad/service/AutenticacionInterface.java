package com.ferreteriapsa.seguridad.service;

import com.ferreteriapsa.seguridad.dto.request.UsuarioRegistroRequest;
import com.ferreteriapsa.seguridad.dto.response.UsuarioResponse;

import java.util.List;

public interface AutenticacionInterface {
    UsuarioResponse registrarUsuario(UsuarioRegistroRequest request);

    void desactivarCuenta(Long usuarioId);

    List<UsuarioResponse> listarInfoUsuarios();

    String obtenerRolUsuario(Long UsuarioId);

    UsuarioResponse obtenerUsuarioPorId(Long usuarioId);

    UsuarioResponse obtenerUsuarioPorUsername(String username);
}
