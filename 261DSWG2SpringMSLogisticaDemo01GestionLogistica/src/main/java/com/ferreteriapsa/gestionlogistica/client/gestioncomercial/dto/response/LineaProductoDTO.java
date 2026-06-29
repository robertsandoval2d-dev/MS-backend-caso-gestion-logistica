package com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response;

public class LineaProductoDTO {
    private Long lineaProductoId;
    private String nombre;

    public LineaProductoDTO() {}

    public Long getLineaProductoId() { return lineaProductoId; }
    public void setLineaProductoId(Long lineaProductoId) { this.lineaProductoId = lineaProductoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombreLinea) { this.nombre = nombreLinea; }
}
