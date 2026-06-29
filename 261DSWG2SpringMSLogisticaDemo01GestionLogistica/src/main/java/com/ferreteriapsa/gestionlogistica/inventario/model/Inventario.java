package com.ferreteriapsa.gestionlogistica.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventarios")
public class Inventario {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventario_id")
    private Long inventarioId;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer stock;

    @Min(0)
    @Column(name = "stock_min")
    private Integer stockMin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rotacion", nullable = false)
    private Rotacion rotacion;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_almacen_id", nullable = false)
    private ZonaAlmacen zonaAlmacen;

    public Inventario() {}

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
    }

    public Rotacion getRotacion() {
        return rotacion;
    }

    public void setRotacion(Rotacion rotacion) {
        this.rotacion = rotacion;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public ZonaAlmacen getZonaAlmacen() {
        return zonaAlmacen;
    }

    public void setZonaAlmacen(ZonaAlmacen zonaAlmacen) {
        this.zonaAlmacen = zonaAlmacen;
    }
}


