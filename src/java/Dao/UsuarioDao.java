/*
 * @version 1.0.0 DataAccessObject supports homeController and ventaController with 
 * methods connecting with DB, making sql queries, storing and deleting images as well 
 * as rows from DB. 
*/
package Dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.PersonaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 DataAccessObject supports homeController and ventaController with 
 * methods connecting with DB, making sql queries, storing and deleting images as well 
 * as rows from DB. 
 */
public class UsuarioDao {
    
    JdbcTemplate jdbcTemplate;
    ConectarDB conectar = new ConectarDB();
    
    //------Copy load images on server
    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\images\\photos";
    private static final String DELETE_DIRECTORY = "..\\..\\web\\";
    //---Load configuration
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; //50MB
    
    /**
     * 
     * @return user id, name and lastname from a sql query to the DB and send it to ventaController 
     * which use this to list the users as options once we're trying to register a new sell.
     */
    public List consultarUsuarios(){
        List usuarios = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
        String sql = "select id_usuario, nombre, apellido from usuarios";
        usuarios = this.jdbcTemplate.queryForList(sql);
        return usuarios;
    }
    
//    public int consultarCodigo(){
//        int id_usuario = 0;
//        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
//        String sql = "select id_usuario, nombre from usuarios";
//        id_usuario = this.jdbcTemplate.queryForObject(sql, Integer.class);
//        return id_usuario;
//    }

    /**
     * 
     * @param foto it takes photo url (which is stored on DB) and uses it as a parameter to 
     * delete image from server. 
     * @param deletePath it generates the path on which images are stored so it can look for the 
     * specific image and delete it.
     * @param id once the image has been deleted, it takes the user id from a HttpServletRequest 
     * and uses it to make a sql sentence in order to delete row from DB.
     */
    //Delete user and photo
    public void deletePhoto(String foto, String deletePath, int id){
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
        //Build file's path on server
        String deleteFile = deletePath + DELETE_DIRECTORY + foto;
        File del = new File(deleteFile);
        System.out.println(deleteFile);
        if (del.delete()){
            String sql = "delete from usuarios where id_usuario = ?";
            jdbcTemplate.update(sql, id);
        }else{
            System.out.println("Couldn't delete image.");
        }
    }
    
    /**
     * 
     * @param p loads model created based on PersonaBean.
     * @param items this is taken from the updateUsuario.jsp form, once its triggered, 
     * we use set and get methods to update the information onto the DB.
     */
    //Update user without updating picture
    public void updateUserNoPhoto(PersonaBean p, List items){
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++){
            FileItem fileItem = (FileItem) items.get(i);
            list.add(fileItem.getString());
        }
        p.setNombre(list.get(0));
        p.setApellido(list.get(1));
        p.setEdad(Integer.parseInt(list.get(2)));
        p.setCorreo(list.get(3));
        
        String sql = "update usuarios set nombre = ?, apellido = ?, "
                + "edad = ?, correo = ? where id_usuario = ?";
        jdbcTemplate.update(sql, p.getNombre(), p.getApellido(), p.getEdad(), p.getCorreo(), p.getId());
    }
    
    /**
     * 
     * @param p loads model created based on PersonaBean.
     * @param isMultiPart this parameter verifies if the data from the form comes 
     * from a MultiPart type of form.
     * @param request this parameter is used to know where the previous image 
     * is located so we can delete it.
     * @param items this is taken from the updateUsuario.jsp form, once its triggered, 
     * we use set and get methods to update the information onto the DB as well as the 
     * pictures on server.
     */
    //Update user including picture
    public void updateUserWithPhoto(PersonaBean p, 
            boolean isMultiPart,
            HttpServletRequest request,
            List items
            ){
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
        
        ArrayList<String> list = new ArrayList<>();
        if (isMultiPart){
            //file instance 
            DiskFileItemFactory file = new DiskFileItemFactory();
            //Set load parameters
            //Set memory threshold for files which will be created and stored on the directory
            file.setSizeThreshold(MEMORY_THRESHOLD);
            //Set temporary storage directory
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));
            //Pass fileitem as parameter to variable
            ServletFileUpload fileUpload = new ServletFileUpload(file);
            //Set maximum size for files
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            //Set maximum request size
            fileUpload.setSizeMax(MAX_REQUEST_SIZE);
            //Set delete path to remove previous image
            //Capture file's path
            String deletePath = request.getServletContext().getRealPath("") + File.separator;
            String foto = request.getParameter("foto");
            String deleteFile = deletePath + DELETE_DIRECTORY + foto;
            File del = new File(deleteFile);
            del.delete();
            //Build a temp path to store loaded files
            //This is a relative path to the actual project path
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            //Create dir if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            for (int i = 0; i < items.size(); i++){
                //Create fileitem var and parse it from list that covers values from form
                FileItem fileItem = (FileItem) items.get(i);
                //Check which var comes from the form
                if (!fileItem.isFormField()){
                    String fileName = new File(fileItem.getName()).getName();
                    String filePath = uploadPath + File.separator + list.get(0) + "_" + list.get(1) + "_" + fileName;
                    File uploadFile = new File(filePath);
                    //Get file's name
                    String nameFile = ("images/photos/" + list.get(0) + "_" + list.get(1) + "_" + fileName);
                    try{
                        //Delete img if already exists
                        uploadFile.delete();
                        //Store file sequence on disk (tomcat's directory)
                        fileItem.write(uploadFile);
                        p.setFoto(nameFile);
                        p.setOldfoto(nameFile);
                    } catch (Exception ex){
                        System.out.printf("write " + ex.getMessage());
                    }
                } else{
                    list.add(fileItem.getString());
                }
            }
            p.setNombre(list.get(0));
            p.setApellido(list.get(1));
            p.setEdad(Integer.parseInt(list.get(2)));
            p.setCorreo(list.get(3));
        }
        
        String sql = "update usuarios set nombre = ?, apellido = ?, edad = ?, "
                + "correo = ?, foto = ?, oldfoto = ? where id_usuario = ?";
        jdbcTemplate.update(sql, p.getNombre(), p.getApellido(), p.getEdad(), 
                p.getCorreo(), p.getFoto(), p.getOldfoto(), p.getId());
    }
}