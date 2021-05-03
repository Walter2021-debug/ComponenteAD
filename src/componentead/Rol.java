/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

import javax.swing.JButton;

/**
 * En esta clase se declaran todas las propiedades, constructores, 
 * métodos Getters y Setters y toString del pojo Rol
 * @version 1.0
 * @since 28/04/2021
 * @author Walter
 */
public class Rol {
    private Integer rolId;
    private String nombre;
    private String descripcion;
    
    /**
    * Método constructor de vacío de la clase Rol
    */
    public Rol() {
    }
    
    /**
    * Método constructor sobrecargado de la clase Rol
    * @param nombre de la clase String
    * @param descripcion de la clase String
    */
    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    /**
    * Método Getter de rolId
     * @return rolId
    */
    public Integer getRolId() {
        return rolId;
    }
    
    /**
    * Método Setter de rolId
     * @param rolId de la clase Integer
    */
    public void setRolId(Integer rolId) {
        this.rolId = rolId;
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
    * Método Getter de descripcion
     * @return descripcion
    */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
    * Método Setter de descripcion
     * @param descripcion de la clase String
    */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
    * Método de la clase String
     * @return rolId, nombre, descripcion
    */
    @Override
    public String toString() {
        return "Rol{" + "rolId=" + rolId + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }
    
    /* Método de la clase String[]
     * @return s
    */
    public String[] toArrayString() {
        JButton botonEditar = new JButton();
        JButton botonEliminar = new JButton();
        String color1 = "#81d4fa";
        String color2 = "#ff5f5f";
        botonEditar.setText("Editar");
        botonEditar.setVisible(true);
        botonEditar.setBackground(java.awt.Color.decode(color1));
        botonEliminar.setText("Eliminar");
        botonEliminar.setVisible(true);
        botonEliminar.setBackground(java.awt.Color.decode(color2));
        String[] s = new String[3];
        int cont = 5;
        for (int i = 0; i < cont; i++) {
            rolId += i;
        }
        s[0] = Integer.toString(rolId);
        s[1] = nombre;
        s[2] = descripcion;
        s[3] = botonEditar.getText();
        s[4] = botonEliminar.getText();
        return s;
    }
    
}
