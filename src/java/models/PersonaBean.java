/*
 * @version 1.0.0 Bean model for users which stores constructor method and get and set 
 * methods for users.
*/
package models;

import javax.ejb.Stateless;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 Bean model for users which stores constructor method and get and set 
 * methods for users.
 */
@Stateless
public class PersonaBean {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String foto;
    private String oldfoto;

    public PersonaBean(){
        
    }
    
    public PersonaBean(String nombre, String apellido, String correo, int edad, String foto, String oldfoto){
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.foto = foto;
        this.oldfoto = foto;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getOldfoto() {
        return oldfoto;
    }

    public void setOldfoto(String oldfoto) {
        this.oldfoto = oldfoto;
    }
    
    
}
