package com.ferreteriapsa.gestionlogistica.trabajador.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "trabajadores")
public class Trabajador {
    // atributos    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trabajador_id")
    private Long trabajadorId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private Long usuarioId;

    @OneToMany(mappedBy = "trabajador")
    private List<Asignacion> asignaciones;


    // constructores
    public Trabajador() {}

    public Trabajador(String nombre, String dni, Long usuarioId) {
        this.nombre = nombre;
        this.dni = dni;
        this.usuarioId = usuarioId;
    }

    // getters y setters

    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Asignacion> getAsignaciones(){
        return asignaciones;
    }
    public void setAsignaciones(List<Asignacion> asignaciones){
        this.asignaciones = asignaciones;
    }
}
