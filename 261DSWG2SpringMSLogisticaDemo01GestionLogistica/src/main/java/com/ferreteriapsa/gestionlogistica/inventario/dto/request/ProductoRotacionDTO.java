package com.ferreteriapsa.gestionlogistica.inventario.dto.request;

import com.ferreteriapsa.gestionlogistica.inventario.model.Rotacion;

public class ProductoRotacionDTO {
    private Rotacion rotacion;

    public ProductoRotacionDTO() {
    }

    public Rotacion getRotacion() {
        return rotacion;
    }

    public void setRotacion(Rotacion rotacion) {
        this.rotacion = rotacion;
    }
}

