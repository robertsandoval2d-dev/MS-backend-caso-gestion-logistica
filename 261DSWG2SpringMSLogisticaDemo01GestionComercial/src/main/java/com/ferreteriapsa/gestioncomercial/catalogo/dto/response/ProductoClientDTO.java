package com.ferreteriapsa.gestioncomercial.catalogo.dto.response;

import java.math.BigDecimal;

public class ProductoClientDTO {
    private Long productoId;
    private String nombre;
    private BigDecimal precioVenta;
    private String categoria;
    private Long lineaProductoId;

    public ProductoClientDTO(){}

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Long getLineaProductoId() { return lineaProductoId; }
    public void setLineaProductoId(Long lineaProductoId) { this.lineaProductoId = lineaProductoId; }
}
