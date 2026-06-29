package com.ferreteriapsa.gestionlogistica.trabajador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ferreteriapsa.gestionlogistica.trabajador.dto.request.*;
import com.ferreteriapsa.gestionlogistica.trabajador.dto.response.*;
import com.ferreteriapsa.gestionlogistica.trabajador.service.TrabajadorService;

import java.util.List;

@RestController
@RequestMapping("/logistica/trabajadores")
public class TrabajadorController {
    private final TrabajadorService trabajadorService;

    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TrabajadorResponse> registrarTrabajador(@RequestBody TrabajadorRequest request) {
        TrabajadorResponse nuevoTrabajador = trabajadorService.registrarTrabajadorCompleto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTrabajador);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TrabajadorResponse>> listarTrabajadores(){
        List<TrabajadorResponse> trabajadores = trabajadorService.listarTrabajadores();
        return new ResponseEntity<>(trabajadores,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<TrabajadorUpdateResponse> actualizarTrabajador(@PathVariable("id") Long trabajadorId, 
        @RequestBody TrabajadorUpdateRequest request){
        
        TrabajadorUpdateResponse trabajadorActualizado = trabajadorService.actualizarTrabajador(request, trabajadorId);
        return new ResponseEntity<>(trabajadorActualizado,HttpStatus.OK);
        //return ResponseEntity.ok(trabajadorActualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desvincularTrabajador(@PathVariable("id") Long trabajadorId){
        trabajadorService.desvincularTrabajador(trabajadorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        //return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sucursales")
    public ResponseEntity<List<TiendaResponse>> listarTiendasConLineas(){
        List<TiendaResponse> listaTiendas = trabajadorService.listarTiendasConLineas();
        return new ResponseEntity<>(listaTiendas,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorDTO> buscarTrabajador(@PathVariable("id") Long usuarioId){
        TrabajadorDTO trabajador = trabajadorService.buscarTrabajador(usuarioId);
        return ResponseEntity.ok(trabajador);
    }
}
