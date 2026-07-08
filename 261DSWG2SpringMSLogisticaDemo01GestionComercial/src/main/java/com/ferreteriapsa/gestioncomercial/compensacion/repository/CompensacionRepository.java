package com.ferreteriapsa.gestioncomercial.compensacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ferreteriapsa.gestioncomercial.compensacion.model.Compensacion;

public interface CompensacionRepository extends JpaRepository<Compensacion, Long> {
    
    @Query("""
        SELECT DISTINCT c
        FROM Compensacion c
        JOIN FETCH c.pedido p
        WHERE p.tiendaId = :idTienda
        AND c.estado = 'PENDIENTE'
    """)
    List<Compensacion> listarCompensacionesPendientes(@Param("idTienda") Long idTienda);
    
}
