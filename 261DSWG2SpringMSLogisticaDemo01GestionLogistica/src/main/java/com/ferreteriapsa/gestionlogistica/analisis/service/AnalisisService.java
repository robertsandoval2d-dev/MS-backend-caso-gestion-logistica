package com.ferreteriapsa.gestionlogistica.analisis.service;

import com.ferreteriapsa.gestionlogistica.inventario.model.Inventario;
import com.ferreteriapsa.gestionlogistica.inventario.model.ZonaAlmacen;
import com.ferreteriapsa.gestionlogistica.inventario.repository.InventarioRepository;
import com.ferreteriapsa.gestionlogistica.inventario.repository.ZonaAlmacenRepository;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Asignacion;
import com.ferreteriapsa.gestionlogistica.trabajador.model.Trabajador;
import com.ferreteriapsa.gestionlogistica.trabajador.repository.TrabajadorRepository;
import com.ferreteriapsa.gestionlogistica.analisis.dto.response.*;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.GestionComercialClient;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.request.ConsultaVentasRequest;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response.ProductoClientDTO;
import com.ferreteriapsa.gestionlogistica.client.gestioncomercial.dto.response.ProductoInfoVentasDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalisisService {

    private final ZonaAlmacenRepository zonaAlmacenRepository;
    private final InventarioRepository inventarioRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final GestionComercialClient gestionComercialClient;

    public AnalisisService(ZonaAlmacenRepository zonaAlmacenRepository, InventarioRepository inventarioRepository, 
        TrabajadorRepository trabajadorRepository, GestionComercialClient gestionComercialClient) {
        this.zonaAlmacenRepository = zonaAlmacenRepository;
        this.inventarioRepository = inventarioRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.gestionComercialClient = gestionComercialClient;
    }

    @Transactional(readOnly = true)
    public List<SaturacionZonaResponse> obtenerSaturacionZonas(Long trabajadorId) {
        List<ZonaAlmacen> zonas = zonaAlmacenRepository.findZonasByTrabajadorId(trabajadorId);

        return zonas.stream()
                .map(zona -> {
                    int max = zona.getCapacidadMaxima();
                    int actual = zona.getCapacidadActual();
                    
                    // La lógica matemática vive aquí
                    double porcentaje = (max == 0) ? 0.0 : 
                        Math.round(((double) actual / max) * 100.0 * 100.0) / 100.0;

                    return new SaturacionZonaResponse(
                            zona.getCategoria(),
                            max,
                            actual,
                            porcentaje 
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ValorInmovilizadoResponse> obtenerCostoInmovilizadoPorTrabajador(Long trabajadorId) {
        
        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
                
        List<Asignacion> asignacionesActivas = trabajador.getAsignaciones().stream()
                .filter(Asignacion::isActivo)
                .collect(Collectors.toList());

        List<Inventario> inventarios = inventarioRepository.findInventarioBasePorTrabajador(trabajadorId);

        if (inventarios.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> productoIds = inventarios.stream()
                .map(Inventario::getProductoId)
                .distinct()
                .collect(Collectors.toList());

        List<ProductoClientDTO> productosCatalog = gestionComercialClient.obtenerProductosPorIds(productoIds);

        Map<Long, ProductoClientDTO> mapaProductos = productosCatalog.stream()
                .collect(Collectors.toMap(ProductoClientDTO::getProductoId, p -> p));

        return inventarios.stream()
            .filter(inv -> {
                ProductoClientDTO prod = mapaProductos.get(inv.getProductoId());
                if (prod == null) return false;

                return asignacionesActivas.stream().anyMatch(a -> 
                    a.getTienda().getAlmacen().getAlmacenId().equals(inv.getZonaAlmacen().getAlmacen().getAlmacenId()) 
                    && a.getLineaProductoId().equals(prod.getLineaProductoId())
                );
            })
            .map(inv -> {
                ProductoClientDTO prod = mapaProductos.get(inv.getProductoId());
                
                BigDecimal precioVenta = prod.getPrecioVenta();
                BigDecimal stockActual = new BigDecimal(inv.getStock());
                BigDecimal valorMonetario = precioVenta.multiply(stockActual);

                return new ValorInmovilizadoResponse(
                        prod.getNombre(),
                        valorMonetario,
                        inv.getRotacion().name(),
                        prod.getCategoria()
                );
            })
            .sorted((dto1, dto2) -> dto2.getValorMonetario().compareTo(dto1.getValorMonetario()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentasStockResponse> obtenerRelacionVentasStock(Long trabajadorId) {

        LocalDateTime fechaLimite = LocalDateTime.now().minusMonths(3);

        List<InventarioLocalDTO> inventariosLocales = inventarioRepository.findInventarioLocalPorTrabajador(trabajadorId);
        
        if (inventariosLocales.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> productoIds = inventariosLocales.stream()
                .map(dto -> dto.getInventario().getProductoId()).distinct().toList();
        List<Long> tiendaIds = inventariosLocales.stream()
                .map(InventarioLocalDTO::getTiendaId).distinct().toList();

        ConsultaVentasRequest request = new ConsultaVentasRequest();
        request.setFechaLimite(fechaLimite);
        request.setProductoIds(productoIds);
        request.setTiendaIds(tiendaIds);
        List<ProductoInfoVentasDTO> datosComerciales = gestionComercialClient.obtenerVentasParaLogistica(request);

        List<VentasStockResponse> respuestas = new ArrayList<>();
        for (InventarioLocalDTO local : inventariosLocales) {
            Long prodId = local.getInventario().getProductoId();
            Long tiendaId = local.getTiendaId();
            Long lineaAsignada = local.getLineaProductoIdAsignada();

            List<ProductoInfoVentasDTO> infoProdList = datosComerciales.stream()
                    .filter(d -> d.getProductoId().equals(prodId))
                    .toList();

            if (infoProdList.isEmpty()) continue;

            Long lineaProductoReal = infoProdList.get(0).getLineaProductoId();
            if (!lineaAsignada.equals(lineaProductoReal)) {
                continue; 
            }

            String nombreProducto = infoProdList.get(0).getNombre();

            Long ventas = infoProdList.stream()
                    .filter(d -> tiendaId.equals(d.getTiendaId()))
                    .map(ProductoInfoVentasDTO::getCantidadVentas)
                    .findFirst()
                    .orElse(0L); 

            respuestas.add(new VentasStockResponse(
                    nombreProducto, 
                    ventas, 
                    local.getInventario().getStock() 
            ));
        }

        return respuestas;
    }

}
