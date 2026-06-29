package com.ferreteriapsa.seguridad.dto.request;

public class UsuarioRegistroRequest {
    private String username;
    private String password;
    private String userRol;

    public UsuarioRegistroRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserRol() { return userRol; }
    public void setUserRol(String userRol) { this.userRol = userRol; }
}
