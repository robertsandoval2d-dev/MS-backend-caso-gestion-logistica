package com.ferreteriapsa.gestionlogistica.trabajador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Almacen;

public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    
}