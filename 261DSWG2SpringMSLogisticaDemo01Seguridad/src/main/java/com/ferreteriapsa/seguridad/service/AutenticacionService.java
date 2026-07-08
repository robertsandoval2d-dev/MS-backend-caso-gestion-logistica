package com.ferreteriapsa.seguridad.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.ferreteriapsa.seguridad.model.*;
import com.ferreteriapsa.seguridad.repository.*;

import jakarta.transaction.Transactional;

import com.ferreteriapsa.seguridad.dto.request.*;
import com.ferreteriapsa.seguridad.dto.response.*;
import com.ferreteriapsa.seguridad.client.gestionlogistica.GestionLogisticaClient;
import com.ferreteriapsa.seguridad.client.gestionlogistica.dto.response.TrabajadorDTO;
import com.ferreteriapsa.seguridad.config.JwtService;

import java.util.List;

@Service
public class AutenticacionService implements AutenticacionInterface{
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final GestionLogisticaClient gestionComercialClient;

    public AutenticacionService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
        GestionLogisticaClient gestionComercialClient
    ) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.gestionComercialClient = gestionComercialClient;
    }

    @Override
    public UsuarioResponse registrarUsuario(UsuarioRegistroRequest request) {
        // 1. verificar si ya existe
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException( //409 CONFLICT
                    HttpStatus.CONFLICT,
                    "El nombre de usuario ya existe"
            );
        }

        //2. crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));  //encriptar password (BCrypt)
        usuario.setActivo(true);
        
        Rol rol = rolRepository.findByNombre(request.getUserRol())
          .orElseThrow(() -> new ResponseStatusException( //404 NOT FOUND
                    HttpStatus.NOT_FOUND,
                    "Rol no encontrado"
            ));

        usuario.setRol(rol);

        // 3. guardar en BD (Hibernate)
        usuarioRepository.save(usuario);

        UsuarioResponse response = new UsuarioResponse();
        response.setUsuarioId(usuario.getUsuarioId());
        response.setRolNombre(usuario.getRol().getNombre());
        response.setUsername(usuario.getUsername());
        response.setActivo(usuario.isActivo());

        return response;
    }

    @Override
    @Transactional
    public void desactivarCuenta(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontró una cuenta de usuario para el trabajador especificado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioResponse> listarInfoUsuarios(){
        return usuarioRepository.listarTodosLosUsuarios();
    }

    @Override
    public String obtenerRolUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontró una cuenta de usuario para el trabajador especificado"));
        return usuario.getRol().getNombre();
    }

    @Override
    public UsuarioResponse obtenerUsuarioPorId(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontró una cuenta de usuario para el trabajador especificado"));
        
        UsuarioResponse response = new UsuarioResponse();
        response.setUsuarioId(usuario.getUsuarioId());
        response.setUsername(usuario.getUsername());
        response.setRolNombre(usuario.getRol().getNombre());
        response.setActivo(usuario.isActivo());

        return response;
    }

    @Override
    public UsuarioResponse obtenerUsuarioPorUsername(String username){
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontró una cuenta de usuario para el trabajador especificado"));
        
        UsuarioResponse response = new UsuarioResponse();
        response.setUsuarioId(usuario.getUsuarioId());
        response.setUsername(usuario.getUsername());
        response.setRolNombre(usuario.getRol().getNombre());
        response.setActivo(usuario.isActivo());

        return response;
    }

    public Auth login(UsuarioRequest request) {

        // 1. Buscar usuario y trabajador
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException( //401 UNAUTHORIZED
                HttpStatus.UNAUTHORIZED,
                "Credenciales inválidas"
                ));
        
        TrabajadorDTO trabajador = gestionComercialClient.buscarTrabajador(usuario.getUsuarioId());

        // 2. Verificar si está activo
        if (!usuario.isActivo()) {
            throw new ResponseStatusException( //403 FORBIDDEN
                    HttpStatus.FORBIDDEN,
                    "Usuario deshabilitado"
            );
        }

        // 3. Comparar contraseña (BCrypt)
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException( //401 UNAUTHORIZED
                HttpStatus.UNAUTHORIZED,
                "Credenciales inválidas"
            );
        }

        // 4. Generar token y refreshToken
         String token = jwtService.generateToken(usuario,trabajador);
         String refreshToken = jwtService.generateRefreshToken(usuario);

        // 5. Devolver token
        return new Auth(token, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken){
        String username = jwtService.extractUsername(refreshToken);

        Usuario usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException( //401 UNAUTHORIZED
        HttpStatus.UNAUTHORIZED,
        "Usuario no encontrado"
        ));

        TrabajadorDTO trabajador = gestionComercialClient.buscarTrabajador(usuario.getUsuarioId());

        if (!usuario.isActivo()) {
            throw new ResponseStatusException( //403 FORBIDDEN
                    HttpStatus.FORBIDDEN,
                    "Usuario deshabilitado"
            );
        }
        
        if(!jwtService.isTokenValid(refreshToken, usuario)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión expirada");
        }

        String token = jwtService.generateToken(usuario,trabajador);

        return new AuthResponse(token);
    }
}
