package com.ferreteriapsa.gestioncomercial.catalogo.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultaVentasRequest {
    private List<Long> productoIds;
    private List<Long> tiendaIds;
    private LocalDateTime fechaLimite;

    // Constructores
    public ConsultaVentasRequest() {}

    public ConsultaVentasRequest(List<Long> productoIds, List<Long> tiendaIds, LocalDateTime fechaLimite) {
        this.productoIds = productoIds;
        this.tiendaIds = tiendaIds;
        this.fechaLimite = fechaLimite;
    }

    // Getters y Setters
    public List<Long> getProductoIds() { return productoIds; }
    public void setProductoIds(List<Long> productoIds) { this.productoIds = productoIds; }

    public List<Long> getTiendaIds() { return tiendaIds; }
    public void setTiendaIds(List<Long> tiendaIds) { this.tiendaIds = tiendaIds; }

    public LocalDateTime getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDateTime fechaLimite) { this.fechaLimite = fechaLimite; }
}
