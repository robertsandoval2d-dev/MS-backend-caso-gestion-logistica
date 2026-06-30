package com.ferreteriapsa.gestioncomercial.planificacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ferreteriapsa.gestioncomercial.planificacion.model.Cronograma;

import java.util.List;

public interface CronogramaRepository extends JpaRepository<Cronograma, Long> {

    @Query("""
        SELECT DISTINCT c
        FROM Cronograma c
        JOIN FETCH c.detallesCronograma dc
        JOIN FETCH dc.productoProveedor pp
        JOIN FETCH pp.producto p
        JOIN FETCH p.lineaProducto lp
        JOIN FETCH pp.proveedor pr
        WHERE c.tiendaId = :tiendaId
        AND dc.estado = 'PENDIENTE'
    """)
    List<Cronograma> listarCronogramasConDetallesPendientes(
        @Param("tiendaId") Long tiendaId
    );
}
