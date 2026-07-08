package com.ferreteriapsa.gestionlogistica.mensajeria.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import com.ferreteriapsa.gestionlogistica.mensajeria.repository.MensajeRepository;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Trabajador;
import com.ferreteriapsa.gestionlogistica.trabajador.repository.TrabajadorRepository;
import com.ferreteriapsa.gestionlogistica.client.seguridad.SeguridadClient;
import com.ferreteriapsa.gestionlogistica.client.seguridad.dto.response.UsuarioResponse;
import com.ferreteriapsa.gestionlogistica.mensajeria.dto.request.*;
import com.ferreteriapsa.gestionlogistica.mensajeria.dto.response.*;
import com.ferreteriapsa.gestionlogistica.mensajeria.model.Mensaje;

import jakarta.transaction.Transactional;

@Service
public class MensajeriaService {

    private final MensajeRepository mensajeRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final SeguridadClient seguridadClient;

    public MensajeriaService(MensajeRepository mensajeRepository, SeguridadClient seguridadClient, TrabajadorRepository trabajadorRepository) {
        this.mensajeRepository = mensajeRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.seguridadClient = seguridadClient;
    }

    @Transactional
    public void registrarMensaje(MensajeRequest request, Long trabajadorId){

        UsuarioResponse receptor = seguridadClient.obtenerUsuarioPorUsername(request.getUsernameDestino());

        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
            .orElseThrow(() -> new ResponseStatusException(//404 NOT FOUND
                HttpStatus.NOT_FOUND,
                "Trabajador no encontrado"
            ));

        UsuarioResponse emisor = seguridadClient.obtenerUsuarioPorId(trabajador.getUsuarioId());

        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setEmisorId(emisor.getUsuarioId());
        nuevoMensaje.setEmisorUsername(emisor.getUsername());
        nuevoMensaje.setReceptorId(receptor.getUsuarioId());
        nuevoMensaje.setReceptorUsername(receptor.getUsername());
        nuevoMensaje.setTitulo(request.getTitulo());
        nuevoMensaje.setMensaje(request.getMensaje());
        nuevoMensaje.setLeido(false);
        nuevoMensaje.setFechaEnvio(LocalDateTime.now());

        mensajeRepository.save(nuevoMensaje);
    }

    public List<MensajeResponse> listarMensajesUsuario(Long trabajadorId){

        // Usuario usuario = usuarioRepository.findByTrabajador_TrabajadorId(trabajadorId).orElse(null);
        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
            .orElseThrow(() -> new ResponseStatusException(//404 NOT FOUND
                HttpStatus.NOT_FOUND,
                "Trabajador no encontrado"
            ));

        UsuarioResponse usuario = seguridadClient.obtenerUsuarioPorId(trabajador.getUsuarioId());
        
        List<Mensaje> mensajes = mensajeRepository.listarMensajesPorUsuario(usuario.getUsuarioId());

        List<MensajeResponse> response = mensajes.stream()
            .map(mensaje -> {
                MensajeResponse mr = new MensajeResponse();
                mr.setMensajeId(mensaje.getMensajeId());
                mr.setTitulo(mensaje.getTitulo());
                mr.setMensaje(mensaje.getMensaje());
                mr.setEmisorId(mensaje.getEmisorId());
                mr.setEmisorUsername(mensaje.getEmisorUsername());
                mr.setReceptorId(mensaje.getReceptorId());
                mr.setReceptorUsername(mensaje.getReceptorUsername());
                mr.setFechaEnvio(mensaje.getFechaEnvio());
                mr.setLeido(mensaje.getLeido());
                return mr;
            }).toList();
        
        return response;
    }

    @Transactional
    public MensajeResponse marcarMensajeComoLeido(Long mensajeId){
        Mensaje mensaje = mensajeRepository.findById(mensajeId)
            .orElseThrow(() -> new ResponseStatusException(//404 NOT FOUND
                HttpStatus.NOT_FOUND,
                "Mensaje no encontrado"
            ));

        mensaje.setLeido(true);

        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);

        MensajeResponse response = new MensajeResponse();

        response.setEmisorId(mensajeGuardado.getMensajeId());
        response.setTitulo(mensajeGuardado.getTitulo());
        response.setMensaje(mensajeGuardado.getMensaje());
        response.setEmisorId(mensajeGuardado.getEmisorId());
        response.setEmisorUsername(mensaje.getEmisorUsername());
        response.setReceptorId(mensaje.getReceptorId());
        response.setReceptorUsername(mensaje.getReceptorUsername());
        response.setFechaEnvio(mensaje.getFechaEnvio());
        response.setLeido(mensaje.getLeido());

        return response;
    }
    
}
