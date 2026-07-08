package com.ferreteriapsa.gestioncomercial.compra.dto.response;

public class DetalleOrdenCompraClientDTO {
    private Long DetalleOrdenCompraId;
    private int cantidad;
    private Long productoId;

    public DetalleOrdenCompraClientDTO(){}

    public Long getDetalleOrdenCompraId() { return DetalleOrdenCompraId; }
    public void setDetalleOrdenCompraId(Long detalleOrdenCompraId) { DetalleOrdenCompraId = detalleOrdenCompraId; }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
}
