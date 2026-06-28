package com.ferreteriapsa.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ferreteriapsa.seguridad.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

}

