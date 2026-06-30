package com.ferreteriapsa.gestioncomercial.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ferreteriapsa.gestioncomercial.catalogo.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("""
        SELECT 
            p.productoId as productoId,
            p.nombre as productoNombre,
            pr.proveedorId as proveedorId,
            pr.nombre as proveedorNombre
        FROM Producto p
        LEFT JOIN ProductoProveedor pp ON pp.producto = p
        LEFT JOIN pp.proveedor pr
        WHERE p.lineaProducto.lineaProductoId = :lineaProductoId
        AND p.productoId IN :productoIdsEnAlmacen
        ORDER BY p.productoId, pr.proveedorId
    """)
    List<CatalogoProjection> obtenerCatalogoFiltrado(
        @Param("lineaProductoId") Long lineaProductoId,
        @Param("productoIdsEnAlmacen") List<Long> productoIdsEnAlmacen
    );

    @Query("""
        SELECT p.productoId 
        FROM Producto p 
        WHERE p.lineaProducto.lineaProductoId = :lineaProductoId
    """)
    List<Long> obtenerIdsPorLineaProductoId(@Param("lineaProductoId") Long lineaProductoId);
}
