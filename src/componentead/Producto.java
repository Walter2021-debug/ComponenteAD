/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

/**
 * En esta clase se declaran todas las propiedades, constructores, 
 * métodos Getters y Setters y toString del pojo Producto
 * @version 1.0
 * @since 28/04/2021
 * @author Walter
 */
public class Producto {
    private Integer productoId;
    private String nombre;
    private String envase;
    private String fechaCaducidad;
    private String imagen;
    private String qr;
    private Colaborador colaborador;
    private Categoria categoria;
    
    /**
    * Método constructor de vacío de la clase Producto
    */
    public Producto() {
    }
    
     /**
    * Método constructor sobrecargado de la clase Producto
    * @param nombre de la clase String
    * @param envase de la clase String
    * @param fechaCaducidad de la clase String
    * @param imagen de la clase String
    * @param qr de la clase String
    * @param colaborador de la clase Colaborador
    * @param categoria de la clase Categoria
    */
    public Producto(String nombre, String envase, String fechaCaducidad, String imagen, String qr, Colaborador colaborador, Categoria categoria) {
        this.nombre = nombre;
        this.envase = envase;
        this.fechaCaducidad = fechaCaducidad;
        this.imagen = imagen;
        this.qr = qr;
        this.colaborador = colaborador;
        this.categoria = categoria;
    }
    
    /**
    * Método Getter de productoId
     * @return colaboradorId
    */
    public Integer getProductoId() {
        return productoId;
    }
    
    /**
    * Método Setter de productoId
     * @param productoId de la clase Integer
    */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
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
    * Método Getter de envase
     * @return envase
    */
    public String getEnvase() {
        return envase;
    }
    
    /**
    * Método Setter de envase
     * @param envase de la clase String
    */
    public void setEnvase(String envase) {
        this.envase = envase;
    }
    
    /**
    * Método Getter de fechaCaducidad
     * @return fechaCaducidad
    */
    public String getFechaCaducidad() {
        return fechaCaducidad;
    }
    
    /**
    * Método Setter de fechaCaducidad
     * @param fechaCaducidad de la clase String
    */
    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
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
    * Método Getter de qr
     * @return qr
    */
    public String getQr() {
        return qr;
    }
    
     /**
    * Método Setter de qr
     * @param qr de la clase String
    */
    public void setQr(String qr) {
        this.qr = qr;
    }
    
    /**
    * Método Getter de colaborador
     * @return colaborador
    */
    public Colaborador getColaborador() {
        return colaborador;
    }
    
     /**
    * Método Setter de colaborador
     * @param colaborador de la clase Colaborador
    */
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    
    /**
    * Método Getter de categoria
     * @return categoria
    */
    public Categoria getCategoria() {
        return categoria;
    }
    
     /**
    * Método Setter de categoria
     * @param categoria de la clase Categoria
    */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    /**
    * Método de la clase String
     * @return productoId, nombre, envase, fechaCaducidad, imagen, qr, colaborador, categoria
    */
    @Override
    public String toString() {
        return "Producto{" + "productoId=" + productoId + ", nombre=" + nombre + ", envase=" + envase 
                + ", fechaCaducidad=" + fechaCaducidad + ", imagen=" + imagen + ", qr=" + qr 
                + ", colaborador=" + colaborador + ", categoria=" + categoria + '}';
    }

    
}
