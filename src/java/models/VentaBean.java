/*
 * @version 1.0.0 Bean model for sells which stores constructor method and get and set 
 * methods for sells.
*/
package models;

import java.sql.Date;
import javax.ejb.Stateless;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 Bean model for sells which stores constructor method and get and set 
 * methods for sells.
 */
@Stateless
public class VentaBean {
    private int id_venta;
    private int id_usuario;
    private int id_articulo;
    private String fecha_venta;

    public VentaBean(){
        
    }
    
    public VentaBean(int id_usuario, int id_articulo, String fecha_venta){
        //this.id_venta = id_venta;
        this.id_usuario = id_usuario;
        this.id_articulo = id_articulo;
        this.fecha_venta = fecha_venta;
    }
    
    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(int id_articulo) {
        this.id_articulo = id_articulo;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }
    
    
}
