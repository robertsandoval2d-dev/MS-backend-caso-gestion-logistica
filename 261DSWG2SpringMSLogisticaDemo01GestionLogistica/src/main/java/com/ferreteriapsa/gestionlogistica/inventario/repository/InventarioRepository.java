package com.ferreteriapsa.gestionlogistica.inventario.repository;

import com.ferreteriapsa.gestionlogistica.analisis.dto.response.InventarioLocalDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ferreteriapsa.gestionlogistica.inventario.model.Inventario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    @Query("""
        SELECT i
        FROM Inventario i
        WHERE i.productoId IN :productosIds
        AND EXISTS (
            SELECT 1
            FROM Asignacion a
            WHERE a.trabajador.trabajadorId = :trabajadorId
            AND a.activo = true
            AND a.tienda.almacen = i.zonaAlmacen.almacen
        )
    """)
    List<Inventario> buscarInventariosPorJefeYProductos(
        @Param("trabajadorId") Long trabajadorId,
        @Param("productosIds") List<Long> productosIds
    );

    Optional<Inventario> findByProductoIdAndZonaAlmacenAlmacenAlmacenId(Long productoId, Long almacenId);

    @Query("""
        SELECT DISTINCT i
        FROM Inventario i
        JOIN i.zonaAlmacen z
        JOIN z.almacen a
        JOIN Tienda t ON t.almacen = a
        JOIN Asignacion asig ON asig.tienda = t
        WHERE asig.trabajador.trabajadorId = :trabajadorId
        AND asig.activo = true
        """)
    List<Inventario> findInventarioBasePorTrabajador(@Param("trabajadorId") Long trabajadorId);

    @Query("""
        SELECT new com.ferreteriapsa.gestionlogistica.analisis.dto.response.InventarioLocalDTO(
            i, t.tiendaId, asig.lineaProductoId
        )
        FROM Inventario i
        JOIN i.zonaAlmacen z
        JOIN z.almacen a
        JOIN Tienda t ON t.almacen = a
        JOIN Asignacion asig ON asig.tienda = t
        WHERE asig.trabajador.trabajadorId = :trabajadorId
        AND asig.activo = true
        """)
    List<InventarioLocalDTO> findInventarioLocalPorTrabajador(@Param("trabajadorId") Long trabajadorId);

    @Query("""
        SELECT DISTINCT i.productoId
        FROM Inventario i
        WHERE i.zonaAlmacen.almacen.almacenId = :almacenId
    """)
    List<Long> obtenerProductoIdsPorAlmacen(@Param("almacenId") Long almacenId);

}
