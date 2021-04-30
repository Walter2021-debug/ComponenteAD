/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

/**
 * En esta clase se declaran todas las propiedades, constructores, 
 * métodos Getters y Setters y toString del pojo ExcepcionAD
 * @author Walter
 * @version 1.0
 * @since 25/04/2021
 */
public class ExcepcionAD extends Exception {
    private String mensajeUsuario;
    private String mensajeAdministrador;
    private Integer codigoError;
    private String sentenciaSQL;
    
    /**
    * Método constructor de vacío de la clase ExcepcionAD
    */
    public ExcepcionAD() {
    }
    
    /**
    * Método constructor sobrecargado de la clase ExcepcionAD
    * @param mensajeUsuario de la clase String
    * @param mensajeAdministrador de la clase String
    * @param codigoError de la clase Integer
    * @param sentenciaSQL de la clase String
    */
    public ExcepcionAD(String mensajeUsuario, String mensajeAdministrador, Integer codigoError, String sentenciaSQL) {
        this.mensajeUsuario = mensajeUsuario;
        this.mensajeAdministrador = mensajeAdministrador;
        this.codigoError = codigoError;
        this.sentenciaSQL = sentenciaSQL;
    }
    /**
    * Método Getter de mensajeUsuario
     * @return mensajeUsuario
    */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }
    /**
    * Método Setter de mensajeUsuario
     * @param mensajeUsuario de la clase String
    */
    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }
    /**
    * Método Getter de mensajeAdministrador
     * @return mensajeAdministrador
    */
    public String getMensajeAdministrador() {
        return mensajeAdministrador;
    }
    /**
    * Método Setter de mensajeAdministrador
     * @param mensajeAdministrador de la clase String
    */
    public void setMensajeAdministrador(String mensajeAdministrador) {
        this.mensajeAdministrador = mensajeAdministrador;
    }
    /**
    * Método Getter de codigoError
     * @return codigoError
    */
    public Integer getCodigoError() {
        return codigoError;
    }
    /**
    * Método Setter de codigoError
     * @param codigoError de la clase Integer
    */
    public void setCodigoError(Integer codigoError) {
        this.codigoError = codigoError;
    }
    /**
    * Método Getter de sentenciaSQL
     * @return sentenciaSQL
    */
    public String getSentenciaSQL() {
        return sentenciaSQL;
    }
    /**
    * Método Setter de sentenciaSQL
     * @param sentenciaSQL de la clase String
    */
    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }
    /**
    * Método de la clase String
     * @return mensajeUsuario, mensajeAdministrador, codigoError, sentenciaSQL
    */
    @Override
    public String toString() {
        return "ExcepcionZapateria{" + "mensajeUsuario=" + mensajeUsuario + ", mensajeAdministrador=" + mensajeAdministrador + ", codigoError=" + codigoError + ", sentenciaSQL=" + sentenciaSQL + '}';
    }
}
