package com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenCompraDTO {
    private Long ordenCompraId;
    private String estado;
    private LocalDateTime plazoFechaMaximo;
    private LocalDateTime fechaEntrega;
    private List<DetalleOrdenCompraDTO> detalles;

    public OrdenCompraDTO() {}

    public Long getOrdenCompraId() { return ordenCompraId; }
    public void setOrdenCompraId(Long ordenCompraId) { this.ordenCompraId = ordenCompraId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getPlazoFechaMaximo() { return plazoFechaMaximo; }
    public void setPlazoFechaMaximo(LocalDateTime plazoFechaMaximo) { this.plazoFechaMaximo = plazoFechaMaximo; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public List<DetalleOrdenCompraDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrdenCompraDTO> detalles) { this.detalles = detalles; }
}