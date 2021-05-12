/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentead;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * En esta clase se declaran todas las propiedades, constructores, métodos CRUD
 * de usuarios, roles, permisos, productos, colaboradores, categorias del pojo
 * ComponenteAD
 * @author Walter
 * @version 1.0
 * @since 25/04/2021
 */
public class ComponenteAD {

    Connection conexion;

    /**
     * Método constructor que permite cargar el jdbc de la clase ComponenteAD
     * @throws componentead.ExcepcionAD se lanzará cuando se produzca algún
     * problema al cargar el jdbc
     */
    public ComponenteAD() throws ExcepcionAD {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
            ead.setMensajeAdministrador(ex.getMessage());
            throw ead;
        }
    }

    /**
     * Método que permite conectar con la BD Ong
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al conectar con la BD Ong
     */
    private void conectarBD() throws ExcepcionAD {
        try {
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.1.135:1521:xe", "Ong", "Ong@2021");
        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
    }

    /**
     * Método que permite desconectar con la BD Ong
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al finalizar la conexión con la BD Ong
     */
    private void desconectarBD() throws ExcepcionAD {
        try {
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
    }

    /**
     * Este método permite insertar un registro en la tabla usuario
     * @param usuario Objecto de la clase Usuario que contiene los datos del
     * usuario a insertar
     * @return Cantidad de registros insertados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int insertarUsuario(Usuario usuario) throws ExcepcionAD {
        String llamada = "{call INSERTAR_REG_USUARIO(?,?,?,?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setString(1, usuario.getNombre());
            sentenciaLlamada.setString(2, usuario.getEmail());
            sentenciaLlamada.setString(3, usuario.getContrasena());
            sentenciaLlamada.setString(4, usuario.getImagen());
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);
            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador de usuario, el nombre, el email y "
                            + "la contraseña no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información del usuario "
                            + "debe ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El nombre del usuario y email solamente debe contener "
                            + "caracteres alfanumericos. "
                            + "El email debe contener una @rroba, un punto, barra baja o cualquier carácter o simbolo especial. "
                            + "La contraseña no debe sobrepasar la longitud máxima de 50 caracteres numericos. ");
                    break;
                case 2899:
                    ead.setMensajeUsuario("Error: El nombre, email, y la contraseña no deben "
                            + "superar la longitud máxima de 50 caracteres. ");
                    break;
                case 20001:
                    ead.setMensajeUsuario("Error: El email no puede contener mas de una arroba. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite eliminar un registro en la tabla usuario
     * @param usuarioId de la clase Integer que contiene el identificador del
     * usuario a eliminar
     * @return Cantidad de registros eliminados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int eliminarUsuario(Integer usuarioId) throws ExcepcionAD {
        String delete = "DELETE FROM usuario WHERE USUARIO_ID=?";
        int registrosAfectados = 0;
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(delete);
            sentenciaPreparada.setInt(1, usuarioId);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(delete);

            switch (ex.getErrorCode()) {
                case 2292:
                    ead.setMensajeUsuario("Error: El usuario tiene un permiso asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla usuario
     * @param usuarioIdViejo de la clase Integer que contiene el identificador
     * del usuario a modificar
     * @param usuario Objecto de la clase Usuario que contiene los datos del
     * usuario a modificar
     * @return Cantidad de registros modificados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int modificarUsuario(Integer usuarioIdViejo, Usuario usuario) throws ExcepcionAD {
        String llamada = "{call ACTUALIZAR_REG_USUARIO(?,?,?,?,?,?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setObject(1, usuarioIdViejo);
            sentenciaLlamada.setObject(2, usuario.getUsuarioId());
            sentenciaLlamada.setString(3, usuario.getNombre());
            sentenciaLlamada.setString(4, usuario.getEmail());
            sentenciaLlamada.setString(5, usuario.getContrasena());
            sentenciaLlamada.setString(6, usuario.getImagen());
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);
            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador del usuario, el nombre, el email, y la contraseña "
                            + "no se modificó porque la información no se pueden repetir. ");
                    break;
                case 1407:
                    ead.setMensajeUsuario("Error: Toda la información del usuario \n"
                            + "debe ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El nombre debe tener una longitud máxima"
                            + " 50 caracteres de la A-Z, \n "
                            + "el email debe contener una arroba, un punto, barrabaja o cualquier carácter especial "
                            + "y también un carácter numerico decimal \n"
                            + "la contrasena debe tener una longitud máxima de 50 caracteres alfanumericos, mayúsculas, "
                            + "minúsculas, caracteres especiales y caracteres numericos. ");
                    break;
                case 2899:
                    ead.setMensajeUsuario("Error: La longitud máxima del nombre, email "
                            + "y la contraseña superan los 50 caracteres. ");
                    break;
                case 20500:
                    ead.setMensajeUsuario("Error: El nombre, email y la contraseña tienen o empiezan con caracteres incorrectos. ");
                    break;
                case 20001:
                    ead.setMensajeUsuario("Error: El email no puede tener mas de una arroba. ");
                    break;
                case 2291:
                    ead.setMensajeUsuario("Error: El usuario tiene un permiso asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite leer un registro en la tabla usuario
     * @param usuarioId de la clase Integer que contiene el identificador del
     * usuario a leer
     * @return Objeto cliente de la clase Usuario leeido
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public Usuario leerUsuario(Integer usuarioId) throws ExcepcionAD {
        Usuario usuario = new Usuario();
        String leerUsuario = "SELECT * FROM USUARIO WHERE USUARIO_ID = '" + usuarioId + "'";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultadoLeer = sentencia.executeQuery(leerUsuario);
            System.out.printf("%-40s %-40s %-40s %-40s %-40s \n", "USUARIO_ID", "NOMBRE", "EMAIL",
                    "CONTRASEÑA", "IMAGEN");
            while (resultadoLeer.next()) {
                usuario = new Usuario();
                usuario.setUsuarioId(resultadoLeer.getInt("USUARIO_ID"));
                usuario.setNombre(resultadoLeer.getString("NOMBRE"));
                usuario.setEmail(resultadoLeer.getString("EMAIL"));
                usuario.setContrasena(resultadoLeer.getString("CONTRASEÑA"));
                usuario.setImagen(resultadoLeer.getString("IMAGEN"));

                System.out.printf("%-40d %-40s %-40s %-40s %-40s \n", resultadoLeer.getInt("USUARIO_ID"), resultadoLeer.getString("NOMBRE"),
                        resultadoLeer.getString("EMAIL"), resultadoLeer.getString("CONTRASEÑA"), resultadoLeer.getString("IMAGEN"));
            }
            sentencia.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerUsuario);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }

        return usuario;
    }

    /**
     * Este método permite leer todos los registros en la tabla usuario
     * @return Objeto listaUsuarios de la clase ArrayList de tipo Usuario
     * leeidos
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public ArrayList<Usuario> leerUsuarios() throws ExcepcionAD {
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        String leerUsuarios = "SELECT * from USUARIO";

        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(leerUsuarios);
            System.out.printf("%-40s %-40s %-40s %-40s %-40s \n", "USUARIO_ID", "NOMBRE", "EMAIL",
                    "CONTRASEÑA", "IMAGEN");
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioId(resultado.getInt("USUARIO_ID"));
                usuario.setNombre(resultado.getString("NOMBRE"));
                usuario.setEmail(resultado.getString("EMAIL"));
                usuario.setContrasena(resultado.getString("CONTRASEÑA"));
                usuario.setImagen(resultado.getString("IMAGEN"));
                listaUsuarios.add(usuario);

                System.out.printf("%-40d %-40s %-40s %-40s %-40s \n", resultado.getInt("USUARIO_ID"), resultado.getString("NOMBRE"),
                        resultado.getString("EMAIL"), resultado.getString("CONTRASEÑA"), resultado.getString("IMAGEN"));
            }
        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerUsuarios);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return listaUsuarios;
    }
    
    /**
     * Este método permite autenticar al usuario contra la base de datos
     * @param usuario de la clase Usuario
     * @return usuario de la clase Usuario leeidos
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún problema al operar con la BD Ong
     */
    public Usuario autenticacion(Usuario usuario) throws ExcepcionAD {
        String consulta = "SELECT NOMBRE, CONTRASENA FROM USUARIOS WHERE NOMBRE = '"
                    + usuario.getNombre() + "' AND CONTRASENA = '" + usuario.getContrasena() + "'";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultadoLeer = sentencia.executeQuery(consulta);
            if (resultadoLeer != null && resultadoLeer.getRow() > 0) {
                while (resultadoLeer.next()) {
                    Usuario user = new Usuario();
                    user.setUsuarioId(resultadoLeer.getInt("USUARIO_ID"));
                    user.setNombre(resultadoLeer.getString("NOMBRE"));
                    user.setEmail(resultadoLeer.getString("EMAIL"));
                    user.setContrasena(resultadoLeer.getString("CONTRASENA"));
                    user.setImagen(resultadoLeer.getString("IMAGEN"));
                    String descifrado = descencriptar(user.getContrasena(), usuario.getContrasena());
                    if (descifrado.equalsIgnoreCase(usuario.getContrasena())
                            && user.getNombre().equals(usuario.getNombre())) {
                        return user;
                    }
                }
                sentencia.close();
                desconectarBD();
            }
            return usuario;
        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(consulta);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
    }
    
    /**
     * Este método permite generar la clave de la contraseña del usuario mediante un funcion SHA_256
     * y con el algoritmo AES
     * @param pass de clase String
     * @return Objeto secKey de la clase SecretKeySpec
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún problema al operar con la BD Ong
     */
    private SecretKeySpec generarClave(String pass) throws ExcepcionAD {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA_256");
            byte[] key = pass.getBytes("UTF_8");
            key = sha.digest(key);
            SecretKeySpec secKey = new SecretKeySpec(key, "AES");
            return secKey;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
    }
    
    /**
     * Este método permite encriptar la contraseña del usuario
     * y cifrarlo con el algoritmo AES
     * @param pass de clase String
     * @return String datosEncriptados de la clase String
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public String encriptar(String pass) throws ExcepcionAD {
        try {
            SecretKeySpec secretKey = generarClave(pass);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] datosEncriptadosBytes = cipher.doFinal(pass.getBytes());
            String datosEncriptadosString = Base64.getEncoder().encodeToString(datosEncriptadosBytes);
            return datosEncriptadosString;
        } catch (ExcepcionAD | InvalidKeyException | NoSuchAlgorithmException
                | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
    }
    
    /**
     * Este método permite descencriptar la clave de la contraseña del usuario
     * @param datosEncriptadosString de la clase String
     * @param pass de clase String
     * @return datosDesencriptadosString de la clase String
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public String descencriptar(String datosEncriptadosString, String pass) throws ExcepcionAD {
        try {
            SecretKeySpec secretKey = generarClave(pass);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] datosDescoficados = Base64.getDecoder().decode(datosEncriptadosString);
            byte[] datosDesencriptadosByte = cipher.doFinal(datosDescoficados);
            String datosDesencriptadosString = new String(datosDesencriptadosByte);
            return datosDesencriptadosString;
        } catch (ExcepcionAD | InvalidKeyException | NoSuchAlgorithmException
                | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
    }

    /**
     * Este método permite insertar un registro en la tabla rol
     * @param rol Objecto de la clase Rol que contiene los datos del rol a
     * insertar
     * @return Cantidad de registros insertados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int insertarRol(Rol rol) throws ExcepcionAD {
        int registrosAfectados = 0;
        String insertarRol = "INSERT INTO rol(rol_id,nombre,descripcion) values(SEQUENCIA_ROL.nextval,?,?)";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(insertarRol);
            sentenciaPreparada.setString(1, rol.getNombre());
            sentenciaPreparada.setString(2, rol.getDescripcion());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(insertarRol);

            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador del rol, el nombre y la descripción "
                            + "no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información del rol debe ser obligatoria. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;

    }

    /**
     * Este método permite eliminar un registro en la tabla rol
     * @param rolId de la clase Integer que contiene el id de la venta a
     * eliminar
     * @return Cantidad de registros eliminados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int eliminarRol(Integer rolId) throws ExcepcionAD {
        String llamada = "{call ELIMINAR_REG_ROL(?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setInt(1, rolId);
            registrosAfectados = sentenciaLlamada.executeUpdate();

            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);

            switch (ex.getErrorCode()) {
                case 2292:
                    ead.setMensajeUsuario("Error: El rol tiene una permiso asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla rol
     * @param rolIdViejo de la clase Integer que contiene el identificador del
     * rol a modificar
     * @param rol Objecto de la clase Rol que contiene los datos del rol a
     * modificar
     * @return Cantidad de registros modificados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int modificarRol(Integer rolIdViejo, Rol rol) throws ExcepcionAD {
        int registrosAfectados = 0;
        String modificarRol = "UPDATE ROL SET NOMBRE=?,"
                + "DESCRIPCION=? WHERE ROL_ID='" + rolIdViejo + "'";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(modificarRol);
            sentenciaPreparada.setString(1, rol.getNombre());
            sentenciaPreparada.setString(2, rol.getDescripcion());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(modificarRol);

            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador del rol, el nombre "
                            + "y la descripción del rol porque la información no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información del rol debe ser obligatoria. ");
                    break;
                case 2291:
                    ead.setMensajeUsuario("Error: El rol tiene un permiso asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;
    }

    /**
     * Este método permite leer un registro en la tabla rol
     * @param rolId de la clase Integer que contiene el identificador del rol a
     * leer
     * @return Objeto venta de la clase Rol leeido
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public Rol leerRol(Integer rolId) throws ExcepcionAD {
        Rol rol = new Rol();
        String leerRol = "SELECT * FROM ROL WHERE ROL_ID = '" + rolId + "'";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(leerRol);
            ResultSet resultadoLeer = sentenciaPreparada.executeQuery();
            System.out.printf("%-40s %-40s %-40s \n", "ROL_ID", "NOMBRE", "DESCRIPCION");
            while (resultadoLeer.next()) {
                rol = new Rol();
                rol.setRolId(resultadoLeer.getInt("ROL_ID"));
                rol.setNombre(resultadoLeer.getString("NOMBRE"));
                rol.setDescripcion(resultadoLeer.getString("DESCRIPCION"));

                System.out.printf("%-40d %-40s %-40s \n", resultadoLeer.getInt("ROL_ID"),
                        resultadoLeer.getString("NOMBRE"),
                        resultadoLeer.getString("DESCRIPCION"));
            }
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerRol);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return rol;
    }

    /**
     * Este método permite leer todos los registros en la tabla rol
     * @return Objeto listaRoles de la clase ArrayList de tipo Rol leeidos
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public ArrayList<Rol> leerRoles() throws ExcepcionAD {
        ArrayList<Rol> listaRoles = new ArrayList();
        String leerRoles = "SELECT * FROM ROL";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(leerRoles);
            System.out.printf("%-40s %-40s %-40s \n", "ROL_ID", "NOMBRE", "DESCRIPCION");
            while (resultado.next()) {
                Rol rol = new Rol();
                rol.setRolId(resultado.getInt("ROL_ID"));
                rol.setNombre(resultado.getString("NOMBRE"));
                rol.setDescripcion(resultado.getString("DESCRIPCION"));
                listaRoles.add(rol);

                System.out.printf("%-40d %-40s %-40s \n", resultado.getInt("ROL_ID"),
                        resultado.getString("NOMBRE"),
                        resultado.getInt("DESCRIPCION"));
            }
            resultado.close();
            sentencia.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerRoles);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return listaRoles;
    }

    /**
     * Este método permite insertar un registro en la tabla Permiso
     * @param permiso Objecto de la clase Permiso que contiene los datos del
     * permiso a insertar
     * @return Cantidad de registros insertados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int insertarPermiso(Permiso permiso) throws ExcepcionAD {
        String llamada = "{call INSERTAR_REG_PERMISO(?,?,?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setObject(1, permiso.getUsuario().getUsuarioId());
            sentenciaLlamada.setObject(2, permiso.getRol().getRolId());
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);
            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador del permiso, el nombre del usuario y "
                            + "el nombre del rol no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información del permiso "
                            + "debe ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El nombre del usuario y el nombre del rol debe contener solamente"
                            + "caracteres alfanumericos. ");
                    break;
                case 2899:
                    ead.setMensajeUsuario("Error: El nombre del usuario y la contraseña no deben superar la longitud"
                            + " máxima de 50 caracteres alfanuméricos. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite eliminar un registro en la tabla permiso
     * @param permisoId de la clase Integer que contiene el identificador del
     * permiso a eliminar
     * @return Cantidad de registros eliminados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int eliminarPermiso(Integer permisoId) throws ExcepcionAD {
        String delete = "DELETE FROM PERMISO WHERE PERMISO_ID=?";
        int registrosAfectados = 0;
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(delete);
            sentenciaPreparada.setInt(1, permisoId);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
            ead.setSentenciaSQL(delete);
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla permiso
     * @param permisoIdViejo de la clase Integer que contiene el identificador
     * del permiso a modificar
     * @param permiso Objecto de la clase Permiso que contiene los datos del
     * permiso a modificar
     * @return Cantidad de registros modificados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int modificarPermiso(Integer permisoIdViejo, Permiso permiso) throws ExcepcionAD {
        String llamada = "{call ACTUALIZAR_REG_PERMISO(?,?,?,?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setObject(1, permisoIdViejo);
            sentenciaLlamada.setObject(2, permiso.getPermisoId());
            sentenciaLlamada.setObject(3, permiso.getUsuario().getUsuarioId());
            sentenciaLlamada.setObject(4, permiso.getRol().getRolId());
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);
            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador del permiso, el nombre del usuario y el nombre del rol "
                            + "no se modificó porque no se pueden repetir. ");
                    break;
                case 1407:
                    ead.setMensajeUsuario("Error: Toda la información del permiso "
                            + "debe ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El nombre del usuario y el nombre del rol solamente"
                            + "deben contener caracteres alfanumericos. ");
                    break;
                case 2899:
                    ead.setMensajeUsuario("Error: La longitud máxima del nombre del usuario "
                            + "y el nombre del rol no deben supera los 50 caracteres alfanumericos. ");
                    break;
                case 20500:
                    ead.setMensajeUsuario("Error: El nombre del usuario y el nombre del rol tienen o empiezan con caracteres incorrectos. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite leer un registro en la tabla permiso
     * @param permisoId de la clase Integer que contiene el identificador del
     * permiso a leer
     * @return Objeto permiso de la clase Permiso leeido
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public Permiso leerPermiso(Integer permisoId) throws ExcepcionAD {
        Permiso permiso = new Permiso();
        String leerPermiso = "SELECT * FROM PERMISO WHERE PERMISO_ID = '" + permisoId + "'";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultadoLeer = sentencia.executeQuery(leerPermiso);
            System.out.printf("%-40s %-40s %-40s \n", "PERMISO_ID", "USUARIO", "ROL");
            while (resultadoLeer.next()) {
                permiso = new Permiso();
                permiso.setPermisoId(resultadoLeer.getInt("PERMISO_ID"));
                Usuario usuario = new Usuario();
                usuario.setNombre(resultadoLeer.getString("USUARIO"));
                permiso.setUsuario(usuario);
                Rol rol = new Rol();
                rol.setNombre(resultadoLeer.getString("ROL"));
                permiso.setRol(rol);

                System.out.printf("%-40d %-40s %-40s \n", resultadoLeer.getInt("PERMISO_ID"), resultadoLeer.getString("USUARIO"),
                        resultadoLeer.getString("ROL"));
            }
            sentencia.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerPermiso);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }

        return permiso;
    }

    /**
     * Este método permite leer todos los registros en la tabla permiso
     * @return Objeto listaPermisos de la clase ArrayList de tipo Permiso
     * leeidos
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public ArrayList<Permiso> leerPermisos() throws ExcepcionAD {
        ArrayList<Permiso> listaPermisos = new ArrayList();
        String leerPermisos = "SELECT * FROM PERMISO";

        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(leerPermisos);
            System.out.printf("%-40s %-40s %-40s \n", "PERMISO_ID", "USUARIO", "ROL");
            while (resultado.next()) {
                Permiso permiso = new Permiso();
                permiso.setPermisoId(resultado.getInt("PERMISO_ID"));
                Usuario usuario = new Usuario();
                usuario.setNombre(resultado.getString("USUARIO"));
                permiso.setUsuario(usuario);
                Rol rol = new Rol();
                rol.setNombre(resultado.getString("ROL"));
                permiso.setRol(rol);
                listaPermisos.add(permiso);

                System.out.printf("%-40d %-40s %-40s \n", resultado.getInt("PERMISO_ID"), resultado.getString("USUARIO"),
                        resultado.getString("ROL"));
                sentencia.close();
                desconectarBD();
            }
        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerPermisos);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return listaPermisos;
    }

    /**
     * Este método permite controlar el acceso del usuario autenticado mediante roles
     * @param username de la clase String
     * @param rol de las clase String
     * @return verdadero si es usuario es valido sino falso
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public boolean controlAcceso(String username, String rol) throws ExcepcionAD {
        String query = "select * from permisos where usuario_id = '" + username + "' and rol_id = '" + rol + "'";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultadoLeer = sentencia.executeQuery(query);
            if (resultadoLeer != null && resultadoLeer.getRow() > 0) {
                while (resultadoLeer.next()) {
                    Permiso permiso = new Permiso();
                    Usuario usuario = new Usuario();
                    Rol roles = new Rol();
                    permiso.setPermisoId(resultadoLeer.getInt("PERMISO_ID"));
                    usuario.setNombre(resultadoLeer.getString("USUARIO"));
                    permiso.setUsuario(usuario);
                    roles.setNombre(resultadoLeer.getString("ROL"));
                    permiso.setRol(roles);

                    if (permiso.getUsuario().equals(username) && permiso.getRol().equals(rol)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            sentencia.close();
            desconectarBD();
        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(query);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }

        return true;
    }

    /**
     * Este método permite insertar un registro en la tabla producto
     * @param producto Objecto de la clase Producto que contiene los datos del
     * producto a insertar
     * @return Cantidad de registros insertados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int insertarProducto(Producto producto) throws ExcepcionAD {
        int registrosAfectados = 0;
        String insertarProducto = "INSERT INTO PRODUCTO(producto_id,nombre,envase,fecha_caducidad,imagen,qr,colaborador,categoria) values(SEQUENCIA_PRODUCTO.nextval,?,?,?,?,?,?,?)";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(insertarProducto);
            sentenciaPreparada.setString(1, producto.getNombre());
            sentenciaPreparada.setString(2, producto.getEnvase());
            sentenciaPreparada.setString(3, producto.getFechaCaducidad());
            sentenciaPreparada.setString(4, producto.getImagen());
            sentenciaPreparada.setString(5, producto.getQr());
            sentenciaPreparada.setObject(6, producto.getColaborador().getColaboradorId());
            sentenciaPreparada.setObject(7, producto.getCategoria().getCategoriaId());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(insertarProducto);

            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador del producto, el nombre, la imagen y el codigo QR "
                            + "porque no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información del producto debe ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El tipo de envase del producto no existe. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;

    }

    /**
     * Este método permite eliminar un registro en la tabla producto
     * @param productoId de la clase Integer que contiene el identificador del
     * producto a eliminar
     * @return Cantidad de registros eliminados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int eliminarProducto(Integer productoId) throws ExcepcionAD {
        String llamada = "{call ELIMINAR_REG_PRODUCTO(?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setInt(1, productoId);
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);
            ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
            throw ead;
        }
        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla producto
     * @param productoIdViejo de la clase Integer que contiene el identificador
     * del rol a modificar
     * @param producto Objecto de la clase Producto que contiene los datos del
     * producto a modificar
     * @return Cantidad de registros modificados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int modificarProducto(Integer productoIdViejo, Producto producto) throws ExcepcionAD {
        int registrosAfectados = 0;
        String modificarProducto = "UPDATE PRODUCTO SET NOMBRE=?, ENVASE=?, FECHA_CADUCIDAD=?, IMAGEN=?, QR=?, "
                + " COLABORADOR=?, CATEGORIA=? WHERE PRODUCTO_ID='" + productoIdViejo + "'";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(modificarProducto);
            sentenciaPreparada.setString(1, producto.getNombre());
            sentenciaPreparada.setString(2, producto.getEnvase());
            sentenciaPreparada.setString(3, producto.getFechaCaducidad());
            sentenciaPreparada.setString(4, producto.getImagen());
            sentenciaPreparada.setString(5, producto.getQr());
            sentenciaPreparada.setObject(6, producto.getColaborador().getColaboradorId());
            sentenciaPreparada.setObject(7, producto.getCategoria().getCategoriaId());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(modificarProducto);

            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador, el nombre "
                            + ", la imagen y el codigo QR del producto no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información del producto debe ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El tipo de envase del producto no existe. ");
                    break;

                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;
    }

    /**
     * Este método permite leer un registro en la tabla producto
     * @param productoId de la clase Integer que contiene el identificador del
     * producto a leer
     * @return Objeto producto de la clase Producto leeido
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public Producto leerProducto(Integer productoId) throws ExcepcionAD {
        Producto producto = new Producto();
        String leerProducto = "SELECT * FROM PRODUCTO WHERE PRODUCTO_ID = '" + productoId + "'";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(leerProducto);
            ResultSet resultadoLeer = sentenciaPreparada.executeQuery();
            System.out.printf("%-40s %-40s %-40s %-40s %-40s %-40s \n", "PRODUCTO_ID", "NOMBRE", "ENVASE", "FECHA_CADUCIDAD",
                    "IMAGEN", "QR", "COLABORADOR");
            while (resultadoLeer.next()) {
                producto = new Producto();
                producto.setProductoId(resultadoLeer.getInt("PRODUCTO_ID"));
                producto.setNombre(resultadoLeer.getString("NOMBRE"));
                producto.setEnvase(resultadoLeer.getString("ENVASE"));
                producto.setFechaCaducidad(resultadoLeer.getString("FECHA_CADUCIDAD"));
                producto.setImagen(resultadoLeer.getString("IMAGEN"));
                producto.setQr(resultadoLeer.getString("QR"));
                Colaborador colaborador = new Colaborador();
                colaborador.setNombre(resultadoLeer.getString("COLABORADOR"));
                Categoria categoria = new Categoria();
                categoria.setNombre(resultadoLeer.getString("CATEGORIA"));
                producto.setColaborador(colaborador);

                System.out.printf("%-40d %-40s %-40s %-40s %-40s %-40s %-40s %-40s \n", resultadoLeer.getInt("PRODUCTO_ID"),
                        resultadoLeer.getString("NOMBRE"),
                        resultadoLeer.getString("ENVASE"),
                        resultadoLeer.getString("FECHA_CADUCIDAD"),
                        resultadoLeer.getString("IMAGEN"),
                        resultadoLeer.getString("QR"),
                        resultadoLeer.getString("COLABORADOR"),
                        resultadoLeer.getString("CATEGORIA"));
            }
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerProducto);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return producto;
    }

    /**
     * Este método permite leer todos los registros en la tabla producto
     * @return Objeto listaProductos de la clase ArrayList de tipo Producto
     * leeidos
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public ArrayList<Producto> leerProductos() throws ExcepcionAD {
        ArrayList<Producto> listaProductos = new ArrayList();
        String leerProductos = "SELECT * FROM PRODUCTO";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(leerProductos);
            System.out.printf("%-40s %-40s %-40s %-40s %-40s %-40s %-40s %-40s  \n", "PRODUCTO_ID", "NOMBRE", "ENVASE",
                    "FECHA_CADUCIDAD", "IMAGEN", "QR", "COLABORADOR", "CATEGORIA");
            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setProductoId(resultado.getInt("PRODUCTO_ID"));
                producto.setNombre(resultado.getString("NOMBRE"));
                producto.setEnvase(resultado.getString("ENVASE"));
                producto.setFechaCaducidad(resultado.getString("FECHA_CADUCIDAD"));
                producto.setImagen(resultado.getString("IMAGEN"));
                producto.setQr(resultado.getString("QR"));
                Colaborador colaborador = new Colaborador();
                colaborador.setNombre(resultado.getString("COLABORADOR"));
                producto.setColaborador(colaborador);
                Categoria categoria = new Categoria();
                categoria.setNombre(resultado.getString("CATEGORIA"));
                producto.setCategoria(categoria);
                listaProductos.add(producto);

                System.out.printf("%-40d %-40s %-40s %-40s %-40s %-40s %-40s \n", resultado.getInt("PRODUCTO_ID"),
                        resultado.getString("NOMBRE"),
                        resultado.getString("ENVASE"),
                        resultado.getString("FECHA_CADUCIDAD"),
                        resultado.getString("IMAGEN"),
                        resultado.getString("QR"),
                        resultado.getString("COLABORADOR"),
                        resultado.getString("CATEGORIA"));
            }
            resultado.close();
            sentencia.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerProductos);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return listaProductos;
    }

    /**
     * Este método permite insertar un registro en la tabla colaborador
     * @param colaborador Objecto de la clase Colaborador que contiene los datos
     * del colaborador a insertar
     * @return Cantidad de registros insertados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int insertarColaborador(Colaborador colaborador) throws ExcepcionAD {
        String llamada = "{call INSERTAR_REG_COLABORADOR(?,?,?,?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setString(1, colaborador.getNombre());
            sentenciaLlamada.setString(2, colaborador.getEmail());
            sentenciaLlamada.setString(3, colaborador.getTelefono());
            sentenciaLlamada.setString(4, colaborador.getFoto());
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);
            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador, el email y "
                            + "el teléfono de los colaboradores no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información del colaborador "
                            + "debe ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El nombre de la persona o empresa colaboradora solamente"
                            + "debe contener caracteres alfanumericos, mayúsculas y minúsculas de la A-Z. "
                            + "El email debe contener una @rroba, un punto, barra baja o cualquier carácter o símbolo especial. "
                            + "El teléfono solo debe contener caracteres numericos. ");
                    break;
                case 2899:
                    ead.setMensajeUsuario("Error: El nombre, email del colaborador no deben "
                            + "superar la longitud máxima de 50 caracteres alfanumericos. El teléfono tampoco debe"
                            + "superar la longitud máxima de 9 caracteres numericos. ");
                    break;
                case 20500:
                    ead.setMensajeUsuario("Error: El nombre, el email, teléfono del colaborador tiene caracteres incorrectos. ");
                    break;
                case 20001:
                    ead.setMensajeUsuario("Error: El email del colaborador no puede contener más de una arroba. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite eliminar un registro en la tabla colaborador
     * @param colaboradorId de la clase Integer que contiene el identificador
     * del colaborador a eliminar
     * @return Cantidad de registros eliminados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int eliminarColaborador(Integer colaboradorId) throws ExcepcionAD {
        String delete = "DELETE FROM COLABORADOR WHERE COLABORADOR_ID=?";
        int registrosAfectados = 0;
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(delete);
            sentenciaPreparada.setInt(1, colaboradorId);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(delete);

            switch (ex.getErrorCode()) {
                case 2292:
                    ead.setMensajeUsuario("Error: El colaborador tiene un producto asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla colaborador
     * @param colaboradorIdViejo de la clase Integer que contiene el
     * identificador del colaborador a modificar
     * @param colaborador Objecto de la clase Colaborador que contiene los datos
     * del colaborador a modificar
     * @return Cantidad de registros modificados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int modificarColaborador(Integer colaboradorIdViejo, Colaborador colaborador) throws ExcepcionAD {
        String llamada = "{call ACTUALIZAR_REG_COLABORADOR(?,?,?,?,?,?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setObject(1, colaboradorIdViejo);
            sentenciaLlamada.setObject(2, colaborador.getColaboradorId());
            sentenciaLlamada.setString(3, colaborador.getNombre());
            sentenciaLlamada.setString(4, colaborador.getEmail());
            sentenciaLlamada.setString(5, colaborador.getTelefono());
            sentenciaLlamada.setString(6, colaborador.getFoto());
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);
            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador, el nombre, el email, teléfono y la foto "
                            + "no se modificó porque la información no se pueden repetir. ");
                    break;
                case 1407:
                    ead.setMensajeUsuario("Error: Toda la información del colaborador "
                            + "deben ser obligatoria. ");
                    break;
                case 2290:
                    ead.setMensajeUsuario("Error: El nombre debe contener solamente"
                            + " caracteres alfanumericos, mayúsculas y minúsculas de A-Z y a-z. "
                            + "El email debe contener una @rroba, un punto, barrabaja o cualquier carácter o símbolo especial "
                            + "y también caracteres numericos decimales. "
                            + "El teléfono debe tener solamente caracteres numericos. ");
                    break;
                case 2899:
                    ead.setMensajeUsuario("Error: El nombre, email "
                            + "del colaborador porque supera la longitud máxima permitida de 50 caracteres alfanumericos "
                            + "o porque el teléfono supera la longitud máxima de 9 caracteres numericos. ");
                    break;
                case 20500:
                    ead.setMensajeUsuario("Error: El nombre, email y el telefono del colaborador "
                            + "tienen o empiezan con caracteres incorrectos. ");
                    break;
                case 20001:
                    ead.setMensajeUsuario("Error: El email del colaborador contiene más de una arroba. ");
                    break;
                case 2291:
                    ead.setMensajeUsuario("Error: El colaborador tiene un producto asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }

        return registrosAfectados;
    }

    /**
     * Este método permite leer un registro en la tabla colaborador
     * @param colaboradorId de la clase Integer que contiene el identificador
     * del colaborador a leer
     * @return Objeto colaborador de la clase Colaborador leeido
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public Colaborador leerColaborador(Integer colaboradorId) throws ExcepcionAD {
        Colaborador colaborador = new Colaborador();
        String leerColaborador = "SELECT * FROM COLABORADOR WHERE COLABORADOR_ID = '" + colaboradorId + "'";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultadoLeer = sentencia.executeQuery(leerColaborador);
            System.out.printf("%-40s %-40s %-40s %-40s %-40s \n", "COLABORADOR_ID", "NOMBRE", "EMAIL",
                    "TELEFONO", "FOTO");
            while (resultadoLeer.next()) {
                colaborador = new Colaborador();
                colaborador.setColaboradorId(resultadoLeer.getInt("COLABORADOR_ID"));
                colaborador.setNombre(resultadoLeer.getString("NOMBRE"));
                colaborador.setEmail(resultadoLeer.getString("EMAIL"));
                colaborador.setTelefono(resultadoLeer.getString("TELEFONO"));
                colaborador.setFoto(resultadoLeer.getString("FOTO"));

                System.out.printf("%-40d %-40s %-40s %-40s %-40s \n", resultadoLeer.getInt("COLABORADOR_ID"), resultadoLeer.getString("NOMBRE"),
                        resultadoLeer.getString("EMAIL"), resultadoLeer.getString("TELEFONO"), resultadoLeer.getString("FOTO"));
            }
            sentencia.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerColaborador);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }

        return colaborador;
    }

    /**
     * Este método permite leer todos los registros en la tabla colaborador
     * @return Objeto listaColaboradores de la clase ArrayList de tipo
     * Colaborador leeidos
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public ArrayList<Colaborador> leerColaboradores() throws ExcepcionAD {
        ArrayList<Colaborador> listaColaboradores = new ArrayList();
        String leerColaboradores = "SELECT * FROM COLABORADORES";

        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(leerColaboradores);
            System.out.printf("%-40s %-40s %-40s %-40s %-40s \n", "COLABORADOR_ID", "NOMBRE", "EMAIL",
                    "TELEFONO", "FOTO");
            while (resultado.next()) {
                Colaborador colaborador = new Colaborador();
                colaborador.setColaboradorId(resultado.getInt("COLABORADOR_ID"));
                colaborador.setNombre(resultado.getString("NOMBRE"));
                colaborador.setEmail(resultado.getString("EMAIL"));
                colaborador.setTelefono(resultado.getString("TELEFONO"));
                colaborador.setFoto(resultado.getString("FOTO"));
                listaColaboradores.add(colaborador);

                System.out.printf("%-40d %-40s %-40s %-40s %-40s \n", resultado.getInt("COLABORADOR_ID"), resultado.getString("NOMBRE"),
                        resultado.getString("EMAIL"), resultado.getString("TELEFONO"), resultado.getString("FOTO"));
            }
        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerColaboradores);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return listaColaboradores;
    }

    /**
     * Este método permite insertar un registro en la tabla categoria
     * @param categoria Objecto de la clase Categoria que contiene los datos del
     * categoria a insertar
     * @return Cantidad de registros insertados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int insertarCategoria(Categoria categoria) throws ExcepcionAD {
        int registrosAfectados = 0;
        String insertarCategoria = "INSERT INTO CATEGORIA(categoria_id,nombre,descripcion) values(SEQUENCIA_CATEGORIA.nextval,?,?)";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(insertarCategoria);
            sentenciaPreparada.setString(1, categoria.getNombre());
            sentenciaPreparada.setString(2, categoria.getDescripcion());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(insertarCategoria);

            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador, el nombre, la descripcion de la categoría "
                            + "no se puede repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información de la categoría debe ser obligatoria. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;

    }

    /**
     * Este método permite eliminar un registro en la tabla categoria
     * @param categoriaId de la clase Integer que contiene el identificador del
     * producto a eliminar
     * @return Cantidad de registros eliminados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int eliminarCategoria(Integer categoriaId) throws ExcepcionAD {
        String llamada = "{call ELIMINAR_REG_CATEGORIA(?)}";
        int registrosAfectados = 0;
        try {
            conectarBD();
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);
            sentenciaLlamada.setInt(1, categoriaId);
            registrosAfectados = sentenciaLlamada.executeUpdate();
            sentenciaLlamada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(llamada);

            switch (ex.getErrorCode()) {
                case 2291:
                    ead.setMensajeUsuario("Error: La categoria tiene un producto asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;
    }

    /**
     * Este método permite modificar un registro en la tabla categoria
     * @param categoriaIdViejo de la clase Integer que contiene el identificador
     * de la categoria a modificar
     * @param categoria Objecto de la clase Categoria que contiene los datos de
     * la categoria a modificar
     * @return Cantidad de registros modificados
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public int modificarCategoria(Integer categoriaIdViejo, Categoria categoria) throws ExcepcionAD {
        int registrosAfectados = 0;
        String modificarCategoria = "UPDATE CATEGORIA SET NOMBRE=?, DESCRIPCION=? "
                + " WHERE CATEGORIA_ID='" + categoriaIdViejo + "'";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(modificarCategoria);
            sentenciaPreparada.setString(1, categoria.getNombre());
            sentenciaPreparada.setString(2, categoria.getDescripcion());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(modificarCategoria);

            switch (ex.getErrorCode()) {
                case 1:
                    ead.setMensajeUsuario("Error: El identificador, el nombre "
                            + " y la descripcion de la categoria no se pueden repetir. ");
                    break;
                case 1400:
                    ead.setMensajeUsuario("Error: Toda la información de la categoria debe ser obligatoria. ");
                    break;
                case 2291:
                    ead.setMensajeUsuario("Error: La categoria tiene un producto asociado. ");
                    break;
                default:
                    ead.setMensajeUsuario("Error en el sistema. Consulta con el administrador. ");
                    break;
            }
            throw ead;
        }
        return registrosAfectados;
    }

    /**
     * Este método permite leer un registro en la tabla categoria
     * @param categoriaId de la clase Integer que contiene el identificador del
     * categoria a leer
     * @return Objeto categoria de la clase Categoria leeido
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public Categoria leerCategoria(Integer categoriaId) throws ExcepcionAD {
        Categoria categoria = new Categoria();
        String leerCategoria = "SELECT * FROM CATEGORIA WHERE CATEGORIA_ID = '" + categoriaId + "'";
        try {
            conectarBD();
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(leerCategoria);
            ResultSet resultadoLeer = sentenciaPreparada.executeQuery();
            System.out.printf("%-40s %-40s %-40s \n", "CATEGORIA_ID", "NOMBRE", "DESCRIPCION");
            while (resultadoLeer.next()) {
                categoria = new Categoria();
                categoria.setCategoriaId(resultadoLeer.getInt("CATEGORIA_ID"));
                categoria.setNombre(resultadoLeer.getString("NOMBRE"));
                categoria.setDescripcion(resultadoLeer.getString("DESCRIPCION"));

                System.out.printf("%-40d %-40s %-40s \n", resultadoLeer.getInt("CATEGORIA_ID"),
                        resultadoLeer.getString("NOMBRE"),
                        resultadoLeer.getString("DESCRIPCION"));
            }
            sentenciaPreparada.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(1);
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerCategoria);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return categoria;
    }

    /**
     * Este método permite leer todos los registros en la tabla categoria
     * @return Objeto listaCategorias de la clase ArrayList de tipo Categoria
     * leeidos
     * @throws componentead.ExcepcionAD Se lanzará cuando se produzca algún
     * problema al operar con la BD Ong
     */
    public ArrayList<Categoria> leerCategorias() throws ExcepcionAD {
        ArrayList<Categoria> listaCategorias = new ArrayList();
        String leerCategorias = "SELECT * FROM CATEGORIA";
        try {
            conectarBD();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(leerCategorias);
            System.out.printf("%-40s %-40s %-40s \n", "CATEGORIA_ID", "NOMBRE", "DESCRIPCION");
            while (resultado.next()) {
                Categoria categoria = new Categoria();
                categoria.setCategoriaId(resultado.getInt("CATEGORIA_ID"));
                categoria.setNombre(resultado.getString("NOMBRE"));
                categoria.setDescripcion(resultado.getString("DESCRIPCION"));
                listaCategorias.add(categoria);

                System.out.printf("%-40d %-40s %-40s \n", resultado.getInt("PRODUCTO_ID"),
                        resultado.getString("NOMBRE"),
                        resultado.getString("DESCRIPCION"));
            }
            resultado.close();
            sentencia.close();
            desconectarBD();

        } catch (SQLException ex) {
            ExcepcionAD ead = new ExcepcionAD();
            ead.setCodigoError(ex.getErrorCode());
            ead.setMensajeAdministrador(ex.getMessage());
            ead.setSentenciaSQL(leerCategorias);
            ead.setMensajeUsuario("Error general del sistema. Consulte con el administrador. ");
            throw ead;
        }
        return listaCategorias;
    }
}
