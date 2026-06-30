package com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response;

public class AsignacionDTO {
    private Long lineaProductoId;
    private Long tiendaId;
    private String nombreLinea;
    private Boolean activo;

    public AsignacionDTO(){}

    public Long getLineaProductoId() { return lineaProductoId; }
    public void setLineaProductoId(Long lineaProductoId) { this.lineaProductoId = lineaProductoId; }

    public Long getTiendaId() { return tiendaId; }
    public void setTiendaId(Long tiendaId) { this.tiendaId = tiendaId; }

    public String getNombreLinea() { return nombreLinea; }
    public void setNombreLinea(String nombreLinea) { this.nombreLinea = nombreLinea; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}

