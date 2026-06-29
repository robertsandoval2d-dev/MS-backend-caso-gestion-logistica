package com.ferreteriapsa.gestioncomercial.catalogo.repository;

public interface CatalogoProjection {

    Long getProductoId();
    String getProductoNombre();

    Long getProveedorId();
    String getProveedorNombre();
}
