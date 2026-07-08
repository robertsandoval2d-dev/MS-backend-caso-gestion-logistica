package com.ferreteriapsa.gestionlogistica.mensajeria.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mensaje_id")
    private Long mensajeId;

    @Column(nullable = false)
    private String titulo;

    private String mensaje;

    @Column(nullable = false)
    private Boolean leido;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "emisor_id", nullable = false)
    private Long emisorId;

    @Column(name = "emisor_username", nullable = false)
    private String emisorUsername;

    @Column(name = "receptor_id", nullable = false)
    private Long receptorId;

    @Column(name = "receptor_username", nullable = false)
    private String receptorUsername;

    public Mensaje(){}

    public Long getMensajeId() { return mensajeId; }
    public void setMensajeId(Long mensajeId) { this.mensajeId = mensajeId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
    
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    
    public Long getEmisorId() { return emisorId; }
    public void setEmisorId(Long emisorId) { this.emisorId = emisorId; }

    public String getEmisorUsername() { return emisorUsername; }
    public void setEmisorUsername(String emisorUsername) { this.emisorUsername = emisorUsername; }
    
    public Long getReceptorId() { return receptorId; }
    public void setReceptorId(Long receptorId) { this.receptorId = receptorId; }

    public String getReceptorUsername() { return receptorUsername; }
    public void setReceptorUsername(String receptorUsername) { this.receptorUsername = receptorUsername; }

}
