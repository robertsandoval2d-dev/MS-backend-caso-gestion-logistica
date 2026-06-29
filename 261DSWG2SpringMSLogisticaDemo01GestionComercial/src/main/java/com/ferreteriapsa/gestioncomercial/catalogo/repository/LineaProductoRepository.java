package com.ferreteriapsa.gestioncomercial.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ferreteriapsa.gestioncomercial.catalogo.model.LineaProducto;

public interface LineaProductoRepository extends JpaRepository<LineaProducto, Long> {
    
}
