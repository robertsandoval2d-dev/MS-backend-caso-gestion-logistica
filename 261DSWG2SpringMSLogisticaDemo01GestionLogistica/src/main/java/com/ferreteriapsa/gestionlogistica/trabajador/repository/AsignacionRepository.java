package com.ferreteriapsa.gestionlogistica.trabajador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Asignacion;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Tienda;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Trabajador;

public interface AsignacionRepository extends JpaRepository<Asignacion,Long>{
    Optional<Asignacion> findByTrabajadorAndActivoTrue(Trabajador trabajador); 
    Optional<Asignacion> findByTiendaAndLineaProductoIdAndActivoTrue(Tienda tienda, Long lineaProductoId);
}
