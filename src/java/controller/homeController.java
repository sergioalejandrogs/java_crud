/*
 * @version 1.0.0 Controller created to load model and views supporting 'usuarios' 
 * (formUsuario, vistaUsuario, addUsuario, deleteUsuario, updateUsuario, listarArticulos).
*/
package controller;

import Dao.ConectarDB;
import Dao.UsuarioDao;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.PersonaBean;
import models.PersonaValidation;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 Controller created to load model and views supporting 'usuarios' 
 * (formUsuario, vistaUsuario, addUsuario, deleteUsuario, updateUsuario).
 */

@Controller
public class homeController {
    
    private final PersonaValidation personaValidar;
    private final JdbcTemplate jdbcTemplate;
    
    //------Copy load images on server
    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\images\\photos";
    //---Load configuration
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; //50MB    
    
    /**
     * Main controller which imports methods to validate form fields and connect to DB.
     */
    public homeController(){
        this.personaValidar = new PersonaValidation();
        ConectarDB conectar = new ConectarDB();
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
    }
    
    /**
     * 
     * @return page from formUsuario.jsp which is loaded based on model from PersonaBean
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView form(){
        return new ModelAndView("views/formUsuario", "persona", new PersonaBean());
    }
    
    /**
     * 
     * @param p loads model created based on PersonaBean.
     * @param result based on the information filled on the form from formUsuario.jsp, 
     * it will take that result as a parameter for method PersonaValidation.validate.
     * @return Depending on the result of the validation, it's going to generate a view 
     * loading once again the form highlinting the errors or vistaUsuario.jsp with the 
     * data taken from the form.
     */
    @RequestMapping(value="formUsuario.htm", method = RequestMethod.POST)
    public ModelAndView form(
            @ModelAttribute("persona") PersonaBean p,
            BindingResult result
        ){
        //It takes the result from the form and use it as a parameter form personaValidar.validate method.
        //Depending on the result from the form it will return to the form highlinting the errors or 
        //it will take user to vistaUsuario.jsp with information from form and list it. 
        this.personaValidar.validate(p, result);
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView();
            mav.addObject("persona", new PersonaBean());
            mav.setViewName("views/formUsuario");
            return mav;
        }
        else{
            ModelAndView mav = new ModelAndView();
            mav.setViewName("views/vistaUsuario");
            mav.addObject("nombre", p.getNombre());
            mav.addObject("apellido", p.getApellido());
            mav.addObject("edad", p.getEdad());
            mav.addObject("correo", p.getCorreo());
            return mav;
        }
    }
    
    /**
     * 
     * @return view of listarUsuarios.jsp taking 'usuarios' data (datos) from DB and listing it 
     * based on PersonaBean model
     */
    @RequestMapping(value="listarUsuarios.htm")
    public ModelAndView listarUsuarios(){
        ModelAndView mav = new ModelAndView();
        String sql = "select * from usuarios";
        List datos = this.jdbcTemplate.queryForList(sql);
        mav.setViewName("views/listarUsuarios");
        mav.addObject("usuario", datos);
        return mav;
    }
    
    /**
     * 
     * @return addUsuario.jsp view taking into account PersonaBean model for the form fields.
     */
    @RequestMapping(value="addUsuario.htm", method=RequestMethod.GET)
    public ModelAndView addUsuario(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("usuario", new PersonaBean());
        mav.setViewName("views/addUsuario");
        return mav;
    }
    
    /**
     * 
     * @param p loads model created based on PersonaBean.
     * @param request takes data from the form once method POST is triggered and use it as a 
     * parameter to verify if the form is MultiPart, to check the path for storing the picture 
     * and saving its path onto the DB.
     * @return view from listarUsuarios.jsp loaded based on PersonaBean model.
     */
    @RequestMapping(value="addUsuario.htm", method=RequestMethod.POST)
    public ModelAndView addUsuario(@ModelAttribute("usuario") PersonaBean p,
        HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        //------------Load img file
        //-----Get file's path
        //-----Check if form has load attribute
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        //-----variable to go through the vector
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
            //Build a temp path to store loaded files
            //This is a relative path to the actual project path
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            //Create dir if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            //Create a list with values taken from the form
            List<FileItem> items = null;
            try{
               items = fileUpload.parseRequest(request);
            } catch (FileUploadException ex){
                System.out.print("Load " + ex.getMessage());
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
                        //Store file's name on disk (tomcat's dir)
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
        System.out.print(list);
        System.out.print(items);
            p.setNombre(list.get(0));
            p.setApellido(list.get(1));
            p.setEdad(Integer.parseInt(list.get(2)));
            p.setCorreo(list.get(3));
        }

        String sql = "insert into usuarios (nombre, apellido, edad, correo, foto, oldfoto) "
                + "values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, p.getNombre(), p.getApellido(), p.getEdad(), p.getCorreo(), p.getFoto(), p.getOldfoto());
        mav.setViewName("redirect:/listarUsuarios.htm");
        return mav;
    }
        
    /**
     * 
     * @param request is used as a parameter which takes the id from the user we want to delete,
     * it also takes the path of the image we need to delete prior to deleting the user from the DB.
     * @return listarUsuarios.jsp view once the image and the user has been deleted from the DB.
     */
    @RequestMapping(value="deleteUsuario.htm")
    public ModelAndView deleteUsuario(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        UsuarioDao userDao = new UsuarioDao();
        int id = Integer.parseInt(request.getParameter("id"));
        //Capture file's path to use it as a parameter so we can delete both user and picture
        String deletePath = request.getServletContext().getRealPath("") + File.separator;
        String foto = request.getParameter("foto");
        //Method to delete user and photo imported from UsuarioDao
        userDao.deletePhoto(foto, deletePath, id);
        mav.setViewName("redirect:/listarUsuarios.htm");
        return mav;
    }
    
    /**
     * 
     * @param request it takes 'id' parameter from listarUsuarios.jsp so we know whcih user we
     * want to modify.
     * @return updateUsuario.jsp view with preloaded information from the user we want to modify, 
     * which is taken from a sql query using the 'id' taken from the request.
     */
    @RequestMapping(value="updateUsuario.htm", method=RequestMethod.GET)
    public ModelAndView updateUsuario(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        //Using the id taken from the previous view, we make a sql query to preload data from the user
        PersonaBean p = consultaUsuarioxId(id);
        mav.addObject("usuario", p);
        mav.setViewName("views/updateUsuario");
        return mav;
    }
    
    /**
     * 
     * @param id in order to preload user data onto updateUsuario.jsp we make a sql query 
     * so we need user id as a parameter to make that query.
     * @return PersonaBean model data taken from the sql query adn send it to the model and view 
     * from updateUsuario(GET) method to preload user data onto the form fields.
     */
    public PersonaBean consultaUsuarioxId(int id){
        PersonaBean p = new PersonaBean();
        String sql = "select * from usuarios where id_usuario = " + id;
        return (PersonaBean)jdbcTemplate.query(sql, new ResultSetExtractor<PersonaBean>()
        {
            public PersonaBean extractData (ResultSet rs) throws SQLException, DataAccessException{
                if(rs.next()){
                    p.setId(rs.getInt("id_usuario"));
                    p.setNombre(rs.getString("nombre"));
                    p.setApellido(rs.getString("apellido"));
                    p.setEdad(rs.getInt("edad"));
                    p.setCorreo(rs.getString("correo"));
                    p.setFoto(rs.getString("foto"));
                }
            return p;
            }
        }
        );
    }
    
    /**
     * 
     * @param p loads model created based on PersonaBean.
     * @param request takes data from the form once method POST is triggered and use it as a 
     * parameter to verify if the form is MultiPart, to check the path for storing new picture, 
     * deleting previous one and updating fields onto the DB.
     * @return view from listarUsuarios.jsp loaded based on PersonaBean model which will reflect 
     * data updated.
     */
    @RequestMapping(value="updateUsuario.htm", method=RequestMethod.POST)
    public ModelAndView updateUsuario(PersonaBean p, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        UsuarioDao userDao = new UsuarioDao();
        ArrayList<String> list = new ArrayList<>();
        //Check if load attribute is configured on form
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        //Check id user file has been modified
        DiskFileItemFactory file = new DiskFileItemFactory();
        //Pass file item as parameter to variable
        ServletFileUpload fileUpload = new ServletFileUpload(file);
        List<FileItem> items = null;
        try{
            items = fileUpload.parseRequest(request);
            for (int i = 0; i < items.size(); i++){
                FileItem fileItem = (FileItem) items.get(i);
                list.add(fileItem.getString());
            }
        } catch (FileUploadException ex){
            System.out.print("Error en carga de imagen homeController/updateUsuario " + ex.getMessage());
        }
        if (list.get(4).isEmpty() || list.get(4).equals("") || list.get(4).equals(null)){
            userDao.updateUserNoPhoto(p, items);
        } else {
            userDao.updateUserWithPhoto(p, isMultiPart, request, items);
        }
        mav.setViewName("redirect:/listarUsuarios.htm");
        return mav;
    }
}
