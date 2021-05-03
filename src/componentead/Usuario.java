/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

/**
 * En esta clase se declaran todas las propiedades, constructores, 
 * métodos Getters y Setters y toString del pojo Usuario
 * @version 1.0
 * @since 28/04/2021
 * @author Walter
 */
public class Usuario {
    private Integer usuarioId;
    private String nombre;
    private String email;
    private String contrasena;
    private String imagen;
    
    /**
    * Método constructor de vacío de la clase Usuario
    */
    public Usuario() {
    }
    
    /**
    * Método constructor sobrecargado de la clase Usuario
    * @param nombre de la clase String
    * @param email de la clase String
    * @param contrasena de la clase String
    * @param imagen de la clase String
    */
    public Usuario(String nombre, String email, String contrasena, String imagen) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.imagen = imagen;
    }
    
    /**
    * Método constructor sobrecargado de la clase Usuario
    * @param nombre de la clase String
    * @param contrasena de la clase String
    */
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }
    
    
    /**
    * Método Getter de usuarioId
     * @return usuarioId
    */
    public Integer getUsuarioId() {
        return usuarioId;
    }
    
    /**
    * Método Setter de usuarioId
     * @param usuarioId de la clase Integer
    */
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    /**
    * Método Getter de nombre
     * @return nombre
    */
    public String getNombre() {
        return nombre;
    }
    
    /**
    * Método Setter de nombre
     * @param nombre de la clase String
    */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
    * Método Getter de email
     * @return email
    */
    public String getEmail() {
        return email;
    }
    
    /**
    * Método Setter de email
     * @param email de la clase String
    */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
    * Método Getter de contrasena
     * @return contrasena
    */
    public String getContrasena() {
        return contrasena;
    }
    
    /**
    * Método Setter de contrasena
     * @param contrasena de la clase String
    */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    /**
    * Método Getter de imagen
     * @return imagen
    */
    public String getImagen() {
        return imagen;
    }
    
    /**
    * Método Setter de imagen
     * @param imagen de la clase String
    */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    /**
    * Método de la clase String
     * @return usuarioId, nombre, email, contrasena, imagen
    */
    @Override
    public String toString() {
        return "Usuario{" + "usuarioId=" + usuarioId + ", nombre=" + nombre + ", email=" + email + ", contrasena=" + contrasena + ", imagen=" + imagen + '}';
    }
    
    /* Método de la clase String[]
     * @return s
    */
    public String[] toArrayString() {
        String[] s = new String[5];
        s[0] = Integer.toString(usuarioId);
        s[1] = nombre;
        s[2] = email;
        s[3] = contrasena;
        s[4] = imagen;
        return s;
    }
    
    
}
