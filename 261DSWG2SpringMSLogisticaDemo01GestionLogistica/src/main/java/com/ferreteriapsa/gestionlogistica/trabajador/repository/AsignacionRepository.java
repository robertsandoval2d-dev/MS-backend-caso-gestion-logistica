package com.ferreteriapsa.gestionlogistica.trabajador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ferreteriapsa.gestionlogistica.trabajador.model.Asignacion;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Tienda;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Trabajador;

public interface AsignacionRepository extends JpaRepository<Asignacion,Long>{
    Optional<Asignacion> findByTrabajadorAndActivoTrue(Trabajador trabajador); 
    Optional<Asignacion> findByTiendaAndLineaProductoIdAndActivoTrue(Tienda tienda, Long lineaProductoId);

    interface DatosAsignacion {
        Long getLineaProductoId();
        Long getAlmacenId();
        Long getTiendaId();
    }

    @Query("""
        SELECT 
            a.lineaProductoId AS lineaProductoId, 
            a.tienda.almacen.almacenId AS almacenId,
            a.tienda.tiendaId AS tiendaId
        FROM Asignacion a
        WHERE a.trabajador.trabajadorId = :trabajadorId
        AND a.activo = true
    """)
    DatosAsignacion obtenerDatosAsignacionActiva(@Param("trabajadorId") Long trabajadorId);
}
