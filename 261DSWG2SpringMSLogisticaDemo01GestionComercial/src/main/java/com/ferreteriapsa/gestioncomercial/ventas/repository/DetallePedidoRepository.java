package com.ferreteriapsa.gestioncomercial.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriapsa.gestioncomercial.ventas.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Long>{
    
}
