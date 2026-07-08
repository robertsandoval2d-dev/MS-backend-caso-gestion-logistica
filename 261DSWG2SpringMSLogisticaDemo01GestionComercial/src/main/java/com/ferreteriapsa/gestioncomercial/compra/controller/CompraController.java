package com.ferreteriapsa.gestioncomercial.compra.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.ferreteriapsa.gestioncomercial.config.CustomUserPrincipal;
import com.ferreteriapsa.gestioncomercial.compra.dto.request.*;
import com.ferreteriapsa.gestioncomercial.compra.dto.response.*;
import com.ferreteriapsa.gestioncomercial.compra.service.CompraService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logistica/compras")
public class CompraController {
    private final CompraService compraService;

    public CompraController(CompraService compraService){
        this.compraService = compraService;
    }

    @PreAuthorize("hasRole('ADMINISTRADOR_DE_TIENDA')")
    @PostMapping("/ordenes-compra")
    public ResponseEntity<OrdenCompraResponse> generarOrdenCompra(
        @AuthenticationPrincipal CustomUserPrincipal principal,
        @RequestBody OrdenCompraRequest request){

        Long trabajadorId = principal.getTrabajadorId();
        OrdenCompraResponse response = compraService.generarOrdenCompra(request, trabajadorId);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ALMACENERO','JEFE_DE_LINEA')")
    @GetMapping("/ordenes-compra")
    public ResponseEntity<List<OrdenesCompraResponse>> listarOrdenes(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestParam(required = false) Long ordenId){

        Long trabajadorId = principal.getTrabajadorId();
        List<OrdenesCompraResponse> listaOrdenesCompra = compraService.listarOrdenesPorTiendaYProveedor(
                trabajadorId,
                ordenId
        );
        return ResponseEntity.ok(listaOrdenesCompra);
    }

    @PreAuthorize("hasRole('JEFE_DE_LINEA')")
    @GetMapping("/ordenes-compra/simple")
    public ResponseEntity<List<OrdenCompraSimpleResponse>> listarOrdenesSimple(
            @AuthenticationPrincipal CustomUserPrincipal principal){

        Long trabajadorId = principal.getTrabajadorId();
        List<OrdenCompraSimpleResponse> listaOrdenesCompraSimple = compraService.listarOrdenesCompraSimple(trabajadorId);

        return ResponseEntity.ok(listaOrdenesCompraSimple);
    }

    @PreAuthorize("hasRole('ALMACENERO')") 
    @GetMapping("/ordenes-compra/{id}")
    public ResponseEntity<OrdenCompraClientDTO> obtenerOrdenCompraClient(
            @PathVariable("id") Long ordenCompraId) {
        
        OrdenCompraClientDTO response = compraService.obtenerOrdenCompraPorId(ordenCompraId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ALMACENERO')")
    @PutMapping("/ordenes-compra/{id}/recepcion")
    public ResponseEntity<Void> recepcionarOrdenCompra(
            @PathVariable("id") Long ordenCompraId,
            @RequestParam String nuevoEstado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaEntrega) {
        
        compraService.recepcionarOrdenCompra(ordenCompraId, nuevoEstado, fechaEntrega);
        
        return ResponseEntity.noContent().build();
    }
    
}
