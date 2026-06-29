package com.ferreteriapsa.gestioncomercial.catalogo.service;

import com.ferreteriapsa.gestioncomercial.catalogo.model.LineaProducto;

public interface CatalogoInterface {
    LineaProducto buscarLineaProducto(Long lineaProductoId);
}
