package com.ferreteriapsa.gestionlogistica.client.seguridad.dto.response;

public class UsuarioResponse {
    private Long usuarioId;
    private String rolNombre;
    private String username;
    private boolean isActivo;

    public UsuarioResponse(){ }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isActivo() { return isActivo; }
    public void setActivo(boolean activo) { this.isActivo = activo; }
}
