package com.ferreteriapsa.gestioncomercial.compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriapsa.gestioncomercial.compra.model.DetalleOrdenCompra;

import java.util.Optional;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra,Long>{
    Optional<DetalleOrdenCompra> findByOrdenCompraOrdenCompraIdAndProductoProductoId(
            Long ordenCompraId,
            Long productoId
    );
    
}
