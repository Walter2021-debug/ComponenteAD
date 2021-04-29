/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import componentead.Categoria;
import componentead.Colaborador;
import componentead.ComponenteAD;
import componentead.ExcepcionAD;
import componentead.Permiso;
import componentead.Producto;
import componentead.Rol;
import componentead.Usuario;

/**
 *
 * @author Usuario
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        Permiso permiso = new Permiso();
        Producto producto = new Producto();
        Colaborador colaborador = new Colaborador();
        Categoria categoria = new Categoria();
        
        usuario.setUsuarioId(24);
        usuario.setNombre("Ismael");
        usuario.setEmail("Maldonado");
        usuario.setContrasena("");
        
        rol.setRolId(2);
        rol.setNombre("Admin");
        rol.setDescripcion("Administrador");
        
        permiso.setPermisoId(30);
        permiso.setUsuario(usuario);
        permiso.setRol(rol);
        
        colaborador.setColaboradorId(5);
        colaborador.setNombre("Juan");
        colaborador.setEmail("juan30@gmail.com");
        colaborador.setTelefono("620684956");
        colaborador.setFoto("/imagen/juan.jpg");
        
        producto.setProductoId(15);
        producto.setNombre("Arroz blanco");
        producto.setEnvase("Bolsa plastica");
        producto.setFechaCaducidad("28/04/2021");
        producto.setImagen("/imagen/arrozBlanco.jpg");
        producto.setQr("/qr/qrArroz");
        producto.setColaborador(colaborador);
        producto.setCategoria(categoria);
        
        /*v.setMarcaZapato("Puma");
        v.setTallaZapato(44);
        v.setColorZapato("Negro");
        v.setSeccionZapato("M");
        v.setCantidadPares(1);
        v.setPrecioUnitario(30);
        c.setClienteId(6);
        v.setCliente(c);*/
        
        try {
            ComponenteAD hrz = new ComponenteAD();
            //hrz.insertarCliente(c); // PROBADO
            //hrz.eliminarCliente(5); // PROBADO
            //hrz.modificarCliente(24, c); // PROBADO
            //hrz.leerCliente(2); // PROBADO
            /*ArrayList<Cliente> listaClientes = hrz.leerClientes();
            System.out.println(listaClientes);*/ // PROBADO
            
            
            //hrz.insertarVenta(v); //PROBADO
            //hrz.eliminarVenta(27); //PROBADO
            //hrz.modificarVenta(3, v); //PROBADO
            //hrz.leerVenta(3); // PROBADO
            /*ArrayList<Venta> listaVentas = hrz.leerVentas();
            System.out.println(listaVentas);*/ // PROBADO
            
            //hrz = new HRComponenteZapateria();
            
        } catch (ExcepcionAD ex) {
            System.out.println(ex.toString());
        }
    }
    
}
