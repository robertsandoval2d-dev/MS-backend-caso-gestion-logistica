package com.ferreteriapsa.gestionlogistica.trabajador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ferreteriapsa.gestionlogistica.trabajador.model.Trabajador;
import com.ferreteriapsa.gestionlogistica.trabajador.dto.response.TrabajadorResponse;

import java.util.List;
import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    @Query("""
        SELECT new com.ferreteriapsa.gestionlogistica.trabajador.dto.response.TrabajadorResponse(
            t.trabajadorId, 
            t.usuarioId,
            t.nombre, 
            t.dni,
            t.mail,
            ti.tiendaId,
            ti.nombre,
            a.lineaProductoId,
            a.nombreLinea
        )
        FROM Trabajador t
        LEFT JOIN t.asignaciones a ON a.activo = true
        LEFT JOIN a.tienda ti
        ORDER BY t.nombre
    """)
    List<TrabajadorResponse> listarTrabajadoresConTienda();

    Optional<Trabajador> findByUsuarioId(Long usuarioId);

    Optional<Trabajador> findByTrabajadorId(Long trabajadorId);

}
