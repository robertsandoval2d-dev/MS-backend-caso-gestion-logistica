package com.ferreteriapsa.gestionlogistica.inventario.controller;

import java.util.List;
import java.util.Map;

import com.ferreteriapsa.gestionlogistica.inventario.dto.request.ProductoRotacionDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.ferreteriapsa.gestionlogistica.config.CustomUserPrincipal;

import com.ferreteriapsa.gestionlogistica.inventario.dto.response.*;
import com.ferreteriapsa.gestionlogistica.inventario.service.InventarioService;
import com.ferreteriapsa.gestionlogistica.inventario.dto.request.*;

@RestController
@RequestMapping("/logistica/inventario")
public class InventarioController {
    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }
    // @PreAuthorize("hasRole('JEFE_DE_LINEA')")
    // @GetMapping("/productos-linea")
    // public ResponseEntity<List<InventarioDTO>> listarLineaProducto(
    //         @AuthenticationPrincipal CustomUserPrincipal principal) {

    //     Long trabajadorId = principal.getTrabajadorId();

    //     List<InventarioDTO> listaProductos = inventarioService.listarInventarioLinea(trabajadorId);
    //     return new ResponseEntity<>(listaProductos, HttpStatus.OK);
    // }

    // @PreAuthorize("hasRole('ALMACENERO')")
    // @PostMapping("/ordenes-compra/recepcion")
    // public ResponseEntity<Map<String,String>> registrarRecepcion(
    //         @RequestBody RegistroMercaderiaRequest request,
    //         @AuthenticationPrincipal CustomUserPrincipal principal){

    //     Long trabajadorId = principal.getTrabajadorId();
    //     inventarioService.regitrarOrdenCompra(request, trabajadorId);

    //     return ResponseEntity.ok(
    //             Map.of("mensaje", "Mercadería recepcionada correctamente")
    //     );
    // }

    // @PreAuthorize("hasRole('JEFE_DE_LINEA')")
    // @PatchMapping("/productos/{id}")
    // public ResponseEntity<ProductoRotacionDTO> cambiarRotacion(
    //         @PathVariable("id") Long productoId,
    //         @RequestBody ProductoRotacionDTO request
    // ){
    //     ProductoRotacionDTO rotacion = inventarioService.cambiarRotacion(productoId, request);
    //     return new ResponseEntity<>(rotacion,HttpStatus.OK);
    // }

    @GetMapping("/trabajador/{id}/filtro-catalogo")
    public ResponseEntity<FiltroCatalogoTrabajadorDTO> obtenerFiltroCatalogoParaTrabajador(@PathVariable("id") Long trabajadorId) {
        FiltroCatalogoTrabajadorDTO dto = inventarioService.obtenerFiltrosCatalogoPorTrabajador(trabajadorId);
        return ResponseEntity.ok(dto);
    }


}
