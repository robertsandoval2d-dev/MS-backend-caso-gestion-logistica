package com.ferreteriapsa.gestionlogistica.trabajador.dto.response;

import java.util.List;

public class TrabajadorDTO {
    private Long trabajadorId;
    private String nombre;
    private List<AsignacionDTO> asignaciones;

    public TrabajadorDTO(){}

    public Long getTrabajadorId() { return trabajadorId; }
    public void setTrabajadorId(Long trabajadorId) { this.trabajadorId = trabajadorId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<AsignacionDTO> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<AsignacionDTO> asignaciones) { this.asignaciones = asignaciones; }
}
