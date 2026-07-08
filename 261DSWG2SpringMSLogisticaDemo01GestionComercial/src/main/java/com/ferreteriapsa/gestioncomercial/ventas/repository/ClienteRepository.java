package com.ferreteriapsa.gestioncomercial.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriapsa.gestioncomercial.ventas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long>{

    
}
