package com.ferreteriapsa.gestioncomercial.catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.ferreteriapsa.gestioncomercial.catalogo.dto.response.*;
import com.ferreteriapsa.gestioncomercial.catalogo.model.LineaProducto;
import com.ferreteriapsa.gestioncomercial.catalogo.service.CatalogoService;
import com.ferreteriapsa.gestioncomercial.config.CustomUserPrincipal;

@RestController
@RequestMapping("/logistica/catalogo")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @PreAuthorize("hasRole('JEFE_DE_LINEA')")
    @GetMapping("/productos-linea")
    public ResponseEntity<List<CatalogoResponse>> listarCatalogoLinea(
            @AuthenticationPrincipal CustomUserPrincipal principal) {

        Long trabajadorId = principal.getTrabajadorId();

        List<CatalogoResponse> catalogo =
                catalogoService.obtenerCatalogo(trabajadorId);

        return new ResponseEntity<>(catalogo, HttpStatus.OK);
    }

    @GetMapping("/linea-detalle/{id}")
    public ResponseEntity<LineaProducto> buscarLineaProducto(@PathVariable("id") Long lineaProductoId){
        LineaProducto lineaProducto = catalogoService.buscarLineaProducto(lineaProductoId);

        return new ResponseEntity<>(lineaProducto, HttpStatus.OK);
    }
}