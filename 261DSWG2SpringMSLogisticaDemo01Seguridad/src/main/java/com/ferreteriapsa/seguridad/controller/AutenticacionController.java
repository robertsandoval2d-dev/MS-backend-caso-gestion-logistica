package com.ferreteriapsa.seguridad.controller;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ferreteriapsa.seguridad.dto.request.*;
import com.ferreteriapsa.seguridad.dto.response.*;
import com.ferreteriapsa.seguridad.service.AutenticacionService;

//@CrossOrigin(origins = "http://localhost:4200") // ADDED
@RestController
@RequestMapping("/logistica/auth")
public class AutenticacionController {
    private final AutenticacionService autenticacionService;

    public AutenticacionController(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioRequest request) {
        Auth credenciales = autenticacionService.login(request);

        ResponseCookie cookie = ResponseCookie.from(
                "refresh_token",
                credenciales.getRefreshToken())
                .httpOnly(true)
                .secure(true) // false en localhost
                .path("/")
                .maxAge(86400) //1 dia * 24 * 60 * 60
                .sameSite("None")
                .build();

        AuthResponse token = new AuthResponse(credenciales.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue("refresh_token") String refreshToken) {
        AuthResponse credenciales = autenticacionService.refreshToken(refreshToken);

        return ResponseEntity.ok(credenciales);
    } 

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        ResponseCookie cookie = ResponseCookie.from(
                "refresh_token",
                "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        return ResponseEntity.ok()
                .header(
                    HttpHeaders.SET_COOKIE,
                    cookie.toString()
                )
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> registrarUsuario(@RequestBody UsuarioRegistroRequest request){
        UsuarioResponse usuario = autenticacionService.registrarUsuario(request);

        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/usuarios/desactivar/{id}")
    public ResponseEntity<Void> desactivarCuentaUsuario(@PathVariable("id") Long usuarioId){
        autenticacionService.desactivarCuenta(usuarioId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("usuarios")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios(){
        List<UsuarioResponse> usuarios = autenticacionService.listarInfoUsuarios();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("usuarios/rol/{id}")
    public ResponseEntity<Map<String,String>> obtenerRolUsuario(@PathVariable("id") Long usuarioId){
        String rol = autenticacionService.obtenerRolUsuario(usuarioId);

        return new ResponseEntity<>(Map.of("rol",rol),HttpStatus.OK);
    }

}
