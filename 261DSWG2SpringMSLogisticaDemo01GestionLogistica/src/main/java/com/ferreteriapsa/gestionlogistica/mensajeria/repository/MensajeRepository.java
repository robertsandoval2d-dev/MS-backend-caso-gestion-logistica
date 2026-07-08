package com.ferreteriapsa.gestionlogistica.mensajeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ferreteriapsa.gestionlogistica.mensajeria.model.Mensaje;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    @Query("""
    SELECT m
    FROM Mensaje m
    WHERE m.emisorId = :usuarioId
       OR m.receptorId = :usuarioId
    ORDER BY m.fechaEnvio DESC
    """)
    List<Mensaje> listarMensajesPorUsuario(Long usuarioId);
}
