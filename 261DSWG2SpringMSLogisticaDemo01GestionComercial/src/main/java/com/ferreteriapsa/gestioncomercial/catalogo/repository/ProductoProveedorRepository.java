package com.ferreteriapsa.gestioncomercial.catalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriapsa.gestioncomercial.catalogo.model.ProductoProveedor;

public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Long> {
    Optional<ProductoProveedor> findByProducto_ProductoIdAndProveedor_ProveedorId(
            Long productoId,
            Long proveedorId
    );
}
