package com.ferreteriapsa.gestionlogistica.inventario.dto.response;

import java.util.List;

public class FiltroCatalogoTrabajadorDTO {
    private Long lineaProductoId;
    private List<Long> productosIds;

    public FiltroCatalogoTrabajadorDTO() {}

    public FiltroCatalogoTrabajadorDTO(Long lineaProductoId, List<Long> productosIds) {
        this.lineaProductoId = lineaProductoId;
        this.productosIds = productosIds;
    }

    public Long getLineaProductoId() { return lineaProductoId; }
    public void setLineaProductoId(Long lineaProductoId) { this.lineaProductoId = lineaProductoId; }
    
    public List<Long> getProductosIds() { return productosIds; }
    public void setProductosIds(List<Long> productosIds) { this.productosIds = productosIds; }
}