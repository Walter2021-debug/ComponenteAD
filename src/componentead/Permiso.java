/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

/**
 * En esta clase se declaran todas las propiedades, constructores, 
 * métodos Getters y Setters y toString del pojo Permiso
 * @version 1.0
 * @since 28/04/2021
 * @author Walter
 */
public class Permiso {
    private Integer permisoId;
    private Usuario usuario;
    private Rol rol;
    
    /**
    * Método constructor de vacío de la clase Permiso
    */
    public Permiso() {
    }
    
     /**
    * Método constructor sobrecargado de la clase Permiso
    * @param usuario de la clase Usuario
    * @param rol de la clase Rol
    */
    public Permiso(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }
    
     /**
    * Método Getter de permisoId
     * @return permisoId
    */
    public Integer getPermisoId() {
        return permisoId;
    }
    
    /**
    * Método Setter de permisoId
     * @param permisoId de la clase Integer
    */
    public void setPermisoId(Integer permisoId) {
        this.permisoId = permisoId;
    }
    
     /**
    * Método Getter de usuario
     * @return usuario
    */
    public Usuario getUsuario() {
        return usuario;
    }
    
    /**
    * Método Setter de usuario
     * @param usuario de la clase Usuario
    */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
    * Método Getter de rol
     * @return rol
    */
    public Rol getRol() {
        return rol;
    }
    
    /**
    * Método Setter de rol
     * @param rol de la clase Rol
    */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    /**
    * Método de la clase String
     * @return permisoId, usuario, rol
    */
    @Override
    public String toString() {
        return "Permiso{" + "permisoId=" + permisoId + ", usuario=" + usuario + ", rol=" + rol + '}';
    }
    
    /* Método de la clase String[]
     * @return s
    */
    public String[] toArrayString() {
        String[] s = new String[3];
        int cont = 5;
        for (int i = 0; i < cont; i++) {
            permisoId = i;
        }
        s[0] = Integer.toString(permisoId);
        s[1] = String.valueOf(usuario);
        s[2] = String.valueOf(rol);
        return s;
    }
}
