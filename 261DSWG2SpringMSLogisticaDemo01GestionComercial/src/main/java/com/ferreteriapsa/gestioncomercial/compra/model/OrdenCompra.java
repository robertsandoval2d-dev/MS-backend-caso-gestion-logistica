package com.ferreteriapsa.gestioncomercial.compra.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ferreteriapsa.gestioncomercial.catalogo.model.Proveedor;

import jakarta.persistence.*;


@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_compra_id")
    private Long ordenCompraId;

    @Column(name = "plazo_fecha_maximo", nullable = false)
    private LocalDateTime plazoFechaMaximo;

    @Column(name = "monto_total_calculado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotalCalculado;
    
    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_entrega", nullable = true) 
    private LocalDateTime fechaEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(name = "administrador_id", nullable = false)
    private Long administradorId;

    @Column(name = "tienda_id")
    private Long tiendaId;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrdenCompra> detalles;

    // Constructor vacío
    public OrdenCompra() {}

    // Getters y Setters
    public Long getOrdenCompraId() { return ordenCompraId; }
    public void setOrdenCompraId(Long ordenCompraId) { this.ordenCompraId = ordenCompraId; }

    public LocalDateTime getPlazoFechaMaximo() { return plazoFechaMaximo; }
    public void setPlazoFechaMaximo(LocalDateTime plazoFechaMaximo) { this.plazoFechaMaximo = plazoFechaMaximo; }

    public BigDecimal getMontoTotalCalculado() { return montoTotalCalculado; }
    public void setMontoTotalCalculado(BigDecimal montoTotalCalculado) { this.montoTotalCalculado = montoTotalCalculado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public List<DetalleOrdenCompra> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrdenCompra> detalles) { this.detalles = detalles; }

    public Long getAdministradorId() { return administradorId; }
    public void setAdministradorId(Long administradorId) { this.administradorId = administradorId; }

    public Long getTiendaId(){ return tiendaId; } 
    public void setTiendaId(Long tiendaId){ this.tiendaId = tiendaId; }
}