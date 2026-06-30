package com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response;

public class ProductoDetalleDTO {
    private Long productoId;
    private String nombre;
    private String categoria;

    // Constructores, Getters y Setters
    public ProductoDetalleDTO() {}

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
