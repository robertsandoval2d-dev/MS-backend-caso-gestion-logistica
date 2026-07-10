package com.ferreteriapsa.gestioncomercial.compra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import com.ferreteriapsa.gestioncomercial.compra.repository.OrdenCompraRepository;
import com.ferreteriapsa.gestioncomercial.catalogo.model.*;
import com.ferreteriapsa.gestioncomercial.catalogo.repository.ProductoRepository;
import com.ferreteriapsa.gestioncomercial.catalogo.repository.ProveedorRepository;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.GestionLogisticaClient;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.AsignacionDTO;
import com.ferreteriapsa.gestioncomercial.client.gestionlogistica.dto.response.TrabajadorDTO;
import com.ferreteriapsa.gestioncomercial.compra.dto.request.*;
import com.ferreteriapsa.gestioncomercial.compra.dto.response.*;
import com.ferreteriapsa.gestioncomercial.compra.model.*;
import com.ferreteriapsa.gestioncomercial.planificacion.repository.DetalleCronogramaRepository;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

import org.springframework.http.HttpStatus;

@Service
public class CompraService implements CompraInterface {
    private final OrdenCompraRepository ordenCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final DetalleCronogramaRepository detalleCronogramaRepository;
    private final GestionLogisticaClient gestionLogisticaClient;

    public CompraService(OrdenCompraRepository ordenCompraRepository,
                            ProveedorRepository proveedorRepository,
                            ProductoRepository productoRepository,
                            DetalleCronogramaRepository detalleCronogramaRepository,
                            GestionLogisticaClient gestionLogisticaClient) {
            this.ordenCompraRepository = ordenCompraRepository;
            this.proveedorRepository = proveedorRepository;
            this.productoRepository = productoRepository;
            this.detalleCronogramaRepository = detalleCronogramaRepository;
            this.gestionLogisticaClient = gestionLogisticaClient;
    }

    @Transactional
    public OrdenCompraResponse generarOrdenCompra(OrdenCompraRequest request, Long trabajadorId) {
        // Validamos el proveedor porque su ID viene del cuerpo del request
        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));

        TrabajadorDTO trabajador = gestionLogisticaClient.buscarTrabajador(trabajadorId);

        Long tiendaId = trabajador.getAsignaciones().stream()
        .filter(AsignacionDTO::isActivo)
        .map(AsignacionDTO::getTiendaId)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El trabajador no tiene tienda activa")
        );

        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setProveedor(proveedor);
        
        // Para el administrador usamos getReferenceById ya que el ID es confiable (viene del token)
        ordenCompra.setAdministradorId(trabajador.getTrabajadorId());
        ordenCompra.setTiendaId(tiendaId);
        
        ordenCompra.setPlazoFechaMaximo(request.getPlazoFechaMaximo());
        ordenCompra.setMontoTotalCalculado(request.getMontoTotalCalculado());
        ordenCompra.setEstado("PENDIENTE");
        ordenCompra.setFechaCreacion(LocalDateTime.now());

        List<DetalleOrdenCompra> detalles = request.getDetalles().stream().map(detalleDto -> {
            // Validamos que el producto exista
            Producto producto = productoRepository.findById(detalleDto.getProductoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            DetalleOrdenCompra detalle = new DetalleOrdenCompra();
            detalle.setNombreLinea(producto.getLineaProducto().getNombre());
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDto.getCantidad());
            detalle.setPrecioUnidad(detalleDto.getPrecioUnidad());
            detalle.setOrdenCompra(ordenCompra);

            // Actualizamos los detalles del cronograma a 'PROGRAMADO'
            detalleCronogramaRepository.actualizarEstadoAProgramado(
                    producto.getProductoId(), 
                    proveedor.getProveedorId(),
                    tiendaId
            );

            return detalle;
        }).collect(Collectors.toList());

        ordenCompra.setDetalles(detalles);

        OrdenCompra guardada = ordenCompraRepository.save(ordenCompra);

        OrdenCompraResponse res = new OrdenCompraResponse();
        res.setOrdenCompraId(guardada.getOrdenCompraId());
        res.setEstado(guardada.getEstado());
        res.setFechaCreacion(guardada.getFechaCreacion());
        
        return res;
    }

    @Transactional(readOnly = true)
    public List<OrdenesCompraResponse> listarOrdenesPorTiendaYProveedor(Long trabajadorId, Long ordenId) {

        TrabajadorDTO trabajador = gestionLogisticaClient.buscarTrabajador(trabajadorId);

        Long tiendaId = trabajador.getAsignaciones().stream()
        .filter(AsignacionDTO::isActivo)
        .map(AsignacionDTO::getTiendaId)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El trabajador no tiene tienda activa")
        );

        List<OrdenCompra> ordenesCompra;

        if (ordenId != null) {

            ordenesCompra =
                    ordenCompraRepository
                            .listarOrdenesCompraPorTiendaYOrdenCompra(
                                    tiendaId,
                                    ordenId
                            );

        } else {

            ordenesCompra =
                    ordenCompraRepository
                            .listarOrdenesCompraPorTienda(tiendaId);

        }
        ordenesCompra.sort(
                Comparator.comparing(
                        oc -> oc.getProveedor().getNombre()
                )
        );

        List<OrdenesCompraResponse> ordenesCompraResponse = ordenesCompra.stream()
                .map(oc -> {

                    OrdenesCompraResponse compraResponse =
                            new OrdenesCompraResponse();

                    compraResponse.setOrdenCompraId(
                            oc.getOrdenCompraId()
                    );

                    compraResponse.setNombreProveedor(
                            oc.getProveedor().getNombre()
                    );

                    compraResponse.setFechaEntrega(
                            oc.getFechaEntrega()
                    );

                    compraResponse.setPlazoFechaMaximo(
                            oc.getPlazoFechaMaximo()
                    );

                    List<ProductoDTO> productos =
                            oc.getDetalles().stream()
                                    .map(detalle -> {

                                        ProductoDTO productoDTO =
                                                new ProductoDTO();

                                        productoDTO.setProductoId(
                                                detalle.getProducto().getProductoId()
                                        );

                                        productoDTO.setNombreProducto(
                                                detalle.getProducto().getNombre()
                                        );

                                        productoDTO.setNombreLinea(
                                                detalle.getNombreLinea()
                                        );

                                        productoDTO.setCantidad(
                                                detalle.getCantidad()
                                        );

                                        productoDTO.setPrecioUnidad(
                                                detalle.getPrecioUnidad()
                                        );

                                        return productoDTO;

                                    }).toList();

                    compraResponse.setProductos(productos);

                    return compraResponse;

                }).toList();

        return ordenesCompraResponse;
    }
    
    @Transactional(readOnly = true)
    public List<OrdenCompraSimpleResponse> listarOrdenesCompraSimple(Long trabajadorId){

        TrabajadorDTO trabajador = gestionLogisticaClient.buscarTrabajador(trabajadorId);

        Long tiendaId = trabajador.getAsignaciones().stream()
        .filter(AsignacionDTO::isActivo)
        .map(AsignacionDTO::getTiendaId)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El trabajador no tiene tienda activa")
        );
        
        LocalDateTime fechaDesde = LocalDateTime.now().minusMonths(1);
        List<OrdenCompra> ordenesCompra = ordenCompraRepository.listarOrdenesCompraPorTiendaHastaMesPasado(tiendaId, fechaDesde);

        List<OrdenCompraSimpleResponse> ordenesCompraSimpleResponse =  ordenesCompra.stream()
                .map(oc -> {
                        OrdenCompraSimpleResponse ordenCompraSimpleResponse =
                        new OrdenCompraSimpleResponse();

                        ordenCompraSimpleResponse.setOrdenCompraId(oc.getOrdenCompraId());
                        ordenCompraSimpleResponse.setNombreProveedor(oc.getProveedor().getNombre());
                        ordenCompraSimpleResponse.setFechaEntrega(oc.getFechaEntrega());
                        ordenCompraSimpleResponse.setFechaPlazoMaximo(oc.getPlazoFechaMaximo());

                        return ordenCompraSimpleResponse;

                }).toList();

        return ordenesCompraSimpleResponse;

    }

    @Transactional(readOnly = true)
    @Override
    public OrdenCompraClientDTO obtenerOrdenCompraPorId(Long ordenCompraId) {
        // 1. Buscamos la orden de compra por su ID
        OrdenCompra ordenCompra = ordenCompraRepository.findById(ordenCompraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden de compra no encontrada"));

        // 2. Mapeamos los datos básicos al DTO principal
        OrdenCompraClientDTO ordenDTO = new OrdenCompraClientDTO();
        ordenDTO.setOrdenCompraId(ordenCompra.getOrdenCompraId());
        ordenDTO.setEstado(ordenCompra.getEstado());
        ordenDTO.setPlazoFechaMaximo(ordenCompra.getPlazoFechaMaximo());
        ordenDTO.setFechaEntrega(ordenCompra.getFechaEntrega());

        // 3. Transformamos la lista de detalles usando Streams
        List<DetalleOrdenCompraClientDTO> detallesDTO = ordenCompra.getDetalles().stream()
                .map(detalle -> {
                    DetalleOrdenCompraClientDTO detalleDTO = new DetalleOrdenCompraClientDTO();
                    detalleDTO.setDetalleOrdenCompraId(detalle.getDetalleOcId());
                    detalleDTO.setCantidad(detalle.getCantidad());
                    detalleDTO.setProductoId(detalle.getProducto().getProductoId());
                    return detalleDTO;
                })
                .collect(Collectors.toList());

        // 4. Asignamos los detalles al DTO principal y retornamos
        ordenDTO.setDetalles(detallesDTO);

        return ordenDTO;
    }

    @Transactional
    @Override
    public void recepcionarOrdenCompra(Long ordenCompraId, String nuevoEstado, LocalDateTime fechaEntrega) {
        OrdenCompra ordenCompra = ordenCompraRepository.findById(ordenCompraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden de compra no encontrada"));

        ordenCompra.setEstado(nuevoEstado);
        ordenCompra.setFechaEntrega(fechaEntrega);
        ordenCompraRepository.save(ordenCompra);
    }
    
}
