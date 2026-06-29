package com.ferreteriapsa.gestionlogistica.trabajador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ferreteriapsa.gestionlogistica.trabajador.model.Tienda;

import java.util.List;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {
@Query("""
        SELECT DISTINCT t 
        FROM Tienda t 
        LEFT JOIN FETCH t.asignaciones a 
        WHERE (a.activo = true OR a IS NULL)
        ORDER BY t.nombre
    """)
    List<Tienda> listarTiendasConLineas();
}
