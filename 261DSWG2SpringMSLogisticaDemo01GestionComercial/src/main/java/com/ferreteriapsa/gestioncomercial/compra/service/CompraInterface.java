package com.ferreteriapsa.gestioncomercial.compra.service;

import java.time.LocalDateTime;

import com.ferreteriapsa.gestioncomercial.compra.dto.response.OrdenCompraClientDTO;

public interface CompraInterface {
    OrdenCompraClientDTO obtenerOrdenCompraPorId(Long ordenCompraId);
    void recepcionarOrdenCompra(Long ordenCompraId, String nuevoEstado, LocalDateTime fechaEntrega);
}
