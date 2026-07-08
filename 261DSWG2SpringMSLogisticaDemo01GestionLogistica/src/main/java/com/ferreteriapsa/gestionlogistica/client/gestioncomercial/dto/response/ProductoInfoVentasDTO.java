package com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response;

public class ProductoInfoVentasDTO {
    private Long productoId;
    private String nombre;
    private Long lineaProductoId;
    private Long tiendaId;
    private Long cantidadVentas;

    public ProductoInfoVentasDTO(Long productoId, String nombre, Long lineaProductoId, Long tiendaId, Long cantidadVentas) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.lineaProductoId = lineaProductoId;
        this.tiendaId = tiendaId;
        this.cantidadVentas = cantidadVentas;
    }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getLineaProductoId() { return lineaProductoId; }
    public void setLineaProductoId(Long lineaProductoId) { this.lineaProductoId = lineaProductoId; }

    public Long getTiendaId() { return tiendaId; }
    public void setTiendaId(Long tiendaId) { this.tiendaId = tiendaId; }

    public Long getCantidadVentas() { return cantidadVentas; }
    public void setCantidadVentas(Long cantidadVentas) { this.cantidadVentas = cantidadVentas; }
}