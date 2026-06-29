package com.ferreteriapsa.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ferreteriapsa.seguridad.dto.response.UsuarioResponse;
import com.ferreteriapsa.seguridad.model.Usuario;

import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("""
            SELECT new com.ferreteriapsa.seguridad.dto.response.UsuarioResponse(
                u.usuarioId, 
                r.nombre, 
                u.username, 
                u.activo
            )
            FROM Usuario u
            LEFT JOIN u.rol r
        """)
    List<UsuarioResponse> listarTodosLosUsuarios();
}

