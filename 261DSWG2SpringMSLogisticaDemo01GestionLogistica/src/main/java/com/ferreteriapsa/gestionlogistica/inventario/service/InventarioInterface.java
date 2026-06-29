package com.ferreteriapsa.gestionlogistica.inventario.service;

import com.ferreteriapsa.gestionlogistica.inventario.dto.response.FiltroCatalogoTrabajadorDTO;

public interface InventarioInterface {
    FiltroCatalogoTrabajadorDTO obtenerFiltrosCatalogoPorTrabajador(Long trabajadorId);
}
