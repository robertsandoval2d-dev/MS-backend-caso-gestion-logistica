package com.ferreteriapsa.gestioncomercial.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ferreteriapsa.gestioncomercial.catalogo.dto.response.ProductoInfoVentasDTO;
import com.ferreteriapsa.gestioncomercial.catalogo.model.Producto;

import java.time.LocalDateTime;
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

    @Query("""
        SELECT p 
        FROM Producto p 
        JOIN FETCH p.lineaProducto lp 
        WHERE p.productoId IN :productosIds
    """)
    List<Producto> findProductosConLineaPorIds(@Param("productosIds") List<Long> productosIds);

    @Query("SELECT new com.ferreteriapsa.gestioncomercial.catalogo.dto.response.ProductoInfoVentasDTO(" +
           "p.productoId, p.nombre, p.lineaProducto.lineaProductoId, ped.tiendaId, COUNT(dp.detallePedidoId)) " +
           "FROM Producto p " +
           "LEFT JOIN DetallePedido dp ON dp.producto.productoId = p.productoId " +
           "LEFT JOIN dp.pedido ped ON ped.tiendaId IN :tiendaIds AND ped.fechaCreacion >= :fechaLimite " +
           "WHERE p.productoId IN :productoIds " +
           "GROUP BY p.productoId, p.nombre, p.lineaProducto.lineaProductoId, ped.tiendaId")
    List<ProductoInfoVentasDTO> contarVentasPorProductosYTiendas(
            @Param("productoIds") List<Long> productoIds,
            @Param("tiendaIds") List<Long> tiendaIds,
            @Param("fechaLimite") LocalDateTime fechaLimite
    );
}
