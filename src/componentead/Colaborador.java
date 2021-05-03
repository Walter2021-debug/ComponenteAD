/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

import javax.swing.JButton;

/**
 * En esta clase se declaran todas las propiedades, constructores, 
 * métodos Getters y Setters y toString del pojo Colaborador
 * @version 1.0
 * @since 28/04/2021
 * @author Walter
 */
public class Colaborador {
    private Integer colaboradorId;
    private String nombre;
    private String email;
    private String telefono;
    private String foto;
    
    /**
    * Método constructor de vacío de la clase Colaborador
    */
    public Colaborador() {
    }
    
    /**
    * Método constructor sobrecargado de la clase Colaborador
    * @param nombre de la clase String
    * @param email de la clase String
    * @param telefono de la clase String
    * @param foto de la clase String
    */
    public Colaborador(String nombre, String email, String telefono, String foto) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.foto = foto;
    }
    
    /**
    * Método Getter de colaboradorId
     * @return colaboradorId
    */
    public Integer getColaboradorId() {
        return colaboradorId;
    }
    
    /**
    * Método Setter de colaboradorId
     * @param colaboradorId de la clase Integer
    */
    public void setColaboradorId(Integer colaboradorId) {
        this.colaboradorId = colaboradorId;
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
    * Método Getter de telefono
     * @return telefono
    */
    public String getTelefono() {
        return telefono;
    }
    
    /**
    * Método Setter de telefono
     * @param telefono de la clase String
    */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
     /**
    * Método Getter de foto
     * @return foto
    */
    public String getFoto() {
        return foto;
    }
    
    /**
    * Método Setter de foto
     * @param foto de la clase String
    */
    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    /**
    * Método de la clase String
     * @return colaboradorId, nombre, email, telefono, foto
    */
    @Override
    public String toString() {
        return "Colaborador{" + "colaboradorId=" + colaboradorId + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + ", foto=" + foto + '}';
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
        String[] s = new String[5];
        int cont = 5;
        for (int i = 0; i < cont; i++) {
            colaboradorId += i;
        }
        s[0] = Integer.toString(colaboradorId);
        s[1] = nombre;
        s[2] = email;
        s[3] = telefono;
        s[4] = foto;
        s[5] = botonEditar.getText();
        s[6] = botonEliminar.getText();
        return s;
    }
    
}
