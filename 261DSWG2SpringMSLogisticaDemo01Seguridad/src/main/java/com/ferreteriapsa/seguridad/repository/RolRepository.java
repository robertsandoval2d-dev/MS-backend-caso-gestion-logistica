package com.ferreteriapsa.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ferreteriapsa.seguridad.model.Rol;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
    
}