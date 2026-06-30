package com.ferreteriapsa.gestioncomercial.catalogo.dto.response;

public class ProductoDetalleDTO {
    private Long productoId;
    private String nombre;
    private String categoria;

    // Constructores obligatorios para JPA
    public ProductoDetalleDTO() {}

    public ProductoDetalleDTO(Long productoId, String nombre, String categoria) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    // Getters y Setters
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
