package com.ferreteriapsa.gestionlogistica.analisis.dto.response;

import com.ferreteriapsa.gestionlogistica.inventario.model.Inventario;

public class InventarioLocalDTO {
    private Inventario inventario;
    private Long tiendaId;
    private Long lineaProductoIdAsignada;

    public InventarioLocalDTO(Inventario inventario, Long tiendaId, Long lineaProductoIdAsignada) {
        this.inventario = inventario;
        this.tiendaId = tiendaId;
        this.lineaProductoIdAsignada = lineaProductoIdAsignada;
    }
    
    public Inventario getInventario() { return inventario; }
    public void setInventario(Inventario inventario) { this.inventario = inventario; }

    public Long getTiendaId() { return tiendaId; }
    public void setTiendaId(Long tiendaId) { this.tiendaId = tiendaId; }

    public Long getLineaProductoIdAsignada() { return lineaProductoIdAsignada; }
    public void setLineaProductoIdAsignada(Long lineaProductoIdAsignada) { this.lineaProductoIdAsignada = lineaProductoIdAsignada; }
}
