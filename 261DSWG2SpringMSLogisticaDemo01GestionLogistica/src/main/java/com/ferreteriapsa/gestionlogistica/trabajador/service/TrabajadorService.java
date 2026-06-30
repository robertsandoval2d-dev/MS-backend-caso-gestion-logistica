package com.ferreteriapsa.gestionlogistica.trabajador.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.GestionComercialClient;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response.LineaProductoDTO;
import com.ferreteriapsa.gestionlogistica.client.seguridad.SeguridadClient;
import com.ferreteriapsa.gestionlogistica.client.seguridad.dto.request.*;
import com.ferreteriapsa.gestionlogistica.client.seguridad.dto.response.*;
import com.ferreteriapsa.gestionlogistica.trabajador.dto.request.*;
import com.ferreteriapsa.gestionlogistica.trabajador.dto.response.*;
import com.ferreteriapsa.gestionlogistica.trabajador.model.*;  
import com.ferreteriapsa.gestionlogistica.trabajador.repository.*;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TrabajadorService implements TrabajadorInterface{
    private final SeguridadClient seguridadClient;
    private final GestionComercialClient gestionComercialClient;
    private final TrabajadorRepository trabajadorRepository;
    private final TiendaRepository tiendaRepository;
    // private final LineaProductoRepository lineaRepository;
    private final AsignacionRepository asignacionRepository;
    
    public TrabajadorService(SeguridadClient seguridadClient, GestionComercialClient gestionComercialClient, TrabajadorRepository trabajadorRepository,
        TiendaRepository tiendaRepository, /*LineaProductoRepository lineaRepository,*/ AsignacionRepository asignacionRepository) {
        this.seguridadClient = seguridadClient;
        this.gestionComercialClient = gestionComercialClient;
        this.trabajadorRepository = trabajadorRepository;
        this.tiendaRepository = tiendaRepository;
        // this.lineaRepository = lineaRepository;
        this.asignacionRepository = asignacionRepository;
    }

    @Transactional
    public TrabajadorResponse registrarTrabajadorCompleto(TrabajadorRequest request) {

        // 1. Crear usuario
        // Usuario usuario = autenticacionService.registrarUsuario(request.getUsername(), request.getPassword(), request.getRol());
        UsuarioRegistroRequest userRequest = new UsuarioRegistroRequest();
        userRequest.setPassword(request.getPassword());
        userRequest.setUsername(request.getUsername());
        userRequest.setUserRol(request.getRol());

        UsuarioResponse usuario = seguridadClient.registrarNuevoUsuario(userRequest);

        // 2. Crear trabajador base
        Trabajador trabajador = new Trabajador();
        trabajador.setNombre(request.getNombre());
        trabajador.setDni(request.getDni());
        trabajador.setUsuarioId(usuario.getUsuarioId());
        trabajador.setMail(request.getMail());

        // 3. Preparar los datos comunes para la Asignación
        trabajador = trabajadorRepository.save(trabajador);

        String rol = usuario.getRolNombre().toLowerCase();

        if (!rol.equalsIgnoreCase("admin")) {
            if (request.getTiendaId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este rol requiere una tienda");
            }

            Tienda tienda = tiendaRepository.findById(request.getTiendaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tienda no encontrada"));

            // 4. Lógica específica según el rol dentro de la Asignación
            switch (rol) {
                case "almacenero":
                case "administrador_de_tienda":
                    // Para roles generales, creamos la asignación normal
                    Asignacion asigGeneral = new Asignacion();
                    asigGeneral.setTrabajador(trabajador);
                    asigGeneral.setTienda(tienda);
                    asigGeneral.setFechaInicio(LocalDate.now());
                    asigGeneral.setActivo(true);
                    asignacionRepository.save(asigGeneral);
                    break;

                case "jefe_de_linea":
                    if (request.getLineaId() == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proporcionar lineaId para jefe de línea");
                    }
                    
                    // LineaProducto linea = lineaRepository.findById(request.getLineaId())
                    //     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada"));
                    LineaProductoDTO linea = gestionComercialClient.buscarLineaProducto(request.getLineaId());

                    // --- LÓGICA DE ROTACIÓN AUTOMÁTICA ---
                    // Buscamos si ya hay alguien activo en esa tienda para esa misma línea
                    Optional<Asignacion> asignacionPrevia = asignacionRepository
                        .findByTiendaAndLineaProductoIdAndActivoTrue(tienda, linea.getLineaProductoId());

                    if (asignacionPrevia.isPresent()) {
                        Asignacion anterior = asignacionPrevia.get();
                        if(anterior.getTrabajador()==null){
                            // CASO 1: La línea existe en la tienda pero está VACANTE. Reutilizamos el registro.
                            anterior.setTrabajador(trabajador);
                            anterior.setFechaInicio(LocalDate.now()); //inicia hoy
                            asignacionRepository.save(anterior);
                        }
                        else if (!anterior.getTrabajador().getTrabajadorId().equals(trabajador.getTrabajadorId())) {
                            // CASO 2: La línea está OCUPADA por otro. Inactivamos (rotación) y creamos nuevo.
                            anterior.setActivo(false);
                            anterior.setFechaFin(LocalDate.now());
                            asignacionRepository.save(anterior);

                            Asignacion nuevaAsig = new Asignacion();
                            nuevaAsig.setTrabajador(trabajador);
                            nuevaAsig.setTienda(tienda);
                            nuevaAsig.setLineaProductoId(linea.getLineaProductoId());
                            nuevaAsig.setNombreLinea(linea.getNombre());
                            nuevaAsig.setFechaInicio(LocalDate.now());
                            nuevaAsig.setActivo(true);
                            asignacionRepository.save(nuevaAsig);
                        }
                    }else{
                        // CASO 3: La tienda no tiene esta línea registrada en su catálogo.
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                            "Error: La tienda " + tienda.getNombre() + " no tiene habilitada la línea seleccionada" + linea.getNombre());
                    }
                    break;

                default:
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Rol no permitido para asignación de tienda");
            }
        }

        return new TrabajadorResponse(
            trabajador.getTrabajadorId(), 
            usuario.getRolNombre(), 
            usuario.getUsername(),
            usuario.isActivo(),
            trabajador.getNombre(),
            trabajador.getDni()
        );
    }

    public List<TrabajadorResponse> listarTrabajadores(){
        List<TrabajadorResponse> trabajadores =trabajadorRepository.listarTrabajadoresConTienda();
       
        if (trabajadores.isEmpty()) {
            return trabajadores;
        }

        List<UsuarioResponse> usuarios = seguridadClient.listarUsuarios();

        Map<Long, UsuarioResponse> mapaUsuarios = usuarios.stream()
                .collect(Collectors.toMap(UsuarioResponse::getUsuarioId, u -> u));

        for (TrabajadorResponse t : trabajadores) {
            
            UsuarioResponse usr = mapaUsuarios.get(t.getUsuarioId()); 
            
            if (usr != null) {
                // Seteamos los datos que trajimos del otro microservicio
                t.setRol(usr.getRolNombre()); 
                t.setUsername(usr.getUsername());
                t.setCuentaActiva(usr.isActivo());
            }
        }
        return trabajadores;
    }

    @Transactional
    public TrabajadorUpdateResponse actualizarTrabajador(TrabajadorUpdateRequest request, Long trabajadorId){
        // 1. Buscar el trabajador
        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
            .orElseThrow(() -> new ResponseStatusException( //404 NOT FOUND
                    HttpStatus.NOT_FOUND,
                    "Trabajador no encontrado"
            ));

        // 2. Obtener el Rol actual del trabajador (Desde su Usuario)
        Map<String,String> respuesta = seguridadClient.obtenerRolUsuario(trabajador.getUsuarioId());
        String rol = respuesta.get("rol").toLowerCase();

        // 3. Validar coherencia entre Rol y Request
        if (rol.equals("jefe_de_linea") && request.getLineaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un Jefe de Línea debe tener una línea asignada.");
        }
        
        if (!rol.equals("jefe_de_linea") && request.getLineaId() != null) {
            // Si no es jefe de línea, ignoramos el lineaId o lanzamos error para evitar datos inconsistentes
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo los Jefes de Línea pueden tener una línea de productos.");
        }

        // 4. Actualizar datos básicos
        trabajador.setNombre(request.getNombre());
        trabajador.setDni(request.getDni());
        trabajador = trabajadorRepository.save(trabajador);

        TrabajadorUpdateResponse response = new TrabajadorUpdateResponse();

        // 5. Gestionar la rotación/cambio de tienda o línea
        // Buscamos si ya tiene una asignación activa actualmente
        if(!rol.equals("admin")){
            Asignacion asignacionActual = asignacionRepository.findByTrabajadorAndActivoTrue(trabajador)
                .orElseThrow(() -> new ResponseStatusException( //404 NOT FOUND
                        HttpStatus.NOT_FOUND,
                        "Asignación no existe"
                ));
            
            Tienda nuevaTienda = tiendaRepository.findById(request.getTiendaId())
                .orElseThrow(() -> new ResponseStatusException( //404 NOT FOUND
                        HttpStatus.NOT_FOUND,
                        "Tienda no encontrada"
                ));

            // Verificamos si los datos de asignación han cambiado
            boolean cambioTienda = (asignacionActual == null) || !asignacionActual.getTienda().getTiendaId().equals(request.getTiendaId());
            boolean cambioLinea = false;

            if (rol.equals("jefe_de_linea") && asignacionActual != null) {
                Long lineaActualId = (asignacionActual.getLineaProductoId() != null) ? asignacionActual.getLineaProductoId() : null;
                cambioLinea = !request.getLineaId().equals(lineaActualId);
            }

            // Si hubo algún cambio en tienda o línea, cerramos la anterior y creamos una nueva
            if (cambioTienda || cambioLinea) {
                
                // A) Cerrar la asignación anterior del trabajador (Trazabilidad)
                if (asignacionActual != null) {
                    // 1. Inactivamos su periodo en el puesto anterior
                    asignacionActual.setActivo(false);
                    asignacionActual.setFechaFin(LocalDate.now());
                    asignacionRepository.save(asignacionActual);

                    // 2. Creamos la vacante SOLAMENTE si es un rol que requiere un puesto específico (Jefe de Línea)
                    if (rol.equals("jefe_de_linea")) {
                        Asignacion vacanteDejada = new Asignacion();
                        vacanteDejada.setTienda(asignacionActual.getTienda());
                        vacanteDejada.setLineaProductoId(asignacionActual.getLineaProductoId());
                        vacanteDejada.setNombreLinea(asignacionActual.getNombreLinea());
                        vacanteDejada.setTrabajador(null); 
                        vacanteDejada.setFechaInicio(null);
                        vacanteDejada.setFechaFin(null);
                        vacanteDejada.setActivo(true); 
                        
                        asignacionRepository.save(vacanteDejada);
                    }
                }

                // B) Lógica de ocupación de nuevo puesto
                if (rol.equals("jefe_de_linea")) {
                    // LineaProducto linea = lineaRepository.findById(request.getLineaId())
                    //     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada"));
                    LineaProductoDTO linea = gestionComercialClient.buscarLineaProducto(request.getLineaId());

                    // Buscamos la configuración de esa línea en esa nueva tienda
                    Optional<Asignacion> asignacionPreviaOpt = asignacionRepository
                        .findByTiendaAndLineaProductoIdAndActivoTrue(nuevaTienda, linea.getLineaProductoId());

                    if (asignacionPreviaOpt.isPresent()) {
                        Asignacion previa = asignacionPreviaOpt.get();

                        if (previa.getTrabajador() == null) {
                            // CASO 1: Puesto Vacante -> Lo ocupamos
                            previa.setTrabajador(trabajador);
                            previa.setFechaInicio(LocalDate.now());
                            asignacionRepository.save(previa);
                        } else if (!previa.getTrabajador().getTrabajadorId().equals(trabajador.getTrabajadorId())) {
                            // CASO 2: Puesto Ocupado por OTRO -> Inactivamos al anterior y creamos nuevo
                            previa.setActivo(false);
                            previa.setFechaFin(LocalDate.now());
                            asignacionRepository.save(previa);

                            Asignacion nuevaAsignacion = new Asignacion();
                            nuevaAsignacion.setTrabajador(trabajador);
                            nuevaAsignacion.setTienda(nuevaTienda);
                            nuevaAsignacion.setLineaProductoId(linea.getLineaProductoId());
                            nuevaAsignacion.setNombreLinea(linea.getNombre());
                            nuevaAsignacion.setFechaInicio(LocalDate.now());
                            nuevaAsignacion.setActivo(true);
                            asignacionRepository.save(nuevaAsignacion);
                        }
                    } else {
                        // CASO 3: Línea no habilitada en la tienda
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                            "Error: La tienda " + nuevaTienda.getNombre() + " no tiene habilitada la línea " + linea.getNombre());
                    }
                } else {
                    // Para roles generales (Almacenero, Admin Tienda)
                    Asignacion nuevaAsignacion = new Asignacion();
                    nuevaAsignacion.setTrabajador(trabajador);
                    nuevaAsignacion.setTienda(nuevaTienda);
                    nuevaAsignacion.setFechaInicio(LocalDate.now());
                    nuevaAsignacion.setActivo(true);
                    asignacionRepository.save(nuevaAsignacion);
                }
            }
            response.setNombreTienda(nuevaTienda.getNombre());
        }
        // 6. Construir respuesta
        response.setTrabajadorId(trabajador.getTrabajadorId());
        response.setNombre(trabajador.getNombre());
        response.setDni(trabajador.getDni());
        
        if (rol.equals("jefe_de_linea")) {
            LineaProductoDTO linea = gestionComercialClient.buscarLineaProducto(request.getLineaId());
            System.out.println(linea.getNombre());
            response.setNombreLinea(linea.getNombre());
        }

        return response;
    }

    @Transactional
    public void desvincularTrabajador(Long trabajadorId) {
        // 1. Buscamos al trabajador
        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajador no encontrado"));

        // 2. Obtenemos el rol del trabajador para saber cómo proceder
        Map<String,String> respuesta = seguridadClient.obtenerRolUsuario(trabajador.getUsuarioId());
        String rol = respuesta.get("rol").toLowerCase();

        // 3. Buscamos su asignación activa actual
        Asignacion asignacionActual = trabajador.getAsignaciones().stream()
                .filter(Asignacion::isActivo)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El trabajador no posee una tienda activa asignada"
                 ));

        if (asignacionActual != null) {
            // 4. Cerramos la asignación actual (aplica para todos los roles)
            asignacionActual.setActivo(false);
            asignacionActual.setFechaFin(LocalDate.now()); 
            asignacionRepository.save(asignacionActual);

            // 5. Creamos la vacante SOLAMENTE si es un Jefe de Línea
            if (rol.equals("jefe_de_linea")) {
                Asignacion vacanteDejada = new Asignacion();
                vacanteDejada.setTienda(asignacionActual.getTienda()); 
                vacanteDejada.setLineaProductoId(asignacionActual.getLineaProductoId()); 
                vacanteDejada.setTrabajador(null);
                vacanteDejada.setFechaInicio(null); 
                vacanteDejada.setFechaFin(null); 
                vacanteDejada.setActivo(true); 
                
                asignacionRepository.save(vacanteDejada);
            }
        }

        // 6. Desactivamos la cuenta a nivel de autenticación/login
        seguridadClient.desactivarCuentaUsuario(trabajador.getUsuarioId());
    }

    public List<TiendaResponse> listarTiendasConLineas(){
        List<Tienda> tiendas = tiendaRepository.listarTiendasConLineas();

        return tiendas.stream()
            .map(tienda -> new TiendaResponse(
                tienda.getTiendaId(),
                tienda.getNombre(),
                tienda.getAsignaciones().stream()
                    // 1. Filtramos solo las asignaciones que tienen línea y están activas
                    .filter(asig -> asig.isActivo() && asig.getLineaProductoId() != null)
                    // 2. Mapeamos la Línea de Producto al Response
                    .map(asig -> {
                        return new LineaProductoResponse(
                            asig.getLineaProductoId(),
                            asig.getNombreLinea()
                        );
                    })
                    // 3. Eliminamos duplicados si una línea tiene varios jefes (opcional pero recomendado)
                    .distinct() 
                    .toList()
            ))
            .toList();
    }

    @Override
    public TrabajadorDTO buscarTrabajadorPorUsuarioId(Long usuarioId) {
        // 1. Buscamos el trabajador y usamos orElse(null) para evitar excepciones
        Trabajador trabajador = trabajadorRepository.findByUsuarioId(usuarioId).orElse(null);

        // 2. Si no se encuentra, retornamos el DTO nulo como pediste
        if (trabajador == null) {
            return null; 
        }

        // 3. Mapeamos la entidad Trabajador al TrabajadorDTO
        TrabajadorDTO trabajadorDTO = new TrabajadorDTO();

        trabajadorDTO.setTrabajadorId(trabajador.getTrabajadorId()); 
        trabajadorDTO.setNombre(trabajador.getNombre());

        // 4. Mapeamos la lista de entidades Asignacion a AsignacionDTO si existe
        if (trabajador.getAsignaciones() != null) {
            List<AsignacionDTO> asignacionesDTO = trabajador.getAsignaciones().stream()
                .map(asignacion -> {
                    AsignacionDTO asigDTO = new AsignacionDTO();
                    asigDTO.setLineaProductoId(asignacion.getLineaProductoId());
                    asigDTO.setNombreLinea(asignacion.getNombreLinea());
                    asigDTO.setActivo(asignacion.isActivo()); 
                    asigDTO.setTiendaId(asignacion.getTienda().getTiendaId());
                    
                    return asigDTO;
                })
                .collect(Collectors.toList());

            trabajadorDTO.setAsignaciones(asignacionesDTO);
        }

        // 5. Retornamos el DTO ya armado
        return trabajadorDTO;
    }

    @Override
    public TrabajadorDTO buscarTrabajadorPorTrabajadorId(Long trabajadorId) {
        // 1. Buscamos el trabajador y usamos orElse(null) para evitar excepciones
        Trabajador trabajador = trabajadorRepository.findByTrabajadorId(trabajadorId).orElse(null);

        // 2. Si no se encuentra, retornamos el DTO nulo como pediste
        if (trabajador == null) {
            return null; 
        }

        // 3. Mapeamos la entidad Trabajador al TrabajadorDTO
        TrabajadorDTO trabajadorDTO = new TrabajadorDTO();

        trabajadorDTO.setTrabajadorId(trabajador.getTrabajadorId()); 
        trabajadorDTO.setNombre(trabajador.getNombre());

        // 4. Mapeamos la lista de entidades Asignacion a AsignacionDTO si existe
        if (trabajador.getAsignaciones() != null) {
            List<AsignacionDTO> asignacionesDTO = trabajador.getAsignaciones().stream()
                .map(asignacion -> {
                    AsignacionDTO asigDTO = new AsignacionDTO();
                    asigDTO.setLineaProductoId(asignacion.getLineaProductoId());
                    asigDTO.setNombreLinea(asignacion.getNombreLinea());
                    asigDTO.setActivo(asignacion.isActivo()); 
                    asigDTO.setTiendaId(asignacion.getTienda().getTiendaId());
                    
                    return asigDTO;
                })
                .collect(Collectors.toList());

            trabajadorDTO.setAsignaciones(asignacionesDTO);
        }

        // 5. Retornamos el DTO ya armado
        return trabajadorDTO;
    }
}
