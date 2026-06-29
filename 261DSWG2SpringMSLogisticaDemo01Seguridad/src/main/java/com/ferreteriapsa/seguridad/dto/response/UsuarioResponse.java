package com.ferreteriapsa.seguridad.dto.response;

public class UsuarioResponse {
    private Long usuarioId;
    private String rolNombre;
    private String username;
    private boolean isActivo;

    public UsuarioResponse(){ }

    public UsuarioResponse(Long usuarioId, String rolNombre, String username, boolean isActivo) {
        this.usuarioId = usuarioId;
        this.rolNombre = rolNombre;
        this.username = username;
        this.isActivo = isActivo;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isActivo() { return isActivo; }
    public void setActivo(boolean activo) { this.isActivo = activo; }
}
