/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

import javax.swing.JButton;

/**
 * En esta clase se declaran todas las propiedades, constructores, 
 * métodos Getters y Setters y toString del pojo Categoria
 * @version 1.0
 * @since 28/04/2021
 * @author Walter
 */
public class Categoria {
    private Integer categoriaId;
    private String nombre;
    private String descripcion;
    
    /**
    * Método constructor de vacío de la clase Categoria
    */
    public Categoria() {
    }
    
    /**
    * Método constructor sobrecargado de la clase Categoria
    * @param nombre de la clase String
    * @param descripcion de la clase String
    */
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    /**
    * Método Getter de categoriaId
     * @return categoriaId
    */
    public Integer getCategoriaId() {
        return categoriaId;
    }
    
    /**
    * Método Setter de categoriaId
     * @param categoriaId de la clase Integer
    */
    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
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
     * @return categoriaId, nombre, descripcion
    */
    @Override
    public String toString() {
        return "Categoria{" + "categoriaId=" + categoriaId + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
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
        botonEditar.setBackground(java.awt.Color.decode(color1));
        botonEliminar.setText("Eliminar");
        botonEliminar.setBackground(java.awt.Color.decode(color2));
        String[] s = new String[3];
        int cont = 5;
        for (int i = 0; i < cont; i++) {
            categoriaId = i;
        }
        s[0] = Integer.toString(categoriaId);
        s[1] = nombre;
        s[2] = descripcion;
        s[3] = botonEditar.getText();
        s[4] = botonEliminar.getText();
        return s;
    }
    
}
