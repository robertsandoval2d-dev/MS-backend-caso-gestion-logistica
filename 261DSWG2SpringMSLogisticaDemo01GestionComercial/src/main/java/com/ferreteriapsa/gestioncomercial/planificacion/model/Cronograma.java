package com.ferreteriapsa.gestioncomercial.planificacion.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cronogramas")
public class Cronograma {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cronograma_id")
    private Long cronogramaId;

    @Column(name="fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "trabajador_id")
    private Long trabajadorId;

    @Column(name = "tienda_id")
    private Long tiendaId;

    @OneToMany(mappedBy = "cronograma")
    private List<DetalleCronograma> detallesCronograma;

    // constructores
    public Cronograma(){}

    //getters and setters
    public Long getCronogramaId() {
        return cronogramaId;
    }

    public void setCronogramaId(Long cronogramaId) {
        this.cronogramaId = cronogramaId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public Long getTiendaId(){
        return tiendaId;
    } 

    public void setTiendaId(Long tiendaId){
        this.tiendaId = tiendaId;
    }

    public List<DetalleCronograma> getDetallesCronograma(){
        return detallesCronograma;
    }

    public void setDetallesCronograma(List<DetalleCronograma> detallesCronograma){
        this.detallesCronograma = detallesCronograma;
    }
}
