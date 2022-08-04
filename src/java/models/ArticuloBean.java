/*
 * @version 1.0.0 Bean model for articles which stores constructor method and get and set 
 * methods for articles.
*/
package models;

import javax.ejb.Stateless;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 Bean model for articles which stores constructor method and get and set 
 * methods for articles.
 */
@Stateless
public class ArticuloBean {
    private int id;
    private String titulo;
    private String descripcion;
    private int precio;
    private String foto;
    private int id_usuario;
    private String oldfoto;

    public ArticuloBean(){
        
    }
    
    public ArticuloBean(String titulo, String descripcion, int precio, String foto, int id_usuario){
//        this.id_articulo = id_articulo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.foto = foto;
        this.id_usuario = id_usuario;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getOldfoto() {
        return oldfoto;
    }

    public void setOldfoto(String oldfoto) {
        this.oldfoto = oldfoto;
    }
    
    
}
